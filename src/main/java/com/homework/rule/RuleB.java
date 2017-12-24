package com.homework.rule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.homework.dto.log.LogBase;
import com.homework.dto.log.LogOpenAccount;
import com.homework.dto.log.LogReceive;

/*
 * 카카오머니 서비스 계좌 개설을 하고 7일 이내, 
 * 카카오머니 받기로 10만원 이상 금액을 5회 이상 하는 경우
 */
public class RuleB implements RuleBase {

	private int withinDay;
	private BigDecimal receiveAmount;
	private int count;
	
	private String className = getClass().getSimpleName();
	
	public RuleB(int withinDay , BigDecimal receiveAmount , int count) {
		this.withinDay = withinDay;
		this.receiveAmount = receiveAmount;
		this.count = count;
	}
	
	@Override
	public HashMap<String, Boolean> checkFraud(List<LogBase> logList) {
		Collections.sort(logList);
		
		int checkCount = 0;
		LocalDate openDate = null;
		HashMap<String, Boolean> result = new HashMap<>();
		result.put(className, false);
		
		if(logList==null) return null;
		
		for(LogBase log: logList){
			if(log instanceof LogOpenAccount){
				openDate = log.getUpdateDateTime().toLocalDate();
			}else if(log instanceof LogReceive){
				LogReceive logReceive = (LogReceive)log;
				if(openDate!=null && isFraudReceive(logReceive, openDate)){
					checkCount++;	 
				}		
			}
		}
		
		if(checkCount>=count){ 
			result.put(className, true);
		}
		
		return result;
	}
	
	private Boolean isFraudReceive(LogReceive log, LocalDate openDate) {
		Period period = openDate.until(log.getUpdateDateTime().toLocalDate());
		return period.getDays() <= withinDay
				&& log.getReceiveAmount().compareTo(receiveAmount)>=0;
	}

}
