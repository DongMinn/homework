package com.homework.rule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Assert;
import org.junit.Test;

import com.homework.dto.log.LogBase;
import com.homework.dto.log.LogCharge;
import com.homework.dto.log.LogOpenAccount;
import com.homework.dto.log.LogReceive;
import com.homework.dto.log.LogTransfer;

public class TestRuleB {
	
	RuleB ruleB = new RuleB(7, new BigDecimal(100000), 5);
	
	List<LogBase> logList = new ArrayList<>();
	HashMap<String, Boolean> result = new HashMap<>();
	RuleEngine ruleEngine = new RuleEngine();
	
	@Test
	public void ruleB_true_모든조건을_충족하는_경우(){
		
		ruleEngine.addRule(ruleB);
		
		
	
		//충전 로그 
		logList.add(new LogCharge(LocalDateTime.of(2017, 12, 20, 17, 20), 411, "110-228-1000", new BigDecimal(200000), "0107118"));
		// 송금 로그 
		logList.add(new LogTransfer(LocalDateTime.of(2017, 12, 20, 17, 40), 411, "110-228-1000", new BigDecimal(200000), "110-228-2000", 412, new BigDecimal(10000)));
		//계좌개설 로그 
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 17, 0), 411, "110-228-1000"));
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 18, 0), 412, "110-228-2000"));
		// 받기 로그
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 22, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 23, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 24, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 25, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		
		
		result = ruleEngine.checkFraud(logList);
		
		Assert.assertThat(result, IsMapContaining.hasEntry("RuleB", true));
	}
	
	@Test
	public void ruleB_false_받기금액이_적은_경우(){
	
		ruleEngine.addRule(ruleB);
		//충전 로그 
		logList.add(new LogCharge(LocalDateTime.of(2017, 12, 20, 17, 20), 411, "110-228-1000", new BigDecimal(200000), "0107118"));
		// 송금 로그 
		logList.add(new LogTransfer(LocalDateTime.of(2017, 12, 20, 17, 40), 411, "110-228-1000", new BigDecimal(200000), "110-228-2000", 412, new BigDecimal(10000)));
		//계좌개설 로그 
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 17, 0), 411, "110-228-1000"));
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 18, 0), 412, "110-228-2000"));
		// 받기 로그
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 22, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 23, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 24, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 25, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(1000)));
		
		
		result = ruleEngine.checkFraud(logList);
		
		
		Assert.assertThat(result, IsMapContaining.hasEntry("RuleB", false));
	}
}
