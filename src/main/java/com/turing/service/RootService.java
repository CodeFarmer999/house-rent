package com.turing.service;

import com.turing.entity.User;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/10 9:55
 **/
public interface RootService {
    List<User> selectAdmin();

    List<User> selectRoot();

    boolean addAdmin(int id);
    
    boolean deleteAdmin(int id);
    
    boolean updateAdmin(User user);
    
}
