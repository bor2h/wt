package com.trip.common.enums;


import com.trip.common.utils.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole implements EnumType {

    ADMIN("어드민"),
   // GUIDE("가이드"),
    GUEST("일반사용자"),
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

