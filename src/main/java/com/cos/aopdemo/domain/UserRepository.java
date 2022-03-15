package com.cos.aopdemo.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    public List<User> findAll() {
        List<User> users = new ArrayList<User>();

        users.add(new User(1, "ssar", "1234", "0102222"));
        users.add(new User(2, "cos", "1234", "0102222"));
        users.add(new User(3, "love", "1234", "0102222"));

        return users;
    }

    public User findById(int id) {
        return User.builder()
                .id(1)
                .username("유병우")
                .password("1234")
                .phone("01023233231")
                .build();
    }

    public void save(JoinReqDto dto) {
        System.out.println("Insert on DB: " + dto);
    }

    public void delete(int id) {
        System.out.println("DB에 삭제하기");
    }

    public void update(int id, UpdateReqDto dto) {
        throw new IllegalArgumentException("아규먼트를 잘못 넣음");
//        System.out.println("DB에 수정하기");
    }
}
