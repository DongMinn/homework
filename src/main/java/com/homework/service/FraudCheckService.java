package com.homework.service;



import com.homework.dto.ResponseDto;


public interface FraudCheckService {
	ResponseDto isFraud(final long user_id);
}
