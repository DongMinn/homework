package com.homework.dto.log;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LogTransfer extends LogBase{
	private BigDecimal balance;
	private String reciverKakaoAccountNo; 
	private long reciverId;
	private BigDecimal sendAmount; 
	
	public LogTransfer(LocalDateTime updateDateTime, long userId, String kakaoAccountNo
			, BigDecimal beforeTransferBalance, String reciverKakaoAccountNo 
			, long reciverId, BigDecimal sendAmount) {
		super(updateDateTime, userId, kakaoAccountNo);
		this.balance = beforeTransferBalance;
		this.reciverKakaoAccountNo = reciverKakaoAccountNo;
		this.reciverId = reciverId;
		this.sendAmount = sendAmount;	
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}
}
