package com.homework.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LogReceive extends LogBase{

	private BigDecimal balance; //받기전 계좌 잔액 
	private String senderKakaoAccountNo;
	private String senderId;
	private BigDecimal receiveAmount;
	
	public LogReceive(LocalDateTime updateDateTime, String userId, String kakaoAccountNo
			,BigDecimal beforeReceiveBalance , String senderKakaoAccountNo
			, String senderId , BigDecimal receiveAmount) {
		super(updateDateTime, userId, kakaoAccountNo);
		
		this.balance = beforeReceiveBalance;
		this.senderKakaoAccountNo = senderKakaoAccountNo;
		this.senderId = senderId;
		this.receiveAmount = receiveAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}
	

}
