package com.turing.service.impl;

import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.common.vo.HouseOfArea;
import com.turing.common.vo.HouseOfRent;
import com.turing.dao.TenantHouseDao;
import com.turing.dao.impl.TenantHouseDaoImpl;
import com.turing.entity.Type;
import com.turing.entity.User;
import com.turing.entity.Zone;
import com.turing.service.TenantHouseService;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 10:26
 **/
public class TenantHouseServiceImpl implements TenantHouseService {
    private final TenantHouseDao dao = new TenantHouseDaoImpl();

    @Override
    public String toString() {
        return "TenantHouseServiceImpl{" +
                "dao=" + dao +
                '}';
    }

    @Override
    public List<Zone> filterByZone() {
        return dao.filterByZone();
    }

    @Override
    public List<HouseOfRent> filterByRent() {
        return dao.filterByRent();
    }

    @Override
    public List<HouseOfArea> filterByArea() {
        return dao.filterByArea();
    }

    @Override
    public List<Type> filterByType() {
        return dao.filterByType();
    }

    @Override
    public List<HouseOfAllPropertities> findHouse(String zone, double rent, double area, String type) {
        return dao.findHouse(zone,rent,area,type);
    }

    @Override
    public HouseOfAllPropertities findHouseByHouseId(int houseId) {
        return dao.findHouseByHouseId(houseId);
    }

    @Override
    public User catMiddleman(int id) {
        return dao.catMiddleman(id);
    }

    @Override
    public List<HouseOfAllPropertities> getAllHouses() {
        return dao.getAllHouses();
    }
}
