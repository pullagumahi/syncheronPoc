package com.gcmb.hsbc.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gcmb.hsbc.Month;
import com.gcmb.hsbc.model.Stock;
import com.gcmb.hsbc.service.CacheService;
import com.google.gson.Gson;

@Service
public class CacheServiceImpl implements CacheService {
	private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

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
	
	@CacheEvict(value = "ratesCache", allEntries=true)
	public void clearCache(){
	}

}
