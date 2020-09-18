package com.gcmb.hsbc.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcmb.hsbc.model.Stock;
import com.gcmb.hsbc.service.ExchangeService;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
public class ExchangeController {
	private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);
	
	@Autowired
	ExchangeService loadService;
	
	

	/*
	 * Given I want to load data
     * When I hit REST endpoint for loading data
     * Then data is loaded into in-memory database
     * And only last 12 months are included (you may restrict yourself to the rate as of the 1st day
     * of the month in question)
      * And only GBP/USD/HKD currencies are included
	 */
	@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public String initialLoad() throws InterruptedException {
		logger.info("initialLoad for current year started");
		int year = Calendar.getInstance().get(Calendar.YEAR);
		loadService.initialLoadData(String.valueOf(year));
		Thread.sleep(10000L);
		Gson gson = new Gson();
		logger.info("initialLoad for current year completed");
		return gson.toJson("Initial loaded to cache succesfully");
	}
	
	@RequestMapping(value = "/byMonthYear/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String loadDataByYearSpecificMonth(@PathVariable(value = "year") String year){
		logger.info("started for loadDataByYearSpecificMonth");
		String json = "";
		Gson gson = new Gson();
		try {
			String[] values = year.split("-");
			if(values.length !=2) {
				return gson.toJson("Month should be given like YYYY-MM");
			}
			String yearVal = values[0];
			String monVal = values[1];
			if(!monthValidation(Integer.valueOf(monVal))) {
				return gson.toJson("Month should be given between 01 to 12 only ");
			}
			if(monVal.length() ==1) {
				monVal= "0" + monVal;
			}
			
			json = gson.toJson(loadService.loadDataByYearSpecificMonth(String.valueOf(yearVal),String.valueOf(monVal)));
			logger.info("completed for loadDataByYearSpecificMonth{}", json);
		} catch (Exception exp) {
			logger.error("started for loadDataByYearSpecificMonth");
			return gson.toJson("Month should be given like YYYY-MM");
		}
		return json;
	}
	
	/*
	 * Given I want to load data for whole year
     * When I hit REST endpoint of our application
     * Then database has 12 rows with data
	 */
	@RequestMapping(value = "/byCompleteYear/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getRatesByDateloadDataByCompleteYear(@PathVariable(value = "year")String year) {
		logger.info("s"
				+ "tarted for getRatesByDateloadDataByCompleteYear");
		Gson gson = new Gson(); 
		try {
			String[] values = year.split("-");
			String yearVal = values[0];
			String json = gson.toJson(loadService.loadDataByCompleteYear(yearVal));
			if(StringUtils.isEmpty(json)) {
				loadService.clearCache();
				loadService.initialLoadData(String.valueOf(year));
				json = gson.toJson(loadService.loadDataByCompleteYear(yearVal));
			}
			logger.info("completed for getRatesByDateloadDataByCompleteYear{}",json);
			return json;
		}catch(Exception exp) {
			logger.error("started for getRatesByDateloadDataByCompleteYear");
			return gson.toJson("Year should be given Numeric");
		}
	}
	
	/**
	 * Given a specific date (e.g. 2019-05-01)
	 * When I access the rates end-point to get the exchange rate for that date for Pounds Sterling
	 * (GBP) against the Euro (EUR)
	 * Then GBP to EUR rate must be the same rate from the API for that date (e.g. 0.86248)
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/bySpecificMonthAndDate/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String loadDataByYearSpecificMonthAndDate(@PathVariable(value = "year")String year) {
		logger.info("started for loadDataByYearSpecificMonthAndDate");
		Gson gson = new Gson(); 
		String[] values = year.split("-");
		if(values.length !=3) {
			return gson.toJson("Enter year in YYYY-MM-DD format"); 
		}
		String yearVal = values[0];
		String monthVal = values[1];
		if(!monthValidation(Integer.valueOf(monthVal))) {
			return gson.toJson("Month should be given between 01 to 12 only ");
		}
		if(monthVal.length() ==1) {
			monthVal= "0" + monthVal;
		}
		String dayVal = values[2];
		
		String json = gson.toJson(loadService.loadDataByYearSpecificMonthAndDate(yearVal,monthVal,dayVal)); 
		logger.info("completed for loadDataByYearSpecificMonthAndDate{}",json);
		return json;
	}
	
	/*
	 * Given Range of dates
     * When I hit REST endpoint for reading database
     * Then data between given date and Today is returned
	 */
	@RequestMapping(value = "/byByGivendateTilldate/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String loadDataByGivenDateAndTodayDate(@PathVariable(value = "year")String year){
		logger.info("started for loadDataByGivenDateAndTodayDate");
		Gson gson = new Gson(); 
		String[] values = year.split("-");
		if(!monthValidation(Integer.valueOf(values[1]))) {
			return gson.toJson("Month should be given between 01 to 12 only ");
		}
		if(values.length !=3) {
			return gson.toJson("Enter year in YYYY-MM-DD format"); 
		}
		List<Stock> byByGivendateTilldate = loadService.loadDataByGivenDateAndTodayDate(String.valueOf(year));
		if(CollectionUtils.isEmpty(byByGivendateTilldate)) {
			loadService.clearCache();
			loadService.initialLoadData(String.valueOf(year));
			byByGivendateTilldate = loadService.loadDataByGivenDateAndTodayDate(String.valueOf(year));
		}
		String json = gson.toJson(byByGivendateTilldate); 
		logger.info("completed for loadDataByGivenDateAndTodayDate{}",json);
		return json;
	}
	
	/*
	 * Given date 2019-02-01
     * When I hit REST endpoint for given date
     * Then GBP rate for 2019-05-01 equals 0.86248
	 */
	@RequestMapping(value = "/byBySpecificMonthAndDateAndExchge/{year}/{exchange}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String loadDataByYearSpecificMonthAndDateAndExchge(@PathVariable(value = "year") String year,@PathVariable(value = "exchange")String exchange) {
		logger.info("started for loadDataByYearSpecificMonthAndDateAndExchge");
		Gson gson = new Gson(); 
		String[] values = year.split("-");
		if(!monthValidation(Integer.valueOf(values[1]))) {
			return gson.toJson("Month should be given between 01 to 12 only ");
		}
		if(values.length !=3) {
			return gson.toJson("Enter year in YYYY-MM-DD format"); 
		}
		
		Float json = loadService.loadDataByYearSpecificMonthAndDateAndExchge((String.valueOf(year)),exchange); 
		logger.info("completed for loadDataByYearSpecificMonthAndDateAndExchge{}",json);
		return json.toString();
	}
	
	private static boolean  monthValidation(int monthValue) {
		 
		Integer[] monthArr = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
		List<Integer> list = Arrays.asList(monthArr);
		if(list.contains(monthValue)) {
			return true;
		}
		return false;
	}
}
