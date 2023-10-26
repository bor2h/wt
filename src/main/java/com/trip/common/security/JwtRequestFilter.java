package com.trip.common.security;

import com.trip.common.enums.UserRole;
import com.trip.domain.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * JWT 토큰 유효성 검사 및 SecurityContextHolder 생성
 */
@Slf4j
public class JwtRequestFilter extends BasicAuthenticationFilter {

    private final HandlerExceptionResolver exceptionResolver;

    public JwtRequestFilter(AuthenticationManager authenticationManager, HandlerExceptionResolver exceptionResolver) {
        super(authenticationManager);
        this.exceptionResolver = exceptionResolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) {


        String token_header = "";

        try {

            token_header = request.getHeader(SecurityConstants.TOKEN_HEADER);

            if (StringUtils.isEmpty(token_header)) {
                filter.doFilter(request, response);
                return;
            }


            Jws<Claims> parsedToken = Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
                    .parseClaimsJws(token_header.replace(SecurityConstants.TOKEN_PREFIX, ""));

            Claims claims = parsedToken.getBody();

            String email = (String) claims.get("email");
            String role = (String) claims.get("role");
            String loginId = (String) claims.get("loginId");
            String userNo = (String) claims.get("sub");

            // CustomUser 에 id 만들어서 user id 값을 SecurityContext 에 넣을 예정
            log.info("loginId: {}, userNo: {}, email: {}", loginId, userNo, email);

            User user = User.builder()
                    .loginId(loginId)
                    .email(email)
                    .role(UserRole.valueOf(role))
                    .id(Long.valueOf(userNo))
                    .password("") // password 빈값으로 넣어야함!
                    .build();

            CustomUser myUserDetails = new CustomUser(user);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            myUserDetails,
                            myUserDetails.getPassword(),
                            myUserDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filter.doFilter(request, response);

        } catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT : {} failed : {}", token_header, exception.getMessage());
            request.setAttribute("RESP_CODE","E401");
            exceptionResolver.resolveException(request, response, null, exception);
        } catch (UnsupportedJwtException exception) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", token_header, exception.getMessage());
            request.setAttribute("RESP_CODE","E401");
            exceptionResolver.resolveException(request, response, null, exception);
        } catch (MalformedJwtException exception) {
            log.warn("Request to parse invalid JWT : {} failed : {}", token_header, exception.getMessage());
            request.setAttribute("RESP_CODE","E401");
            exceptionResolver.resolveException(request, response, null, exception);
        } catch (Exception exception) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", token_header, exception.getMessage());
            request.setAttribute("RESP_CODE","E401");
            exceptionResolver.resolveException(request, response, null, exception);
        }
    }
}