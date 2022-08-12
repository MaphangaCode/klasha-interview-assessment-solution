package com.solutions.assessment.interview.klasha.service.impl;

import com.solutions.assessment.interview.klasha.domain.entity.LocationUser;
import com.solutions.assessment.interview.klasha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AppUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final LocationUser user = userRepository.findByEmailAndDeletedAtIsNull(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                username, user.getPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }
}
