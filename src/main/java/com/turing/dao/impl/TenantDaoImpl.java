package com.turing.dao.impl;

import com.turing.dao.TenantDao;
import com.turing.entity.User;
import com.turing.entity.UserRole;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:09
 **/
public class TenantDaoImpl extends BaseDaoImpl<User> implements TenantDao {

    @Override
    public boolean addTenant(User user) {
        String sql1 = "insert into user(username, password, phone, name) values(?, ?, ?, ?)";
        String sql2 = "insert into user_role(user_id, role_id) values(?, ?)";
        try {
            // 首先查询是否已经存在该用户的username
            User user1 = queryForObject("select * from user where username = ?", user.getUsername());
            if (user1 == null) {
                User userInsert = C3P0Utils.getQueryRunner().insert(sql1, new BeanHandler<>(User.class), user.getUsername(), user.getPassword(), user.getPhone(), user.getName());
                // 查询刚刚插入的user的id
                User userProxy = C3P0Utils.getQueryRunner().query("select id from user where username = ?", new BeanHandler<>(User.class), user.getUsername());
                UserRole userRoleInsert = C3P0Utils.getQueryRunner().insert(sql2, new BeanHandler<>(UserRole.class), userProxy.getId(), 3);
                return userInsert != null && userRoleInsert != null;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteTenant(int id) {
        int updateCount = 0;
        int updateCountOfEnd = 0;
        List<UserRole> query = null;
        List<Integer> list = null;
        String sql1 = "select id id, user_id userId, role_id roleId from user_role where user_id = ?";
        try {
            query = C3P0Utils.getQueryRunner().query(sql1, new BeanListHandler<>(UserRole.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (UserRole userRole : query) {
            if (userRole != null && userRole.getRoleId() == 3 && query.size() > 1) {
                String sql2 = "delete from user_role where role_id = ? and user_id = ?";
                try {
                    updateCount = C3P0Utils.getQueryRunner().update(sql2, 3, id);
                    return updateCount > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (userRole != null && userRole.getRoleId() == 3 && query.size() == 1) {
                try {
                    int update = C3P0Utils.getQueryRunner().update("delete from user_role where user_id = ?", id);
                    int update1Count = C3P0Utils.getQueryRunner().update("delete from user where id = ?", id);
                    return update > 0 && update1Count > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateTenant(User user) {
        String sql = "update user set username = ?, password = ?, phone = ?, name = ? where id = ? and id in (select user_id from user_role where role_id = 3)";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, user.getUsername(), user.getPassword(), user.getPhone(), user.getName(), user.getId());
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getTenant(int id) {
        String sql = "select * from user where id = ? and id in (select user_id from user_role where role_id = 3)";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanHandler<>(User.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getTenants() {
        String sql = "select * from user where id in (select user_id from user_role where role_id = 3)";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User queryForObject(String sql, String username) {
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanHandler<>(User.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
