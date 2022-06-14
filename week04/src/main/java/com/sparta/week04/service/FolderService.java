package com.sparta.week04.service;

import com.sparta.week04.model.Folder;
import com.sparta.week04.model.Product;
import com.sparta.week04.model.User;
import com.sparta.week04.repository.FolderRepository;
import com.sparta.week04.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository, ProductRepository productRepository) {
        this.folderRepository = folderRepository;
        this.productRepository = productRepository;
    }

    // 로그인한 회원에 폴더들 등록
    @Transactional //을 붙이면 aop가 동작하는 것이다!
    public List<Folder> addFolders(List<String> folderNames, User user) {
//        List<Folder> folderList = new ArrayList<>(); //폴더들을 담을 폴더리스트를 만든다.
//        for (String folderName : folderNames) { //folderNames에서 forderName을 하나하나 꺼내서
//            Folder folder = new Folder(folderName, user); //user 정보와 합쳐서 Folder를 만든다.
//            folderList.add(folder); //만든 폴더들을 폴더리스트에 넣어줌
//        }
        // 1) 입력으로 들어온 폴더 이름(Name)을 기준으로, 회원(user)이 이미 생성한 폴더들을 조회합니다.
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);
        List<Folder> savedFolderList = new ArrayList<>(); //(결과를 담을) 빈 폴더리스트 생성
        for (String folderName : folderNames) { //하나하나 꺼내서
            // 2) 이미 생성한 폴더가 아닌 경우만 폴더 생성
            if (isExistFolderName(folderName, existFolderList)) { //바로 아래에 만들어준 함수가 있다.
                throw new IllegalArgumentException("중복된 폴더명을 제거해 주세요! 폴더명: " + folderName);
            }else {
                Folder folder = new Folder(folderName, user);
                folder = folderRepository.save(folder);
                savedFolderList.add(folder);
                //폴더명 - 양말, 과자, 신발 등을 동시에 추가할 경우, 신발을 만나면 중복되지만
                //@Transactional을 붙여주면 중복되는 순간, 앞에 저장했던 폴더들이 다 rollback된다.
            }
        }
        return savedFolderList;
    }
    //트랜잭션: 데이터베이스에서 데이터에 대한 하나의 논리적 실행단계
    //- 더 이상 쪼갤 수 없는 최소단위의 작업. 모두 저장되거나, 아무 것도 저장되지 않거나를 보장!!

    //@Transactional 붙인거와 아래거와 같음
/*    public List<Folder> addFolders(List<String> folderNames, User user) {
        // 트랜잭션의 시작
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 1) 입력으로 들어온 폴더 이름을 기준으로, 회원이 이미 생성한 폴더들을 조회합니다.
            List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);

            List<Folder> savedFolderList = new ArrayList<>();
            for (String folderName : folderNames) {
                // 2) 이미 생성한 폴더가 아닌 경우만 폴더 생성
                if (isExistFolderName(folderName, existFolderList)) {
                    // Exception 발생!
                    throw new IllegalArgumentException("중복된 폴더명을 제거해 주세요! 폴더명: " + folderName);
                } else {
                    Folder folder = new Folder(folderName, user);
                    // 폴더명 저장
                    folder = folderRepository.save(folder);
                    savedFolderList.add(folder);
                }
            }

            // 트랜잭션 commit
            transactionManager.commit(status);

            return savedFolderList;
        } catch (Exception ex) {
            // 트랜잭션 rollback
            transactionManager.rollback(status);
            throw ex;
        }
    }*/

    private boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        // 기존 폴더 리스트에서 folder name 이 있는지?
        for (Folder existFolder : existFolderList) {
            if (existFolder.getName().equals(folderName)) {
                return true;
            }
        }
        return false;
    }


    // 로그인한 회원이 등록된 모든 폴더 조회
    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }



    // 회원 ID 가 소유한 폴더에 저장되어 있는 상품들 조회
    public Page<Product> getProductsInFolder(
            Long folderId,
            int page,
            int size,
            String sortBy,
            boolean isAsc,
            User user
    ) {
        //이거 3줄이 정렬 세트이다
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Long userId = user.getId();
        return productRepository.findAllByUserIdAndFolderList_Id(userId, folderId, pageable);
    }
}