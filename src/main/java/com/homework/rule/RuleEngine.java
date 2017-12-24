package com.homework.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.homework.dto.log.LogBase;

public class RuleEngine implements RuleBase {
	private List<RuleBase> ruleList = new ArrayList<>();
	private HashMap<String, Boolean> result = new HashMap<>();
	
	//룰 추가 
	public void addRule(RuleBase rule){
		ruleList.add(rule);
	}
	//룰 제거 
	public void removeRule(RuleBase rule){
		ruleList.remove(rule);
	}
	
	@Override
	public HashMap<String, Boolean> checkFraud(List<LogBase> logList) {
		if(logList == null) return null;
		
		//룰 순회하면서 로그 리스트 체크 후 결과 map에 저장 
		for(RuleBase rule : ruleList){
			result.putAll(rule.checkFraud(logList));
		}
		
		return result;
	}

}
