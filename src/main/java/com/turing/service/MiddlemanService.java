package com.turing.service;

import com.turing.entity.User;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:10
 **/
public interface MiddlemanService {
    boolean addMiddleman(User user);
    
    boolean deleteMiddleman(int id);
    
    boolean updateMiddleman(User user);
    
    User getMiddleman(int id);
    
    List<User> getMiddlemans();
}
