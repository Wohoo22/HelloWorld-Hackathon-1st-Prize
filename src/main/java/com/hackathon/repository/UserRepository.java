package com.hackathon.repository;

import com.hackathon.entity.User;

public interface UserRepository {
    int getLevel(String username);

    void createUser(String username);

    int increaseLevel(String username);
}