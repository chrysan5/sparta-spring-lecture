package com.sparta.week04.mvc;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.week04.controller.ProductController;
import com.sparta.week04.controller.UserController;
import com.sparta.week04.dto.ProductRequestDto;
import com.sparta.week04.model.User;
import com.sparta.week04.model.UserRoleEnum;
import com.sparta.week04.security.UserDetailsImpl;
import com.sparta.week04.security.WebSecurityConfig;
import com.sparta.week04.service.ProductService;
import com.sparta.week04.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//mvc테스트는 컨트롤러를 테스트 ->http 통신이 필요함 (브라우저와 통신)
//user,product를 원래 나눠야하지만 합쳐놓음

//이거 테스트안됨.....


//@WebMvcTest : 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션입니다.
// 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있습니다. 단, @Service, @Component, @Repository 등은 사용할 수 없다.


@WebMvcTest(
        controllers = {UserController.class, ProductController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class UserProductMvcTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

//    @MockBean
//    KakaoUserService kakaoUserService;

    @MockBean
    ProductService productService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetup() {
// Mock 테스트 유져 생성
        String username = "제이홉";
        String password = "hope!@#";
        String email = "hope@sparta.com";
        UserRoleEnum role = UserRoleEnum.USER;
        User testUser = new User(username, password, email, role);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
        //여기 토큰안에 UserDetailsImpL이 잇음 -그 안에 user가 있음
    }

    @Test
    @DisplayName("로그인 view")
    void test1() throws Exception {
// when - then
        mvc.perform(get("/user/login"))
                .andExpect(status().isOk()) //내장함수 isOk
                .andExpect(view().name("login"))
                .andDo(print()); //http에서의 header, body를 print해줌
    }

    @Test
    @DisplayName("회원 가입 요청 처리")
    void test2() throws Exception {
// given -컨트롤러의 registorUser함수의 signupRequestDto의 form을 만들어주는것임
        MultiValueMap<String, String> signupRequestForm = new LinkedMultiValueMap<>();
        signupRequestForm.add("username", "제이홉");
        signupRequestForm.add("password", "hope!@#");
        signupRequestForm.add("email", "hope@sparta.com");
        signupRequestForm.add("admin", "false");

// when - then
        mvc.perform(post("/user/signup")
                        .params(signupRequestForm)
                )
                .andExpect(status().is3xxRedirection()) //redirect하면 3xx나와야하므로
                .andExpect(view().name("redirect:/user/login"))
                //컨트롤러의 return값이 view name임
                .andDo(print());
    }

    @Test
    @DisplayName("신규 관심상품 등록")
    void test3() throws Exception {
// given
        this.mockUserSetup(); //가짜유저를 만들어줌(그래야 상품등록되므로)
        String title = "Apple <b>에어팟</b> 2세대 유선충전 모델 (MV7N2KH/A)";
        String imageUrl = "https://shopping-phinf.pstatic.net/main_1862208/18622086330.20200831140839.jpg";
        String linkUrl = "https://search.shopping.naver.com/gate.nhn?id=18622086330";
        int lPrice = 77000;
        ProductRequestDto requestDto = new ProductRequestDto(
                title,
                imageUrl,
                linkUrl,
                lPrice
        );

        String postInfo = objectMapper.writeValueAsString(requestDto); //objectMapper는 json 형태를담은 스트링을 만들어줌

// when - then
        mvc.perform(post("/api/products")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
