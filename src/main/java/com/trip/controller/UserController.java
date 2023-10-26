package com.trip.controller;

import com.trip.common.security.CustomUser;
import com.trip.dto.GuideDTO;
import com.trip.dto.user.UserDto.LoginRequest;
import com.trip.dto.user.UserDto;
import com.trip.service.JwtService;
import com.trip.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "사용자 관련 API ", description = "사용자 Create,Read,Update,Delete")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Operation(summary = "이메일 중복확인", description = """
            * JWT 토큰 없이 (로그인 없이) 요청 가능한 API 입니다.    
            * 회원 가입 할 때 사용 가능 합니다.  
            """)
    @GetMapping("/user-emails/{email}/exists")
    public ResponseEntity<?> checkEmailDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkEmailDuplicate(email));
    }
    @Operation(summary = "loginId 중복확인", description = """
            * JWT 토큰 없이 (로그인 없이) 요청 가능한 API 입니다.  
            * 회원 가입 할 때 사용 가능 합니다.    
            """)
    @GetMapping("/user-loginId/{loginId}/exists")
    public ResponseEntity<?> checkLoginIdDuplicate(@PathVariable String loginId) {
        return ResponseEntity.ok(userService.checkLoginIdDuplicate(loginId));
    }

    @Operation(summary = "회원가입",description = """
            * 회원가입 > User 생성  
            * JWT 토큰 없이 (로그인 없이) 요청 가능한 API 입니다.   
            * 회원 가입 할 때 사용 가능 합니다.     
            """)
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto.UserInfoDto> createUser(@Valid @ModelAttribute UserDto.UserSaveRequest requestDto) {
        return ResponseEntity.ok(userService.save(requestDto));
    }


    @Operation(summary = "로그인 요청 (JWT 토큰 발급)", description = """
            * 로그인 성공시 JWT 토큰을 발급합니다.  
            """)
    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(jwtService.jwtLogin(request,response));
    }

    @Operation(summary = "비밀번호 변경", description = """
            * JWT 토큰에서 User 정보를 가져옵니다.
            """)
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal CustomUser securityUser, @Valid @RequestBody UserDto.ChangePasswordRequest request) {
        userService.updatePassword(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "로그아웃", description = """
            * JWT 토큰에서 User 정보를 가져옵니다.
            """)
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUser securityUser) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "회원 탈퇴" , description = """
            * JWT 토큰에서 User 정보를 가져옵니다.
            """)
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal CustomUser securityUser) {
        userService.withdraw(securityUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "내 정보 조회", description = """
            * JWT 토큰에서 User 정보를 가져옵니다.
            """)
    @GetMapping("/my-info")
    public ResponseEntity<UserDto.UserInfoDto> myPage(@AuthenticationPrincipal CustomUser securityUser) {
        return ResponseEntity.ok(userService.getUserResource(securityUser));
    }


}