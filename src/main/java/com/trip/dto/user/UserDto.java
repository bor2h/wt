package com.trip.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trip.common.enums.*;
import com.trip.common.utils.CommonEncoder;
import com.trip.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


public class UserDto {

    @Schema(name = "[요청 DTO] 회원가입",description = "(User 정보 저장)")
    @AllArgsConstructor
    @Data
    public static class UserSaveRequest {

        @NotBlank(message = "이름 (을)를 입력해주세요.")
        @Schema(description = "이름" , example = "홍길동")
        private String userName;

        @NotBlank(message = "아이디 (을)를 입력해주세요.")
        @Schema(description = "회원 아이디" , example = "hong")
        private String loginId;

        @NotBlank(message = "이메일 주소 (을)를 입력해주세요.")
        @Email(message = "올바른 이메일 주소 (을)를 입력해주세요.")
        @Schema(description = "이메일" ,example = "hong@naver.com")
        private String email;

        @NotBlank(message = "비밀번호 (을)를 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 15자 이하로 입력해주세요.")
        @Schema(description = "비밀번호" ,example = "hong12345!")
        private String password;

        @NotBlank(message = "여행스타일 (즉흥적,계획적,모르겠어요) (을)를 입력해주세요.")
        @Schema(description = "여행스타일 (즉흥적,계획적,모르겠어요)" ,example = "즉흥적")
        private String travelStyleOne;

        @NotBlank(message = "여행스타일 (예술적인,활동적인,모르겠어요) (을)를 입력해주세요.")
        @Schema(description = "여행스타일 (예술적인,활동적인,모르겠어요)" ,example = "예술적인")
        private String travelStyleTwo;

        @Schema(description = "닉네임" ,example = "홍")
        @NotBlank
        private String nickName;

        @Schema(description = "나이" ,example = "31")
        @NotBlank
        private String age;

        @NotBlank(message = "휴대폰 번호 (을)를 입력해주세요.")
        @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
        @Schema(description = "휴대폰 번호" ,example = "01012345678")
        private String phone;

        @NotBlank(message = "생년월일 (을)를 입력해주세요.")
        @Schema(description = "생년월일 (YYYYYMMDD)" ,example = "19930101")
        private String birthDate;

        @NotNull
        @Schema(description = "성별" ,example = "MALE")
        private Gender gender;

        @NotNull
        @Schema(description = "계정 권한 (ADMIN, GUEST)" ,example = "ADMIN")
        private UserRole role;

        @Schema(description = "상태 (ACTIVE, INACTIVE)", example = "ACTIVE")
        @NotNull(message = "userStatus")
        private UserStatus userStatus;

        @Schema(description = "프로필 사진")
        private MultipartFile file;
        public User toEntity() {

            return User.builder()
                    .userName(this.userName)
                    .email(this.email)
                    .age(this.age)
                    .nickName(this.nickName)
                    .travelStyleOne(this.travelStyleOne)
                    .travelStyleTwo(this.travelStyleTwo)
                    .password(new CommonEncoder().encode(password))
                    .phone(this.phone)
                    .loginId(this.loginId)
                    .gender(this.gender)
                    .birthDate(this.birthDate)
                    .role(this.role)
                    .userStatus(this.userStatus)
                    .build();
        }
    }



    @Schema(name = "[요청 DTO] 로그인")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginRequest {

        @NotBlank(message = "로그인 ID 을(를) 입력해주세요.")
        @Schema(description = "로그인 ID" ,example = "hong")
        private String loginId;

        @NotBlank(message = "비밀번호 을(를) 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 15자 이하로 입력해주세요.")
        @Schema(description = "비밀번호" ,example = "hong12345!")
        private String password;
    }



    @Schema(name = "[응답 DTO] 로그인")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {

        @Schema(description = "accessToken" ,example = "accessToken")
        private String accessToken;

        @Schema(description = "refreshToken" ,example = "refreshToken")
        private String refreshToken;
    }

    @Schema(name = "[요청 DTO] 비밀번호 변경")
    @Getter
    @NoArgsConstructor
    public static class ChangePasswordRequest {

        @NotBlank(message = "로그인 ID 을(를) 입력해주세요.")
        @Schema(description = "로그인 ID" ,example = "hong")
        private String loginId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하로 입력해주세요.")
        @Schema(description = "변경 후 비밀번호" ,example = "hong09876!")
        private String passwordAfter;

        @Schema(description = "변경 전 비밀번호" ,example = "hong12345!")
        private String passwordBefore;
    }

    @Schema(name = "[응답 DTO] 사용자 정보")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserInfoDto {

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

        @Schema(description = "여행스타일 (즉흥적,계획적,모르겠어요)" ,example = "즉흥적")
        private String travelStyleOne;

        @Schema(description = "여행스타일 (예술적인,활동적인,모르겠어요)" ,example = "예술적인")
        private String travelStyleTwo;

        @Schema(description = "프로필 사진" ,example = "1")
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
    }

    @Schema(name = "[응답 DTO] 사용자 검색")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserSearchResponse {

        private Long id;

        @Schema(description = "이름" , example = "홍길동")
        private String userName;

        @Schema(description = "이메일" ,example = "hong@naver.com")
        private String email;

        @Schema(description = "성별" ,example = "MALE")
        private Gender gender;

        @Schema(description = "휴대폰 번호" ,example = "01012345678")
        private String phone;

        @Schema(description = "생년월일 (YYYYYMMDD)" ,example = "19930101")
        private String birthDate;

        @Schema(description = "계정 권한" ,example = "ADMIN")
        private UserRole userRole;

        @Schema(description = "계정 상태 (ACTIVE , INACTIVE)" ,example = "ACTIVE")
        private UserStatus userStatus;

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FindPasswordUserDto {

        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        @Schema(description = "이메일" ,example = "hong@naver.com")
        private String email;

        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
        @Schema(description = "휴대폰 번호" ,example = "01012345678")
        private String phone;
        public static FindPasswordUserDto of(User user) {
            return FindPasswordUserDto.builder()
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .build();
        }
    }

}
