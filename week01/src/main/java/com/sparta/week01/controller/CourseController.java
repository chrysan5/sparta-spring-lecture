package com.sparta.week01.controller;

import com.sparta.week01.prac.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



//💡 데이터로 응답하려면, RestController 를 사용해야 합니다.
//        - Rest란 서버의 응답이 JSON 형식임을 나타냅니다.
//        - HTML, CSS 등을 주고받을 때는 Rest 를 붙이지 않습니다.


@RestController //이걸 적어주면 응답전용(controller)인걸 의미 -json으로 응답하는 자동응답기
public class CourseController {

    @GetMapping("/courses") //localhost:8080/courses를 주소줄에 치면 course.java 페이지를 리턴한다.
    public Course getCourses() {
        Course course = new Course();
        course.setTitle("웹개발의 봄 스프링");
        course.setTutor("남병관");
        return course; //course가 json형식으로 바꿔진상태 -> {"title":"웹개발의 봄 스프링","tutor":"남병관"}상태로 브라우저에 뜸
    }
}