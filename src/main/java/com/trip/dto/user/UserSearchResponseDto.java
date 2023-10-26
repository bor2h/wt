package com.trip.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import com.trip.common.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


@Schema(name = "[응답 DTO] 사용자 검색 리스트")
@Data
public class UserSearchResponseDto {

    @Schema(description = "id" , example = "1")
    private Long id;

    @Schema(description = "이름" , example = "홍길동")
    private String userName;

    @Schema(description = "이메일" ,example = "hong@naver.com")
    private String email;

    @Schema(description = "휴대폰 번호" ,example = "01012345678")
    private String phone;

    @Schema(description = "로그인 ID" ,example = "hong")
    private String loginId;

    @Schema(description = "여행스타일 1 (즉흥적,계획적,모르겠어요)" ,example = "즉흥적")
    private String travelStyleOne;

    @Schema(description = "여행스타일 2 (예술적인,활동적인,모르겠어요)" ,example = "예술적인")
    private String travelStyleTwo;

    @Schema(description = "프로필 사진 (fileNo)" ,example = "1")
    private String profileImg;

    @Schema(description = "닉네임" ,example = "홍")
    private String nickName;

    @Schema(description = "나이" ,example = "31")
    private String age;

    @Schema(description = "생년월일 (YYYYYMMDD)" ,example = "19930101")
    private String birthDate;

    @Schema(description = "계정 권한" ,example = "ADMIN")
    private UserRole role;

    @Schema(description = "계정 상태" ,example = "ACTIVE")
    private UserStatus userStatus;

    @Schema(description = "성별" ,example = "MALE")
    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @Schema(description = "생성일시" ,example = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;

    @QueryProjection

    public UserSearchResponseDto(Long id, String userName, String email, String phone, String loginId, String travelStyleOne, String travelStyleTwo, String profileImg, String nickName, String age, String birthDate, UserRole role, UserStatus userStatus, Gender gender, LocalDateTime createdAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.loginId = loginId;
        this.travelStyleOne = travelStyleOne;
        this.travelStyleTwo = travelStyleTwo;
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.age = age;
        this.birthDate = birthDate;
        this.role = role;
        this.userStatus = userStatus;
        this.gender = gender;
        this.createdAt = createdAt;
    }
}
