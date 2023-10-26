package com.trip.repository;


import com.trip.domain.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<Guide,Long> {

    Optional<Guide> findByUserNo(Long userNo);

}
