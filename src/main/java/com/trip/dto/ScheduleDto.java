package com.trip.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trip.domain.DaySchedule;
import com.trip.domain.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleDto {

    @Schema(name = "[요청 DTO] 여행 기록 저장")
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SaveScheduleRequest {

        @NotBlank
        @Schema(description = "여행 기록 이미지" ,example = "2")
        private String scheduleImage;

        @NotBlank
        @Schema(description = "36 자리 uuid" ,example = "2ccc806c-6787-4964-8d7b-22ee3c295d7e")
        private String scheduleId;

        @NotBlank
        @Schema(description = "여행 기록 제목" ,example = "뚜벅이의 걷기 일기")
        private String scheduleName;

        @NotBlank
        @Schema(description = "출발 위치" ,example = "묵호역")
        private String startingLocation;

        @NotBlank
        @Schema(description = "여행의 거리 (단거리,장거리)" ,example = "장거리")
        private String distance;

        @Schema(description = "해시태그" ,example = "#걷기여행#산책로")
        private String hashtag;

        @Schema(description = "성인 인원수 (만 18세 이상)" ,example = "2")
        private int adults;
        
        @Schema(description = "아동 인원수 (만 7세 이상)" ,example = "2")
        private int child;

        @Schema(description = "여행 기간" ,example = "3")
        private int days;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "일정 시작일 yyyy-MM-dd" ,example = "2023-11-13")
        private LocalDate startAt;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "일정 종료일 yyyy-MM-dd" ,example = "2023-11-15")
        private LocalDate endAt;

        @NotNull
        @Schema(description = "여행 일정 리스트")
        private List<SaveDayScheduleRequest> daySchedules;

        public Schedule toEntity(){
            return Schedule.builder()
                    .scheduleId(scheduleId)
                    .scheduleName(scheduleName)
                    .hashtag(hashtag)
                    .adults(adults)
                    .child(child)
                    .startAt(startAt)
                    .scheduleImage(scheduleImage)
                    .endAt(endAt)
                    .days(days)
                    .distance(distance)
                    .daySchedules(daySchedules.stream().map(d -> d.toEntity(scheduleId)).collect(Collectors.toList()))
                    .startingLocation(startingLocation)
                    .build();
        }
    }

    @Schema(name = "[요청 DTO] SaveDayScheduleRequest")
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SaveDayScheduleRequest {

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description ="날짜/시간 (yyyy-MM-dd HH:mm:ss)" ,example = "2023-11-15 10:00:00")
        private LocalDateTime dateTime;

        @Schema(description ="N일차" ,example = "1")
        private int dateNumber;

        @NotBlank
        @Schema(description ="주소" ,example = "장호항")
        private String address;

        @Schema(description ="여행 체크 리스트" ,example = "수건,양말,손수건")
        private String checkList;

        public DaySchedule toEntity(String scheduleId){
            return DaySchedule.builder()
                    .scheduleId(scheduleId)
                    .dateNumber(dateNumber)
                    .dateTime(dateTime)
                    .address(address)
                    .checkList(checkList)
                    .build();
        }

    }



}
