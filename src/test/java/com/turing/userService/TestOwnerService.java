package com.turing.userService;

import com.turing.entity.User;
import com.turing.service.OwnerService;
import com.turing.service.impl.OwnerServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:19
 **/
public class TestOwnerService {
    private final OwnerService ownerService = new OwnerServiceImpl();

    /**
     * 测试添加房东    ----> 成功
     */
    @Test
    public void testAddOwner() {
        User user = new User(2, "sunce", "sunce", "17529158944", "孙策");
        boolean result = ownerService.addOwner(user);
        System.out.println(result ? "添加房东成功" : "添加房东失败");
    }

    /**
     * 测试删除房东    ----> 成功
     */
    @Test
    public void testDeleteOwner() {
        boolean result = ownerService.deleteOwner(12);
        System.out.println(result ? "删除房东成功" : "删除房东失败");
    }

    /**
     * 测试修改房东    ----> 成功
     */
    @Test
    public void testUpdateOwner() {
        User user = new User(13, "daqiao", "daqiao", "17529158944", "大乔");
        boolean result = ownerService.updateOwner(user);
        System.out.println(result ? "更新房东成功" : "更新房东失败");
    }

    /**
     * 测试根据id查询单个房东    ----> 成功
     */
    @Test
    public void testGetOwner() {
        User user = ownerService.getOwner(13);
        if (user != null) {
            System.out.println("该房东信息如下:");
            System.out.println(user);
        } else {
            System.out.println("很抱歉，未查询到该房东信息，请重新检查房东id是否正确!!!");
        }
    }
    
    @Test
    public void testGetOwners() {
        List<User> owners = ownerService.getOwners();
        if (owners != null) {
            owners.forEach(System.out::println);
        } else {
            System.out.println("很抱歉，至今还没有房东信息，请先添加房东!!!");
        }
    }
}
