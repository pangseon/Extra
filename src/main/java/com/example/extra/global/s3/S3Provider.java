package com.example.extra.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Provider {
    private final AmazonS3 amazonS3;
    public static final String SEPARATOR = "/";
    @Value("${cloud.aws.s3.bucket.name}")
    public String bucket;
    public final String url = "https://extra-file.s3.ap-northeast-2.amazonaws.com/";

    public void createFolder(String folderName) {
        if (!amazonS3.doesObjectExist(bucket, folderName)) {
            amazonS3.putObject(
                bucket,
                folderName + SEPARATOR,
                new ByteArrayInputStream(new byte[0]),
                new ObjectMetadata());
        }
    }
    public String originalFileName(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return "";
        }
        if (Objects.equals(multipartFile.getContentType(), "image/png")
            || Objects.equals(multipartFile.getContentType(), "image/jpeg")) {
            String fileType = switch (multipartFile.getContentType()) {
                case "image/png" -> ".png";
                case "image/jpeg" -> ".jpg";
                default -> throw new IllegalStateException(
                    "Unexpected value: " + multipartFile.getContentType());
            };
            return UUID.randomUUID() + fileType;
        } else {
            throw new IllegalArgumentException("잘못된 파일 형식입니다");
        }
    }
    private static ObjectMetadata setObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        return metadata;
    }
    public String saveFile(MultipartFile multipartFile, String imageName) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        ObjectMetadata metadata = setObjectMetadata(multipartFile);
        amazonS3.putObject(bucket, imageName, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, imageName).toString();
    }

}
