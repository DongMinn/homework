package com.homework.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homework.dao.UserLogDao;
import com.homework.dto.log.LogBase;
import com.homework.dto.response.ResponseDto;
import com.homework.service.FraudCheckService;


@RestController
public class RuleController {

	@Autowired
	private FraudCheckService fraudCheckService;
	@Autowired
	private UserLogDao userLogDao;
	
	@RequestMapping(value = "/v1/fraud/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<ResponseDto> isFraud(@PathVariable("user_id") final long user_id){
		
		
		List<LogBase> logList = new ArrayList<>();
		logList = userLogDao.findById(user_id);
		ResponseDto responseDto = fraudCheckService.isFraud(user_id , logList);
		
		
		return new ResponseEntity<ResponseDto>(responseDto , HttpStatus.OK);
	}
	
}
