package com.homework.dao;

import java.util.List;



import com.homework.dto.log.LogBase;

/*
 * DataSource 영역에 대한 구현은 생략. 
 */

public interface UserLogDao {
	List<LogBase> findById(long user_id);
}
