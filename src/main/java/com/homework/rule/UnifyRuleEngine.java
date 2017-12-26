package com.homework.rule;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;


@Component
public class UnifyRuleEngine extends RuleEngine{

	
	public UnifyRuleEngine() {
		super.addRule(new RuleA(1, new BigDecimal(200000), new BigDecimal(1000)));
		super.addRule(new RuleB(7, new BigDecimal(100000), 5));
		super.addRule(new RuleC(2, new BigDecimal(50000), 3));
	}
	
}
