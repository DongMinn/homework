package com.homework.dto.log;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LogReceive extends LogBase{
	private BigDecimal balance;  
	private String senderKakaoAccountNo;
	private long senderId;
	private BigDecimal receiveAmount;
	
	public LogReceive(LocalDateTime updateDateTime, long userId, String kakaoAccountNo
			, BigDecimal beforeReceiveBalance, String senderKakaoAccountNo
			, long senderId, BigDecimal receiveAmount) {
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
