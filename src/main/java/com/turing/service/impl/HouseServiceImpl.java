package com.turing.service.impl;

import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.dao.HouseDao;
import com.turing.dao.impl.HouseDaoImpl;
import com.turing.entity.House;
import com.turing.service.HouseService;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/12 8:53
 **/
public class HouseServiceImpl implements HouseService {
    private HouseDao dao = new HouseDaoImpl();
    
    @Override
    public boolean addHouse(int userId, House house, String location, String type) {
        return dao.addHouse(userId, house, location, type);
    }

    @Override
    public boolean deleteHouse(int userId, int houseId) {
        return dao.deleteHouse(userId, houseId);
    }

    @Override
    public boolean updateHouse(int userId, House house, String location, String type) {
        return dao.updateHouse(userId, house, location, type);
    }

    @Override
    public List<HouseOfAllPropertities> getHouses(int userId) {
        List<HouseOfAllPropertities> houses = dao.getHouses(userId);
        if (houses != null) {
            return houses;
        } else {
            return null;
        }
    }

    @Override
    public StringBuilder catRentMoney(int ownerId) {
        return dao.catRentMoney(ownerId);
    }

}
