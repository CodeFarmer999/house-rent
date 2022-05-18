package com.turing.userService;

import com.turing.entity.Rule;
import com.turing.service.RuleService;
import com.turing.service.impl.RuleServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/14 19:30
 **/
public class TestRuleService {
    private RuleService ruleService = new RuleServiceImpl();
    
    @Test
    public void testAddRule() {
        boolean result = ruleService.addRule("这是第一条规则");
        System.out.println(result ? "制度添加成功" : "制度添加失败");
    }
    
    @Test
    public void testDeleteRule() {
        boolean result = ruleService.deleteRule(1);
        System.out.println(result ? "制度删除成功" : "制度删除失败");
    }
    
    @Test
    public void testUpdateRule() {
        boolean result = ruleService.updateRule(2, "这是新的规则!");
        System.out.println(result ? "制度更新成功" : "制度更新失败");
    }
    
    @Test
    public void testGetRule() {
        String rule = ruleService.getRule(2);
        System.out.println(rule);
    }
    
    @Test
    public void testGetAllRules() {
        List<Rule> allRules = ruleService.getAllRules();
        for (Rule allRule : allRules) {
            System.out.println(allRule);
        }
    }
}
