package com.turing.userService;

import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.service.MiddlemanHouseService;
import com.turing.service.impl.MiddlemanHouseServceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/15 10:45
 **/
public class TestMiddlemanHouseService {
    private MiddlemanHouseService middlemanHouseService = new MiddlemanHouseServceImpl();

    /**
     * 查看自己管理的房子   ---->成功
     */
    @Test
    public void testCatHouseOfAllPropertitiesSelf() {
        List<HouseOfAllPropertities> houseOfAllPropertities = middlemanHouseService.catHouseOfAllPropertitiesSelf(17);
        if (houseOfAllPropertities.size() > 0) {
            for (HouseOfAllPropertities houseOfAllPropertity : houseOfAllPropertities) {
                System.out.println(houseOfAllPropertity);
            }
        } else {
            System.out.println("您还没有管理的房子!");
        }
    }

    /**
     * 测试查看没有被管理的房子   ---->成功
     */
    @Test
    public void testGetHouseOfNotDistribute() {
        List<HouseOfAllPropertities> houseOfNotDistribute = middlemanHouseService.getHouseOfNotDistribute();
        if (houseOfNotDistribute.size() > 0) {
            for (HouseOfAllPropertities house : houseOfNotDistribute) {
                System.out.println(house);
            }
        } else {
            System.out.println("很抱歉，还没有房子可以申请!");
        }
    }

    /**
     * 测试申请管理房子   ---->成功
     */
    @Test
    public void testApplyForHouse() {
        boolean result = middlemanHouseService.applyForHouse(77, 17);
        System.out.println(result ? "申请成功" : "申请失败");
    }

    /**
     * 放弃管理房子   ---->成功
     */
    @Test
    public void testAbandonHouse() {
        boolean result = middlemanHouseService.abandonHouse(80, 17);
        System.out.println(result ? "放弃成功" : "放弃失败");
    }

    /**
     * 测试查看获得的佣金   ---->成功
     */
    @Test
    public void testCatRentMoney() {
        middlemanHouseService.catRentMoney(15);
    }
}
