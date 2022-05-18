package com.turing.service;

import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.entity.House;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/12 8:53
 **/
public interface HouseService {
    boolean addHouse(int userId, House house, String location, String type);
    
    boolean deleteHouse(int userId, int houseId);
    
    boolean updateHouse(int userId, House house, String location, String type);
    
    List<HouseOfAllPropertities> getHouses(int userId);

    StringBuilder catRentMoney(int ownerId);
}   
