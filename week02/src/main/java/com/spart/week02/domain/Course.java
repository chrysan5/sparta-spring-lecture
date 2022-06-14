package com.spart.week02.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Getter //lombok꺼
@NoArgsConstructor //lombok이 기본생성자를 자동으로 생성
@Entity //테이블임을 나타냄
public class Course extends Timestamped{

    @Id // ID 값, Primary Key로 사용하겠다는 뜻입니다.
    @GeneratedValue(strategy = GenerationType.AUTO) //=auto_increment. 자동 증가 명령입니다.
    private Long id; //=bigint

    @Column(nullable = false) //=not null. 컬럼값이고 반드시 값이 존재해야 함을 의미
    private String title; //=varchar

    @Column(nullable = false)
    private String tutor;


    //id는 getter,setter 설정해주지 않음 setter는 repository에서 자동으로 해준다
//    public Long getId(){ return  this.id; }
//    public String getTitle() { return this.title; }
//    public String getTutor() { return this.tutor; } 이거대신 @Getter.


    public Course(String title, String tutor) {
        this.title = title;
        this.tutor = tutor;
    }

    public Course(CourseRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.tutor = requestDto.getTutor();
    }

    public void update(CourseRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.tutor = requestDto.getTutor();
    }
}
