package com.turing.userService;

import com.turing.entity.User;
import com.turing.service.MiddlemanService;
import com.turing.service.impl.MiddlemanServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:19
 **/
public class TestMiddlemanService {
    private final MiddlemanService middlemanService = new MiddlemanServiceImpl();

    /**
     * 测试添加中介    ----> 成功
     */
    @Test
    public void testAddMiddleman() {
        User user = new User(2, "sunce", "sunce", "17529158944", "孙策");
        boolean result = middlemanService.addMiddleman(user);
        System.out.println(result ? "添加中介成功" : "添加中介失败");
    }

    /**
     * 测试删除中介    ----> 成功
     */
    @Test
    public void testDeleteMiddleman() {
        boolean result = middlemanService.deleteMiddleman(14);
        System.out.println(result ? "删除中介成功" : "删除中介失败");
    }

    /**
     * 测试修改中介    ----> 成功
     */
    @Test
    public void testUpdateMiddleman() {
        User user = new User(15, "huowu", "huowu", "17529219816", "不知去向");
        boolean result = middlemanService.updateMiddleman(user);
        System.out.println(result ? "更新中介成功" : "更新中介失败");
    }

    /**
     * 测试根据id查询单个中介    ----> 成功
     */
    @Test
    public void testGetMiddleman() {
        User user = middlemanService.getMiddleman(15);
        if (user != null) {
            System.out.println("该中介信息如下:");
            System.out.println(user);
        } else {
            System.out.println("很抱歉，未查询到该中介信息，请重新检查中介id是否正确!!!");
        }
    }
    
    @Test
    public void testGetMiddlemans() {
        List<User> middlemans = middlemanService.getMiddlemans();
        if (middlemans != null) {
            middlemans.forEach(System.out::println);
        } else {
            System.out.println("很抱歉，至今还没有中介信息，请先添加中介!!!");
        }
    }
}
