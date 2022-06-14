package com.sparta.week03.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();
    //다찾아서 수정된날짜 기준으로 정렬해줘 (내림차순->)최신순으로.
    //Memo 클래스가 상속한 timestamped 추상클래스에 modifiedAt란 변수이름을 쿼리 메소드 규칙으로 적용한 것
}

//JpaRepository라는 미리 작성된 코드(findAll(), delete, save 등) 를 MemoRepository가 갖다쓴다
// 어디다 가져다 쓰나면 Memo라는 클래스고, id가 Long인 녀석에 대해서 가져다 쓸거다


//findby..변수이름 등 여러 메소드는 Query creation from method name를 docs.spring.io에 검색해보기
//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
