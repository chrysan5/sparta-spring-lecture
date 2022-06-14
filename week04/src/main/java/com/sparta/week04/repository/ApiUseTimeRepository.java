package com.sparta.week04.repository;

import com.sparta.week04.model.ApiUseTime;
import com.sparta.week04.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User user);
}