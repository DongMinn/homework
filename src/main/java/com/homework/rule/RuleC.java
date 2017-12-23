package com.homework.rule;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import java.util.List;

import com.homework.dto.log.LogBase;
import com.homework.dto.log.LogReceive;

public class RuleC implements RuleBase {

	private int withinHour;
	private BigDecimal receiveAmount;
	private int count;
	
	private String className = this.getClass().getSimpleName();
	
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
		//시간순으로 정렬 
		Collections.sort(logList);
		
		int checkCount = 0;
		HashMap<String, Boolean> hashMap = new HashMap<>();
		List<LocalDateTime> tmpList = new ArrayList<>();
		
		if(logList==null) return null;
		
		for(LogBase log: logList){
			if(log instanceof LogReceive){
				LogReceive logReceive = (LogReceive)log;
				if(logReceive.getReceiveAmount().compareTo(this.receiveAmount)>=0){
					tmpList.add(logReceive.getUpdateDateTime());
				}
			}
		}
		
		// 받기 횟수가 애초에 적다면 바로 true 리턴 
		if(tmpList.size() < this.count){
			hashMap.put(className, true);
			return hashMap;
		}else{
			for( int i =0 ; i<tmpList.size()-(this.count-1); i++){
				
				checkCount=1;
				
				for(int j=1; j<this.count; j++){
					
					// 시간정렬된 로그들의 시간 차이가 n분 이내라면 
					if(tmpList.get(i).until(tmpList.get(i+j), ChronoUnit.MINUTES) <= this.withinHour*60){
						checkCount++;
					}
				}
				if(checkCount>=this.count){
					hashMap.put(className, false);
					return hashMap;
				}
				
			}
		}
		hashMap.put(className, true);
		return hashMap;
	}

}
