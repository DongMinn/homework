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

public class RuleB implements RuleBase {

	private int withinDay;
	private BigDecimal receiveAmount;
	private int count;
	
	private String className = this.getClass().getSimpleName();
	
	public RuleB(int withinDay , BigDecimal receiveAmount , int count) {
		this.withinDay = withinDay;
		this.receiveAmount = receiveAmount;
		this.count = count;
	}
	
	@Override
	public HashMap<String, Boolean> fraudCheck(List<LogBase> logList) {
		/*
		 * - 카카오머니 서비스 계좌 개설을 하고 7일 이내, 
		 * 카카오머니 받기로 10만원 이상 금액을 5회 이상 하는 경우
		 */
		
		//시간순으로 정렬 
		Collections.sort(logList);
		
		int checkCount = 0;
		LocalDate openDate = null;
		LocalDate receiveDate = null;
		HashMap<String, Boolean> hashMap = new HashMap<>();
		
		if(logList==null) return null;
		
		for(LogBase log: logList){
			
			//계좌 개설 시간 저장 
			if(log instanceof LogOpenAccount){
				
				openDate = log.getUpdateDateTime().toLocalDate();
				
			}else if(log instanceof LogReceive){
				LogReceive logReceive = (LogReceive)log;
				receiveDate = logReceive.getUpdateDateTime().toLocalDate();
				if(openDate!=null){
					
					Period period = openDate.until(receiveDate);
					
					if(period.getDays() < this.withinDay
							&& logReceive.getReceiveAmount().compareTo(this.receiveAmount)>=0){
						checkCount++;
					}
				}		
			}
			
		}
		if(checkCount>=this.count){// 조건에 부합하는 경우 
			hashMap.put(className, false);
		}else{
			hashMap.put(className, true);
		}
		return hashMap;
	}

}
