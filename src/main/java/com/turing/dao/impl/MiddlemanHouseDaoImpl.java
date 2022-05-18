package com.turing.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.dao.MiddlemanHouseDao;
import com.turing.entity.House;
import com.turing.entity.Order;
import com.turing.entity.UserRole;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/15 10:34
 **/
public class MiddlemanHouseDaoImpl extends BaseDaoImpl<HouseOfAllPropertities> implements MiddlemanHouseDao {
    @Override
    public List<HouseOfAllPropertities> catHouseOfAllPropertitiesSelf(int middlemanId) {
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
                "  and house_location.house_id = house.id\n" +
                "  and house.middleman_id = ?";
        try {
            List<HouseOfAllPropertities> queryList = C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfAllPropertities.class), middlemanId);
            return queryList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HouseOfAllPropertities> getHouseOfNotDistribute() {
        // String sql = "select id, rent, area, is_hire isHire, owner_id ownerId, middleman_id middlemanId from house where middleman_id = 0";
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
                "  and house_location.house_id = house.id\n" +
                "  and middleman_id = 0";
        try {
            List<HouseOfAllPropertities> houseList = C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfAllPropertities.class));
            return houseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean applyForHouse(int houseId, int middlemanId) {
        // 首先判断输入的是不是中介角色
        try {
            UserRole userRole = C3P0Utils.getQueryRunner().query("select user_role.id, user_id userId, role_id roleId from user, user_role where user.id = user_id and user.id = ?", new BeanHandler<>(UserRole.class), middlemanId);
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
            int updateCount = C3P0Utils.getQueryRunner().update(sql, middlemanId, houseId);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean abandonHouse(int houseId, int middlemanId) {
        String sql = "select id, rent, area, is_hire isHire, owner_id ownerId, middleman_id middlemanId from house where id = ?";
        try {
            House queryHouse = C3P0Utils.getQueryRunner().query(sql, new BeanHandler<>(House.class), houseId);
            if (queryHouse == null || queryHouse.getMiddlemanId() != middlemanId) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "update house set middleman_id = 0 where id = ?";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, houseId);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public StringBuilder catRentMoney(int middlemanId) {
        StringBuilder builder = StrUtil.builder();
        String sql = "select o.id                  id,\n" +
                "       o.user_id userId,\n" +
                "       o.allow_time          allowTime,\n" +
                "       o.rent_money          rentMoney,\n" +
                "       o.rent_money_is_pay   rentMoneyIsPay,\n" +
                "       o.middle_money        middleMoney,\n" +
                "       o.middle_money_is_pay middleMoneyIsPay\n" +
                "from `order` o,\n" +
                "     house_rent hr,\n" +
                "     house h\n" +
                "where hr.house_id = h.id\n" +
                "  and hr.user_id = o.user_id\n" +
                "  and h.middleman_id = ?";

        try {
            double rentMoneyOfAll = 0;
            List<Order> orderList = C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(Order.class), middlemanId);
            if (orderList != null && orderList.size() > 0) {
                builder.append("您的佣金收入如下：");
                for (Order order : orderList) {
                    rentMoneyOfAll += order.getMiddleMoney();
                    builder.append(order).append("\n");
                }
                builder.append("所有订单的租金总和为：").append(rentMoneyOfAll);
            } else {
                builder.append("你还没有卖出房子，加油吧!");
            }
            return builder;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return builder;
    }
}
