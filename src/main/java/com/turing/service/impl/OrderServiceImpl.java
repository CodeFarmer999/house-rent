package com.turing.service.impl;

import com.turing.dao.OrderDao;
import com.turing.dao.impl.OrderDaoImpl;
import com.turing.entity.Order;
import com.turing.service.OrderService;
import com.turing.utils.C3P0Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/16 7:36
 **/
public class OrderServiceImpl implements OrderService {
    @Override
    public String toString() {
        return "OrderServiceImpl{" +
                "dao=" + dao +
                '}';
    }

    private OrderDao dao = new OrderDaoImpl();

    @Override
    public boolean createOrder(int userId, int allowTime, double rentMoney, boolean rentMoneyIsPay, double middleMoney, boolean middleMoneyIsPay) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            return dao.createOrder(connection, userId, allowTime, rentMoney, rentMoneyIsPay, middleMoney, middleMoneyIsPay);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                assert connection != null;
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean deleteOrder(String id, int userId) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            return dao.deleteOrder(id, userId);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                assert connection != null;
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Order> getUserOrders(int userId) {
        return dao.getUserOrders(userId);
    }

    @Override
    public double getHouseRentMoney(String uuid, int userId) {
        return dao.getHouseRentMoney(uuid, userId);
    }

    @Override
    public boolean reLetOrder(String uuid, int addTime, double addRentMoney, int userId) {
        return dao.reLetOrder(uuid, addTime, addRentMoney, userId);
    }
}
