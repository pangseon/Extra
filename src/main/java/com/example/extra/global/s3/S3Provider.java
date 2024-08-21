package com.example.extra.global.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardErrorCode;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Provider {
    private final AmazonS3 amazonS3;
    private static final String SEPARATOR = "/";
    private static final String PROFILE_IMAGE_NAME = "profile.jpg";
    // 기본 이미지는 노션 써서 호스팅 해놓음
    private static final String DEFAULT_PROFILE_URL =
        "https://hyeneung-masaru.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fb714748b-dc50-4402-91d7-3a9c74581f7c%2F83960662-338b-41a1-90d6-f7f8b0663781%2Fdefault_profile.jpg?table=block&id=debc7caa-344f-4031-ab60-7d91b0c8f515&spaceId=b714748b-dc50-4402-91d7-3a9c74581f7c&width=2000&userId=&cache=v2";
    private static final long VALID_TIME = 30 * 60 * 1000; // 30 분
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${cloud.aws.s3.url}")
    public String url;

    // 회원가입 시 폴더 생성(API 테스트 완료)
    public void createFolder(String folderName) {
        if (!amazonS3.doesObjectExist(bucket, folderName)) {
            amazonS3.putObject(
                bucket,
                folderName + SEPARATOR,
                new ByteArrayInputStream(new byte[0]),
                new ObjectMetadata());
        }
    }
    // 출연자 프로필 사진 삭제(API 테스트 완료)
    public void deleteProfileImage(String presignedUrl) {
        try {
            // URL에서 객체 키 추출
            String objectKey = extractObjectKey(presignedUrl);

            // DeleteObjectRequest 객체를 생성하여 삭제 요청
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, objectKey);
            amazonS3.deleteObject(deleteObjectRequest);
        } catch (IllegalArgumentException e) {
            // 잘못된 presigned URL에 대한 예외 처리
            log.error("Error: " + e.getMessage());
        } catch (Exception e) {
            // S3 객체 삭제 중 발생한 예외 처리
            log.error("Error deleting object from S3: " + e.getMessage());
        }
    }
    // 출연자 프로필 사진 수정(API 테스트 완료)
    public void updateProfileImage(String presignedUrl, Long accountId, MultipartFile multipartFile){
        String objectKey;
        if(Objects.equals(presignedUrl, DEFAULT_PROFILE_URL)){
            String folderName = accountId.toString();
            objectKey = folderName + SEPARATOR + PROFILE_IMAGE_NAME;
        }
        else{
            objectKey = extractObjectKey(presignedUrl);
        }
        saveFile(multipartFile, objectKey);
    }
    // 출연자 의상 사진 저장(API 테스트 완료)
    public void saveCostumeApprovalBoardImage(
        String parentFolder,
        String childFolder,
        MultipartFile multipartFile
    ){
        String fileName = getFileNameWithExtension(
            multipartFile,
            childFolder + "_" + UUID.randomUUID()
        );
        String ObjectKey = parentFolder + SEPARATOR + childFolder + SEPARATOR + fileName;
        saveFile(multipartFile, ObjectKey);
    }
    // 출연자 의상 사진 수정(API 테스트 완료)
    public void updateCostumeApprovalBoardImage(String presignedUrl, MultipartFile multipartFile){
        saveFile(multipartFile, extractObjectKey(presignedUrl));
    }

    // 프로필 사진 프론트에 전달(API 테스트 완료)
    public String getProfileImagePresignedUrl(Long accountId){
        String folderName = accountId.toString();
        String objectKey = folderName + SEPARATOR + PROFILE_IMAGE_NAME;
        return getPresignedUrl(objectKey, HttpMethod.GET);
    }
    public String getCostumeImagePresignedPutUrlForCostumeImage(Long accountId, Long costumeApprovalBoardId){
        String newImageName = "costume.jpg";
        String objectKey = accountId.toString() + SEPARATOR + costumeApprovalBoardId.toString() + SEPARATOR + newImageName;
        return getPresignedUrl(objectKey, HttpMethod.PUT);
    }
    public String getCostumeImagePresignedPutUrlForProfile(Long accountId){
        String objectKey = accountId.toString() + SEPARATOR + PROFILE_IMAGE_NAME;
        return getPresignedUrl(objectKey, HttpMethod.PUT);
    }
    // 의상 사진 프론트에 전달(API 테스트 완료)
    public String getCostumeImagePresignedUrl(Long accountId, Long costumeApprovalBoardId) {
        // 폴더 경로 생성
        String folderName = accountId.toString() + SEPARATOR + costumeApprovalBoardId.toString() + SEPARATOR;
        // 폴더 내 파일 목록을 가져오기 위한 요청 생성
        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
            .withBucketName(bucket)
            .withPrefix(folderName)
            .withDelimiter(SEPARATOR);

        // S3에서 파일 목록 가져오기
        ListObjectsV2Result listObjectsResult = amazonS3.listObjectsV2(listObjectsRequest);
        List<S3ObjectSummary> objectSummaries = listObjectsResult.getObjectSummaries();

        // 폴더 내에 파일이 없는 경우 예외 처리
        if (objectSummaries.isEmpty()) {
            throw new CostumeApprovalBoardException(CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_IMAGE);
        }

        // 첫 번째 파일을 선택
        S3ObjectSummary firstObject = objectSummaries.get(0);
        String objectKey = firstObject.getKey();
        // aws s3 콘솔에서 직접 만들면 첫 번째 파일은 폴더. 두 번째 파일부터 이미지가 됨.
        if (objectKey.equals(folderName)){
            objectKey = objectSummaries.get(1).getKey();
        }
        log.info(objectKey);
        return getPresignedUrl(objectKey, HttpMethod.GET);
    }
    private String getPresignedUrl(String objectKey, HttpMethod httpMethod){
        // Presigned URL 유효 기간 설정
        Date expiration = new Date(System.currentTimeMillis() + VALID_TIME);

        // Presigned URL 생성 요청
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucket, objectKey)
                .withMethod(httpMethod)
                .withExpiration(expiration);

        // Presigned URL 생성 및 반환
        URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        if (Objects.isNull(presignedUrl)){
            return null;
        }
        return presignedUrl.toString();
    }

    private String getFileNameWithExtension(MultipartFile multipartFile, String fileName) {
        if (ObjectUtils.isEmpty(multipartFile)) {
            return null;
        }
        String fileType = switch (multipartFile.getContentType()) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            default -> ""; // controller 단에서 validation 완료함
        };
        return fileName + fileType;
    }

    private static ObjectMetadata setObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        return metadata;
    }

    private void saveFile(
        MultipartFile multipartFile,
        String objectKey // S3 Object key. {accountId}/{costumeApprovalBoardId}/{Random}.jpg
    ) {
        try{
            ObjectMetadata metadata = setObjectMetadata(multipartFile);
            amazonS3.putObject(bucket,objectKey, multipartFile.getInputStream(), metadata);
        } catch (IOException e){
            log.error("Error saving image to S3 : {}", e.getMessage());
        }
    }

    /**
     * 주어진 Presigned URL에서 S3 객체 키를 추출함
     * 입력 : https://your-bucket-name.s3.amazonaws.com/path/to/your/object.jpg
     * 출력 : path/to/your/object.jpg
     */
    public static String extractObjectKey(String presignedUrl) {
        try {
            URL url = new URL(presignedUrl);
            String path = url.getPath();
            return path.startsWith("/") ? path.substring(1) : path;

        } catch (MalformedURLException e) {
            // URL이 잘못되었을 경우 예외 발생
            throw new IllegalArgumentException("Invalid presigned URL", e);
        }
    }

}
