package com.trip.common.config;

import com.trip.common.security.CustomUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null == authentication || !authentication.isAuthenticated()){
            return null;
        }

        return Optional.of(((CustomUser)authentication.getPrincipal()).getUser().getId().toString());
    }
}
