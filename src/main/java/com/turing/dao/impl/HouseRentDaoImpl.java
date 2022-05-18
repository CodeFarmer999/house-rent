package com.turing.dao.impl;

import com.turing.dao.HouseRentDao;
import com.turing.entity.House;
import com.turing.entity.HouseRent;
import com.turing.entity.UserRole;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 19:46
 **/
public class HouseRentDaoImpl extends BaseDaoImpl<HouseRent> implements HouseRentDao {

    @Override
    public boolean addHouseRent(int houseId, int userId, int leftTime) {
        String sql = "insert into house_rent(house_id, user_id, left_time) values(?, ?, ?)";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, houseId, userId, leftTime);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteHouseRent(int houseId, int userId) {
        String sql = "delete from house_rent where house_id = ? and user_id = ?";
        String sql2 = "update house set is_hire = false where house_id = ?";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, houseId, userId);
            int updateCount2 = C3P0Utils.getQueryRunner().update(sql2, houseId);
            return updateCount > 0 && updateCount2 > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<HouseRent> getAllHouseRent() {
        String sql = "select id, house_id houseId, user_id userId, left_time leftTime from house_rent";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseRent.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<HouseRent> getHouseRentByUserId(int userId) {
        String sql = "select id, house_id houseId, user_id userId, left_time leftTime from house_rent where user_id = ?";
        try {
            List<HouseRent> houseRentList = C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseRent.class), userId);
            return houseRentList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean houseDistribute(int houseId, int userId) {
        // 首先判断输入的是不是中介角色
        try {
            UserRole userRole = C3P0Utils.getQueryRunner().query("select user_role.id, user_id userId, role_id roleId from user, user_role where user.id = user_id and user.id = ?", new BeanHandler<>(UserRole.class), userId);
            if (userRole.getRoleId() != 5) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 其次判断房屋是否已经被分配或者房屋是否存在
        // 判断房屋是都存在
        try {
            House house = C3P0Utils.getQueryRunner().query("select id, rent, area, is_hire isHire, owner_id ownerId, middleman_id middlemanId from house where id = ?", new BeanHandler<>(House.class), houseId);
            if (house == null) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 最后判断房屋是否已经被分配
        try {
            House queryHouse = C3P0Utils.getQueryRunner().query("select id, rent, area, is_hire isHire, owner_id ownerId, middleman_id middlemanId from house where id = ?", new BeanHandler<>(House.class), houseId);
            if (queryHouse.getMiddlemanId() != 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // 如果上面的判断都通过了，则可以分配房屋
        String sql = "update house set middleman_id = ? where id = ?";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, userId, houseId);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<House> getHouseOfNotDistribute() {
        String sql = "select id, rent, area, is_hire isHire, owner_id ownerId, middleman_id middlemanId from house where middleman_id = 0";
        try {
            List<House> houseList = C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(House.class));
            return houseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
