package com.trip.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.trip.common.enums.Gender;
import com.trip.common.enums.TableStatus;
import com.trip.domain.DaySchedule;
import com.trip.domain.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(name = "[응답 DTO] 여행 기록 정보")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleInfoDto {

    @Schema(description = "일정 고유키 pk (36 자리 uuid)" ,example = "2ccc806c-6787-4964-8d7b-22ee3c295d7e")
    private String scheduleId;

    @Schema(description = "조회수",example = "1")
    private Long viewCount;

    @Schema(description = "좋아요수",example = "0")
    private Long likeCount;

    @Schema(description = "여행 기록 제목" ,example = "뚜벅이의 걷기 일기")
    private String scheduleName;

    @Schema(description = "여행 기록 이미지" ,example = "2")
    private String scheduleImage;

    @Schema(description = "출발 위치" ,example = "묵호역")
    private String startingLocation;

    @Schema(description = "해시태그" ,example = "#걷기여행#산책로")
    private String hashtag;

    @Schema(description = "여행 기간" ,example = "3")
    private int days;

    @Schema(description = "여행의 거리 (단거리,장거리)" ,example = "장거리")
    private String distance;

    @Schema(description = "성인 인원수 (만 18세 이상)" ,example = "2")
    private int adults;

    @Schema(description = "아동 인원수 (만 7세 이상)" ,example = "2")
    private int child;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "일정 시작일" ,example = "2023-11-13")
    private LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "일정 종료일" ,example = "2023-11-15")
    private LocalDate endAt;

    @Schema(description = "user pk 고유키",example = "1")
    private Long userNo;

    @Schema(description = "생성자" ,example = "1")
    private String createdId;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @Schema(description = "생성일시" ,example = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "여행 일정 리스트")
    private List<DayScheduleInfo> daySchedules;


    @Schema(name = "[응답 DTO] 여행 일정 정보")
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class DayScheduleInfo {

        @Schema(description = "daySchedule pk 여행 일정 고유키",example = "1")
        private Long dayScheduleNo;

        @Schema(description = "일정 고유키 pk (36 자리 uuid)" ,example = "2ccc806c-6787-4964-8d7b-22ee3c295d7e")
        private String scheduleId;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dateTime;

        @Schema(description ="N일차" ,example = "1")
        private int dateNumber;

        @Schema(description ="주소" ,example = "장호항")
        private String address;

        @Schema(description ="여행 체크 리스트" ,example = "수건,양말,손수건")
        private String checkList;

    }

}
