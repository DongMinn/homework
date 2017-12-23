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

	
//	private String userId = 411;
//	private String kakaoAccountNo = "110-228-1000";
	
	
	@Test
	public void testFraudCheckRuleA_false() {
		List<LogBase> logList = new ArrayList<>();
		HashMap<String, Boolean> expectMap = new HashMap<>();
		//expectMap.put("RuleA", false);
		
		RuleA ruleA = new RuleA(1, new BigDecimal(200000), new BigDecimal(1000));
		RuleB ruleB = new RuleB(7, new BigDecimal(100000), 5);
		RuleC ruleC = new RuleC(2, new BigDecimal(50000), 3);
		
		RuleEngine ruleEngine = new RuleEngine();
		
		ruleEngine.addRule(ruleA);
		ruleEngine.addRule(ruleB);
		ruleEngine.addRule(ruleC);
		
//		logList.sort(c);
		
		 
		//계좌개설 로그 
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 17, 0), 411, "110-228-1000"));
		logList.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 18, 0), 412, "110-228-2000"));
		//충전 로그 
		logList.add(new LogCharge(LocalDateTime.of(2017, 12, 20, 17, 20), 411, "110-228-1000", new BigDecimal(200000), "0107118"));
		// 송금 로그 
		logList.add(new LogTransfer(LocalDateTime.of(2017, 12, 20, 17, 40), 411, "110-228-1000", new BigDecimal(200000), "110-228-2000", 412, new BigDecimal(199900)));
		
//		Arrays.sort(logList);
		
		expectMap = ruleEngine.fraudCheck(logList);
		
//		Iterator<String> keys = expectMap.keySet().iterator();
//		
//		while(keys.hasNext()){
//			String key = keys.next();
//			System.out.println("키:"+key +"값:"+expectMap.get(key));
//		}
		
	
		//Assert.assertEquals(true, compositeCondition.isFraud(baseEventLogs));
		Assert.assertThat(expectMap, IsMapContaining.hasEntry("RuleA", false));
	}
	
	@Test
	public void testFraudCheckRuleA_true(){
		List<LogBase> logList2 = new ArrayList<>();
		HashMap<String, Boolean> expectMap = new HashMap<>();
//		expectMap.put("RuleA", false);
		
		RuleA ruleA = new RuleA(1, new BigDecimal(200000), new BigDecimal(1000));
		RuleB ruleB = new RuleB(7, new BigDecimal(100000), 5);
		RuleC ruleC = new RuleC(2, new BigDecimal(50000), 3);
		
		RuleEngine ruleEngine = new RuleEngine();
		
		ruleEngine.addRule(ruleA);
		ruleEngine.addRule(ruleB);
		ruleEngine.addRule(ruleC);
		
		
		//계좌개설 로그 
		logList2.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 17, 0), 411, "110-228-1000"));
		logList2.add(new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 18, 0), 412, "110-228-2000"));
		//충전 로그 
		logList2.add(new LogCharge(LocalDateTime.of(2017, 12, 20, 17, 20), 411, "110-228-1000", new BigDecimal(200000), "0107118"));
		// 송금 로그 
		logList2.add(new LogTransfer(LocalDateTime.of(2017, 12, 20, 17, 40), 411, "110-228-1000", new BigDecimal(200000), "110-228-2000", 412, new BigDecimal(10000)));
		
		expectMap = ruleEngine.fraudCheck(logList2);
		
		//Assert.assertEquals(true, compositeCondition.isFraud(baseEventLogs));
		Assert.assertThat(expectMap, IsMapContaining.hasEntry("RuleA", true));
	}
	
	@Test
	public void testFraudCheckRuleB_false(){
		List<LogBase> logList = new ArrayList<>();
		HashMap<String, Boolean> expectMap = new HashMap<>();
//		expectMap.put("RuleA", false);
		
		RuleA ruleA = new RuleA(1, new BigDecimal(200000), new BigDecimal(1000));
		RuleB ruleB = new RuleB(7, new BigDecimal(100000), 5);
		RuleC ruleC = new RuleC(2, new BigDecimal(50000), 3);
		
		RuleEngine ruleEngine = new RuleEngine();
		
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
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 22, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 23, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 24, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 25, 17, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		
		Collections.sort(logList);
		
		
		
		expectMap = ruleEngine.fraudCheck(logList);
		
		
//		Iterator<String> keys = expectMap.keySet().iterator();
//		
//		while(keys.hasNext()){
//			String key = keys.next();
//			System.out.println("키:"+key +"값:"+expectMap.get(key));
//		
//		}
		
		Assert.assertThat(expectMap, IsMapContaining.hasEntry("RuleA", true));
		Assert.assertThat(expectMap, IsMapContaining.hasEntry("RuleB", false));
	}
	
	
	@Test
	public void testFraudCheckRuleC_false(){
		List<LogBase> logList = new ArrayList<>();
		HashMap<String, Boolean> expectMap = new HashMap<>();
//		expectMap.put("RuleA", false);
		
		RuleA ruleA = new RuleA(1, new BigDecimal(200000), new BigDecimal(1000));
		RuleB ruleB = new RuleB(7, new BigDecimal(100000), 5);
		RuleC ruleC = new RuleC(2, new BigDecimal(50000), 3);
		
		RuleEngine ruleEngine = new RuleEngine();
		
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
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 12, 30), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 13, 30), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 14, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 21, 0), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		logList.add(new LogReceive(LocalDateTime.of(2017, 12, 21, 21, 30), 411, "110-228-1000", new BigDecimal(190000), "110-228-2000", 412, new BigDecimal(110000)));
		
	
		
		expectMap = ruleEngine.fraudCheck(logList);
		
		 
//		Iterator<String> keys = expectMap.keySet().iterator();
//		
//		while(keys.hasNext()){
//			String key = keys.next();
//			System.out.println("키:"+key +"값:"+expectMap.get(key));
//		
//		}
		
		Assert.assertThat(expectMap, IsMapContaining.hasEntry("RuleA", true));
		Assert.assertThat(expectMap, IsMapContaining.hasEntry("RuleB", false));
		Assert.assertThat(expectMap, IsMapContaining.hasEntry("RuleC", false));
	}


}
