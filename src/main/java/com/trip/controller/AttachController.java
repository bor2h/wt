package com.trip.controller;

import com.trip.common.enums.FileType;
import com.trip.common.security.CustomUser;
import com.trip.domain.AttachFile;
import com.trip.dto.AttachFileDto;
import com.trip.service.AttachFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "첨부파일 API", description = "첨부파일 관리")
@RequestMapping("/attach")
@RequiredArgsConstructor
@RestController
public class AttachController {

    private final AttachFileService attachFileService;

    @Operation(summary = "파일 업로드 (대량)"
            , description = """
            * 첨부파일 업로드 (다건)
            * ※주의사항※  
            * ```refId``` 를 제대로 입력해주세요  
            """)
    @PostMapping(value = "/{refId}/{fileType}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AttachFileDto.AttachFileInfo>> fileDataUpload(@AuthenticationPrincipal CustomUser securityUser,
                                               @PathVariable String refId,
                                               @PathVariable FileType fileType,
                                               @RequestPart(value = "files") MultipartFile[] files
                                        ) {
        return ResponseEntity.ok(attachFileService.upload(refId, fileType,files));
    }

    @Operation(summary = "파일 업로드 (개별)"
            , description = """
           *  첨부파일 업로드 (개별)   
           * ※주의사항※  
           * ```refId``` 를 제대로 입력해주세요    
            """)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AttachFileDto.AttachFileInfo> uploadFile(@AuthenticationPrincipal CustomUser securityUser,@Valid @ModelAttribute AttachFileDto.AttachSaveRequest request) {
        return ResponseEntity.ok(attachFileService.fileUpload(request.getRefId(), request.getFileType(),request.getFile()));
    }

    @Operation(summary = "파일 다운로드" ,description = "첨부파일 다운로드")
    @GetMapping(value = "/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadFile(@PathVariable Long fileId) {
        return attachFileService.download(fileId);
    }

    @Operation(summary = "파일 개별 삭제", description = "첨부파일 개별 삭제")
    @DeleteMapping(value = "/{fileId}")
    public ResponseEntity<Void> deleteFileById(@AuthenticationPrincipal CustomUser securityUser,@PathVariable Long fileId) {
        attachFileService.deleteFileById(fileId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "파일 전체 삭제", description = "첨부파일 전체 삭제")
    @DeleteMapping(value = "/{refId}/{fileType}")
    public ResponseEntity<Void> deleteFile(@AuthenticationPrincipal CustomUser securityUser
                                        ,@PathVariable String refId
                                        ,@PathVariable FileType fileType) {
        attachFileService.deleteFile(refId, fileType.getText());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "파일 정보 조회 (개별)" ,description = "개별 파일 정보 조회")
    @GetMapping(value = "/find/{fileNo}")
    public ResponseEntity<AttachFileDto.AttachFileInfo> findByFileNo(@PathVariable Long fileNo) {
        return ResponseEntity.ok(attachFileService.findByFileNo(fileNo));
    }


    @Operation(summary = "파일 정보 리스트 조회" ,description = "파일 정보 리스트  조회")
    @GetMapping(value = "/find/all")
    public ResponseEntity<List<AttachFileDto.AttachFileInfo>> findAllFile() {
        return ResponseEntity.ok(attachFileService.findAllFile());
    }

}