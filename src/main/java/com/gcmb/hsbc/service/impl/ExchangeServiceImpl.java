package com.gcmb.hsbc.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.gcmb.hsbc.Month;
import com.gcmb.hsbc.MonthVal;
import com.gcmb.hsbc.model.Stock;
import com.gcmb.hsbc.service.CacheService;
import com.gcmb.hsbc.service.ExchangeService;
import com.google.gson.Gson;

@Service
public class ExchangeServiceImpl implements ExchangeService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CacheService cacheService;


	@Override
	public Stock loadDataByYearSpecificMonth(String yearVal,String monVal) throws InterruptedException  {
		
		logger.info("started loading loadDataByYearSpecificMonth...");

		Map<String, HashMap<String, Stock>> mapStockPeryear = cacheService.initialLoadData(yearVal);

		HashMap<String, Stock> hashMapperMonthsInfo = mapStockPeryear.get(yearVal);
		Thread.sleep(30000L);
		Stock stockInfo = new Stock();
		if(hashMapperMonthsInfo.containsKey(monVal)) {
			stockInfo = hashMapperMonthsInfo.get(monVal);
			return stockInfo;
		}
		
		logger.info("completed loading loadDataByYearSpecificMonth...");

		return null;
	}

	@Override
	public Map<String, TreeMap<String, Stock>> loadDataByCompleteYear(String year) throws InterruptedException {

		logger.info("started loading loadDataByCompleteYear...");
		
		String[] values = year.split("-");

		String yearVal = values[0];

		Map<String, HashMap<String, Stock>> mapStockPeryear = cacheService.initialLoadData(yearVal);
		
		Thread.sleep(10000L);
		
		Map<String, TreeMap<String, Stock>> MonthsUpdatedStockPeryear=  populateMonth(mapStockPeryear);
		
		logger.info("completed loading loadDataByCompleteYear...");

		return MonthsUpdatedStockPeryear;

	}

	@Override
	public Stock loadDataByYearSpecificMonthAndDate(String yearVal,String monthVal,String DayVal) {
		logger.info("started loading loadDataByYearSpecificMonthAndDate...");
		String year = yearVal+"-"+monthVal+"-"+DayVal;
		String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + year, String.class);
		Gson gson = new Gson();
		Stock rateJson = gson.fromJson(str, Stock.class);
		logger.info("completed loading loadDataByYearSpecificMonthAndDate...");
		return rateJson;
	}

	@Override
	public List<Stock> loadDataByGivenDateAndTodayDate(String year) {
		logger.info("started loading loadDataByGivenDateAndTodayDate...");

		List<Stock> stockList = new ArrayList<>();

		String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + year, String.class);
		Gson gson = new Gson();
		Stock rateJson = gson.fromJson(str, Stock.class);
		stockList.add(rateJson);

		LocalDate currentdate = LocalDate.now();
		String currentdateApi = restTemplate.getForObject("https://api.ratesapi.io/api/" + currentdate, String.class);
		Gson currentdateGson = new Gson();
		Stock currentdateRateJson = currentdateGson.fromJson(currentdateApi, Stock.class);
		stockList.add(currentdateRateJson);
		logger.info("completed loading loadDataByGivenDateAndTodayDate...");
		return stockList;
	}

	@Override
	public float loadDataByYearSpecificMonthAndDateAndExchge(String year, String excge) {
		logger.info("started loading loadDataByYearSpecificMonthAndDateAndExchge...");

		String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + year, String.class);
		Gson gson = new Gson();
		Stock rateJson = gson.fromJson(str, Stock.class);

		if (excge.equalsIgnoreCase("GBP")) {
			logger.info("completed loading loadDataByYearSpecificMonthAndDateAndExchge...");
			return rateJson.getRates().getGBP();
		} else if (excge.equalsIgnoreCase("USD")) {
			logger.info("completed loading loadDataByYearSpecificMonthAndDateAndExchge...");
			return rateJson.getRates().getUSD();
		} else {
			logger.info("completed loading loadDataByYearSpecificMonthAndDateAndExchge...");
			return rateJson.getRates().getHKD();
		}
	}
	
	private Map<String, TreeMap<String, Stock>> populateMonth(Map<String, HashMap<String, Stock>> mapStockPeryear) {
		Map<String, TreeMap<String, Stock>> monthsStockPeryear = new HashMap<String, TreeMap<String, Stock>>();
		TreeMap<String, Stock> map_permonth = new TreeMap<>();
		
		Set<String> setValues = mapStockPeryear.keySet();
        Collection<HashMap<String, Stock>> monthsMap =  mapStockPeryear.values();
		
		for(HashMap<String, Stock> map : monthsMap) {
			for (Map.Entry<String,Stock> entry : map.entrySet()) {
				for(MonthVal e: MonthVal.values()) {
				    if(e.getMonthCode() == Integer.valueOf(entry.getKey())) {
				    	map_permonth.put(e.name(), entry.getValue());
				    }
				  }
			}
		}
		monthsStockPeryear.put(setValues.iterator().next(),map_permonth);
		return monthsStockPeryear;
	}
	


}
