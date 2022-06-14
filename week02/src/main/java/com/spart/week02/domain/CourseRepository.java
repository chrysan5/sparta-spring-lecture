package com.spart.week02.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    //course 녀석으로 id의 형태가 long
}
