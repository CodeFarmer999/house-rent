package com.turing.userService;

import com.turing.common.vo.Function;
import com.turing.entity.User;
import com.turing.service.UserService;
import com.turing.service.impl.UserServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Turing
 * @date 2022/5/9 9:25
 **/
public class TestUserService {
    UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserServiceImpl();
        System.out.println("请输入您的手机号:");
        String phone = scanner.nextLine();
        System.out.println("请输入您的账号信息里的名字:");
        String name = scanner.nextLine();
        System.out.println("请输入您的新密码:");
        String password = scanner.nextLine();
        
        // 随机生成一个6位数的验证码
        int verificationCode = (int) (Math.random() * 900000 + 10000);
        System.out.println("您的验证码是" + verificationCode + ",请输入您的验证码:");
        
        int code = scanner.nextInt();

        while (code != verificationCode) {
            System.out.println("验证码错误，请尝试重新输入:");
            code = scanner.nextInt();
        }

        boolean result = userService.resetPassword(phone, name, password);
        System.out.println(result ? "重置密码成功" : "重置密码失败");
    }

    /**
     * 测试用户登录     ---> 成功
     */
    @Test
    public void testLogin() {
        String username = "yuange";
        String password = "yuange";

        List<Function> functionList = userService.login(username, password);

        // functionList.forEach(System.out::println);

        ArrayList<String> list = new ArrayList<>();

        if (functionList != null) {
            for (Function function : functionList) {
                list.add(function.getPrivilegeName());
            }
        }

        assert functionList != null;

        System.out.println("用户-- " + functionList.get(0).getName() + " --的权限如下：");

        list.forEach(System.out::println);

        System.out.println("请选择您要进行的操作:");

        for (int i = 1; i <= list.size(); i++) {
            System.out.println(i + "." + list.get(i - 1));
        }

        // 因为我这里是测试方法，所以这里就直接输入了，不用输入数字，在你的客户端上运行时，就要输入数字，用scanner输入数字，让choice接收
        int choice = 2;
        int maxChoice = list.size();
        for (int i = 0; i < maxChoice; i++) {
            if (choice == i + 1) {
                receiveActions(list.get(i));
            }
        }
    }


    /**
     * 测试用户注册     ----> 成功
     */
    @Test
    public void testRegister() {
        // 注册管理员
        // User user = new User(1, "lichenxu", "lichenxu", "17025768651", "李晨旭");
        // boolean result = userService.register(user, 2);
        // System.out.println(result ? "注册成功" : "注册失败");

        // 注册普通用户
        User user = new User(1, "yuange", "yuange", "13889699280", "元歌");
        boolean result = userService.register(user, 3);
        System.out.println(result ? "注册成功" : "注册失败");
    }

    /**
     * 测试用户通过手机号找回密码     ---> 成功
     */
    @Test
    public void testGetPasswordByPhone() {
        String phone = "15103271026";
        String name = "硕硕666";
        String password = userService.getPasswordByPhone(phone, name);
        if (password != null) {
            System.out.println("手机号码为" + phone + "的用户的密码是" + password);
        } else {
            System.out.println("手机号码为" + phone + "的用户不存在，或者您输入的name与数据库中的name不一致,请重新尝试!");
        }
    }

    /**
     * 测试用户通过账号、手机号找回密码     ---> 成功
     */
    @Test
    public void testResetPassword() {
        String phone = "15103271026";
        String password = "shuoshuo";
        String name = "硕硕";
        // 随机生成一个6位数的验证码
        int verificationCode = (int) (Math.random() * 900000 + 10000);
        System.out.println("您的验证码是" + verificationCode + ",请输入您的验证码:");
        Scanner scanner = new Scanner(System.in);
        int code = scanner.nextInt();

        while (code != verificationCode) {
            System.out.println("验证码错误，请尝试重新输入:");
            code = scanner.nextInt();
        }
        
        boolean result = userService.resetPassword(phone, name, password);
        System.out.println(result ? "重置密码成功" : "重置密码失败");
        
    }

    public static void receiveActions(String choiceName) {
        switch (choiceName) {
            case "分配角色":
                System.out.println("执行对应的分配角色操作");
                break;
            case "管理员控制":
                System.out.println("执行对应的管理员控制操作");
                break;
            case "租客管理":
                System.out.println("执行对应的租客管理操作");
                break;
            case "房主管理":
                System.out.println("执行对应的房主管理操作");
                break;
            case "中介管理":
                System.out.println("执行对应的中介管理操作");
                break;
            case "个人信息更新":
                System.out.println("执行对应的个人信息更新操作");
                break;
            case "购房":
                System.out.println("执行对应的购房操作");
                break;
            case "个人信息查询":
                System.out.println("执行对应的个人信息查询操作");
                break;
            case "选房":
                System.out.println("执行对应的选房操作");
                break;
            default:
                break;
        }
    }

   

    /**
     * 测试获取普通用户列表     ---> 成功    
     */
    @Test
    public void testSelectUser() {
        List<User> adminList = userService.selectUser();
        adminList.forEach(System.out::println);
    }

  
    /**
     * 测试获取房东列表     ---> 成功    
     */
    @Test
    public void testSelectOwner() {
        List<User> adminList = userService.selectOwner();
        adminList.forEach(System.out::println);
    }

    /**
     * 测试获取中介列表     ---> 成功    
     */
    @Test
    public void testSelectMiddleman() {
        List<User> adminList = userService.selectMiddleman();
        adminList.forEach(System.out::println);
    }

    /**
     * 测试获取所有用户列表     ---> 成功    
     */
    @Test
    public void testGetAll() {
        userService.getAll().forEach(System.out::println);
    }
    
    

}
