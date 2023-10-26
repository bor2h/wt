package com.trip.controller;

import com.trip.common.enums.GuideStatus;
import com.trip.common.security.CustomUser;
import com.trip.dto.GuideDTO;
import com.trip.dto.user.UserDto;
import com.trip.dto.user.UserSearchCondition;
import com.trip.dto.user.UserSearchResponseDto;
import com.trip.service.AdminService;
import com.trip.service.GuideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "관리자 API", description = " ```admin``` 권한이 필요합니다.")
@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;
    private final GuideService guideService;

    @Operation(summary = "사용자 검색"
            , description = """
                * 사용자 검색 리스트
                * ※주의사항※  
                *  ```admin``` 권한이 필요합니다.  
                * sort (정렬) 기준  ```createdAt``` 
                * page : 기본 페이지 번호 (기본값은 0)
                * size : 기본 크기 (기본값은 10)
                * direction : 오름차순 (ASC), 내림차순 (DESC)
                """)
    @GetMapping("/users/search")
    public ResponseEntity<List<UserSearchResponseDto>> searchUser(@AuthenticationPrincipal CustomUser customUser,
            @PageableDefault(page = 0,size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @ModelAttribute UserSearchCondition searchCondition){
        adminService.isAdmin(customUser);
        return ResponseEntity.ok(adminService.searchUser(pageable,searchCondition));
    }

    @Operation(summary = "[엑셀 다운] 사용자 리스트", description = """
            * 사용자 리스트 엑셀 다운
            * ※주의사항※  
            * ```admin``` 권한이 필요합니다.  
            * sort (정렬) 기준  ```createdAt``` 
            * page : 기본 페이지 번호 (기본값은 0)
            * size : 기본 크기 (기본값은 10)
            * direction : 오름차순 (ASC), 내림차순 (DESC)
            """)
    @GetMapping("/users/search/excel")
    public void searchUserAndExcelDown(@AuthenticationPrincipal CustomUser customUser,HttpServletResponse response,
            @PageableDefault(page = 0,size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        adminService.isAdmin(customUser);
        adminService.findUserAndExcelDown(response,pageable);
    }

    @Operation(summary = "사용자 단건 조회"
            , description = """
               *  사용자 단건 조회  
               *  ※주의사항※  
               *  ```admin``` 권한이 필요합니다.  
                """)
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto.UserInfoDto> findUserById(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long userId){
        adminService.isAdmin(customUser);
        return ResponseEntity.ok(adminService.findUserById(userId));
    }


    @Operation(summary = "사용자 정지"
            , description = """
              *  사용자 정지   
              *  ※주의사항※  
              *   ```admin``` 권한이 필요합니다.  
                """)
    @GetMapping("/users/ban")
    public ResponseEntity<Void> userBan(@AuthenticationPrincipal CustomUser customUser,@RequestParam Long userId){
        adminService.isAdmin(customUser);
        adminService.userBan(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "가이드 검수 상태 수정", description = """  
            *   가이드 검수 상태 수정  
            *   ※주의사항※    
            *   ```admin``` 권한이 필요합니다.    
            *   isPass = N (검수 X )  
            *   isPass = Y (검수 완료)  
    """)
    @PutMapping("/guide/{guideId}/{status}")
    public ResponseEntity<GuideDTO.GuideResponse> updateGuideStatus(@AuthenticationPrincipal CustomUser customUser,@PathVariable Long guideId, @PathVariable GuideStatus status){
        adminService.isAdmin(customUser);
        return ResponseEntity.ok(guideService.updateGuideStatus(guideId,status));
    }

}
