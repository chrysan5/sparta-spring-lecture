package com.sparta.week04.dto;

import com.sparta.week04.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@NoArgsConstructor
public class ApiUseTimeDto {
    private Long id;
    private String username;
    private Long totalTime;

}
