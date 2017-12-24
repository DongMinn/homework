package com.homework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.dto.ResponseDto;
import com.homework.dto.log.LogBase;
import com.homework.rule.RuleEngine;

@Service
public class FraudCheckServiceImpl implements FraudCheckService {

	@Autowired
	private RuleEngine ruleEngine;
	
	@Override
	public ResponseDto isFraud(long user_id , List<LogBase> logList) { 
		HashMap<String, Boolean> checkedFraud = ruleEngine.checkFraud(logList);
		ResponseDto responseDto = new ResponseDto(user_id); 
		responseDto.setIs_fraud(true);
		
		Iterator<String> keys = checkedFraud.keySet().iterator();
		
		ArrayList<String> fraudRules = new ArrayList<String>();
		while(keys.hasNext()){
			String key = keys.next();
			if(checkedFraud.get(key)==false){
				fraudRules.add(key);
			}
		} 
		
		responseDto.setRule(String.join(",", fraudRules));
		
		if(responseDto.getRule()!=null){
			responseDto.setIs_fraud(false);
		}
		return responseDto;
	}

}
