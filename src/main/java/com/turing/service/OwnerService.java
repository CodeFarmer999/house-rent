package com.turing.service;

import com.turing.entity.User;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:10
 **/
public interface OwnerService {
    boolean addOwner(User user);
    
    boolean deleteOwner(int id);
    
    boolean updateOwner(User user);
    
    User getOwner(int id);
    
    List<User> getOwners();
}
