package com.spart.week02.controller;

import com.spart.week02.domain.Course;
import com.spart.week02.domain.CourseRepository;
import com.spart.week02.domain.CourseRequestDto;
import com.spart.week02.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RequiredArgsConstructor //final 있으므로
@RestController //json으로 응답
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseService courseService;


    // PostMapping을 통해서, 같은 주소라도 방식이 다름을 구분합니다.
    @PostMapping("/api/courses")
    public Course createCourse(@RequestBody CourseRequestDto requestDto) {
        // requestDto 는, 생성 요청을 의미합니다.
        // 강의 정보를 만들기 위해서는 강의 제목과 튜터 이름이 필요하잖아요. 그 정보를 가져오는 녀석입니다.
        //@RequestBody는 컨트롤러에서 요청을 받는 녀석이란 걸 표시. 여튼 이게 있어야 요청 받은 정보가 requestDto안에 들어간다

        Course course = new Course(requestDto);
        return courseRepository.save(course); // JPA를 이용하여 DB에 저장하고, 결과로 생성한 코스를 반환함
    }

    @GetMapping("/api/courses")
    public List<Course> getCourses() {
        return courseRepository.findAll();
        //[{"id":1,"title":"웹개발의 봄, Spring","tutor":"임민영"}]이 웹브라우저에 뜬다
    }

    @PutMapping("/api/courses/{id}") //유동적인 id값을 @PathVariable가 넣어줌
    public Long updateCourse(@PathVariable Long id, @RequestBody CourseRequestDto requestDto) {
        return courseService.update(id, requestDto);
    }

    @DeleteMapping("/api/courses/{id}")
    public Long deleteCourse(@PathVariable Long id){
        courseRepository.deleteById(id);
        return id;
    }
}