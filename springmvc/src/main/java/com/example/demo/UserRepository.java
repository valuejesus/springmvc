package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserRepository {
    public UserModel selectByUser(String username);
}
