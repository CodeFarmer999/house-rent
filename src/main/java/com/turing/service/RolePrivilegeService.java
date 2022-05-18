package com.turing.service;

import com.turing.entity.Privilege;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 15:35
 **/
public interface RolePrivilegeService {
    boolean insertPermissionAssignment(int roleId, int privilegeId);

    boolean deletePermissionAssignment(int roleId, int privilegeId);
    
    List<Privilege> listPermissionsByRoleId(int roleId);
    
    List<Privilege> listPermissionsByUserId(int userId);
    
    List<Privilege> listAllPermissions();
}
