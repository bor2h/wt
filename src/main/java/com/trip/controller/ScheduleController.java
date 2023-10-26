package com.trip.controller;

import com.trip.common.security.CustomUser;
import com.trip.dto.ScheduleDto;
import com.trip.dto.ScheduleInfoDto;
import com.trip.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@Tag(name = "Schedule", description = "Schedule API")
@RequiredArgsConstructor
@RequestMapping("/schedule")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "UUID 생성", description = """
            * generate UUID
            * ```scheduleId``` 생성시 필요한 uuid
            * ```scheduleId``` 는 Schedule 테이블의 PK 입니다.
            """)
    @GetMapping("/uuid")
    public ResponseEntity<?> generateUUID(){
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }


    @Operation(summary = "여행 기록 전체 조회", description = "여행 기록 리스트")
    @GetMapping
    public ResponseEntity<List<ScheduleInfoDto>> findAllSchedule(){
        return ResponseEntity.ok(scheduleService.findAllSchedule());
    }

    @Operation(summary = "여행 기록 등록"
            , description = """
            * 여행 기록 등록  
            * ※ 주의 사항 ※    
            *  파일을 먼저 등록한후  ```파일 아이디 fileNo``` 값을 ```scheduleImage``` 값에 입력해주세요
            """)
    @PostMapping
    public ResponseEntity<ScheduleInfoDto> saveSchedule(@AuthenticationPrincipal CustomUser customUser, @Valid @RequestBody ScheduleDto.SaveScheduleRequest request){
        return ResponseEntity.ok(scheduleService.saveSchedule(customUser,request));
    }


    @Operation(summary = "나의 여행 기록 조회", description = """
            * 나의 여행 기록 조회  
            * ※ 주의 사항 ※       
            * JWT 토큰에서 User 정보를 가져옵니다.    
            """)
    @GetMapping("/my")
    public ResponseEntity<?> findMySchedule(@AuthenticationPrincipal CustomUser customUser){
        return ResponseEntity.ok(scheduleService.findMySchedule(customUser));
    }



    @Operation(summary = "여행 기록 단일 조회", description = """
            * 여행 기록 단일 조회
            * ※ 주의 사항 ※     
            *  ```scheduleId``` 는 Schedule 테이블의 PK 입니다.
            """)
    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> findScheduleById(@AuthenticationPrincipal CustomUser customUser,@PathVariable String scheduleId){
        return ResponseEntity.ok(scheduleService.findScheduleById(customUser,scheduleId));
    }

}