package com.resturant.automatedticketingsystem.util;

import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToMap {
	private static final Logger lgr = LogManager.getLogger(ObjectToMap.class.getName());
	public static Map<String, String> convertValue(Object obj) {
		Map<String, Object> map = new ObjectMapper().convertValue(obj, new TypeReference<Map<String, Object>>() {});
		lgr.atInfo().log(map);
		return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> null != e.getValue() ? e.getValue().toString() : Constants.EMPTY));
	}
}
