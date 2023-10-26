package com.trip.dto.user;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.trip.dto.user.QUserSearchResponseDto is a Querydsl Projection type for UserSearchResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserSearchResponseDto extends ConstructorExpression<UserSearchResponseDto> {

    private static final long serialVersionUID = 1693941491L;

    public QUserSearchResponseDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> userName, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<String> phone, com.querydsl.core.types.Expression<String> loginId, com.querydsl.core.types.Expression<String> travelStyleOne, com.querydsl.core.types.Expression<String> travelStyleTwo, com.querydsl.core.types.Expression<String> profileImg, com.querydsl.core.types.Expression<String> nickName, com.querydsl.core.types.Expression<String> age, com.querydsl.core.types.Expression<String> birthDate, com.querydsl.core.types.Expression<com.trip.common.enums.UserRole> role, com.querydsl.core.types.Expression<com.trip.common.enums.UserStatus> userStatus, com.querydsl.core.types.Expression<com.trip.common.enums.Gender> gender, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(UserSearchResponseDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, com.trip.common.enums.UserRole.class, com.trip.common.enums.UserStatus.class, com.trip.common.enums.Gender.class, java.time.LocalDateTime.class}, id, userName, email, phone, loginId, travelStyleOne, travelStyleTwo, profileImg, nickName, age, birthDate, role, userStatus, gender, createdAt);
    }

}

