package com.turing.dao.impl;

import com.turing.common.vo.Description;
import com.turing.dao.UserRoleDao;
import com.turing.entity.UserRole;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 11:15
 **/
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRoleDao {
    public UserRoleDaoImpl(String tableName) {
        super(tableName);
    }

    @Override
    public UserRole updateUserRole(int userId, int roleId) {
        try {
            return C3P0Utils.getQueryRunner().query("select id, user_id userId, role_id roleId from user_role where user_id = ? and role_id = ?", new BeanHandler<>(UserRole.class), userId, roleId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Description> getAllUserRole() {
        String sql = "select u.id id, u.name name, r.name roleName from user u, role r, user_role ur where u.id = ur.user_id and r.id = ur.role_id";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(Description.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteUserRole(int userId, int roleId) {
        String sql = "delete from user_role where user_id = ? and role_id = ?";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, userId, roleId);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
