package com.sparta.week04.controller;


import com.sparta.week04.dto.ProductMypriceRequestDto;
import com.sparta.week04.dto.ProductRequestDto;
import com.sparta.week04.model.Product;
import com.sparta.week04.model.User;
import com.sparta.week04.model.UserRoleEnum;
import com.sparta.week04.security.UserDetailsImpl;
import com.sparta.week04.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    // 신규 상품 등록(관심상품 등록하기)
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 회원 테이블의 ID
        Long userId = userDetails.getUser().getId();
        Product product = productService.createProduct(requestDto, userId);
        return product;
    }


    // 신규 상품 등록 - apiUseTime 계산 적용시
/*
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        long startTime = System.currentTimeMillis(); // 측정 시작 시간
        try {
            Long userId = userDetails.getUser().getId();
            Product product = productService.createProduct(requestDto, userId);
            return product;
        } finally {
            long endTime = System.currentTimeMillis(); // 측정 종료 시간
            long runTime = endTime - startTime;  // 수행시간 = 종료 시간 - 시작 시간

            User loginUser = userDetails.getUser(); // 로그인 회원 정보
            ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser) // API 사용시간 및 DB 에 기록
                    .orElse(null);
            if (apiUseTime == null) { // 로그인 회원의 기록이 없으면
                apiUseTime = new ApiUseTime(loginUser, runTime);
            } else { // 로그인 회원의 기록이 이미 있으면
                apiUseTime.addUseTime(runTime);
            }
            System.out.println("[API Use Time] Username: " + loginUser.getUsername() + ", Total Time: " + apiUseTime.getTotalTime() + " ms");
            apiUseTimeRepository.save(apiUseTime);
        }
    }
*/


    // (로그인한 회원이 등록한) 관심 상품 조회
    @GetMapping("/api/products")
    public Page<Product> getProducts( //page: 조회할 페이지 번호
                                      @RequestParam("page") int page, //프론트로부터 받아오는 page 값은 1부터시작
                                      @RequestParam("size") int size, //size : 한 페이지에 보여줄 상품 개수
                                      @RequestParam("sortBy") String sortBy, //정렬항목(ex-id, title, lprice, createdAt)
                                      @RequestParam("isAsc") boolean isAsc, //true일경우 오름차순, false인 경우 내림차순
                                      @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        page = page - 1; //서버의 페이지는 0부터시작하므로
        return productService.getProducts(userId, page, size, sortBy, isAsc);
        //poduct정보를 가져올때 product의 필드인 folderList도 자동으로 가져온다.(@ManyToMnay므로)

    }
    //서버에서 클라이언트로 보내는 값들 -> 스프링이 알아서 보내줌(페이지 검사로 보면 나온다)
 /*   - number: 조회된 페이지 번호 (**0부터 시작**)
    - content: 조회된 상품 정보 (product 정보들이 배열로 나감)
    - size: 한 페이지에 보여줄 상품 개수
    - numberOfElements: 실제 조회된 상품 개수(마지막 페이지는 size 크기와 다르므로 이값이 필요)
    - totalElement: 전체 상품 개수 (회원이 등록한 모든 상품의 개수)
    - totalPages: 전체 페이지 수 (totalPages = totalElement / size 결과를 소수점 올림)
        - first: 첫 페이지인지? (boolean)
        - last: 마지막 페이지인지? (boolean)*/




    // (관리자용) 전체 상품 조회
    @Secured(UserRoleEnum.Authority.ADMIN)
    @GetMapping("/api/admin/products")
    public Page<Product> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        page = page -1;
        return productService.getAllProducts(page, size, sortBy, isAsc);
    }



    // 설정 가격 변경(관심상품의 희망 최저가 업데이트)
    @PutMapping("/api/products/{id}") //여기서 id는 product의 id 이다.
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto productMypriceRequestDto) {
        Product product = productService.updateProduct(id, productMypriceRequestDto);
        return product.getId();
    }



    //관심 상품에 폴더 추가
    @PostMapping("/api/products/{productId}/folder")
    public Long addFolder(
            @PathVariable Long productId,
            @RequestParam Long folderId, //folderId는 form 형태로 받음
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        Product product = productService.addFolder(productId, folderId, user);
        return product.getId();
    }
}