package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoEntity {
    private String id; // 오브젝트 아이디
    private String userId; // 이 오브젝트를 생성한 유저의 아이디
    private String title; // 투두(todo) 타이틀
    private boolean done;

}
