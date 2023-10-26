package com.trip.service;


import com.trip.common.enums.FileType;
import com.trip.common.exception.Exception401;
import com.trip.common.exception.Exception404;
import com.trip.common.security.CustomUser;
import com.trip.common.utils.CommonEncoder;
import com.trip.dto.AttachFileDto;
import com.trip.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.trip.common.exception.Exception400;
import com.trip.common.exception.Exception500;
import com.trip.dto.user.UserDto.UserInfoDto;
import com.trip.dto.user.UserDto.UserSaveRequest;
import com.trip.domain.User;
import com.trip.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AttachFileService attachFileService;

    @Transactional(readOnly = true)
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }
    @Transactional(readOnly = true)
    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Transactional
    public UserInfoDto save(UserSaveRequest requestDto) {
        Optional<User> userOP = userRepository.findByLoginId(requestDto.getLoginId());
        if (userOP.isPresent()) {
            // 이 부분이 try catch 안에 있으면 Exception500에게 제어권을 뺏긴다.
            throw new Exception400("loginId", "loginId 이 존재합니다");
        }

        try {
            User user = userRepository.save(requestDto.toEntity());
            AttachFileDto.AttachFileInfo attachFile = attachFileService.fileUpload(user.getId().toString(), FileType.USER,requestDto.getFile());
            user.setProfileImg(attachFile.getFileNo().toString());
            return user.toDto();
        } catch (Exception e) {
            throw new Exception500("회원가입 실패 : " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public UserInfoDto getUserResource(CustomUser customUser) {
        User user = userRepository.findByLoginId(customUser.getUser().getLoginId()).orElseThrow(() -> new Exception404("존재하지않는 사용자입니다."));
        return user.toDto();
    }


    // 비밀번호 수정
    @Transactional
    public void updatePassword(UserDto.ChangePasswordRequest request) {

        User user = userRepository.findByLoginId(request.getLoginId()).orElseThrow(() -> new Exception404("존재하지않는 사용자입니다."));
        if (!new CommonEncoder().matches(request.getPasswordBefore(), user.getPassword())) {
            throw new Exception401("이전 비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(new CommonEncoder().encode(request.getPasswordAfter()));
    }


    // 회원 탈퇴
    @Transactional
    public void withdraw(CustomUser securityUser) {
        User user = userRepository.findByLoginId(securityUser.getUser().getLoginId()).orElseThrow(() -> new Exception404("존재하지 않는 email 입니다."));
        userRepository.delete(user);
        //user.delete();
    }


}
