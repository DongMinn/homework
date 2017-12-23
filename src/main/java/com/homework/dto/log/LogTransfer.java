package com.homework.dto.log;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LogTransfer extends LogBase{

	private BigDecimal balance;//송금이체전 계좌 잔액 
	private String reciverKakaoAccountNo;  // 수신자 계좌 번호 
	private long reciverId; // 수신자 ID
	private BigDecimal sendAmount; // 송금 한 금액 
	
	public LogTransfer(LocalDateTime updateDateTime, long userId, String kakaoAccountNo
			, BigDecimal beforeTransferBalance , String reciverKakaoAccountNo 
			,long reciverId , BigDecimal sendAmount) {
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
