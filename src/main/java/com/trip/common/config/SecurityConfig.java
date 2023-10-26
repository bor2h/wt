package com.trip.common.config;

import com.trip.common.exception.Exception401;
import com.trip.common.exception.Exception403;
import com.trip.common.security.FilterResponseUtil;
import com.trip.common.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CorsConfig corsConfig;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

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


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(PERMIT_URL_ARRAY);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // JWT 필터 등록이 필요함
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {

            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            // 9. CORS 설정
            builder.addFilter(corsConfig.corsFilter());
            // 시큐리티 관련 필터
            builder.addFilterBefore(new JwtRequestFilter(authenticationManager, exceptionResolver), BasicAuthenticationFilter.class);

            super.configure(builder);
        }
    }

    /*
        BCryptPasswordEncoder: Spring Security 에서 제공하는 비밀번호 암호화 객체
        service 에서 비밀번호를 암호화,Match  할수 있도록 bean 으로 등록한후 CommonEncoder class 를 util 로 사용
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. CSRF 해제
        http.csrf().disable();

        // 2. jSessionId 사용 거부 (STATELESS 로 설정하면 쿠키에 세션키를 저장하지 않는다.)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 3. form 로그인 해제 (UsernamePasswordAuthenticationFilter 비활성화)
        http.formLogin().disable();

        // 4. 로그인 인증창이 뜨지 않게 비활성화
        http.httpBasic().disable();

        // 5. 커스텀 필터 등록 (security filter 교환)
        http.apply(new CustomSecurityFilterManager());

        // 6. 인증 실패 처리
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            log.warn("인증되지 않은 사용자가 resource 접근 : {}", authException.getMessage());
            FilterResponseUtil.unAuthorized(request,response, new Exception401("E40301"));
        });

        // 7. 권한 실패 처리
        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            log.warn("권한이 없는 사용자가 resource 접근 : {}", accessDeniedException.getMessage());
            FilterResponseUtil.forbidden(request,response, new Exception403("E40301"));
        });


        // 8. 인증, 권한 필터 설정
        http
                // img , css 과 같은 static resources 는 허용
                .authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .and()
                .authorizeRequests().antMatchers(PERMIT_URL_ARRAY).permitAll()
                .and()
                .authorizeRequests(
                        authorize -> authorize
//                            .antMatchers("/api/v1/guide/").authenticated()
//                            .antMatchers("/api/v1/admin/").hasRole("ADMIN")
//                            .antMatchers("/api/v1/user/").access("hasRole('GUEST') or hasRole('ADMIN')")
//                            .anyRequest().permitAll()
                                .anyRequest().authenticated()
                );

        // h2-console 접속을 위해 설정
        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }


}
