package com.turing.userService;

import com.turing.entity.User;
import com.turing.service.RootService;
import com.turing.service.impl.RootServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/10 9:58
 **/
public class TestRootService {
    
    private final RootService rootService = new RootServiceImpl();

    /**
     * 测试获取管理员列表     ---> 成功    
     */
    @Test
    public void testSelectAdmin() {
        List<User> adminList = rootService.selectAdmin();
        adminList.forEach(System.out::println);
    }

    /**
     * 测试获取root列表     ---> 成功    
     */
    @Test
    public void testSelectRoot() {
        List<User> adminList = rootService.selectRoot();
        adminList.forEach(System.out::println);
    }

    /**
     * 测试添加管理员     ---> 成功    
     */
    @Test
    public void testAddAdmin() {
        System.out.println(rootService.addAdmin(4) ? "添加管理员成功" : "添加管理员失败");
    }

    /**
     * 测试删除管理员     ---> 成功    
     */
    @Test
    public void testDeleteAdmin() {
        boolean result = rootService.deleteAdmin(6);
        System.out.println(result ? "该管理员角色已被删除" : "该管理员角色删除失败");
    }

    /**
     * 测试更改管理员信息    ---> 成功  
     */
    @Test
    public void testupdateAdmin() {
        User user = new User(6, "xialuote", "xialuote", "15873594451", "夏洛特");
        boolean result = rootService.updateAdmin(user);
        System.out.println(result ? "修改管理员信息成功" : "修改管理员信息失败");
    }

}
