package com.example.extra.domain.costumeapprovalboard.CostumeApprovalBoard;

import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/costumeapprovalboards")
public class CostumeApprovalBoardController {

    private final CostumeApprovalBoardService costumeApprovalBoardService;
}
