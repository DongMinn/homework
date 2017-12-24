package com.homework.rule;

//import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Assert;
import org.junit.Test;

import com.homework.dto.log.LogBase;
import com.homework.dto.log.LogCharge;
import com.homework.dto.log.LogOpenAccount;
import com.homework.dto.log.LogReceive;
import com.homework.dto.log.LogTransfer;




public class TestRuleEngine {
	
	RuleA ruleA = new RuleA(1, new BigDecimal(200000), new BigDecimal(1000));
	RuleB ruleB = new RuleB(7, new BigDecimal(100000), 5);
	RuleC ruleC = new RuleC(2, new BigDecimal(50000), 3);
	List<LogBase> logList = new ArrayList<>();
	HashMap<String, Boolean> result = new HashMap<>();
	RuleEngine ruleEngine = new RuleEngine();
	
	@Test
	public void ruleEngine_BC_규칙_충족하는_경우(){
		
		
		ruleEngine.addRule(ruleA);
		ruleEngine.addRule(ruleB);
		ruleEngine.addRule(ruleC);
		
		
		//충전 로그 
		logList.add(new LogCharge(LocalDateTime.of(2017, 12, 20, 17, 20), 411, "110-228-1000", new BigDecimal(200000), "0107118"));
		// 송금 로그 
		logList.add(new LogTransfer(LocalDateTime.of(2017, 12, 20, 17, 40), 411, "110-228-1000", new BigDecimal(200000), "110-228-2000", 412, new BigDecimal(10000)));
		//계좌개설 로그 
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 17, 0), 411, "110-228-1000"));
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 18, 0), 412, "110-228-2000"));
		// 받기 로그
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 11, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 13, 30), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 14, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 21, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 21, 30), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 12, 30), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
	
		
		result = ruleEngine.checkFraud(logList);
	
		Assert.assertThat(result, IsMapContaining.hasEntry("RuleA", false));
		Assert.assertThat(result, IsMapContaining.hasEntry("RuleB", true));
		Assert.assertThat(result, IsMapContaining.hasEntry("RuleC", true));
	}

	@Test
	public void ruleEngine_모든규칙_충족하지않는_경우(){
		
		ruleEngine.addRule(ruleA);
		ruleEngine.addRule(ruleB);
		ruleEngine.addRule(ruleC);
		
		
		//충전 로그 
		logList.add(new LogCharge(LocalDateTime.of(2017, 12, 20, 17, 20), 411, "110-228-1000", new BigDecimal(200000), "0107118"));
		// 송금 로그 
		logList.add(new LogTransfer(LocalDateTime.of(2017, 12, 20, 17, 40), 411, "110-228-1000", new BigDecimal(200000), "110-228-2000", 412, new BigDecimal(10000)));
		//계좌개설 로그 
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 17, 0), 411, "110-228-1000"));
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 18, 0), 412, "110-228-2000"));
		// 받기 로그
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 11, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 13, 30), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 14, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 21, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 30, 21, 30), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 30, 12, 30), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(1000)));
	
		
		result = ruleEngine.checkFraud(logList);
		
		 
		Assert.assertThat(result, IsMapContaining.hasEntry("RuleA", false));
		Assert.assertThat(result, IsMapContaining.hasEntry("RuleB", false));
		Assert.assertThat(result, IsMapContaining.hasEntry("RuleC", false));
	}


}
