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


/*
 * RuleA
 * 카카오머니 서비스 계좌 개설을 하고 1시간 이내, 20만원 충전 
 * 후 잔액이 1000원 이하가 되는 경우
 */

public class RuleA implements RuleBase{

	private int withinHour;
	private BigDecimal chargeAmount;
	private BigDecimal balance;
	
	private String className = getClass().getSimpleName();
	
	public RuleA(int withinHour , BigDecimal chargeAmount , BigDecimal balance) {
		this.withinHour = withinHour;
		this.chargeAmount = chargeAmount;
		this.balance = balance;		
	}
	
	@Override
	public HashMap<String, Boolean> checkFraud(List<LogBase> logList) {
		Collections.sort(logList);
		
		BigDecimal tmpBalance = new BigDecimal(0); // 로그상 유저의 잔액을 저장할 변수
		LocalDateTime openDateTime = null;
		HashMap<String, Boolean> result = new HashMap<>();
		result.put(className, false);
		
		if(logList==null) return null;	//Test 코드에 추가, 추가로 확인하기 
		
		for(LogBase log: logList){
			//계좌 개설 시간 저장 
			if(log instanceof LogOpenAccount){
				openDateTime = log.getUpdateDateTime();
			}//충전한 금액, 시간 조건에 맞으면 잔액 추가 
			else if(log instanceof LogCharge){
				LogCharge logCharge = (LogCharge)log;
				
				//계좌 개설 로그가 있으면서, 충전한시간과 계좌개설시간이 x시간 이내인경우 
				if(openDateTime!=null && isFraudCharge(logCharge, openDateTime)){
					tmpBalance = tmpBalance.add(logCharge.getChareAmount());
				}
			}else if(log instanceof LogTransfer){
				LogTransfer logTransfer = (LogTransfer)log;
				
				if(openDateTime != null && isFraudTransfer(logTransfer, openDateTime)){
					if(balance.compareTo(tmpBalance.subtract(logTransfer.getSendAmount())) > 0){
						//모든 조건에부합함. 
						result.put(className,true);
						return result;
					}
				}
				
			}
			
		}

		return result;
	}

	private Boolean isFraudCharge (LogCharge log, LocalDateTime openDateTime) {
		return log.getUpdateDateTime().until(openDateTime, ChronoUnit.MINUTES) < (withinHour*60) 
				&& log.getChareAmount().compareTo(chargeAmount) >= 0;
	}
	
	private Boolean isFraudTransfer (LogTransfer log , LocalDateTime openDateTime) {
		return log.getUpdateDateTime().until(openDateTime, ChronoUnit.MINUTES)<(withinHour*60);
	}
}
