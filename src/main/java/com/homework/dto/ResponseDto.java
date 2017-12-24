package com.homework.dto;

public class ResponseDto  {
	private long user_id;
	private boolean is_fraud;
	private String rule = null;
	
	public ResponseDto(){}
	
	public ResponseDto(long user_id){
		this.user_id = user_id;
	}
	
	public ResponseDto(long user_id , boolean is_fraud , String rule){
		this.user_id = user_id;
		this.is_fraud = is_fraud;
		this.rule = rule;
	}
	
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public boolean getIs_fraud() {
		return is_fraud;
	}
	public void setIs_fraud(boolean is_fraud) {
		this.is_fraud = is_fraud;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}	
}
