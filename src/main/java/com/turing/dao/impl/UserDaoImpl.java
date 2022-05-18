package com.turing.dao.impl;

import com.turing.common.vo.Function;
import com.turing.dao.UserDao;
import com.turing.entity.User;
import com.turing.entity.UserRole;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 8:16
 **/
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public User selectByUsernameAndPassword(String username, String password) {
        String sql = "select * from user where username = ? and password = ?";
        return queryForObject(sql, username, password);
    }

    @Override
    public List<Function> selectPrivilegesByUsername(String username) {
        String sql = "select u.name name, p.name privilegeName from privilege p, role_privilege rp, role r, user_role ur, user u where u.id = ur.user_id and r.id = ur.role_id and rp.role_id = r.id and p.id = rp.privilege_id and u.username = ?";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(Function.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUserRole(User user, int roleId) {
        String sql1 = "insert into user(username, password, phone, name) values(?, ?, ?, ?)";
        String sql2 = "insert into user_role(user_id, role_id) values(?, ?)";
        try {
            // 首先查询是否已经存在该用户的username
            User user1 = queryForObject("select * from user where username = ?", user.getUsername());
            if (user1 == null) {
                User userInsert = C3P0Utils.getQueryRunner().insert(sql1, new BeanHandler<>(User.class), user.getUsername(), user.getPassword(), user.getPhone(), user.getName());
                // 查询刚刚插入的user的id
                User userProxy = C3P0Utils.getQueryRunner().query("select id from user where username = ?", new BeanHandler<>(User.class), user.getUsername());
                UserRole userRoleInsert = C3P0Utils.getQueryRunner().insert(sql2, new BeanHandler<>(UserRole.class), userProxy.getId(), roleId);
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
    public String getPasswordByPhone(String phone, String name) {
        String sql = "select * from user where phone = ? and name = ?";
        try {
            User user = C3P0Utils.getQueryRunner().query(sql, new BeanHandler<>(User.class), phone, name);
            if (user != null) {
                return user.getPassword();
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean resetPassword(String phone, String name, String password) {
        String sql = "update user set password = ? where phone = ? and name = ?";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, password, phone, name);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> selectUser() {
        String sql = "select u.* from user u, user_role ur, role r where u.id = ur.user_id and r.id = ur.role_id and r.name = 'user'";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    @Override
    public List<User> selectOwner() {
        String sql = "select u.* from user u, user_role ur, role r where u.id = ur.user_id and r.id = ur.role_id and r.name = 'owner'";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> selectMiddleman() {
        String sql = "select u.* from user u, user_role ur, role r where u.id = ur.user_id and r.id = ur.role_id and r.name = 'middleman'";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User queryForObject(String sql, String username, String password) {
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanHandler<>(User.class), username, password);
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
