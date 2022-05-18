package com.turing.userService;

import com.turing.entity.House;
import com.turing.entity.HouseRent;
import com.turing.service.HouseRentService;
import com.turing.service.impl.HouseRentServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 20:26
 **/
public class TestHouseRentService {
    private HouseRentService houseRentService = new HouseRentServiceImpl();

    /**
     * 测试添加房屋租赁信息     ---->  成功
     */
    @Test
    public void testAddHouseRent() {
        boolean result = houseRentService.addHouseRent(77, 4, 30);
        System.out.println(result ? "房屋租赁成功" : "房屋租赁失败");
    }

    /**
     * 测试删除房屋租赁信息     ---->  成功
     */
    @Test
    public void testDeleteHouseRent() {
        boolean result = houseRentService.deleteHouseRent(77, 4);
        System.out.println(result ? "房屋租赁删除成功" : "房屋租赁删除失败");
    }

    /**
     * 测试获取所有房屋租赁信息     ---->  成功
     */
    @Test
    public void testGetAllHouseRent() {
        List<HouseRent> allHouseRent = houseRentService.getAllHouseRent();
        if (allHouseRent.size() > 0) {
            for (HouseRent houseRent : allHouseRent) {
                System.out.println(houseRent);
            }
        } else {
            System.out.println("没有房屋租赁信息");
        }
    }

    /**
     * 测试为中介分配房屋     ---->  成功
     */
    @Test
    public void testHouseDistribute() {
        boolean result = houseRentService.houseDistribute(80, 17);
        System.out.println(result ? "房屋分配成功" : "房屋分配失败");
    }

    /**
     * 测试查看未分配房屋列表   ---->  成功
     */
    @Test
    public void testGetHouseOfNotDistribute() {
        List<House> houseOfNotDistribute = houseRentService.getHouseOfNotDistribute();
        if (houseOfNotDistribute.size() > 0) {
            for (House house : houseOfNotDistribute) {
                System.out.println(house);
            }
        } else {
            System.out.println("没有房屋可以分配");
        }
    }
}
