package com.turing.service;

import com.turing.common.vo.Function;
import com.turing.entity.User;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 8:07
 **/
public interface UserService {
    boolean add(User user);

    boolean update(User user);

    boolean del(User user);

    List<User> list();

    List<Function> login(String username, String password);

    boolean register(User user, int roleId);

    String getPasswordByPhone(String phone, String name);

    User loginForUser(String username, String password);

    boolean resetPassword(String username, String name, String password);
    
    List<User> selectUser();

    List<User> selectOwner();

    List<User> selectMiddleman();
    
    List<User> getAll();
}
