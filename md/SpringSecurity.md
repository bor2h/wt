## Spring Security

- SecurityConfig.java 의 PERMIT_URL_ARRAY 에 jwt 토큰 없이 요청 가능한 URL 을 지정할 수 있습니다.
```java
package com.trip.common.config;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // Spring Security 인증 없이 호출 가능한 URL
    private static final String[] PERMIT_URL_ARRAY = {
            "/h2-console/**",           // h2 console
            "/favicon.ico",             // 파비콘
            "/error",                   // Error
            "/swagger-ui/**",           // Swagger
            "/swagger-resources/**",    // Swagger
            "/v3/api-docs/**",          // Swagger
            "/users/login/**",          // 로그인
            "/users/register/**",       // 회원가입
            "/users/user-loginId/**",   // 로그인 아이디 중복 체크
            "/users/user-emails/**",    // 이메일 중복 체크
            "/schedule/uuid/**",        // 일정 등록 > Random UUID 생성
    };
    
    // ----------------- 생략 ----------------- 
}
```

- src/main/java/com/trip/common/security 폴더에 Security 관련 class 가 있습니다.