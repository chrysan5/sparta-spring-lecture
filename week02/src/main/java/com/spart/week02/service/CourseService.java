package com.spart.week02.service;

import com.spart.week02.domain.Course;
import com.spart.week02.domain.CourseRepository;
import com.spart.week02.domain.CourseRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@RequiredArgsConstructor //CourseService 생성자 자동 생성으로 courseRepository 자유롭게 씀
@Service // 스프링에게 이 클래스는 서비스임을 명시.(sql과 같이 update할수있으니까 알아둬)
public class CourseService {

    // final: 서비스에게 꼭 필요한 녀석임을 명시. 변경 불가
    private final CourseRepository courseRepository;
//    // 이 생성자를 통해, Service 클래스를 만들 때 꼭 Repository를 넣어주도록 스프링에게 알려줌
//    public CourseService(CourseRepository courseRepository) { //매개변수로 받아오는 courseRepository는 스프링이 @Service를 통해 생성자를 통해 생성시 알아서 넣어준다
//        this.courseRepository = courseRepository; //이걸 통해서 courseRepository가 내가 언제든 쓸수있게 스프링이 생성해서 넘겨준다
//    }

    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
    public Long update(Long id, CourseRequestDto requestDto) {//업뎃할 녀석의 정보, 업뎃할 정보를 가져올 녀석을 매개변수로 받음
        Course course1 = courseRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        course1.update(requestDto);
        return course1.getId(); //업뎃된 녀석의 아이디를 리턴
    }
}
