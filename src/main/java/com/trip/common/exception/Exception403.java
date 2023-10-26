package com.trip.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


// 권한 없음
@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ResponseEntity<?> body(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }
}