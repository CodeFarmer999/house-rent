package com.turing.service;

import com.turing.common.vo.HouseOfAllPropertities;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/15 10:33
 **/
public interface MiddlemanHouseService {
    List<HouseOfAllPropertities> catHouseOfAllPropertitiesSelf(int middlemanId);

    List<HouseOfAllPropertities> getHouseOfNotDistribute();
    
    boolean applyForHouse(int houseId, int middlemanId);
    
    boolean abandonHouse(int houseId, int middlemanId);

    StringBuilder catRentMoney(int middlemanId);
}
