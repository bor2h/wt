package com.trip.dto;


import com.trip.common.enums.FileType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AttachFileDto {

    @Schema(name = "[요청 DTO] 파일 저장")
    @AllArgsConstructor
    @Data
    public static class AttachSaveRequest {

        @NotBlank
        @Schema(description = "참조 pk" , example = "2ccc806c-6787-4964-8d7b-22ee3c295d7e")
        private String refId;

        @NotNull
        @Schema(description = "fileType" , example = "SCHEDULE")
        private FileType fileType;

        @NotNull
        @Schema(description = "파일")
        private MultipartFile file;

    }

    @Schema(name = "[응답 DTO] 파일 정보")
    @AllArgsConstructor
    @Data
    @Builder
    public static class AttachFileInfo {
        @Schema(description = "파일 pk" , example = "1")
        private Long fileNo;

        @Schema(description = "파일 용량" , example = "127409")
        private Long fileSize;

        @Schema(description = "파일 참조 Table pk" , example = "2ccc806c-6787-4964-8d7b-22ee3c295d7e")
        private String refId;

        @Schema(description = "fileType (USER , GUIDE ,SCHEDULE)" , example = "SCHEDULE")
        private FileType fileType;

        @Schema(description = "파일 저장 경로" , example = "/data/SCHEDULE/2ccc806c-6787-4964-8d7b-22ee3c295d7e/1eccacbb-e527-47d1-98bb-9df3186aeea2")
        private String uploadPath;

        @Schema(description = "파일 유형" , example = "image/png")
        private String contentType;

        @Schema(description = "파일이름" , example = "guide.png")
        private String fileName;

    }



}
