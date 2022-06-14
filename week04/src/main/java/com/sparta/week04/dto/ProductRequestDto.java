package com.sparta.week04.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequestDto {
    private String title;
    private String link;
    private String image;
    private int lprice; //최저가

}