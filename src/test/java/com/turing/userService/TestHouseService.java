package com.turing.userService;

import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.entity.House;
import com.turing.service.HouseService;
import com.turing.service.impl.HouseServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/12 9:26
 **/
public class TestHouseService {
    private HouseService houseService = new HouseServiceImpl();

    /**
     * 测试添加房屋    ---->成功
     */
    @Test
    public void testAddHouse() {
        int userId = 15;
        House house = new House(3600, 120, userId);
        String location = "沧州市泊头市C777";
        String type = "四室二厅";
        boolean result = houseService.addHouse(userId, house, location, type);
        System.out.println(result ? "房屋添加成功!" : "房屋添加失败!");
    }

    /**
     * 测试删除房屋    ---->成功
     */
    @Test
    public void testDeleteHouse() {
        int houseId = 47;
        int userId = 15;
        boolean result = houseService.deleteHouse(userId, houseId);
        System.out.println(result ? "房屋删除成功!" : "房屋删除失败!");
    }

    @Test
    public void testUpdateHouse() {
        House house = new House(67, 3600, 120, 15);
        boolean result = houseService.updateHouse(15, house, "沧州市运河区C999", "四室二厅");
        System.out.println(result ? "房屋修改成功!" : "房屋修改失败!");
    }

    /**
     * 测试查询房屋    ---->成功
     */
    @Test
    public void testGetHouses() {
        List<HouseOfAllPropertities> houses = houseService.getHouses(13);
        houses.forEach(System.out::println);
    }
    
    @Test
    public void testCatRentMoney() {
        StringBuilder stringBuilder = houseService.catRentMoney(13);
        System.out.println(stringBuilder.toString());
    }
}
