package com.turing.service;

import com.turing.common.vo.Description;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 11:16
 **/
public interface UserRoleService {
    boolean updateUserRole(int userId, int roleId);
    
    List<Description> getAllUserRole();
    
    boolean deleteUserRole(int userId, int roleId);
}
