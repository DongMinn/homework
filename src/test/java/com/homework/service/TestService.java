package com.homework.service;

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
import com.homework.dto.log.LogBase;
import com.homework.dto.log.LogCharge;
import com.homework.dto.log.LogOpenAccount;
import com.homework.dto.log.LogTransfer;
import com.homework.dto.response.ResponseDto;

@RunWith(MockitoJUnitRunner.class)
public class TestService {


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
	public void findById_ruleCheckA_false() throws Exception{
		
		List<LogBase> logList = Arrays.asList(
				new LogOpenAccount(LocalDateTime.of(2017, 12, 20, 17, 0), 411, "110-228-1000"),
				new LogCharge(LocalDateTime.of(2017, 12, 20, 17, 20), 411, "110-228-1000", new BigDecimal(200000), "0107118"),
				new LogTransfer(LocalDateTime.of(2017, 12, 20, 17, 40), 411, "110-228-1000", new BigDecimal(200000), "110-228-2000", 412, new BigDecimal(199900))
				);
		when(mockUserLogDao.findById(411)).thenReturn(logList);
		when(fraudCheckServiceImple.isFraud(411, logList)).thenReturn(new ResponseDto(411, false, "RuleA"));
		
		mockMvc.perform(get("/v1/fraud/{user_id}"  , 411))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.user_id", is(411)))
					.andExpect(jsonPath("$.is_fraud", is(false)))
					.andExpect(jsonPath("$.rule", is("RuleA")));
		
		
		verify(mockUserLogDao, times(1)).findById(411);
		verify(fraudCheckServiceImple, times(1)).isFraud(411, logList);
		verifyNoMoreInteractions(mockUserLogDao);
		verifyNoMoreInteractions(fraudCheckServiceImple);
							
				
	}	
}
