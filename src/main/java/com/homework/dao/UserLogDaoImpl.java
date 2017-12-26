package com.homework.dao;

import java.util.List;
import org.springframework.stereotype.Component;

import com.homework.dto.log.LogBase;


/*
 * DataSource 영역에 대한 구현은 생략. 
 */
@Component
public class UserLogDaoImpl implements UserLogDao {
	
	@Override
	public List<LogBase> findById(long user_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
