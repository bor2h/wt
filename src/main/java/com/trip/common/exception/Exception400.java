package com.trip.common.exception;

import com.trip.common.utils.ValidDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class Exception400 extends RuntimeException {

    private String key;
    private String value;

    public Exception400(String key, String value) {
        super(value);
        this.key = key;
        this.value = value;
    }

    public ResponseEntity<?> body(){
        ValidDto validDTO = new ValidDto(key,value);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validDTO);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}