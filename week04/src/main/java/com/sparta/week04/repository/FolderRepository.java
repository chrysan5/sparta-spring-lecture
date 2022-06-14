package com.sparta.week04.repository;


import com.sparta.week04.model.Folder;
import com.sparta.week04.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
    List<Folder> findAllByUserAndNameIn(User user, List<String> names);
    // List<String> names(names는 폴더이름을 의미) 안에 user 있는지 찾기

}

