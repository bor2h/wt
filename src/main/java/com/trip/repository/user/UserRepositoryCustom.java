package com.trip.repository.user;

import com.trip.dto.user.UserSearchCondition;
import com.trip.dto.user.UserSearchResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {

    List<UserSearchResponseDto> userSearch (UserSearchCondition condition, Pageable pageable);
}
