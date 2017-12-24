package com.homework.dto.log;

import java.time.LocalDateTime;

public class LogOpenAccount extends LogBase{
	public LogOpenAccount(LocalDateTime updateDateTime , long userId , String kakaoAccountNo) {
		super(updateDateTime , userId , kakaoAccountNo);
	}
}
