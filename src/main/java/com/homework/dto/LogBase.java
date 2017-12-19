package com.homework.dto;

import java.time.LocalDateTime;

public class LogBase implements Comparable<LogBase>{
	private LocalDateTime updateDateTime;
	private String userId;
	private String kakaoAccountNo;
	
	
	public LogBase(LocalDateTime updateDateTime , String userId , String kakaoAccountNo) {
		this.updateDateTime = updateDateTime;
		this.userId = userId;
		this.kakaoAccountNo = kakaoAccountNo;
	}
	
	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}


	@Override
	public int compareTo(LogBase o) {
		// TODO Auto-generated method stub
		if(this.updateDateTime.isBefore(o.getUpdateDateTime()))
			return -1;
		else 
			return 1;
	}
	
	
}
