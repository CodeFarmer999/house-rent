package com.turing.userService;

import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.common.vo.HouseOfArea;
import com.turing.common.vo.HouseOfRent;
import com.turing.entity.Type;
import com.turing.entity.User;
import com.turing.entity.Zone;
import com.turing.service.TenantHouseService;
import com.turing.service.impl.TenantHouseServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 10:38
 **/
public class TestTenantHouseService {
    private TenantHouseService tenantHouseService = new TenantHouseServiceImpl();

    /**
     * 测试查询所有的房屋地区
     */
    @Test
    public void testFilterByZone() {
        List<Zone> zones = tenantHouseService.filterByZone();
        if (zones.size() > 0) {
            zones.forEach(System.out::println);
        } else {
            System.out.println("没有数据");
        }
    }

    /**
     * 测试查询所有的房屋租金
     */
    @Test
    public void testFilterByRent() {
        List<HouseOfRent> rentList = tenantHouseService.filterByRent();
        if (rentList.size() > 0) {
            rentList.forEach(System.out::println);
        } else {
            System.out.println("没有数据");
        }
    }

    /**
     * 测试查询所有的房屋面积
     */
    @Test
    public void testFilterByArea() {
        List<HouseOfArea> areaList = tenantHouseService.filterByArea();
        if (areaList.size() > 0) {
            areaList.forEach(System.out::println);
        } else {
            System.out.println("没有数据");
        }
    }

    /**
     * 测试查询所有的房屋类型
     */
    @Test
    public void testFilterByType() {
        List<Type> typeList = tenantHouseService.filterByType();
        if (typeList.size() > 0) {
            typeList.forEach(System.out::println);
        } else {
            System.out.println("没有数据");
        }
    }

    /**
     * 测试根据不同条件查询可选房屋
     */
    @Test
    public void testFindHouse() {
        // System.out.println("请问您想要根据哪种条件进行筛选?");
        // System.out.println("1.地区");
        // System.out.println("2.租金");
        // System.out.println("3.面积");
        // System.out.println("4.户型");

        System.out.println("请问您对房屋地区有要求吗?");
        // 如果对房屋地区有要求
        List<Zone> zoneList = tenantHouseService.filterByZone();
        if (zoneList.size() > 0) {
            zoneList.forEach(System.out::println);
        } else {
            System.out.println("很抱歉，目前还没有房屋可供您选择");
        }

        System.out.println("请问您对房屋租金有要求吗?");
        // 如果对房屋租金有要求
        List<HouseOfRent> rentList = tenantHouseService.filterByRent();
        if (rentList.size() > 0) {
            rentList.forEach(System.out::println);
        } else {
            System.out.println("很抱歉，目前还没有房屋可供您选择");
        }
        
        System.out.println("请问您对房屋面积有要求吗?");
        // 如果对房屋面积有要求
        List<HouseOfArea> areaList = tenantHouseService.filterByArea();
        if (areaList.size() > 0) {
            areaList.forEach(System.out::println);
        } else {
            System.out.println("很抱歉，目前还没有房屋可供您选择");
        }
        
        System.out.println("请问您对房屋类型有要求吗?");
        // 如果对房屋类型有要求
        List<Type> typeList = tenantHouseService.filterByType();
        if (typeList.size() > 0) {
            typeList.forEach(System.out::println);
        } else {
            System.out.println("很抱歉，目前还没有房屋可供您选择");
        }
        
        String zone = "沧州市运河区";
        double rent = 10000;
        double area = 150;
        String type = "三室两厅";
        
        List<HouseOfAllPropertities> houseList = tenantHouseService.findHouse(zone, rent, area, type);
        
        if (houseList.size() > 0) {
            houseList.forEach(System.out::println);
        } else {
            System.out.println("很抱歉，目前还没有房屋可供您选择");
        }
    }

    /**
     * 测试查看中介信息
     */
    @Test
    public void testCatMiddleman() {
        User user = tenantHouseService.catMiddleman(20);
        if (user == null) {
            System.out.println("很抱歉，没有该中介，请重新尝试!");
        } else {
            System.out.println("中介id\t中介姓名\t 中介电话");
            System.out.println(user.getId() + "\t\t" + user.getName() + "\t\t " + user.getPhone());
        }
    }
    
    @Test
    public void testGetAllHouses() {
        List<HouseOfAllPropertities> allHouses = tenantHouseService.getAllHouses();
        if (allHouses.size() > 0) {
            allHouses.forEach(System.out::println);
        } else {
            System.out.println("很抱歉，目前还没有房屋可供您选择");
        }
    }
}
