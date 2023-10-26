package com.trip.repository;

import com.trip.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulerRepository extends JpaRepository<Schedule,String> {

    List<Schedule> findByUserNo(Long userNo);
}
