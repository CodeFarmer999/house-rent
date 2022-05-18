package com.turing.service.impl;

import com.turing.common.vo.Description;
import com.turing.dao.UserRoleDao;
import com.turing.dao.impl.UserRoleDaoImpl;
import com.turing.entity.Role;
import com.turing.entity.User;
import com.turing.entity.UserRole;
import com.turing.service.UserRoleService;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 11:19
 **/
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleDao userRoleDao = new UserRoleDaoImpl("user_role");

    /**
     * 根据用户id设置用户角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return 是否设置成功
     */
    @Override
    public boolean updateUserRole(int userId, int roleId) {
        UserRole userRole1 = null;
        userRole1 = userRoleDao.updateUserRole(userId, roleId);
        User maxUer = null;
        User minUser = null;
        Role maxRole = null;
        Role minRole = null;
        if (userRole1 == null) {
            if (roleId == 1) {
                roleId = 0;
            }
            // userRole1.setRoleId(roleId);
            userRole1 = new UserRole(userId, roleId);
            try {
                maxUer = C3P0Utils.getQueryRunner().query("select * from user where id = (select max(id) from user)", new BeanHandler<>(User.class));
                minUser = C3P0Utils.getQueryRunner().query("select * from user where id = (select min(id) from user)", new BeanHandler<>(User.class));
                maxRole = C3P0Utils.getQueryRunner().query("select * from role where id = (select max(id) from role)", new BeanHandler<>(Role.class));
                minRole = C3P0Utils.getQueryRunner().query("select * from role where id = (select min(id) from role)", new BeanHandler<>(Role.class));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            assert maxUer != null;
            if (userId > maxUer.getId() || userId < minUser.getId() || roleId > maxRole.getId() || roleId < minRole.getId() || userId == 0 || roleId == 0) {
                return false;
            } else {
                try {
                    if (userRole1.getRoleId() <= 0 || userRole1.getRoleId() >= 6) {
                        return false;
                    } else {
                        int updateCount = userRoleDao.insert(userRole1);
                        return updateCount > 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;

        } else {
            return false;
        }

    }

    @Override
    public List<Description> getAllUserRole() {
        return userRoleDao.getAllUserRole();
    }

    @Override
    public boolean deleteUserRole(int userId, int roleId) {
        return userRoleDao.deleteUserRole(userId, roleId);
    }
}
