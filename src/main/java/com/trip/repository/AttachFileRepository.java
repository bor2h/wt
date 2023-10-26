package com.trip.repository;


import com.trip.domain.AttachFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {

    void deleteByRefId(String refId);
}
