package com.trip.service;

import com.trip.common.enums.UserStatus;
import com.trip.common.exception.Exception401;
import com.trip.common.exception.Exception404;
import com.trip.common.security.JwtTokenProvider;
import com.trip.domain.User;
import com.trip.dto.user.UserDto;
import com.trip.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtService {


    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;


    /**
     * login > jwt access token 발급
     */
    public UserDto.LoginResponse jwtLogin (UserDto.LoginRequest request, HttpServletResponse response){

        String loginId = request.getLoginId();
        String password = request.getPassword();

        User foundUser = userRepository.findByLoginId(loginId).orElseThrow(()-> new Exception404("존재하지 않는 사용자 입니다."));

        if (!UserStatus.ACTIVE.equals(foundUser.getUserStatus())){
            throw new Exception401("사용할 수 없는 계정입니다.");
        }

        // 입력한 비밀번호와 DB 의 비밀번호 match
        if(!passwordEncoder.matches(password,foundUser.getPassword())){
            throw new Exception401("비밀번호가 일치하지 않습니다.");
        }

        // 엑세스 토큰 생성
        String jwtAccessToken = jwtTokenProvider.createAccessToken(foundUser);

        // Refresh 토큰 생성
        String jwtRefreshToken = jwtTokenProvider.createRefreshToken(foundUser);

        log.info("jwtAccessToken: " + jwtAccessToken); // JWT 토큰 출력
        log.info("jwtRefreshToken: " + jwtRefreshToken); // JWT 토큰 출력

        // 쿠키에 토큰 정보를 설정
        Cookie accessTokenCookie = new Cookie("accessToken", jwtAccessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(24 * 60 * 60); // 토큰의 유효 기간 설정 (예: 24시간)
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", jwtRefreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 토큰의 유효 기간 설정 (예: 7일)
        response.addCookie(refreshTokenCookie);

        return UserDto.LoginResponse.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }
}
