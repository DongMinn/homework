package com.homework.dto.log;

import java.time.LocalDateTime;

public class LogBase implements Comparable<LogBase>{
	private LocalDateTime updateDateTime;
	private long userId;
	private String kakaoAccountNo;
	
	public LogBase(LocalDateTime updateDateTime , long userId , String kakaoAccountNo) {
		this.updateDateTime = updateDateTime;
		this.userId = userId;
		this.kakaoAccountNo = kakaoAccountNo;
	}
	
	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	@Override
	public int compareTo(LogBase log) {
		// TODO Auto-generated method stub
		if(updateDateTime.isBefore(log.getUpdateDateTime()))
			return -1;
		else 
			return 1;
	}
}
