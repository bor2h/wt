package com.trip.repository.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trip.common.enums.Gender;
import com.trip.common.enums.UserRole;
import com.trip.common.enums.UserStatus;
import com.trip.dto.user.QUserSearchResponseDto;
import com.trip.dto.user.UserSearchCondition;
import com.trip.dto.user.UserSearchResponseDto;

import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.trip.domain.QUser.user;
import static org.springframework.util.ObjectUtils.isEmpty;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression userNameEq(String username) {
        return isEmpty(username) ? null : user.userName.eq(username);
    }

    private BooleanExpression genderEq(Gender gender) {
        return isEmpty(gender) ? null : user.gender.eq(gender);
    }

    private BooleanExpression statusEq(UserStatus status) {
        return isEmpty(status) ? null : user.userStatus.eq(status);
    }

    private BooleanExpression roleEq(UserRole role) {
        return isEmpty(role) ? null : user.role.eq(role);
    }

    private BooleanExpression birthDateBetween(String start, String end) {
        return (isEmpty(start) || isEmpty(end)) ? null : user.birthDate.between(start,end);
    }

    @Override
    public List<UserSearchResponseDto> userSearch(UserSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(new QUserSearchResponseDto(
                        user.id
                        ,user.userName
                        ,user.email
                        ,user.phone
                        ,user.loginId
                        ,user.travelStyleOne
                        ,user.travelStyleTwo
                        ,user.profileImg
                        ,user.nickName
                        ,user.age
                        ,user.birthDate
                        ,user.role
                        ,user.userStatus
                        ,user.gender
                        ,user.createdAt
                ))
                .from(user)
                .where(userNameEq(condition.getUserName()),
                        genderEq(condition.getGender()),
                        statusEq(condition.getUserStatus()),
                        roleEq(condition.getRole()),
                        birthDateBetween(condition.getBirthDateStart(),condition.getBirthDateEnd())
                )
                .orderBy(user.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
