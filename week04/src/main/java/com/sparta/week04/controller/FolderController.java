package com.sparta.week04.controller;


import com.sparta.week04.dto.FolderRequestDto;
import com.sparta.week04.model.Folder;
import com.sparta.week04.model.Product;
import com.sparta.week04.model.User;
import com.sparta.week04.security.UserDetailsImpl;
import com.sparta.week04.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FolderController {
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    //폴더 추가 -> 1개~ n개 까지 추가 가능
    @PostMapping("api/folders")
    public List<Folder> addFolders(
            @RequestBody FolderRequestDto folderRequestDto, // List<String> folderNames가 json 형태로 담겨있음
            @AuthenticationPrincipal UserDetailsImpl userDetails //로긴한 사용자 정보 받아옴
    ) {
        List<String> folderNames = folderRequestDto.getFolderNames();
        User user = userDetails.getUser(); //유저정보 들어있음

        List<Folder> folders = folderService.addFolders(folderNames, user);
        return folders;
    }

    //이 공통적인 부분을 aop를 이용한 exceptionHandler로 처리할수있다.
    @PostMapping("api/folders")
/*
    public ResponseEntity addFolders(
            @RequestBody FolderRequestDto folderRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            List<String> folderNames = folderRequestDto.getFolderNames();
            User user = userDetails.getUser();

            List<Folder> folders = folderService.addFolders(folderNames, user);
            return new ResponseEntity(folders, HttpStatus.OK);
        } catch(IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage(ex.getMessage());
            return new ResponseEntity(
                    restApiException, // HTTP body
                    HttpStatus.BAD_REQUEST); // HTTP status code
        }
    }
*/
    //이건 이 컨트롤러에서만 적용됨
    //이것도 aop이다. 모든 IllegalArgumentException에 대해 적용됨
/*
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity handleException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(ex.getMessage());
        return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
    }
*/


    //폴더 전체를 조회해서 관심상품의 밑에 해시테그(폴더명)를 달아줄 수 있는 기능이 있다.
    // 회원이 등록한 모든 폴더 조회(그 회원이 추가한 것에 해당하는 모든 폴더 조회)
    @GetMapping("api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return folderService.getFolders(userDetails.getUser()); // 해당 user가 가진 폴더 찾기!★★★★★
    }



    // 회원이 등록한 폴더 내 모든 상품 조회 (회원이 등록한 폴더선택시 관심상품들 나오게 조회)
    @GetMapping("api/folders/{folderId}/products") //Page<Product>를 출력해줘야 한다
    public Page<Product> getProductsInFolder(
            @PathVariable Long folderId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        page = page - 1;
        //forderService, productService는 판단에 따라 사용하면됨. 여기서는 이걸로 쓸거다.
        return folderService.getProductsInFolder(
                folderId,
                page,
                size,
                sortBy,
                isAsc,
                userDetails.getUser()
        );
    }
}
