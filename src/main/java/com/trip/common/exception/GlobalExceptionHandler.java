package com.trip.common.exception;

import com.trip.common.annotation.MyErrorLog;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers, HttpStatus status,
                                                          WebRequest request) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField)
                .collect(Collectors.toList());

        String plainMsg = String.join(",",errorMessages);
        headers.set("RESP_CODE", "E400");
        headers.set("RESP_MSG", plainMsg);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers).build();
    }


    @ExceptionHandler(value = {ExpiredJwtException.class, UnsupportedJwtException.class, MalformedJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException e, WebRequest request) {
        //String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        String RESP_CODE = ((ServletWebRequest)request).getRequest().getAttribute("RESP_CODE").toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("RESP_CODE", RESP_CODE);
        headers.set("RESP_MSG", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .headers(headers).build();
    }


    // 인증 실패 처리
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("RESP_CODE", "E401");
        headers.set("RESP_MSG", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .headers(headers).build();
    }

    // 권한 실패 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("RESP_CODE", "E403");
        headers.set("RESP_MSG", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .headers(headers).build();
    }

    /**
     * handlerOtherExceptions handles any unhandled exceptions.
     */
    @MyErrorLog
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e, WebRequest request) {

        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        log.warn("Error Message : [{}] , Request Uri : [{}]",e.getMessage(),requestUri);

        HttpHeaders headers = new HttpHeaders();
        headers.set("RESP_CODE", "E500");
        headers.set("RESP_MSG", "unknownServerError");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers).build();
    }

    @MyErrorLog
    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e) {
        return e.body();
    }

    @MyErrorLog
    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e) {
        return e.body();
    }

    @MyErrorLog
    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e) {
        return e.body();
    }

    @MyErrorLog
    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e) {
        return e.body();
    }

    @MyErrorLog
    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e) {
        return e.body();
    }

}
