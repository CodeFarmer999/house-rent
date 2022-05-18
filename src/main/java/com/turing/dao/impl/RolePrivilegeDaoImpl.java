package com.turing.dao.impl;

import com.turing.dao.RolePrivilegeDao;
import com.turing.entity.Privilege;
import com.turing.entity.RolePrivilege;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 15:34
 **/
public class RolePrivilegeDaoImpl extends BaseDaoImpl<RolePrivilege> implements RolePrivilegeDao {
    public RolePrivilegeDaoImpl(String tableName) {
        super(tableName);
    }

    @Override
    public boolean insertPermissionAssignment(int roleId, int privilegeId) {
        try {
            if (privilegeId <= 0){
                return false;
            }
            RolePrivilege query = C3P0Utils.getQueryRunner().query("select * from role_privilege where role_id = ? and privilege_id = ?", new BeanHandler<>(RolePrivilege.class), roleId, privilegeId);
            if (query != null) {
                return false;
            }
            int count = C3P0Utils.getQueryRunner().update("insert into role_privilege(role_id, privilege_id) values(?, ?)", roleId, privilegeId);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int deletePermissionAssignment(int roleId, int privilegeId) {
        try {
            if (privilegeId <= 0){
                return 0;
            }
            return C3P0Utils.getQueryRunner().update("delete from role_privilege where role_id = ? and privilege_id = ?", roleId, privilegeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Privilege> listPermissionsByRoleId(int roleId) {
        try {
            List<Privilege> privilegeList = C3P0Utils.getQueryRunner().query("select * from privilege where id in (select privilege_id from role_privilege where role_id = ?)", new BeanListHandler<>(Privilege.class), roleId);
            if (privilegeList != null) {
                return privilegeList;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Privilege> listPermissionsByUserId(int userId) {
        String sql = "select * from privilege where id in (select privilege_id from role_privilege where role_id in (select role_id from user_role where user_id = ?))";
        try {
            List<Privilege> privilegeList = C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(Privilege.class), userId);
            if (privilegeList != null) {
                return privilegeList;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Privilege> listAllPermissions() {
        try {
            return C3P0Utils.getQueryRunner().query("select * from privilege", new BeanListHandler<>(Privilege.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
