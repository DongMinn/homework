package com.homework.rule;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.homework.dto.log.LogBase;
import com.homework.dto.log.LogReceive;

/*
 *  - 2시간 이내, 카카오머니 받기로 5만원 이상 금액을 3회 이상 하는 경우
 */
public class RuleC implements RuleBase {

	private int withinHour;
	private BigDecimal receiveAmount;
	private int count;
	
	private String className = getClass().getSimpleName();
	
	public RuleC(int withinHour , BigDecimal receiveAmount , int count) {
		this.withinHour = withinHour;
		this.receiveAmount = receiveAmount;
		this.count = count;
	}
	
	@Override
	public HashMap<String, Boolean> checkFraud(List<LogBase> logList) {
		 
		Collections.sort(logList);
		HashMap<String, Boolean> result = new HashMap<>();
		result.put(className, false);
		
		if(logList==null) return null;
		
		List<LocalDateTime> receiveDateTimeList = getReceiveDateTimeList(logList);
		LinkedList<LocalDateTime> comparisonTargetList = new LinkedList<>();	
		
		for(LocalDateTime receiveDateTime : receiveDateTimeList) {
			comparisonTargetList.addLast(receiveDateTime);
			
			if(comparisonTargetList.size() == count) {
				LocalDateTime firstDateTime = comparisonTargetList.getFirst();
				LocalDateTime lastDateTime = comparisonTargetList.getLast();
				
				if(firstDateTime.until(lastDateTime,ChronoUnit.MINUTES) <= withinHour*60) {
					result.put(className, true);
					return result;
				}
				else {
					comparisonTargetList.removeFirst();
				}
			}
			
		}
		
		return result;
	}
	
	private List<LocalDateTime> getReceiveDateTimeList(List<LogBase> logList) {
		List<LocalDateTime> receiveDateTimeList = new ArrayList<>();
		
		for(LogBase log: logList){
			if(log instanceof LogReceive){
				LogReceive logReceive = (LogReceive)log;
				if(logReceive.getReceiveAmount().compareTo(receiveAmount)>=0){
					receiveDateTimeList.add(logReceive.getUpdateDateTime());
				}
			}
		}
		
		return receiveDateTimeList;
	}
}
