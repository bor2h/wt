package com.trip.common.security;



import com.trip.domain.User;
import com.trip.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    // JWT 토큰 생성, 토큰 유효성 검증 기능이 구현된 클래스.

    private final UserService userService;

    @Value("${jwt.secret}") // application.yml에 있는 변수값을 읽어오기위해사용한다
    private String secret; // application.yml에 있던 jwt.secret 값을 secret 변수에 저장한다.

    @Value("${jwt.accessToken.expiration}")
    private int accessTokenExpiration;

    @Value("${jwt.refreshToken.expiration}")
    private int refreshTokenExpiration;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.audience}")
    private String audience;

    public JwtTokenProvider(UserService userService,
                            @Value("${jwt.secret}") String secret) {
        this.userService = userService;
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    // 로그인 서비스 할 때 같이 사용합니다. 엑세스 토큰 생성
    // header, payload, Signature 세 부분으로 구성
    public String createAccessToken(User user) {

        Date now = new Date();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,secret.getBytes()) // 키와, 알고리즘을 넣는다.
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + accessTokenExpiration )) // 현재시간기준으로 만료시간을 잡아야하니까 현재시간에 만료시간을 넣어준다.
                .setIssuedAt(new Date()) // 토큰 발행 시간 정보
                .setIssuer(issuer) // 발급자
                .setAudience(audience) // 대상자
                .setSubject(String.valueOf(user.getId())) // 제목
                .claim("loginId",user.getLoginId()) // JWT payload에 저장되는 정보단위
                .claim("role",user.getRole())
                .claim("email",user.getEmail())
                .compact(); // 위 설정대로 JWT 토큰을 생성한다는 의미.
    }

    // 로그인 서비스 할 때 같이 사용합니다.
    // 리프레시 토큰 생성 -- 엑세스토큰으로 요청을 먼저 -- 탈취에 취약함 -> 엑세스만 사용하다가 만료되면 리프레쉬토큰으로 다시 엑세스토큰을 발급 받아 사용
    public String createRefreshToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + refreshTokenExpiration ))
                .setIssuedAt(new Date()) // 토큰 발행 시간 정보
                .setIssuer(issuer) // 발급자
                .setAudience(audience) // 대상자
                .setSubject(String.valueOf(user.getId())) // 제목
                .claim("loginId",user.getLoginId()) // JWT payload에 저장되는 정보단위
                .claim("role",user.getRole())
                .claim("email",user.getEmail())
                .compact(); // 위 설정대로 JWT 토큰을 생성한다는 의미.
    }



    public boolean validateToken(String token) {
        try {
            log.info("토큰 검증로직 작동");
            Jwts.parserBuilder()// jwt 파서 생성 이 파서를 사용해서 토큰의 서명을 검증한다.
                    .setSigningKey(Base64.getDecoder().decode(secret)) // 사용된 암호화 알고리즘과 동일한 secret 값을 사용하여 서명 검증
                    .build() // jwt 파서 빌드
                    .parseClaimsJws(token); // 주어진 토큰을 검증한다.
            log.info("토큰 검증 성공");
            return true;
        } catch (ExpiredJwtException ex) {
            // 토큰이 만료되었다
            log.error("토큰이 만료 되었습니다", ex.getMessage());
        } catch (JwtException | IllegalArgumentException ex) {
            // 토큰 유효성 검증 실패
            log.error("유효하지 않은 토큰 입니다.", ex.getMessage());
        }
        return false;
    }

}

