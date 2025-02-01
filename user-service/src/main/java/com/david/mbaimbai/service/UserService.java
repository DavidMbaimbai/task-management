package com.david.mbaimbai.service;

import com.david.mbaimbai.entity.User;

import java.util.List;

public interface UserService {

    public User getUserProfile(String jwt);
    public List<User> getAllUsers();
}
