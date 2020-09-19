package com.gcmb.hsbc.service;

import java.util.HashMap;
import java.util.Map;

import com.gcmb.hsbc.model.Stock;

public interface CacheService {
	public Map<String, HashMap<String, Stock>> initialLoadData(String year);
	
	public void clearCache();
}
