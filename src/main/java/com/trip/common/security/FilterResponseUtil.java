package com.trip.common.security;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterResponseUtil {
    public static void unAuthorized(HttpServletRequest request,HttpServletResponse response, Exception e)  {
        response.setHeader("RESP_CODE","E40001");
        response.setHeader("RESP_MSG","unAuthorized");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    public static void forbidden(HttpServletRequest request,HttpServletResponse response, Exception e) {
        response.setHeader("RESP_CODE","E40301");
        response.setHeader("RESP_MSG","forbidden");
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
