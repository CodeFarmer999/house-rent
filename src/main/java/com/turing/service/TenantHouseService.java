package com.turing.service;

import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.common.vo.HouseOfArea;
import com.turing.common.vo.HouseOfRent;
import com.turing.entity.Type;
import com.turing.entity.User;
import com.turing.entity.Zone;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 10:22
 **/
public interface TenantHouseService {
    List<Zone> filterByZone();

    List<HouseOfRent> filterByRent();
    
    List<HouseOfArea> filterByArea();
    
    List<Type> filterByType();

    List<HouseOfAllPropertities> findHouse(String zone, double rent, double area, String type);

    HouseOfAllPropertities findHouseByHouseId(int houseId);
    
    User catMiddleman(int id);
    
    List<HouseOfAllPropertities> getAllHouses();
}
