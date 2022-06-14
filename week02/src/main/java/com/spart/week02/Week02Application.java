package com.spart.week02;

import com.spart.week02.domain.Course;
import com.spart.week02.domain.CourseRepository;
import com.spart.week02.domain.CourseRequestDto;
import com.spart.week02.service.CourseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing //이거 해줘야 Timestamped 쓸 수 있다.
@SpringBootApplication
public class Week02Application {
	public static void main(String[] args) {
		SpringApplication.run(Week02Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(CourseRepository courseRepository, CourseService courseService) {
		return (args) -> {

			// 데이터 저장하기
			//Course course1 = new Course("웹개발의 봄 스프링", "임민영");
			//repository.save(course1);과 같음
			courseRepository.save(new Course("프론트엔드의 꽃, 리액트", "임민영"));

			// 데이터 전부 조회하기
			List<Course> courseList = courseRepository.findAll();
			for (int i=0; i<courseList.size(); i++) {
				Course course = courseList.get(i);
				System.out.println(course.getId());
				System.out.println(course.getTitle());
				System.out.println(course.getTutor());
			}

			//데이터 업뎃하기
			CourseRequestDto requestDto = new CourseRequestDto("웹개발의 봄, Spring", "임민영");
			courseService.update(1L, requestDto); //아이디가1인놈을 변경(long 타입)
			courseList = courseRepository.findAll();
			for (int i=0; i<courseList.size(); i++) {
				Course course = courseList.get(i);
				System.out.println(course.getId());
				System.out.println(course.getTitle());
				System.out.println(course.getTutor());
			}

			//데이터 삭제하기기
			//courseRepository.deleteAll();

		};
	}
}
