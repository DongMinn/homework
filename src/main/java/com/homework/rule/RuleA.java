package com.homework.rule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.homework.dto.log.LogBase;
import com.homework.dto.log.LogCharge;
import com.homework.dto.log.LogOpenAccount;
import com.homework.dto.log.LogTransfer;



public class RuleA implements RuleBase{

	private int withinHour;
	private BigDecimal charge;
	private BigDecimal balance;
	
	private String className = this.getClass().getSimpleName();
	
	
	public RuleA(int withinHour , BigDecimal charge , BigDecimal balance) {
		this.withinHour = withinHour;
		this.charge = charge;
		this.balance = balance;		
	}
	
	@Override
	public HashMap<String, Boolean> fraudCheck(List<LogBase> logList) {
	/*
	 * 카카오머니 서비스 계좌 개설을 하고 1시간 이내, 20만원 충전 
	 * 후 잔액이 1000원 이하가 되는 경우
	 * -> 송금을 했다는 뜻이겠지??
	 */
		
		//시간순으로 정렬 
		Collections.sort(logList);
		
		BigDecimal tmpBalance = new BigDecimal(0); // 로그상 유저의 잔액을 저장할 변수
		LocalDateTime openDateTime = null;
		LocalDateTime chargeDateTime = null;
		LocalDateTime transferDateTime = null;
		HashMap<String, Boolean> hashMap = new HashMap<>();
		
		if(logList==null) return null;
		
		for(LogBase log: logList){
			
			//계좌 개설 시간 저장 
			if(log instanceof LogOpenAccount){
				
				openDateTime = log.getUpdateDateTime();
				
			}//충전한 금액, 시간 조건에 맞으면 잔액 추가 
			else if(log instanceof LogCharge){
				LogCharge logCharge = (LogCharge)log;
				chargeDateTime = logCharge.getUpdateDateTime();
				
				//계좌 개설 로그가 있으면서, 충전한시간과 계좌개설시간이 x시간 이내인경우 
				if(openDateTime!=null 
						&& chargeDateTime.until(openDateTime, ChronoUnit.MINUTES)<(this.withinHour*60)
						&& logCharge.getChareAmount().compareTo(this.charge) >= 0){
					tmpBalance = tmpBalance.add(logCharge.getChareAmount());
				}
				
			}else if(log instanceof LogTransfer){
				LogTransfer logTransfer = (LogTransfer)log;
				transferDateTime = logTransfer.getUpdateDateTime();
				
				if(openDateTime != null 
						&& transferDateTime.until(openDateTime, ChronoUnit.MINUTES)<(this.withinHour*60) ){
					
					if(this.balance.compareTo(tmpBalance.subtract(logTransfer.getSendAmount())) > 0){
						//모든 조건에부합함. 
						hashMap.put(className, false);
						return hashMap;
					}
				}
				
			}
			
		}
		
		hashMap.put(className, true);
		return hashMap;
	}

}
