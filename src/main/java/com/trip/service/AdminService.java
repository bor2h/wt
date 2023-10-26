package com.trip.service;

import com.trip.common.annotation.MyLog;
import com.trip.common.enums.UserRole;
import com.trip.common.exception.Exception401;
import com.trip.common.exception.Exception403;
import com.trip.common.security.CustomUser;
import com.trip.domain.User;
import com.trip.repository.user.UserRepository;
import com.trip.common.exception.Exception404;
import com.trip.dto.user.UserDto;
import com.trip.dto.user.UserSearchCondition;
import com.trip.dto.user.UserSearchResponseDto;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ExcelService excelService;

    public boolean isAdmin(CustomUser user){
        if(UserRole.ADMIN.equals(user.getUser().getRole())){
            return true;
        } else {
            throw new Exception403("API 권한을 확인해주세요.");
        }
    }

    @MyLog
    @Transactional(readOnly = true)
    public UserDto.UserInfoDto findUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new Exception404("존재하지 않는 사용자입니다."));
        return user.toDto();
    }



    /**
     * 사용자 리스트 엑셀다운로드
     * @param response
     * @param pageable
     */
    public void findUserAndExcelDown(HttpServletResponse response, Pageable pageable){

        try {
            Page<User> users = userRepository.findAll(pageable);

            List<UserDto.UserInfoDto> userList = users.stream()
                    .map(u -> u.toDto())
                    .collect(Collectors.toList());

            log.info("findUserAndExcelDown >  userList size :: {}",userList.size());

            excelService.createExcelDownloadResponse(response);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public List findUserAll(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        return users.stream()
                .map(u -> u.toDto()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserSearchResponseDto> searchUser(Pageable pageable, UserSearchCondition searchCondition){
        return userRepository.userSearch(searchCondition,pageable);
    }

    @MyLog
    @Transactional
    public void userBan(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new Exception404("존재하지 않는 사용자입니다."));
        user.isBan();
    }

}
