package com.turing.dao.impl;

import com.turing.dao.RuleDao;
import com.turing.entity.Rule;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 19:16
 **/
public class RuleDaoImpl extends BaseDaoImpl<Rule> implements RuleDao {

    @Override
    public boolean addRule(String rule) {
        String sql = "insert into rule(name) values(?)";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, rule);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteRule(int ruleId) {
        String sql = "delete from rule where id = ?";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, ruleId);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRule(int ruleId, String rule) {
        String sql = "update rule set name = ? where id = ?";
        try {
            int updateCount = C3P0Utils.getQueryRunner().update(sql, rule, ruleId);
            return updateCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getRule(int ruleId) {
        String sql = "select * from rule where id = ?";
        try {
            Rule query = C3P0Utils.getQueryRunner().query(sql, new BeanHandler<>(Rule.class), ruleId);
            return query.getName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Rule> getAllRules() {
        ArrayList<Rule> list = new ArrayList<>();
        String sql = "select * from rule";
        try {
            List<Rule> queryList = C3P0Utils.getQueryRunner().query(sql, new BeanListHandler<>(Rule.class));
            for (Rule rule : queryList) {
                list.add(rule);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
