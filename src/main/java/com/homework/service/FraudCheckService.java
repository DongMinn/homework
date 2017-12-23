package com.homework.service;

import java.util.List;

import com.homework.dto.log.LogBase;
import com.homework.dto.response.ResponseDto;

public interface FraudCheckService {

	ResponseDto isFraud(final long user_id , final List<LogBase> logList);
	
}
