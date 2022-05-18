package com.turing.dao;

import com.turing.common.vo.Description;
import com.turing.entity.UserRole;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 11:14
 **/
public interface UserRoleDao extends BaseDao<UserRole> {
    UserRole updateUserRole(int userId, int roleId);

    List<Description> getAllUserRole();

    boolean deleteUserRole(int userId, int roleId);
}
