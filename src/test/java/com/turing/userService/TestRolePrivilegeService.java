package com.turing.userService;

import com.turing.entity.Privilege;
import com.turing.service.RolePrivilegeService;
import com.turing.service.impl.RolePrivilegeServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 15:43
 **/
public class TestRolePrivilegeService {
    private RolePrivilegeService rolePrivilegeService = new RolePrivilegeServiceImpl();

    /**
     * 测试添加角色权限   ---> 测试成功
     */
    @Test
    public void testInsertRolePrivilege() {
        boolean result = rolePrivilegeService.insertPermissionAssignment(1, 3);
        System.out.println(result ? "添加权限成功" : "添加权限失败");
    }

    /**
     * 测试删除角色权限    ---> 测试成功
     */
    @Test
    public void testDeleteRolePrivilege() {
        boolean result = rolePrivilegeService.deletePermissionAssignment(1, 3);
        System.out.println(result ? "删除权限成功" : "删除权限失败");
    }

    /**
     * 测试查询角色权限 根据roleId    ---> 测试成功
     */
    @Test
    public void testListPermissionsByRoleId() {
        List<Privilege> privileges = rolePrivilegeService.listPermissionsByRoleId(3);
        privileges.forEach(System.out::println);
    }

    /**
     * 测试查询角色权限 根据userId    ---> 测试成功
     */
    @Test
    public void testListPermissionsByUserId() {
        rolePrivilegeService.listPermissionsByUserId(3).forEach(System.out::println);
    }

    /**
     * 测试获取所有角色权限    ---> 测试成功
     */
    @Test
    public void testListAllPrivileges() {
        rolePrivilegeService.listAllPermissions().forEach(System.out::println);
    }
}
