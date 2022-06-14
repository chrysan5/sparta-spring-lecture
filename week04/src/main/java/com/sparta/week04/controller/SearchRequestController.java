//package com.sparta.week04.controller;
//
//import com.sparta.week04.dto.ItemDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RequiredArgsConstructor // final 로 선언된 클래스를 자동으로 생성합니다.
//@RestController // JSON으로 응답함을 선언합니다.
//public class SearchRequestController {
//
//    private final NaverShopSearch naverShopSearch;
//
//    @GetMapping("/api/search")
//    public List<ItemDto> getItems(@RequestParam String query) {//물음표뒤의 녀석을 받기위해서 @RequestParam.
//        //받아오는 변수명(query)이 매개변수와 꼭 같아야한다.
//        String resultString = naverShopSearch.search(query);
//        return naverShopSearch.fromJSONtoItems(resultString);
//    }
//}