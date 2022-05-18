package com.turing.dao;

import com.turing.entity.Order;

import java.sql.Connection;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/16 7:35
 **/
public interface OrderDao extends BaseDao<Order>{
    boolean createOrder(Connection connection,int userId, int allowTime, double rentMoney, boolean rentMoneyIsPay, double middleMoney, boolean middleMoneyIsPay);

    boolean deleteOrder(String id, int userId);

    List<Order> getUserOrders(int userId);

    double getHouseRentMoney(String uuid, int userId);

    boolean reLetOrder(String uuid, int addTime, double addRentMoney, int userId);

}
