package com.turing.dao;

import com.turing.entity.User;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/10 9:52
 **/
public interface RootDao extends BaseDao<User>{
    List<User> selectRoot();

    List<User> selectAdmin();
    
    boolean addAdmin(int id);

    boolean deleteAdmin(int id);

    boolean updateAdmin(User user);
}
