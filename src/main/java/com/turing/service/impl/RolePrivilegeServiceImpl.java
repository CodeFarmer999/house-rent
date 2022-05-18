package com.turing.service.impl;

import com.turing.dao.RolePrivilegeDao;
import com.turing.dao.impl.RolePrivilegeDaoImpl;
import com.turing.entity.Privilege;
import com.turing.service.RolePrivilegeService;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 15:35
 **/
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
    private final RolePrivilegeDao rolePrivilegeDao = new RolePrivilegeDaoImpl("role_privilege");

    /**
     * 增加角色权限
     *
     * @param roleId 角色id
     * @param privilegeId 权限id
     * @return 是否成功
     */
    @Override
    public boolean insertPermissionAssignment(int roleId, int privilegeId) {
        // RolePrivilege rolePrivilege1 = null;
        //
        // rolePrivilege1 = rolePrivilegeDao.insertPermissionAssignment(roleId, privilegeId);
        //
        // if (rolePrivilege1 != null) {
        //     return false;
        // } else {
        //     RolePrivilege rolePrivilege = new RolePrivilege(roleId, privilegeId);
        //     try {
        //         int insertCount = rolePrivilegeDao.insert(rolePrivilege);
        //         if (insertCount > 0) {
        //             return true;
        //         } else {
        //             return false;
        //         }
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // }
        //
        // return false;

         return rolePrivilegeDao.insertPermissionAssignment(roleId, privilegeId);
    }

    /**
     * 删除角色权限
     * @param roleId 角色id
     * @param privilegeId 权限id
     * @return 是否成功
     */
    @Override
    public boolean deletePermissionAssignment(int roleId, int privilegeId) {
        int deleteCount = rolePrivilegeDao.deletePermissionAssignment(roleId, privilegeId);
        return deleteCount > 0;
    }

    /**
     * 查询角色权限
     * @param roleId 角色id
     * @return 权限列表
     */
    @Override
    public List<Privilege> listPermissionsByRoleId(int roleId) {
        return rolePrivilegeDao.listPermissionsByRoleId(roleId);
    }

    @Override
    public List<Privilege> listPermissionsByUserId(int userId) {
        return rolePrivilegeDao.listPermissionsByUserId(userId);
    }

    @Override
    public List<Privilege> listAllPermissions() {
        return rolePrivilegeDao.listAllPermissions();
    }


}
