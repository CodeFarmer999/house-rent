package com.turing.service;

import com.turing.entity.User;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:10
 **/
public interface TenantService {
    boolean addTenant(User user);
    
    boolean deleteTenant(int id);
    
    boolean updateTenant(User user);
    
    User getTenant(int id);
    
    List<User> getTenants();
}
