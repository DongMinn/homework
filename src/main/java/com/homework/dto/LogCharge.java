package com.homework.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LogCharge extends LogBase{

	private BigDecimal chareAmount;
	private String bankAccountNo;
	public LogCharge(LocalDateTime updateDateTime, String userId, String kakaoAccountNo
			,BigDecimal chargeAmount , String bankAccountNo) {
		super(updateDateTime, userId, kakaoAccountNo);
		
		this.chareAmount = chargeAmount;
		this.bankAccountNo = bankAccountNo;
	}
	public BigDecimal getChareAmount() {
		return chareAmount;
	}
	
	

}
