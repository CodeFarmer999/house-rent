package com.turing.dao.impl;

import cn.hutool.core.lang.UUID;
import com.turing.dao.OrderDao;
import com.turing.entity.House;
import com.turing.entity.HouseRent;
import com.turing.entity.Order;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/16 7:35
 **/
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {
    @Override
    public boolean createOrder(Connection connection, int userId, int allowTime, double rentMoney, boolean rentMoneyIsPay, double middleMoney, boolean middleMoneyIsPay) {
        UUID uuid = UUID.randomUUID();
        int flag = 2;
        int updateCount1 = 0;
        int updateCount2 = 0;
        String sql = "insert into `order`(id, user_id, allow_time, rent_money, rent_money_is_pay, middle_money, middle_money_is_pay)\n" +
                "values (?, ?, ?, ?, ?, ?, ?)";
        try {
            updateCount1 = C3P0Utils.getQueryRunner().update(sql, uuid.toString(), userId, allowTime, rentMoney, rentMoneyIsPay, middleMoney, middleMoneyIsPay);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (rentMoneyIsPay && middleMoneyIsPay) {
            flag = 1;
        }


        try {
            updateCount2 = C3P0Utils.getQueryRunner().update("insert into order_status(order_id, status_id) VALUES (?, ?)", uuid.toString(), flag);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateCount1 > 0 && updateCount2 > 0;
    }

    @Override
    public boolean deleteOrder(String id, int userId) {
        Order query = null;
        int updateCount1 = 0;
        int updateCount2 = 0;
        int updateCount3 = 0;
        int flag = 0;
        int updateCount = 0;
        int houseId = 0;
        try {
            List<Order> orderList = C3P0Utils.getQueryRunner().query("select id                id,\n" +
                    "       user_id           userId,\n" +
                    "       allow_time        allowTime,\n" +
                    "       rent_money        rentMoney,\n" +
                    "       rent_money_is_pay rentMoneyIsPay,\n" +
                    "       middle_money      middleMoney,\n" +
                    "       middle_money_is_pay\n" +
                    "from `order`\n" +
                    "where user_id = ?", new BeanListHandler<>(Order.class), userId);
            for (Order order : orderList) {
                if (!order.getId().equals(id)) {
                    flag++;
                } else {
                    break;
                }
            }

            List<HouseRent> queryList = C3P0Utils.getQueryRunner().query("select id, house_id houseId, user_id userId, left_time leftTime from house_rent where user_id = ?", new BeanListHandler<>(HouseRent.class), userId);
            for (int i = 0; i < queryList.size(); i++) {
                if (i == flag) {
                    HouseRent query1 = C3P0Utils.getQueryRunner().query("select id, house_id houseId, user_id userId, left_time leftTime from house_rent where id = ?", new BeanHandler<>(HouseRent.class), queryList.get(i).getId());
                    houseId = query1.getHouseId();
                    updateCount = C3P0Utils.getQueryRunner().update("delete from house_rent where id = ?", queryList.get(i).getId());
                }
            }

            updateCount3 = C3P0Utils.getQueryRunner().update("update house set is_hire = false where id = ?", houseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            query = C3P0Utils.getQueryRunner().query("select id,\n" +
                    "       user_id           userId,\n" +
                    "       allow_time        allowTime,\n" +
                    "       rent_money        rentMoney,\n" +
                    "       rent_money_is_pay rentMoneyIsPay,\n" +
                    "       middle_money      middleMoney,\n" +
                    "       middle_money_is_pay\n" +
                    "                         middleMoneyIsPay\n" +
                    "from `order`\n" +
                    "where user_id = ?\n" +
                    "  and id = ?", new BeanHandler<>(Order.class), userId, id);
            if (query == null) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            updateCount1 = C3P0Utils.getQueryRunner().update("delete from `order` where id = ? and user_id = ?", id, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert query != null;
        try {
            updateCount2 = C3P0Utils.getQueryRunner().update("delete from order_status where order_id = ?", query.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return updateCount1 > 0 && updateCount2 > 0 && updateCount > 0 && updateCount3 > 0;
    }

    @Override
    public List<Order> getUserOrders(int userId) {
        try {
            List<Order> orderList = C3P0Utils.getQueryRunner().query("select id,\n" +
                    "       user_id             userId,\n" +
                    "       allow_time          allowTime,\n" +
                    "       rent_money          rentMoney,\n" +
                    "       rent_money_is_pay   rentMoneyIsPay,\n" +
                    "       middle_money        middleMoney,\n" +
                    "       middle_money_is_pay middleMoneyIsPay\n" +
                    "from `order`\n" +
                    "where user_id = ?", new BeanListHandler<>(Order.class), userId);
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double getHouseRentMoney(String uuid, int userId) {
        Order order = null;
        int flag = 0;
        int houseId = 0;
        List<Order> orderList = null;
        try {
            orderList = C3P0Utils.getQueryRunner().query("select id                id,\n" +
                    "       user_id           userId,\n" +
                    "       allow_time        allowTime,\n" +
                    "       rent_money        rentMoney,\n" +
                    "       rent_money_is_pay rentMoneyIsPay,\n" +
                    "       middle_money      middleMoney,\n" +
                    "       middle_money_is_pay\n" +
                    "from `order`\n" +
                    "where user_id = ?", new BeanListHandler<>(Order.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Order orderr : orderList) {
            if (!orderr.getId().equals(uuid)) {
                flag++;
            } else {
                break;
            }
        }

        List<HouseRent> queryList = null;
        try {
            queryList = C3P0Utils.getQueryRunner().query("select id, house_id houseId, user_id userId, left_time leftTime from house_rent where user_id = ?", new BeanListHandler<>(HouseRent.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < queryList.size(); i++) {
            if (i == flag) {
                HouseRent query1 = null;
                try {
                    query1 = C3P0Utils.getQueryRunner().query("select id, house_id houseId, user_id userId, left_time leftTime from house_rent where id = ?", new BeanHandler<>(HouseRent.class), queryList.get(i).getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                houseId = query1.getHouseId();

                try {
                    House house = C3P0Utils.getQueryRunner().query("select id, rent, area, is_hire isHire, owner_id ownerId, middleman_id middlemanId from house where id = ?", new BeanHandler<>(House.class), houseId);
                    return house.getRent();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

    @Override
    public boolean reLetOrder(String uuid, int addTime, double addRentMoney, int userId) {
        Order order = null;
        int updateCount = 0;
        int flag = 0;
        int houseId = 0;
        List<Order> orderList = null;
        try {
            orderList = C3P0Utils.getQueryRunner().query("select id                id,\n" +
                    "       user_id           userId,\n" +
                    "       allow_time        allowTime,\n" +
                    "       rent_money        rentMoney,\n" +
                    "       rent_money_is_pay rentMoneyIsPay,\n" +
                    "       middle_money      middleMoney,\n" +
                    "       middle_money_is_pay\n" +
                    "from `order`\n" +
                    "where user_id = ?", new BeanListHandler<>(Order.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Order orderr : orderList) {
            if (!orderr.getId().equals(uuid)) {
                flag++;
            } else {
                break;
            }
        }

        List<HouseRent> queryList = null;
        try {
            queryList = C3P0Utils.getQueryRunner().query("select id, house_id houseId, user_id userId, left_time leftTime from house_rent where user_id = ?", new BeanListHandler<>(HouseRent.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < queryList.size(); i++) {
            if (i == flag) {
                HouseRent query1 = null;
                try {
                    query1 = C3P0Utils.getQueryRunner().query("select id, house_id houseId, user_id userId, left_time leftTime from house_rent where id = ?", new BeanHandler<>(HouseRent.class), queryList.get(i).getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // houseId = query1.getHouseId();
                try {
                    updateCount = C3P0Utils.getQueryRunner().update("update house_rent set left_time = left_time + ? where id = ?", addTime, queryList.get(i).getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        String sql = "select id                id,\n" +
                "       user_id           userId,\n" +
                "       allow_time        allowTime,\n" +
                "       rent_money        rentMoney,\n" +
                "       rent_money_is_pay rentMoneyIsPay,\n" +
                "       middle_money      middleMoney,\n" +
                "       middle_money_is_pay\n" +
                "from `order`\n" +
                "where id = ?";
        try {
            order = C3P0Utils.getQueryRunner().query(sql, new BeanHandler<>(Order.class), uuid);
            if (order == null) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql1 = "update `order` set rent_money = rent_money + ?, allow_time = allow_time + ? where id = ?";
        try {
            updateCount = C3P0Utils.getQueryRunner().update(sql1, addRentMoney, addTime, uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateCount > 0;
    }
}
