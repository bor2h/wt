package com.trip.repository.user;

import com.trip.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {

    Optional<User> findByEmail(String email);
    Optional<User> findByLoginId(String loginId);

    Optional<User> findByUserName(String userName);
    boolean existsByEmail(String email);

    boolean existsByLoginId(String loginId);

}
