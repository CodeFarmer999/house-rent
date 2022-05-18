package com.turing.service;

import com.turing.entity.House;
import com.turing.entity.HouseRent;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 19:45
 **/
public interface HouseRentService {
    boolean addHouseRent(int houseId, int userId, int leftTime);
    
    boolean deleteHouseRent(int houseId, int userId);
    
    List<HouseRent> getAllHouseRent();

    List<HouseRent> getHouseRentByUserId(int userId);
    
    boolean houseDistribute(int houseId, int userId);
    
    List<House> getHouseOfNotDistribute();
}
