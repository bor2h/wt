package com.trip.controller;

import com.trip.common.enums.GuideStatus;
import com.trip.common.security.CustomUser;
import com.trip.dto.GuideDTO;
import com.trip.service.GuideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Guide API",description = "Guide API")
@RequestMapping("/guide")
@Slf4j
@RequiredArgsConstructor
@RestController
public class GuideController {

    private final GuideService guideService;

    @Operation(summary = "가이드 리스트 조회 ( 전체 )", description = "가이드 전체 리스트 조회")
    @GetMapping("/all")
    public ResponseEntity<List<GuideDTO.GuideResponse>> findAllGuide(){
        return ResponseEntity.ok(guideService.findAllGuide());
    }

    @Operation(summary = "가이드 리스트 조회 (페이징)"
            , description = """
            * sort (정렬) > createdAt (최신순)
            * sort (정렬) > likeCount (인기순)
            * page : 기본 페이지 번호 (기본값은 0)
            * size : 기본 크기 (기본값은 10)
            """)
    @GetMapping
    public ResponseEntity<List<GuideDTO.GuideResponse>> pageGuide(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                       @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort
                                       ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        return ResponseEntity.ok(guideService.pageGuide(pageable));
    }


    @Operation(summary = "가이드 신청", description = """
           * 가이드 신청  
           * ※주의사항※  
           * JWT 토큰에서 user 정보를 가져옵니다.  
           * 초기에는 isPass (검수 상태) = N 으로 등록됩니다.  
            """)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuideDTO.GuideResponse> createGuide(@AuthenticationPrincipal CustomUser customUser,@Valid @ModelAttribute GuideDTO.GuideCreateRequest request) {
        return ResponseEntity.ok(guideService.createGuide(customUser,request));
    }

    @Operation(summary = "가이드 상세 조회 > MyPage"
            , description = """
            * MyPage 에서 가이드 상세 조회
            * ※주의사항※
            * isPass (검수상태) = N 일 경우 검수진행중이라고 표시
            * JWT 토큰에서 user 정보를 가져옵니다.  
            """)
    @GetMapping("/detail")
    public ResponseEntity<GuideDTO.GuideResponse> findGuideInfoByUserNo(@AuthenticationPrincipal CustomUser customUser) {
        return ResponseEntity.ok(guideService.findGuideInfoByUserNo(customUser));
    }

    @Operation(summary = "가이드 좋아요", description = """
            * 가이드 좋아요
            * ※주의사항※   
            * JWT 토큰에서 user 정보를 가져옵니다.
            """)
    @GetMapping("/like/{userNo}")
    public ResponseEntity<Long> likeGuide(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long userNo) {
        return ResponseEntity.ok(guideService.likeGuide(customUser,userNo));
    }

    @Operation(summary = "가이드 좋아요 취소", description = """
            가이드 좋아요 취소
            * ※주의사항※   
            * JWT 토큰에서 user 정보를 가져옵니다.
            """)
    @GetMapping("/dislike/{userNo}")
    public ResponseEntity<?> disLikeGuide(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long userNo) {
        return ResponseEntity.ok(guideService.disLikeGuide(customUser,userNo));
    }
}
