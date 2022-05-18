package com.turing.dao;

import com.turing.entity.User;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:09
 **/
public interface OwnerDao extends BaseDao<User> {
    boolean addOwner(User user);

    boolean deleteOwner(int id);

    boolean updateOwner(User user);

    User getOwner(int id);

    List<User> getOwners();
}
