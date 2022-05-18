package com.turing.dao;

import com.turing.common.vo.Function;
import com.turing.entity.User;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 1:20
 **/
public interface UserDao extends BaseDao<User>{

    User selectByUsernameAndPassword(String username, String password);
    
    List<Function> selectPrivilegesByUsername(String username);

    boolean addUserRole(User user, int roleId);

    String getPasswordByPhone(String phone, String name);
    
    boolean resetPassword(String phone,String name, String password);
    
    List<User> selectUser();
    
    List<User> selectOwner();
    
    List<User> selectMiddleman();
}
