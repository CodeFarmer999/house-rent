package com.turing.dao;

import com.turing.entity.Privilege;
import com.turing.entity.RolePrivilege;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 15:32
 **/
public interface RolePrivilegeDao extends BaseDao<RolePrivilege> {
    boolean insertPermissionAssignment(int roleId, int privilegeId);
    
    int deletePermissionAssignment(int roleId, int privilegeId);

    List<Privilege> listPermissionsByRoleId(int roleId);

    List<Privilege> listPermissionsByUserId(int userId);

    List<Privilege> listAllPermissions();
}
