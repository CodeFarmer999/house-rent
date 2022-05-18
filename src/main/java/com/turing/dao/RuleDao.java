package com.turing.dao;

import com.turing.entity.Rule;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 19:16
 **/
public interface RuleDao extends BaseDao<Rule>{
    boolean addRule(String rule);

    boolean deleteRule(int ruleId);

    boolean updateRule(int ruleId, String rule);

    String getRule(int ruleId);

    List<Rule> getAllRules();
}
