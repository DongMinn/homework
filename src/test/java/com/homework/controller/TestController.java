package com.homework.controller;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.filters.CorsFilter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.homework.controller.RuleController;
import com.homework.dao.UserLogDao;
import com.homework.dto.ResponseDto;
import com.homework.dto.log.LogBase;
import com.homework.dto.log.LogCharge;
import com.homework.dto.log.LogOpenAccount;
import com.homework.dto.log.LogReceive;
import com.homework.dto.log.LogTransfer;
import com.homework.service.FraudCheckServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestController {


	private MockMvc mockMvc;
	
	@Mock
	UserLogDao mockUserLogDao;
	@Mock
	FraudCheckServiceImpl fraudCheckServiceImple;
	
	@InjectMocks
	private RuleController ruleController;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(ruleController)
				.addFilter(new CorsFilter())
				.build();
				
	}
	
	@Test
	public void restAPI_테스트() throws Exception{
		
		List<LogBase> logList = new ArrayList<LogBase>();
		
		when(mockUserLogDao.findById(411)).thenReturn(logList);
		when(fraudCheckServiceImple.isFraud(411, logList)).thenReturn(new ResponseDto(411, false, "RuleB,RuleC"));
		
		
		mockMvc.perform(get("/v1/fraud/{user_id}"  , 411))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.user_id", is(411)))
					.andExpect(jsonPath("$.is_fraud", is(false)))
					.andExpect(jsonPath("$.rule", is("RuleB,RuleC")));
		
		
		verify(mockUserLogDao, times(1)).findById(411);
		verify(fraudCheckServiceImple, times(1)).isFraud(411, logList);
		verifyNoMoreInteractions(mockUserLogDao);
		verifyNoMoreInteractions(fraudCheckServiceImple);
							
				
	}
}
