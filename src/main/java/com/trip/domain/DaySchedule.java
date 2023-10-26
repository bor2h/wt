package com.trip.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trip.dto.ScheduleInfoDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DaySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_schedule_no")
    private Long dayScheduleNo;

    @Column(name = "schedule_id")
    private String scheduleId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "varchar(255) comment '날짜/시간'")
    private LocalDateTime dateTime;

    @Column(columnDefinition = "int comment 'N일차'")
    private int dateNumber;

    @Column(columnDefinition = "varchar(255) comment '주소'")
    private String address;

    @Column(columnDefinition = "varchar(1000) comment '체크리스트'")
    private String checkList;

    @ManyToOne(targetEntity = Schedule.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    private Schedule schedule;


    public ScheduleInfoDto.DayScheduleInfo toDto(){
        return ScheduleInfoDto.DayScheduleInfo.builder()
                .scheduleId(scheduleId)
                .dayScheduleNo(dayScheduleNo)
                .address(address)
                .checkList(checkList)
                .dateNumber(dateNumber)
                .dateTime(dateTime)
                .build();
    }
}
