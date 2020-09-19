package com.gcmb.hsbc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.gcmb.hsbc.model.Stock;

public interface ExchangeService {
	
	public Stock loadDataByYearSpecificMonth(String year,String monVal) throws InterruptedException;
	
	public Map<String,TreeMap<String,Stock>> loadDataByCompleteYear(String year) throws InterruptedException;
	
	public Stock loadDataByYearSpecificMonthAndDate(String year,String monthVal,String DayVal);
	
	public List<Stock> loadDataByGivenDateAndTodayDate(String year);
	
	public float loadDataByYearSpecificMonthAndDateAndExchge(String year,String excge);
}

