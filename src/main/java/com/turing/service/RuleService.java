package com.turing.service;

import com.turing.entity.Rule;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 19:15
 **/
public interface RuleService {
    boolean addRule(String rule);
    
    boolean deleteRule(int ruleId);
    
    boolean updateRule(int ruleId, String rule);
    
    String getRule(int ruleId);
    
    List<Rule> getAllRules();
}
