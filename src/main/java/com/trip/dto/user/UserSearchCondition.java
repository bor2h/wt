package com.trip.dto.user;

import com.trip.common.enums.Gender;
import com.trip.common.enums.UserRole;
import com.trip.common.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "[요청 DTO] 사용자 검색 조건")
@AllArgsConstructor
@Data
public class UserSearchCondition {

    @Schema(description = "이름" , example = "홍길동")
    private String userName;

    @Schema(description = "성별" ,example = "MALE")
    private Gender gender;

    @Schema(description = "계정 상태" , example = "ACTIVE")
    private UserStatus userStatus;

    @Schema(description = "계정 권한" ,example = "ADMIN")
    private UserRole role;

    @Schema(description = "생년월일 시작(YYYYYMMDD)" ,example = "19810601")
    private String birthDateStart;

    @Schema(description = "생년월일 끝(YYYYYMMDD)" ,example = "19810601")
    private String birthDateEnd;
}
