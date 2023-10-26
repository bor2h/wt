package com.trip.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import com.trip.dto.ScheduleInfoDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Schedule extends BaseEntity {

    @Id
    private String scheduleId;

    @Builder.Default
    @Column(columnDefinition = "bigint default 0 comment '조회수'")
    private Long viewCount = 0L;

    @Builder.Default
    @Column(columnDefinition = "bigint default 0 comment '좋아요수'")
    private Long likeCount = 0L;

    @Column(columnDefinition = "varchar(255) comment '여행 기록 제목'")
    private String scheduleName;

    @Column(columnDefinition = "varchar(255) comment '출발 위치'")
    private String startingLocation;

    @Column(columnDefinition = "varchar(255) comment 'hashtag'")
    private String hashtag;

    @Column(columnDefinition = "int comment '여행기간'")
    private int days;

    @Column(columnDefinition = "varchar(255) comment '여행의 거리 (단거리,장거리)'")
    private String distance;

    @Column(columnDefinition = "int comment '성인 인원수 (만 18세 이상)'")
    private int adults;

    @Column(columnDefinition = "int comment '아동 인원수 (만 7세 이상)'")
    private int child;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "varchar(100) comment '일정 시작일'")
    private LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "varchar(100) comment '일정 종료일'")
    private LocalDate endAt;

    // -------------------------------------- 연관 관계
    @Column
    private Long userNo;

    @Column(columnDefinition = "varchar(255) comment '여행 기록 이미지 파일 아이디'")
    private String scheduleImage;

    @Column(columnDefinition = "varchar(255) comment '여행 일정 리스트'")
    @Builder.Default
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<DaySchedule> daySchedules = new ArrayList<>();

    public ScheduleInfoDto toDto(){
        return ScheduleInfoDto.builder()
                .scheduleId(scheduleId)
                .adults(adults)
                .child(child)
                .viewCount(viewCount)
                .likeCount(likeCount)
                .scheduleName(scheduleName)
                .scheduleImage(scheduleImage)
                .startingLocation(startingLocation)
                .days(days)
                .hashtag(hashtag)
                .distance(distance)
                .adults(adults)
                .child(child)
                .startAt(startAt)
                .endAt(endAt)
                .userNo(userNo)
                .daySchedules(daySchedules.stream().map(d -> d.toDto()).collect(Collectors.toList()))
                .createdAt(getCreatedAt())
                .createdId(getCreatedId())
                .build();
    }


}
