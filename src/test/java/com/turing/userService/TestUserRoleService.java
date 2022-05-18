package com.turing.userService;

import com.turing.common.vo.Description;
import com.turing.service.UserRoleService;
import com.turing.service.impl.UserRoleServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 13:44
 **/
public class TestUserRoleService {
    private UserRoleService userRoleService = new UserRoleServiceImpl();

    @Test
    public void testUpdateUserRole() {
        boolean result = userRoleService.updateUserRole(4, 9);
        System.out.println(result ? "更新成功" : "更新失败");
    }

    /**
     * 测试将驼峰字符串转换为下划线字符串
     */
    @Test
    public void testHumpTransfer() {
        System.out.println(humpTransfer("UserRole"));
    }

    private static String humpTransfer(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(str.substring(0, 1).toLowerCase());
        for (int i = 1; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();

    }
    
    @Test
    public void testGetAllUserRole() {
        List<Description> allUserRole = userRoleService.getAllUserRole();
        allUserRole.forEach(System.out::println);
    }
    
    @Test
    public void testDeleteUserRole() {
        boolean result = userRoleService.deleteUserRole(3, 5);
        System.out.println(result ? "删除成功" : "删除失败");
    }
}
