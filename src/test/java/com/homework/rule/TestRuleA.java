package com.homework.rule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.homework.dto.log.LogBase;
import com.homework.dto.log.LogCharge;
import com.homework.dto.log.LogOpenAccount;
import com.homework.dto.log.LogTransfer;

public class TestRuleA {
	
	RuleA ruleA = new RuleA(1, new BigDecimal(200000), new BigDecimal(1000));
	HashMap<String, Boolean> result = new HashMap<>();
	List<LogBase> logList = new ArrayList<>();
	
	RuleEngine ruleEngine = new RuleEngine();
	
	@Test
	public void ruleA_true_모든조건을_충족하는_경우() {
		
		ruleEngine.addRule(ruleA);
		
		 
		//계좌개설 로그 
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 17, 0), 411, "110-228-1000"));
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 18, 0), 412, "110-228-2000"));
		//충전 로그 
		logList.add(new LogCharge(LocalDateTime.of(2017, 12, 20, 17, 20), 411, "110-228-1000", new BigDecimal(200000), "0107118"));
		// 송금 로그 
		logList.add(new LogTransfer(LocalDateTime.of(2017, 12, 20, 17, 40), 411, "110-228-1000", new BigDecimal(200000), "110-228-2000", 412, new BigDecimal(199900)));
				
		result = ruleEngine.checkFraud(logList);
		

		Assert.assertThat(result, IsMapContaining.hasEntry("RuleA", true));
	}
	
	
	@Test
	public void ruleA_false_잔액이_충분한_경우(){
			
		ruleEngine.addRule(ruleA);
		
		//계좌개설 로그 
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 17, 0), 411, "110-228-1000"));
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 18, 0), 412, "110-228-2000"));
		//충전 로그 
		logList.add(new LogCharge(LocalDateTime.of(2017, 12, 20, 17, 20), 411, "110-228-1000", new BigDecimal(200000), "0107118"));
		// 송금 로그 
		logList.add(new LogTransfer(LocalDateTime.of(2017, 12, 20, 17, 40), 411, "110-228-1000", new BigDecimal(200000), "110-228-2000", 412, new BigDecimal(10000)));
		
		result = ruleEngine.checkFraud(logList);
		
		Assert.assertThat(result, IsMapContaining.hasEntry("RuleA", false));
	}
}
