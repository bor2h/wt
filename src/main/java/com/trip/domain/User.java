package com.trip.domain;


import com.trip.common.enums.*;
import javax.persistence.*;

import com.trip.dto.user.UserDto;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_tb")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(100) comment '로그인 아이디'",unique = true)
    private String loginId;

    @Column(columnDefinition = "varchar(100) comment '이메일 '",unique = true)
    private String email;

    @Column(columnDefinition = "varchar(100) comment '이름'")
    private String userName;

    @Column(columnDefinition = "varchar(255) comment '비밀번호'")
    private String password;

    @Column(columnDefinition = "varchar(255) comment '프로필 사진'")
    private String profileImg;

    @Column(columnDefinition = "varchar(255) comment '닉네임'")
    private String nickName;
    
    @Column(columnDefinition = "varchar(100) comment '성향 (여행 스타일)'")
    private String travelStyleOne;
    @Column(columnDefinition = "varchar(100) comment '성향 (여행 스타일)'")
    private String travelStyleTwo;

    @Column(columnDefinition = "varchar(50) comment '휴대폰 번호 01012345678'")
    private String phone;

    @Column(columnDefinition = "varchar(50) comment '나이'")
    private String age;

    @Column(columnDefinition = "varchar(100) null comment '생년월일 YYYYYMMDD'")
    private String birthDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) null comment '성별'")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(100) comment '계정 상태'")
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(100) comment '계정 권한'")
    private UserRole role;

    public void updateUserRole(UserRole role) {
        this.role = role;
    }

//    public void delete() {
//        this.userStatus = UserStatus.DELETED;
//    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void isBan() {
        this.userStatus = UserStatus.INACTIVE;
    }


    public UserDto.UserInfoDto toDto() {
        return UserDto.UserInfoDto.builder()
                .id(id)
                .loginId(loginId)
                .age(age)
                .nickName(nickName)
                .profileImg(profileImg)
                .userName(userName)
                .email(email)
                .role(role)
                .gender(gender)
                .birthDate(birthDate)
                .userStatus(userStatus)
                .phone(phone)
                .travelStyleOne(travelStyleOne)
                .travelStyleTwo(travelStyleTwo)
                .createdAt(getCreatedAt())
                .build();
    }

}
