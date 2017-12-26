package com.homework.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.homework.dto.log.LogBase;


public class RuleEngine implements RuleBase {
	private List<RuleBase> ruleList = new ArrayList<>();
	private HashMap<String, Boolean> result = new HashMap<>();
	 
	public void addRule(RuleBase rule){
		ruleList.add(rule);
	} 
	public void removeRule(RuleBase rule){
		ruleList.remove(rule);
	}
	
	@Override
	public HashMap<String, Boolean> checkFraud(List<LogBase> logList) {
		if(logList == null) return null;
		
		for(RuleBase rule : ruleList){
			result.putAll(rule.checkFraud(logList));
		}
		
		return result;
	}

}
