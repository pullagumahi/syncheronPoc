package com.gcmb.hsbc.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.gcmb.hsbc.service.ExchangeService;
import com.google.gson.Gson;

@Service
public class ExchangeServiceImpl implements ExchangeService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Override
	@Cacheable(value = "ratesCache")
	public Map<String, HashMap<String, Stock>> initialLoadData(String year) {
		
		logger.info("Started loading complete year data...");

		HashMap<String, Stock> map_permonth = new HashMap<>();
		
		String[] values = year.split("-");

		String yearVal = values[0];
		
		logger.info("Started loading complete year{} data...",yearVal);

		// Year --> Month
		Map<String, HashMap<String, Stock>> mapStockPeryear = new HashMap<String, HashMap<String, Stock>>();

		if (!mapStockPeryear.containsKey(yearVal)) {
			Runnable r1 = new Runnable() {
				public void run() {
					String key = yearVal + "-01" + "-" + Month.JAN.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("01", rateJson);
				}
			};

			Runnable r2 = new Runnable() {
				public void run() {
					String key = yearVal + "-02" + "-" + Month.FEB.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("02", rateJson);
				}
			};

			Runnable r3 = new Runnable() {
				public void run() {
					String key = yearVal + "-03" + "-" + Month.MAR.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("03", rateJson);
				}
			};

			Runnable r4 = new Runnable() {
				public void run() {
					String key = yearVal + "-04" + "-" + Month.APR.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("04", rateJson);
				}
			};

			Runnable r5 = new Runnable() {
				public void run() {
					String key = yearVal + "-05" + "-" + Month.MAY.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("05", rateJson);
				}
			};

			Runnable r6 = new Runnable() {
				public void run() {
					String key = yearVal + "-06" + "-" + Month.JUNE.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("06", rateJson);
				}
			};

			Runnable r7 = new Runnable() {
				public void run() {
					String key = yearVal + "-07" + "-" + Month.JUL.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("07", rateJson);
				}
			};

			Runnable r8 = new Runnable() {
				public void run() {
					String key = yearVal + "-08" + "-" + Month.AUG.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("08", rateJson);
				}
			};

			Runnable r9 = new Runnable() {
				public void run() {
					String key = yearVal + "-09" + "-" + Month.SEP.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("09", rateJson);
				}
			};
			Runnable r10 = new Runnable() {
				public void run() {
					String key = yearVal + "-10" + "-" + Month.OCT.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("10", rateJson);
				}
			};
			Runnable r11 = new Runnable() {
				public void run() {
					String key = yearVal + "-11" + "-" + Month.NOV.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("11", rateJson);
				}
			};
			Runnable r12 = new Runnable() {
				public void run() {
					String key = yearVal + "-12" + "-" + Month.DEC.getMonthLastDay();
					String str = restTemplate.getForObject("https://api.ratesapi.io/api/" + key, String.class);
					Gson gson = new Gson();
					Stock rateJson = gson.fromJson(str, Stock.class);
					map_permonth.put("12", rateJson);
				}
			};

			new Thread(r1).start();
			new Thread(r2).start();
			new Thread(r3).start();
			new Thread(r4).start();
			new Thread(r5).start();

			new Thread(r6).start();
			new Thread(r7).start();
			new Thread(r8).start();
			new Thread(r9).start();

			new Thread(r10).start();
			new Thread(r11).start();
			new Thread(r12).start();

			mapStockPeryear.put(yearVal, map_permonth);
			
			logger.info("completed loading complete year{} data...",yearVal);

		}
		return mapStockPeryear;
	}

	@Override
	public Stock loadDataByYearSpecificMonth(String yearVal,String monVal) throws InterruptedException  {
		
		logger.info("started loading loadDataByYearSpecificMonth...");

		Map<String, HashMap<String, Stock>> mapStockPeryear = initialLoadData(yearVal);

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
	public Map<String, HashMap<String, Stock>> loadDataByCompleteYear(String year) throws InterruptedException {

		logger.info("started loading loadDataByCompleteYear...");
		
		String[] values = year.split("-");

		String yearVal = values[0];

		Map<String, HashMap<String, Stock>> mapStockPeryear = initialLoadData(yearVal);
		
		Thread.sleep(10000L);
		
		Map<String, HashMap<String, Stock>> MonthsUpdatedStockPeryear=  populateMonth(mapStockPeryear);
		
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
	
	private Map<String, HashMap<String, Stock>> populateMonth(Map<String, HashMap<String, Stock>> mapStockPeryear) {
		Map<String, HashMap<String, Stock>> monthsStockPeryear = new HashMap<String, HashMap<String, Stock>>();
		HashMap<String, Stock> map_permonth = new HashMap<>();
		
		Set<String> setValues = mapStockPeryear.keySet();
        Collection<HashMap<String, Stock>> monthsMap =  mapStockPeryear.values();
		
		for(HashMap<String, Stock> map : monthsMap) {
			for (Map.Entry<String,Stock> entry : map.entrySet()) {
				for(MonthVal e: MonthVal.values()) {
				    if(e.getMonthLastDay() == Integer.valueOf(entry.getKey())) {
				    	map_permonth.put(e.name(), entry.getValue());
				    }
				  }
			}
		}
		monthsStockPeryear.put(setValues.iterator().next(),map_permonth);
		return monthsStockPeryear;
	}
	
	@CacheEvict(value = "ratesCache", allEntries=true)
	public void clearCache(){
	}

}
