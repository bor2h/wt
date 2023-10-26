package com.trip.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trip.common.enums.GuideStatus;
import com.trip.common.security.CustomUser;
import com.trip.domain.Guide;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class GuideDTO {


    @Schema(name = "[요청 DTO] 가이드 등록")
    @Data
    @AllArgsConstructor
    public static class GuideCreateRequest {

        @Schema(description = "사진")
        private MultipartFile file;

        @NotBlank(message = "자신을 소개할 내용 을(를) 입력해주세요.")
        @Schema(description = "자신을 소개할 내용" ,example = "자신을 소개할 내용을 입력해주세요")
        private String content;

        public Guide toEntity(CustomUser customUser){
            return Guide.builder()
                    .content(content)
                    .isPass(GuideStatus.N)
                    .userNo(customUser.getUserNo())
                    .build();
        }
    }

    @Schema(name = "[응답 DTO] 가이드 조회")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GuideResponse {

        @Schema(description = "Guide pk",example = "1")
        private Long id;

        @Schema(description = "user pk",example = "1")
        private Long userNo;

        @Schema(description = "조회수",example = "1")
        private Long viewCount;

        @Schema(description = "좋아요수",example = "0")
        private Long likeCount;

        @Schema(description = "사진",example = "2")
        private String profileImg;

        @Schema(description = "자신을 소개할 내용" ,example = "자신을 소개할 내용을 입력해주세요")
        private String content;

        @Schema(description = "검수 완료 (Y,N)",example = "Y")
        private GuideStatus isPass;

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "생성일시" ,example = "yyyy-MM-dd hh:mm:ss")
        private LocalDateTime createdAt;

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "수정일시" ,example = "yyyy-MM-dd hh:mm:ss")
        private LocalDateTime updatedAt;
    }

}
