package com.turing.dao.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.turing.common.vo.HouseOfAllPropertities;
import com.turing.dao.HouseDao;
import com.turing.entity.*;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/12 8:54
 **/
public class HouseDaoImpl extends BaseDaoImpl<House> implements HouseDao {


    @Override
    public boolean addHouse(int userId, House house, String location, String type) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            int updateCount = 0;
            int updateCount1 = 0;
            int updateCount2 = 0;
            int updateCount3 = 0;
            int update = 0;
            int update1 = 0;
            int update2 = 0;
            House targetHouse = null;
            Location query1 = null;

            // 更新house表
            String sql = "insert into house(id, rent, area, is_hire, owner_id, middleman_id) values(?,?,?,?,?,?)";
            updateCount = C3P0Utils.getQueryRunner().update(connection, sql, 0, house.getRent(), house.getArea(), false, userId, 0);


            List<House> queryList = C3P0Utils.getQueryRunner().query(connection, "select * from house where rent = ? and area = ? and owner_id = ?", new BeanListHandler<>(House.class), house.getRent(), house.getArea(), userId);
            if (queryList.size() > 0) {
                int size = queryList.size();
                targetHouse = queryList.get(size - 1);
            }


            // 模拟网络断开连接
            // System.out.println(10 / 0);

            // 更新location表和house_location表
            if (updateCount > 0) {
                String sql1 = "insert into location(id, name) values(?, ?)";

                updateCount1 = C3P0Utils.getQueryRunner().update(connection, sql1, 0, location);
                if (updateCount1 > 0) {
                    String sql2 = "select * from location where name = ?";
                    Location query = C3P0Utils.getQueryRunner().query(connection, sql2, new BeanHandler<Location>(Location.class), location);
                    update = C3P0Utils.getQueryRunner().update(connection, "insert into house_location(house_id, location_id) values(?, ?)", targetHouse.getId(), query.getId());
                }

            } else {
                try {
                    assert connection != null;
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return false;
            }

            // 更新zone表和location_zone表
            if (update > 0) {
                Zone queryZone = null;
                String sql2 = "insert into zone(id, name) values(?, ?)";
                String sql3 = "select * from zone where name = ?";
                String substring = location.substring(0, 6);

                query1 = C3P0Utils.getQueryRunner().query(connection, "select * from location where name = ?", new BeanHandler<>(Location.class), location);


                queryZone = C3P0Utils.getQueryRunner().query(connection, sql3, new BeanHandler<>(Zone.class), substring);

                if (queryZone == null) {
                    // 存入到zone表
                    updateCount2 = C3P0Utils.getQueryRunner().update(connection, sql2, 0, substring);
                }
                Zone query = C3P0Utils.getQueryRunner().query(connection, "select * from zone where name = ?", new BeanHandler<>(Zone.class), substring);
                int zoneId = query.getId();
                // 存入到location_zone表
                String sql4 = "insert into location_zone(id, location_id, zone_id) values(?,?,?)";
                updateCount3 = C3P0Utils.getQueryRunner().update(connection, sql4, 0, query1.getId(), zoneId);
            } else {
                try {
                    assert connection != null;
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return false;

            }

            Type query = C3P0Utils.getQueryRunner().query(connection, "select * from type where name = ?", new BeanHandler<>(Type.class), type);
            if (query == null) {
                int update3 = C3P0Utils.getQueryRunner().update(connection, "insert into type(id, name) values(?, ?)", 0, type);
                if (update3 > 0) {
                    query = C3P0Utils.getQueryRunner().query(connection, "select * from type where name = ?", new BeanHandler<>(Type.class), type);
                    int typeId = query.getId();
                    update2 = C3P0Utils.getQueryRunner().update(connection, "insert into house_type(house_id, type_id) values(?, ?)", targetHouse.getId(), typeId);
                }
            } else {
                update1 = C3P0Utils.getQueryRunner().update(connection, "insert into house_type(id, house_id, type_id) values(?, ?, ?)", 0, targetHouse.getId(), query.getId());
            }

            // 测试事务回滚
            // System.out.println(10 / 0);

            connection.commit();
            connection.setAutoCommit(true);
            return update1 > 0 || update2 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
                connection.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean deleteHouse(int userId, int houseId) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            List<House> houseList = null;
            List<? extends HouseType> query = null;
            int typeId = 0;
            int flag = 0;

            houseList = C3P0Utils.getQueryRunner().query(connection, "select id, rent, area, is_hire isHire, owner_id ownerId, middleman_id middlemanId from house where owner_id = ?", new BeanListHandler<>(House.class), userId);

            for (House house : houseList) {
                if (house.getOwnerId() == userId && house.getId() == houseId) {
                    int update1 = C3P0Utils.getQueryRunner().update(connection, "delete from house where id = ?", houseId);
                    if (update1 > 0) {
                        String sql3 = "select id id, house_id houseId, type_id typeId from house_type where house_id = ?";
                        HouseType houseType = C3P0Utils.getQueryRunner().query(connection, sql3, new BeanHandler<>(HouseType.class), houseId);
                        typeId = houseType.getTypeId();
                        String sql4 = "select id id, house_id houseId, type_id typeId from house_type where type_id = ?";
                        query = C3P0Utils.getQueryRunner().query(connection, sql4, new BeanListHandler<>(houseType.getClass()), typeId);

                        String sql2 = "delete from house_type where house_id = ?";
                        int update2 = C3P0Utils.getQueryRunner().update(connection, sql2, houseId);
                        if (update2 > 0) {
                            if (query.size() > 1) {
                                int update4 = C3P0Utils.getQueryRunner().update("delete from house_location where house_id = ?", houseId);
                                if (update4 > 0) {
                                    String sql6 = "select house_id houseId, location_id locationId from house_location where house_id = ?";
                                    HouseLocation query1 = C3P0Utils.getQueryRunner().query(connection, sql6, new BeanHandler<>(HouseLocation.class), houseId);
                                    int locationId = query1.getLocationId();
                                    Location query2 = C3P0Utils.getQueryRunner().query("select * from location where id = ?", new BeanHandler<>(Location.class), locationId);
                                    String name = query2.getName();
                                    String substring = name.substring(0, 6);
                                    List<Location> query3 = C3P0Utils.getQueryRunner().query("select * from location", new BeanListHandler<>(Location.class));

                                    for (Location location : query3) {
                                        String name1 = location.getName();
                                        String substring1 = name1.substring(0, 6);
                                        if (substring1.equals(substring)) {
                                            flag++;
                                        }
                                    }
                                    String sql7 = "delete from location where id = ?";
                                    int update3 = C3P0Utils.getQueryRunner().update(connection, sql7, locationId);
                                    int update6 = C3P0Utils.getQueryRunner().update("delete from location_zone where location_id = ?", locationId);
                                    if (update3 > 0 && update6 > 0) {
                                        if (flag > 1) {
                                            connection.commit();
                                            connection.setAutoCommit(true);
                                            return true;
                                        } else if (flag == 1) {
                                            int update5 = C3P0Utils.getQueryRunner().update("delete from zone where name = ?", substring);
                                            if (update5 > 0) {
                                                connection.commit();
                                                connection.setAutoCommit(true);
                                                return true;
                                            }
                                        } else {
                                            connection.commit();
                                            connection.setAutoCommit(true);
                                            return false;
                                        }
                                    }
                                }
                            } else if (query.size() == 1) {
                                String sql5 = "delete from type where id = ?";
                                int update = C3P0Utils.getQueryRunner().update(connection, sql5, typeId);
                                if (update > 0) {
                                    int update4 = C3P0Utils.getQueryRunner().update("delete from house_location where house_id = ?", houseId);
                                    if (update4 > 0) {
                                        String sql6 = "select house_id houseId, location_id locationId from house_location where house_id = ?";
                                        HouseLocation query1 = C3P0Utils.getQueryRunner().query(connection, sql6, new BeanHandler<>(HouseLocation.class), houseId);
                                        int locationId = query1.getLocationId();
                                        Location query2 = C3P0Utils.getQueryRunner().query("select * from location where id = ?", new BeanHandler<>(Location.class), locationId);
                                        String name = query2.getName();
                                        String substring = name.substring(0, 6);
                                        List<Location> query3 = C3P0Utils.getQueryRunner().query("select * from location", new BeanListHandler<>(Location.class));

                                        for (Location location : query3) {
                                            String name1 = location.getName();
                                            String substring1 = name1.substring(0, 6);
                                            if (substring1.equals(substring)) {
                                                flag++;
                                            }
                                        }
                                        String sql7 = "delete from location where id = ?";
                                        int update3 = C3P0Utils.getQueryRunner().update(connection, sql7, locationId);
                                        int update6 = C3P0Utils.getQueryRunner().update("delete from location_zone where location_id = ?", locationId);
                                        if (update3 > 0 && update6 > 0) {
                                            if (flag == 1) {
                                                int update5 = C3P0Utils.getQueryRunner().update("delete from zone where name = ?", substring);
                                                if (update5 > 0) {
                                                    connection.commit();
                                                    connection.setAutoCommit(true);
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        return false;
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
                connection.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateHouse(int userId, House house, String location, String type) {
        Connection connection = null;
        int updateCount3 = 0;
        int updateCount4 = 0;
        int update = 0;
        boolean flag = false;
        try {
            connection = C3P0Utils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            List<HouseOfAllPropertities> houses = getHouses(userId);
            for (HouseOfAllPropertities houseOfAllPropertities : houses) {
                if (houseOfAllPropertities.getId() == house.getId()) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                return false;
            }

            // 更新house表
            String sql1 = "update house set rent = ?,area = ?,is_hire = ?,middleman_id = ? where id = ?";

            int updateCount1 = C3P0Utils.getQueryRunner().update(connection, sql1, house.getRent(), house.getArea(), false, 0, house.getId());

            // 更新type表以及house_type表
            String sql2 = "select * from house h, type t, house_type ht where h.id = ht.house_id and ht.type_id = t.id and h.id = ?";

            Type queryType = C3P0Utils.getQueryRunner().query(connection, sql2, new BeanHandler<>(Type.class), house.getId());

            // 先去数据库中查找到该房屋的原来的type的name
            String name = queryType.getName();

            // 如果原来的type的name和新的type的name不一样，那么就更新type表和house_type表
            if (!name.equals(type)) {
                // 更新type表
                String sql3 = "select * from type where name = ?";
                // 查询type表中是否有该类型
                List<Type> queryList = C3P0Utils.getQueryRunner().query(connection, sql3, new BeanListHandler<>(Type.class), type);
                if (queryList.size() == 0) {
                    // 如果没有该类型，那么就插入一条新的type记录
                    String sql4 = "insert into type(id, name) values(?, ?)";
                    int updateCount2 = C3P0Utils.getQueryRunner().update(connection, sql4, 0, type);
                    // 查询刚刚插入的type的id
                    String sql5 = "select * from type where name = ?";
                    Type query = C3P0Utils.getQueryRunner().query(connection, sql5, new BeanHandler<>(Type.class), type);
                    // 刚刚新插入的id
                    int id = query.getId();

                    // 如果house_type表中只有一条记录是原来的该name对应的type，则删除掉type表中对应的type_id的记录
                    String sql6 = "select id id, house_id houseId, type_id typeId from house_type where house_id = ?";
                    HouseType query1 = C3P0Utils.getQueryRunner().query(connection, sql6, new BeanHandler<>(HouseType.class), house.getId());
                    int typeId = query1.getTypeId();
                    String sql7 = "select id id, house_id houseId, type_id typeId from house_type where type_id = ?";
                    List<HouseType> query2 = C3P0Utils.getQueryRunner().query(connection, sql7, new BeanListHandler<>(HouseType.class), typeId);
                    if (query2.size() == 1) {
                        String sql8 = "delete from type where id = ?";
                        updateCount3 = C3P0Utils.getQueryRunner().update(connection, sql8, query2.get(0).getTypeId());
                    }

                    // 更新house_type表
                    String sql9 = "update house_type set type_id = ? where house_id = ?";
                    update = C3P0Utils.getQueryRunner().update(connection, sql9, id, house.getId());
                } else {
                    // 如果有该类型
                    // 查询原来的type的id
                    String sql10 = "select ht.id id, ht.house_id houseId, ht.type_id typeId from house h, house_type ht where h.id = ht.house_id and h.id = ?";
                    HouseType query = C3P0Utils.getQueryRunner().query(connection, sql10, new BeanHandler<>(HouseType.class), house.getId());
                    int typeId = query.getTypeId();
                    String sql11 = "select id id, house_id houseId, type_id typeId from house_type where type_id = ?";
                    List<HouseType> query1 = C3P0Utils.getQueryRunner().query(connection, sql11, new BeanListHandler<>(HouseType.class), typeId);
                    if (query1.size() == 1) {
                        int updateCount5 = C3P0Utils.getQueryRunner().update(connection, "delete from type where id = ?", query1.get(0).getTypeId());
                    }

                    // 更新house_type表
                    String sql12 = "select id, name from type where name = ?";
                    Type query2 = C3P0Utils.getQueryRunner().query(connection, sql12, new BeanHandler<>(Type.class), type);
                    int id = query2.getId();

                    String sql13 = "update house_type set type_id = ? where house_id = ?";
                    update = C3P0Utils.getQueryRunner().update(connection, sql13, id, house.getId());

                }

                if (update > 0) {
                    String sql = "select l.id id, l.name name from location l, house h, house_location hl where h.id = hl.house_id and hl.location_id = l.id and h.id = ?";
                    Location query = C3P0Utils.getQueryRunner().query(connection, sql, new BeanHandler<>(Location.class), house.getId());
                    // 先获取到原来的位置名
                    String name1 = query.getName();
                    // 如果传过来的location与之前的不一样了
                    if (!name1.equals(location)) {
                        String s1 = "update location set name = ? where id = ?";
                        int update1 = C3P0Utils.getQueryRunner().update(connection, s1, location, query.getId());

                        if (update1 > 0) {
                            String substring = location.substring(0, 6);
                            // 先去zone表中查看是否有该地区
                            String s3 = "select id, name from zone where name = ?";
                            Zone query1 = C3P0Utils.getQueryRunner().query(connection, s3, new BeanHandler<>(Zone.class), substring);
                            if (query1 == null) {
                                // 如果没有该地区，则插入一条新的地区
                                String s4 = "insert into zone(name) values(?)";
                                int update2 = C3P0Utils.getQueryRunner().update(connection, s4, substring);
                                if (update2 > 0) {
                                    String s5 = "select id from zone where name = ?";
                                    Zone query2 = C3P0Utils.getQueryRunner().query(connection, s5, new BeanHandler<>(Zone.class), substring);
                                    int id = query2.getId();
                                    // 在zone_location表中修改该location的zone_id
                                    String s6 = "update location_zone set zone_id = ? where location_id = ?";
                                    int update3 = C3P0Utils.getQueryRunner().update(connection, s6, id, query.getId());
                                    connection.commit();
                                    connection.setAutoCommit(true);
                                    return update3 > 0;
                                }
                            } else {
                                // 如果有该地区，则直接修改zone_location表中的zone_id
                                String s7 = "update location_zone set zone_id = ? where location_id = ?";
                                int update2 = C3P0Utils.getQueryRunner().update(connection, s7, query1.getId(), query.getId());
                                connection.commit();
                                connection.setAutoCommit(true);
                                return update2 > 0;
                            }
                        }
                    } else {
                        return true;
                    }
                }
            } else {
                String sql = "select l.id id, l.name name from location l, house h, house_location hl where h.id = hl.house_id and hl.location_id = l.id and h.id = ?";
                Location query = C3P0Utils.getQueryRunner().query(connection, sql, new BeanHandler<>(Location.class), house.getId());
                // 先获取到原来的位置名
                String name1 = query.getName();
                // 如果传过来的location与之前的不一样了
                if (!name1.equals(location)) {
                    String s1 = "update location set name = ? where id = ?";
                    int update1 = C3P0Utils.getQueryRunner().update(connection, s1, location, query.getId());

                    if (update1 > 0) {
                        String substring = location.substring(0, 6);
                        // 先去zone表中查看是否有该地区
                        String s3 = "select id, name from zone where name = ?";
                        Zone query1 = C3P0Utils.getQueryRunner().query(connection, s3, new BeanHandler<>(Zone.class), substring);
                        if (query1 == null) {
                            // 如果没有该地区，则插入一条新的地区
                            String s4 = "insert into zone(name) values(?)";
                            int update2 = C3P0Utils.getQueryRunner().update(connection, s4, substring);
                            if (update2 > 0) {
                                String s5 = "select id from zone where name = ?";
                                Zone query2 = C3P0Utils.getQueryRunner().query(connection, s5, new BeanHandler<>(Zone.class), substring);
                                int id = query2.getId();
                                // 在zone_location表中修改该location的zone_id
                                String s6 = "update location_zone set zone_id = ? where location_id = ?";
                                int update3 = C3P0Utils.getQueryRunner().update(connection, s6, id, query.getId());
                                connection.commit();
                                connection.setAutoCommit(true);
                                return update3 > 0;
                            }
                        } else {

                            String s8 = "select id, name from zone where name = ?";
                            Zone query2 = C3P0Utils.getQueryRunner().query(connection, s8, new BeanHandler<>(Zone.class), substring);
                            int id = query2.getId();
                            String s9 = "select id id, location_id locationId, zone_id zoneId from location_zone where zone_id = ?";
                            List<LocationZone> query3 = C3P0Utils.getQueryRunner().query(connection, s9, new BeanListHandler<>(LocationZone.class), id);
                            if (query3.size() == 1) {
                                String s10 = "delete from zone where id = ?";
                                int update2 = C3P0Utils.getQueryRunner().update(connection, s10, id);
                            }

                            // 如果有该地区，则直接修改zone_location表中的zone_id
                            String s7 = "update location_zone set zone_id = ? where location_id = ?";
                            int update2 = C3P0Utils.getQueryRunner().update(connection, s7, query1.getId(), query.getId());
                            connection.commit();
                            connection.setAutoCommit(true);
                            return update2 > 0;
                        }
                    }
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
                connection.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<HouseOfAllPropertities> getHouses(int userId) {
        String sql = "select house.id      id,\n" +
                "       rent,\n" +
                "       area,\n" +
                "       is_hire != 0       isHire,\n" +
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
                "  and house.owner_id = ?";
        try {
            return C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(HouseOfAllPropertities.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StringBuilder catRentMoney(int ownerId) {
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
                "  and h.owner_id = ?";

        try {
            double rentMoneyOfAll = 0;
            List<Order> orderList = C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(Order.class), ownerId);
            orderList = CollUtil.distinct(orderList);
            if (orderList != null && orderList.size() > 0) {
                builder.append("您的租金收入如下：\n");
                for (Order order : orderList) {
                    rentMoneyOfAll += order.getRentMoney();
                    builder.append(order).append("\n");
                }
                builder.append("所有订单的租金总和为：").append(rentMoneyOfAll).append("\n");
            } else {
                builder.append("您的中介还没有卖出房子，加油吧!\n");
            }
            return builder;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return builder;
    }
}
