package com.sparta.week04.repository;


import com.sparta.week04.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //List<Product> findAllByUserId(Long userId);
    Page<Product> findAllByUserId(Long userId, Pageable pageable);
    //product 테이블의 userId

    //pageable이라는 만들어진 함수를 넣고 list 대신 page로 바꿔주면 알아서 결과를 준다.
    Page<Product> findAllByUserIdAndFolderList_Id(Long userId, Long folderId, Pageable pageable); //★★★★★
    //Product 테이블에 있는 FolderList의 FolderList_Id에 받아온 folderId를 넣어준다.
}


//    static <T> Page<T> empty(Pageable pageable) {
//        return new PageImpl<>(Collections.emptyList(), pageable, 0);
//    }





//JpaRepository - 마우스오른쪽버튼 -goto -implementation -simpleJpaRepository
//를 가면 findById 같은 함수들이 다 제공되고 있는 것을 볼 수 있다.
//spring data jpa가 다해줌

