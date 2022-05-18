package com.turing.service.impl;

import com.turing.dao.RuleDao;
import com.turing.dao.impl.RuleDaoImpl;
import com.turing.entity.Rule;
import com.turing.service.RuleService;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 19:15
 **/
public class RuleServiceImpl implements RuleService {
    private RuleDao dao = new RuleDaoImpl();
    
    @Override
    public boolean addRule(String rule) {
        return dao.addRule(rule);
    }

    @Override
    public boolean deleteRule(int ruleId) {
        return dao.deleteRule(ruleId);
    }

    @Override
    public boolean updateRule(int ruleId, String rule) {
        return dao.updateRule(ruleId, rule);
    }

    @Override
    public String getRule(int ruleId) {
        return dao.getRule(ruleId);
    }

    @Override
    public List<Rule> getAllRules() {
        return dao.getAllRules();
    }
}
