package com.homework.dao;

import java.util.List;

import com.homework.dto.log.LogBase;

public interface UserLogDao {

	List<LogBase> findById(long user_id);
}
