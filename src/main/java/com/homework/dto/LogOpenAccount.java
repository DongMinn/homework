package com.homework.dto;

import java.time.LocalDateTime;

public class LogOpenAccount extends LogBase{

	public LogOpenAccount(LocalDateTime updateDateTime , String userId , String kakaoAccountNo) {
		super(updateDateTime , userId , kakaoAccountNo);
	}
	
	
}
