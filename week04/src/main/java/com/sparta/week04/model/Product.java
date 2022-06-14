package com.sparta.week04.model;

import com.sparta.week04.dto.ProductRequestDto;
import com.sparta.week04.validator.ProductValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    @Column(nullable = false)
    private Long userId;

    @ManyToMany(fetch = LAZY)
    private List<Folder> folderList;


    // 관심 상품 생성 시 이용
    public Product(ProductRequestDto requestDto, Long userId) {
        // 입력값 Validation -ProductValidator을 new로 생성하지않고 static으로 만들어서 바로 가져다 쓴다
        ProductValidator.validateProductInput(requestDto, userId);

        this.userId = userId; // 관심상품을 등록한 회원 Id 저장
        this.title = requestDto.getTitle();
        this.image = requestDto.getImage();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.myprice = 0;
    }

    public void addFolder(Folder folder) {
        this.folderList.add(folder);
    }
}




