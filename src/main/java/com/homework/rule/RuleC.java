package com.homework.rule;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

import com.homework.dto.LogBase;
import com.homework.dto.LogReceive;

public class RuleC implements RuleBase {

	private int withinHour;
	private BigDecimal receiveAmount;
	private int count;
	
	public RuleC(int withinHour , BigDecimal receiveAmount , int count) {
		this.withinHour = withinHour;
		this.receiveAmount = receiveAmount;
		this.count = count;
	}
	
	@Override
	public HashMap<String, Boolean> fraudCheck(List<LogBase> logList) {
		/*
		 *  - 2시간 이내, 카카오머니 받기로 5만원 이상 금액을 3회 이상 하는 경우
		 */
		
		int checkCount = 0;
		LocalDateTime startDateTime = null;
		LocalDateTime receiveDateTime = null;
		HashMap<String, Boolean> hashMap = new HashMap<>();
		
		if(logList==null) return null;
		
		for(LogBase log: logList){
			if(log instanceof LogReceive){
				
				LogReceive logReceive = (LogReceive)log;
				
				if(logReceive.getReceiveAmount().compareTo(this.receiveAmount)>=0){
					if(startDateTime==null) {
						startDateTime = logReceive.getUpdateDateTime();
						checkCount++;
					}else{
						receiveDateTime = logReceive.getUpdateDateTime();
						if(receiveDateTime.until(startDateTime, ChronoUnit.MINUTES) < this.withinHour*60){
							checkCount++;
							//조건에 부합함으로 종료 
							if(checkCount>=this.count){
								hashMap.put(this.getClass().getName(), false);
								return hashMap;
							}
						}else{
							checkCount=1;
							startDateTime = receiveDateTime;
						}
					}
					
				}
			}		
		}
		hashMap.put(this.getClass().getName(), true);
		return hashMap;
	}

}
