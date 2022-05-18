package com.turing.dao.impl;

import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.common.vo.HouseOfArea;
import com.turing.common.vo.HouseOfRent;
import com.turing.dao.TenantHouseDao;
import com.turing.entity.*;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 10:29
 **/
public class TenantHouseDaoImpl extends BaseDaoImpl<House> implements TenantHouseDao {
    @Override
    public List<Zone> filterByZone() {
        String sql = "select id, name from zone";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(Zone.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HouseOfRent> filterByRent() {
        String sql = "select rent from house";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfRent.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HouseOfArea> filterByArea() {
        String sql = "select area from house";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfArea.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Type> filterByType() {
        String sql = "select * from type";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(Type.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HouseOfAllPropertities> findHouse(String zone, double rent, double area, String type) {
        String sql = "select house.id      id,\n" +
                "       rent,\n" +
                "       area,\n" +
                "       is_hire       isHire,\n" +
                "       owner_id      ownerId,\n" +
                "       middleman_id  middlemanId,\n" +
                "       type.name     typeName,\n" +
                "       location.name locationName\n" +
                "from house,\n" +
                "     type,\n" +
                "     house_type,\n" +
                "     location,\n" +
                "     house_location,\n" +
                "zone,\n" +
                "location_zone\n" +
                "where house.id = house_type.house_id\n" +
                "  and house_type.type_id = type.id\n" +
                "  and location.id = house_location.location_id\n" +
                "  and house_location.house_id = house.id\n" +
                "and location.id = location_zone.location_id\n" +
                "and zone.id = location_zone.zone_id\n" +
                "  and zone.name like ? and rent <= ? and area >= ? and type.name like ? and is_hire = 0 and middleman_id != 0";

        zone = zone != null ? zone : "";

        rent = rent != 0 ? rent : 100000;

        area = area;

        type = type != null ? type : "";

        if (zone.equals("") && type.equals("")) {
            sql = sql.replace("and zone.name like ?", "");
            sql = sql.replace("and type.name like ?", "");
            try {
                return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfAllPropertities.class), rent, area);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (zone.equals("") && !type.equals("")) {
            sql = sql.replace("and zone.name like ?", "");
            try {
                return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfAllPropertities.class), rent, area, '%' + type + '%');
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (!zone.equals("") && type.equals("")) {
            sql = sql.replace("and type.name like ?", "");
            try {
                return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfAllPropertities.class), '%' + zone + '%', rent, area);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfAllPropertities.class), '%' + zone + '%', rent, area, '%' + type + '%');
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public HouseOfAllPropertities findHouseByHouseId(int houseId) {
        String sql = "select house.id      id,\n" +
                "       rent,\n" +
                "       area,\n" +
                "       is_hire       isHire,\n" +
                "       owner_id      ownerId,\n" +
                "       middleman_id  middlemanId,\n" +
                "       type.name     typeName,\n" +
                "       location.name locationName\n" +
                "from house,\n" +
                "     type,\n" +
                "     house_type,\n" +
                "     location,\n" +
                "     house_location,\n" +
                "zone,\n" +
                "location_zone\n" +
                "where house.id = house_type.house_id\n" +
                "  and house_type.type_id = type.id\n" +
                "  and location.id = house_location.location_id\n" +
                "  and house_location.house_id = house.id\n" +
                "and location.id = location_zone.location_id\n" +
                "and zone.id = location_zone.zone_id\n" +
                "  and house.id = ? and is_hire = 0 and middleman_id != 0";

        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanHandler<>(HouseOfAllPropertities.class), houseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User catMiddleman(int id) {
        String sql = "select * from user where id = ?";
        String sql1 = "select id, user_id userId, role_id roleId from user_role where user_id = ?";
        try {
            UserRole query = C3P0Utils.getQueryRunner().query(sql1, new BeanHandler<>(UserRole.class), id);
            if (query != null && query.getRoleId() != 5) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanHandler<>(User.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HouseOfAllPropertities> getAllHouses() {
        String sql = "select house.id      id,\n" +
                "       rent,\n" +
                "       area,\n" +
                "       is_hire       isHire,\n" +
                "       owner_id      ownerId,\n" +
                "       middleman_id  middlemanId,\n" +
                "       type.name     typeName,\n" +
                "       location.name locationName\n" +
                "from house,\n" +
                "     type,\n" +
                "     house_type,\n" +
                "     location,\n" +
                "     house_location\n" +
                "where house.id = house_type.house_id\n" +
                "  and house_type.type_id = type.id\n" +
                "  and location.id = house_location.location_id\n" +
                "  and house_location.house_id = house.id and middleman_id != 0 and house.is_hire = false \n";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfAllPropertities.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
