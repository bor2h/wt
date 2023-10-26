package com.trip.common.enums;

import com.trip.common.utils.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public enum FileType implements EnumType {
    USER("USER"),
    GUIDE("GUIDE"),
    SCHEDULE("SCHEDULE"),
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
