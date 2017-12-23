package com.homework.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.dao.UserLogDao;
import com.homework.dto.log.LogBase;
import com.homework.dto.response.ResponseDto;
import com.homework.rule.RuleEngine;

@Service
public class FraudCheckServiceImpl implements FraudCheckService {

	@Autowired
	private RuleEngine ruleEngine;
	
	@Override
	public ResponseDto isFraud(long user_id , List<LogBase> logList) {
		HashMap<String, Boolean> hashMap = new HashMap<>();
		ResponseDto resDto = new ResponseDto();
		 
		hashMap = ruleEngine.fraudCheck(logList);
		 
		Iterator<String> keys = hashMap.keySet().iterator();
		
		resDto.setUser_id(user_id);
		resDto.setRule(null);
		
		while(keys.hasNext()){
			String key = keys.next();
			if(hashMap.get(key)==false){
				if(resDto.getRule()==null){
					resDto.setRule(key);
				}else{
					String tmp = resDto.getRule();
					tmp +=", "+ key;
					resDto.setRule(tmp);
				}
			}
		} 
		if(resDto.getRule()==null){
			resDto.setIs_fraud(true);
		}else{
			resDto.setIs_fraud(false);
		}
		
		return resDto;
	}

}
