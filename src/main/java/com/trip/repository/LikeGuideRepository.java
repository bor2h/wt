package com.trip.repository;

import com.trip.domain.LikeGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeGuideRepository extends JpaRepository<LikeGuide,LikeGuide.LikeGuideId> {
}
