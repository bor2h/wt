package com.trip.service;

import com.trip.common.exception.Exception401;
import com.trip.common.exception.Exception404;
import com.trip.common.security.CustomUser;
import com.trip.domain.DaySchedule;
import com.trip.domain.Schedule;
import com.trip.dto.ScheduleDto;
import com.trip.dto.ScheduleInfoDto;
import com.trip.repository.DayScheduleRepository;
import com.trip.repository.SchedulerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class ScheduleService {

    private final SchedulerRepository schedulerRepository;
    private final DayScheduleRepository dayScheduleRepository;

    @Transactional(readOnly = true)
    public List<ScheduleInfoDto> findAllSchedule(){
        return schedulerRepository.findAll().stream()
                .map(s->s.toDto())
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ScheduleInfoDto> findMySchedule(CustomUser customUser){
        return schedulerRepository.findByUserNo(customUser.getUserNo()).stream()
                .map(s->s.toDto())
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public ScheduleInfoDto findScheduleById(CustomUser customUser,String scheduleId){
        return schedulerRepository.findById(scheduleId).orElseThrow(()->new Exception404("findScheduleById")).toDto();
    }



    @Transactional
    public ScheduleInfoDto saveSchedule(CustomUser customUser, ScheduleDto.SaveScheduleRequest request){

        try {
            Schedule schedule = schedulerRepository.save(request.toEntity());
            schedule.setUserNo(customUser.getUserNo());

            return schedule.toDto();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
