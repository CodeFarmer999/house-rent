package com.turing.service.impl;

import com.turing.common.vo.Function;
import com.turing.dao.UserDao;
import com.turing.dao.impl.UserDaoImpl;
import com.turing.entity.User;
import com.turing.service.UserService;
import com.turing.utils.C3P0Utils;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 8:07
 **/
public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    /**
     * 添加用户
     *
     * @param user 用户对象
     * @return 是否添加成功
     */
    @Override
    public boolean add(User user) {
        try {
            int insertCount = dao.insert(user);
            return insertCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     * @return 更新成功返回true，否则返回false
     */
    @Override
    public boolean update(User user) {
        try {
            // int updateCount = dao.update(user);
            // return updateCount > 0;

            int updateCount = C3P0Utils.getQueryRunner().update("update user set password = ?, phone = ?, name = ? where id = ?", user.getPassword(), user.getPhone(), user.getName(), user.getId());
            return updateCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除用户
     *
     * @param user 用户对象
     * @return 删除成功返回true，否则返回false
     */
    @Override
    public boolean del(User user) {
        try {
            int deleteCount = dao.delete(user);
            return deleteCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询所有用户
     *
     * @return 所有用户
     */
    @Override
    public List<User> list() {
        List<User> users = null;
        try {
            users = dao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回true，否则返回false
     */
    @Override
    public List<Function> login(String username, String password) {
        User user = dao.selectByUsernameAndPassword(username, password);
        if (user == null) {
            return null;
        } else {
            return dao.selectPrivilegesByUsername(username);
        }
    }


    /**
     * 根据用户名查询并注册新用户
     *
     * @param user   用户对象
     * @param roleId 角色id
     * @return 注册成功返回true，否则返回false
     */
    @Override
    public boolean register(User user, int roleId) {
        try {
            return dao.addUserRole(user, roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getPasswordByPhone(String phone, String name) {
        return dao.getPasswordByPhone(phone, name);
    }

    @Override
    public User loginForUser(String username, String password) {
        return dao.selectByUsernameAndPassword(username, password);
    }

    @Override
    public boolean resetPassword(String phone, String name, String password) {
        return dao.resetPassword(phone, name, password);
    }

    @Override
    public List<User> selectUser() {
        return dao.selectUser();
    }

    @Override
    public List<User> selectOwner() {
        return dao.selectOwner();
    }

    @Override
    public List<User> selectMiddleman() {
        return dao.selectMiddleman();
    }

    @Override
    public List<User> getAll() {
        try {
            return dao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
