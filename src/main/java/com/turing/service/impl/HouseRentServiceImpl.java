package com.turing.service.impl;

import com.turing.dao.HouseRentDao;
import com.turing.dao.impl.HouseRentDaoImpl;
import com.turing.entity.House;
import com.turing.entity.HouseRent;
import com.turing.service.HouseRentService;
import com.turing.utils.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 19:45
 **/
public class HouseRentServiceImpl implements HouseRentService {
    private HouseRentDao dao = new HouseRentDaoImpl();

    @Override
    public String toString() {
        return "HouseRentServiceImpl{" +
                "dao=" + dao +
                '}';
    }

    @Override
    public boolean addHouseRent(int houseId, int userId, int leftTime) {
        int updateCount = 0;
        try {
            updateCount = C3P0Utils.getQueryRunner().update("update house set is_hire = ? where id = ?", true, houseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (updateCount > 0) {
            return dao.addHouseRent(houseId, userId, leftTime);
        }
        return false;
    }

    @Override
    public boolean deleteHouseRent(int houseId, int userId) {
        return dao.deleteHouseRent(houseId, userId);
    }

    @Override
    public List<HouseRent> getAllHouseRent() {
        return dao.getAllHouseRent();
    }

    @Override
    public List<HouseRent> getHouseRentByUserId(int userId) {
        return dao.getHouseRentByUserId(userId);
    }

    @Override
    public boolean houseDistribute(int houseId, int userId) {
        return dao.houseDistribute(houseId, userId);
    }

    @Override
    public List<House> getHouseOfNotDistribute() {
        return dao.getHouseOfNotDistribute();
    }
}
