package com.homework.rule;

import java.util.HashMap;
import java.util.List;

import com.homework.dto.log.LogBase;

public interface RuleBase {
	HashMap<String, Boolean> checkFraud(final List<LogBase> logList);
}
