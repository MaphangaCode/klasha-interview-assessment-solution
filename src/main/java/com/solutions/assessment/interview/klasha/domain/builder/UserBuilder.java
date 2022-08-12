package com.solutions.assessment.interview.klasha.domain.builder;

import com.solutions.assessment.interview.klasha.domain.entity.LocationUser;

import java.time.LocalDateTime;

public class UserBuilder {
    private final LocationUser user = new LocationUser();

    public UserBuilder setId(final Long id) {
        this.user.setId(id);
        return this;
    }

    public UserBuilder setFirstName(final String firstName) {
        this.user.setFirstName(firstName);
        return this;
    }

    public UserBuilder setSurname(final String surname) {
        this.user.setSurname(surname);
        return this;
    }

    public UserBuilder setPassword(final String password) {
        this.user.setPassword(password);
        return this;
    }

    public UserBuilder setCreatedAt(final LocalDateTime createdAt) {
        this.user.setCreatedAt(createdAt);
        return this;
    }

    public UserBuilder setDeletedAt(final LocalDateTime deletedAt) {
        this.user.setDeletedAt(deletedAt);
        return this;
    }

    public UserBuilder setEmail(final String email) {
        this.user.setEmail(email);
        return this;
    }

    public LocationUser build() {
        return this.user;
    }

}
