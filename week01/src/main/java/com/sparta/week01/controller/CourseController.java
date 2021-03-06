package com.sparta.week01.controller;

import com.sparta.week01.prac.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



//๐ก ๋ฐ์ดํฐ๋ก ์๋ตํ๋ ค๋ฉด, RestController ๋ฅผ ์ฌ์ฉํด์ผ ํฉ๋๋ค.
//        - Rest๋ ์๋ฒ์ ์๋ต์ด JSON ํ์์์ ๋ํ๋๋๋ค.
//        - HTML, CSS ๋ฑ์ ์ฃผ๊ณ ๋ฐ์ ๋๋ Rest ๋ฅผ ๋ถ์ด์ง ์์ต๋๋ค.


@RestController //์ด๊ฑธ ์ ์ด์ฃผ๋ฉด ์๋ต์ ์ฉ(controller)์ธ๊ฑธ ์๋ฏธ -json์ผ๋ก ์๋ตํ๋ ์๋์๋ต๊ธฐ
public class CourseController {

    @GetMapping("/courses") //localhost:8080/courses๋ฅผ ์ฃผ์์ค์ ์น๋ฉด course.java ํ์ด์ง๋ฅผ ๋ฆฌํดํ๋ค.
    public Course getCourses() {
        Course course = new Course();
        course.setTitle("์น๊ฐ๋ฐ์ ๋ด ์คํ๋ง");
        course.setTutor("๋จ๋ณ๊ด");
        return course; //course๊ฐ jsonํ์์ผ๋ก ๋ฐ๊ฟ์ง์ํ -> {"title":"์น๊ฐ๋ฐ์ ๋ด ์คํ๋ง","tutor":"๋จ๋ณ๊ด"}์ํ๋ก ๋ธ๋ผ์ฐ์ ์ ๋ธ
    }
}