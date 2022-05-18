package com.turing.dao.impl;

import com.turing.dao.RootDao;
import com.turing.entity.User;
import com.turing.entity.UserRole;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/10 9:53
 **/
public class RootDaoImpl extends BaseDaoImpl<User> implements RootDao {
    /**
     * 查询所有root用户
     * @return root用户
     */
    @Override
    public List<User> selectRoot() {
        String sql = "select u.* from user u, user_role ur, role r where u.id = ur.user_id and r.id = ur.role_id and r.name = 'root'";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有admin用户
     * @return admin用户
     */
    @Override
    public List<User> selectAdmin() {
        String sql = "select u.* from user u, user_role ur, role r where u.id = ur.user_id and r.id = ur.role_id and r.name = 'admin'";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addAdmin(int id) {
        String sql = "select * from user";
        try {
            List<User> list = C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(User.class));
            for (User user : list) {
                if (user.getId() == id) {
                    
                    String s = "select id, user_id userId, role_id roleId from user_role where user_id = ?";
                    List<UserRole> queryList = C3P0Utils.getQueryRunner().query(s, new BeanListHandler<>(UserRole.class), id);
                    if (queryList.size() > 0) {
                        for (UserRole userRole : queryList) {
                            if (userRole.getRoleId() == 2) {
                                return false;
                            }
                        }
                    }
                    // String sql = "update user_role set role_id = 2 where user_id = ?";
                    String sql1 = "insert into user_role(user_id, role_id) values(?, 2)";
                    try {
                        int updateCount = C3P0Utils.getQueryRunner().update(sql1, id);
                        return updateCount > 0;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAdmin(int id) {
        String sql = "update user_role set role_id = 3 where user_id = ? and role_id = 2;";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, id);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAdmin(User user) {
        String sql = "update user set username = ?, password = ?, phone = ?, name = ? where id = ? and id in (select user_id from user_role where role_id = 2)";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, user.getUsername(), user.getPassword(), user.getPhone(), user.getName(), user.getId());
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
