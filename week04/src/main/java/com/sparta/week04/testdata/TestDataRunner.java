package com.sparta.week04.testdata;

import com.sparta.week04.dto.ItemDto;
import com.sparta.week04.model.Folder;
import com.sparta.week04.model.Product;
import com.sparta.week04.model.User;
import com.sparta.week04.model.UserRoleEnum;
import com.sparta.week04.repository.FolderRepository;
import com.sparta.week04.repository.ProductRepository;
import com.sparta.week04.repository.UserRepository;
import com.sparta.week04.service.ItemSearchService;
import com.sparta.week04.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.week04.service.ProductService.MIN_MY_PRICE;

//ApplicationRunner를 사용해서 구현을하면 스프링이 기동하는 순간에 run 함수를 override하면 아래 코드들이 실행됨
@Component
public class TestDataRunner implements ApplicationRunner {
    //테스트 데이더라서 @RequiredArgsConstructor 대신 그냥 @Autowired로 주입받는거임
    @Autowired
    UserService userService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ItemSearchService itemSearchService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
        User testUser = new User("슈가", passwordEncoder.encode("123"), "sugar@sparta.com", UserRoleEnum.USER);
        testUser = userRepository.save(testUser);

        User testUser1 = new User("정국", passwordEncoder.encode("123"), "jg@sparta.com", UserRoleEnum.USER);
        User testUser2 = new User("제이홉", passwordEncoder.encode("123"), "hope@sparta.com", UserRoleEnum.USER);
        User testAdminUser1 = new User("아미", passwordEncoder.encode("123"), "army@sparta.com", UserRoleEnum.ADMIN);
        testUser1 = userRepository.save(testUser1);
        testUser2 = userRepository.save(testUser2);
        testAdminUser1 = userRepository.save(testAdminUser1);

        // 테스트 User 의 관심상품 등록
        // 검색어 당 관심상품 10개 등록
        createTestData(testUser, "신발");
        createTestData(testUser, "과자");
        createTestData(testUser, "키보드");
        createTestData(testUser, "휴지");
        createTestData(testUser, "휴대폰");
        createTestData(testUser, "앨범");
        createTestData(testUser, "헤드폰");
        createTestData(testUser, "이어폰");
        createTestData(testUser, "노트북");
        createTestData(testUser, "무선 이어폰");
        createTestData(testUser, "모니터");
    }

    private void createTestData(User user, String searchWord) throws IOException {
        // 네이버 쇼핑 API 통해 상품 검색
        List<ItemDto> itemDtoList = itemSearchService.getItems(searchWord);

        List<Product> productList = new ArrayList<>();

        for (ItemDto itemDto : itemDtoList) {
            Product product = new Product();
            // 관심상품 저장 사용자
            product.setUserId(user.getId());
            // 관심상품 정보
            product.setTitle(itemDto.getTitle());
            product.setLink(itemDto.getLink());
            product.setImage(itemDto.getImage());
            product.setLprice(itemDto.getLprice());

            // 희망 최저가 랜덤값 생성
            // 최저 (100원) ~ 최대 (상품의 현재 최저가 + 10000원)
            int myPrice = getRandomNumber(MIN_MY_PRICE, itemDto.getLprice() + 10000);
            product.setMyprice(myPrice);

            productList.add(product);
        }

        productRepository.saveAll(productList);
        Folder folder = new Folder(searchWord, user);
        folderRepository.save(folder);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}