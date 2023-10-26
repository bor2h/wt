package com.trip.common.enums;

import com.trip.common.utils.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContentType implements EnumType {

    IMG("이미지"),
    VIDEO("비디오"),
    VOICE("음성")
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
