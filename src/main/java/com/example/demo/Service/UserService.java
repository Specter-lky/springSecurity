package com.example.demo.Service;

import com.example.demo.Entity.Users;

public interface UserService {
    public Users selectUserByUserName(String userName);
}
