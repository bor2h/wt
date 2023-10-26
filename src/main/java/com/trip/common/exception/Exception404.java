package com.trip.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


// 권한 없음
@Getter
public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public ResponseEntity<?> body(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}