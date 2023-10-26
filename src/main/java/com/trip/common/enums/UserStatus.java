package com.trip.common.enums;

import com.trip.common.utils.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 계정 상태
 */
@Getter
@RequiredArgsConstructor
public enum UserStatus implements EnumType {

    ACTIVE("활성"),
    INACTIVE("비활성화"),
    //DELETED("탈퇴")
    ;

    private final String text;

    @Override
    public String getId() {
        return this.name();
    }

    @Override
    public String getText() {
        return this.text;
    }
}
