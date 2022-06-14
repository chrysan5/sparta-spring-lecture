package com.sparta.week03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing ///수정시 시간바뀌면 자동업뎃해줌 ->이걸해야 시간변수들에 null값이 안뜬다
@SpringBootApplication
//https://developers.kakao.com/console/app
public class Week03Application {
    public static void main(String[] args) {
        SpringApplication.run(Week03Application.class, args);
    }
}
