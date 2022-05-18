package com.turing.userService;

import com.turing.entity.User;
import com.turing.service.TenantService;
import com.turing.service.impl.TenantServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:19
 **/
public class TestTenantService {
    private final TenantService tenantService = new TenantServiceImpl();

    /**
     * 测试添加用户    ----> 成功
     */
    @Test
    public void testAddTenant() {
        User user = new User(2, "sunce", "sunce", "17529158944", "孙策");
        boolean result = tenantService.addTenant(user);
        System.out.println(result ? "添加租客成功" : "添加租客失败");
    }

    /**
     * 测试删除用户    ----> 成功
     */
    @Test
    public void testDeleteTenant() {
        boolean result = tenantService.deleteTenant(5);
        System.out.println(result ? "删除租客成功" : "删除租客失败");
    }

    /**
     * 测试修改用户    ----> 成功
     */
    @Test
    public void testUpdateTenant() {
        User user = new User(9, "daqiao", "daqiao", "17529158944", "大乔");
        boolean result = tenantService.updateTenant(user);
        System.out.println(result ? "更新租客成功" : "更新租客失败");
    }

    /**
     * 测试根据id查询单个用户    ----> 成功
     */
    @Test
    public void testGetTenant() {
        User user = tenantService.getTenant(3);
        if (user != null) {
            System.out.println("该租客信息如下:");
            System.out.println(user);
        } else {
            System.out.println("很抱歉，未查询到该租客信息，请重新检查租客id是否正确!!!");
        }
    }
    
    @Test
    public void testGetTenants() {
        List<User> tenants = tenantService.getTenants();
        if (tenants != null) {
            tenants.forEach(System.out::println);
        } else {
            System.out.println("很抱歉，至今还没有租客信息，请先添加租客!!!");
        }
    }
}
