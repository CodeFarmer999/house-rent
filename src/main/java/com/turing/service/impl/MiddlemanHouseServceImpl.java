package com.turing.service.impl;

import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.dao.MiddlemanHouseDao;
import com.turing.dao.impl.MiddlemanHouseDaoImpl;
import com.turing.service.MiddlemanHouseService;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/15 10:33
 **/
public class MiddlemanHouseServceImpl implements MiddlemanHouseService {
    private MiddlemanHouseDao dao = new MiddlemanHouseDaoImpl();
    
    @Override
    public List<HouseOfAllPropertities> catHouseOfAllPropertitiesSelf(int middlemanId) {
        return dao.catHouseOfAllPropertitiesSelf(middlemanId);
    }

    @Override
    public List<HouseOfAllPropertities> getHouseOfNotDistribute() {
        return dao.getHouseOfNotDistribute();
    }

    @Override
    public boolean applyForHouse(int houseId, int middlemanId) {
        return dao.applyForHouse(houseId, middlemanId);
    }

    @Override
    public boolean abandonHouse(int houseId, int middlemanId) {
        return dao.abandonHouse(houseId, middlemanId);
    }

    @Override
    public StringBuilder catRentMoney(int middlemanId) {
        return dao.catRentMoney(middlemanId);
    }
}
