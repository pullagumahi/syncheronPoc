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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;

import com.gcmb.hsbc.Month;
import com.gcmb.hsbc.MonthVal;
import com.gcmb.hsbc.model.Stock;
import com.gcmb.hsbc.service.CacheService;
import com.gcmb.hsbc.service.ExchangeService;

@RestController
@EnableAutoConfiguration
public class ExchangeController {
	private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);
	
	private ExchangeService loadService;
	
	private CacheService cacheService;
	
	@Autowired
	public ExchangeController(ExchangeService loadService, CacheService cacheService) {
		super();
		this.loadService = loadService;
		this.cacheService = cacheService;
	}

	@ApiOperation(value = "Request by Specific Month and Year and Date ",  notes = "User Story 1 : Acceptance criteria 1 - only last 12 months are included and only GBP/USD/HKD currencies are included ")
	@RequestMapping(method = RequestMethod.GET, value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public String initialLoad() throws InterruptedException {
		logger.info("initialLoad for current year started");
		int year = Calendar.getInstance().get(Calendar.YEAR);
		cacheService.initialLoadData(String.valueOf(year));
		Thread.sleep(10000L);
		Gson gson = new Gson();
		logger.info("initialLoad for current year completed");
		return gson.toJson("Please open http://localhost:portno/swagger-ui.html#");
	}
	
	@ApiOperation(value = "Request by Specific Month and Year",  notes = "By default current year only GBP/USD/HKD currencies are included")
	@RequestMapping(method = RequestMethod.GET, value = "/byMonthYear/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
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
	

	@ApiOperation(value = "Request by Specific Year  ",  notes = "User Story 1 : Acceptance criteria 3 - only last 12 months are included and only GBP/USD/HKD currencies are included ")
	@RequestMapping(method = RequestMethod.GET, value = "/byCompleteYear/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getRatesByDateloadDataByCompleteYear(@PathVariable(value = "year")String year) {
		logger.info("s"
				+ "tarted for getRatesByDateloadDataByCompleteYear");
		Gson gson = new Gson(); 
		try {
			String[] values = year.split("-");
			String yearVal = values[0];
			String json = gson.toJson(loadService.loadDataByCompleteYear(yearVal));
			if(StringUtils.isEmpty(json)) {
				cacheService.clearCache();
				cacheService.initialLoadData(String.valueOf(year));
				json = gson.toJson(loadService.loadDataByCompleteYear(yearVal));
			}
			logger.info("completed for getRatesByDateloadDataByCompleteYear{}",json);
			return json;
		}catch(Exception exp) {
			logger.error("started for getRatesByDateloadDataByCompleteYear");
			return gson.toJson("Year should be given Numeric");
		}
	}
	

	@ApiOperation(value = "Request by Specific Month and Year and Day",  notes = "User Story 1 : Acceptance criteria 2 : Specific date (e.g. 2019-05-01)")
	@RequestMapping(method = RequestMethod.GET, value = "/bySpecificMonthAndDate/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
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
		if(Integer.valueOf(dayVal)>31) {
			return gson.toJson("Month should be given between 01 to 30 or 31 only ");
		}
		
		String json = gson.toJson(loadService.loadDataByYearSpecificMonthAndDate(yearVal,monthVal,dayVal)); 
		logger.info("completed for loadDataByYearSpecificMonthAndDate{}",json);
		return json;
	}
	

	
	@ApiOperation(value = "Request by given date and today date",  notes = "User Story 2 : Acceptance criteria 1 : Specific date (e.g. 2019-05-01)data between given date and Today is returned")
	@RequestMapping(method = RequestMethod.GET, value = "/byByGivendateTilldate/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String loadDataByGivenDateAndTodayDate(@PathVariable(value = "year")String year){
		logger.info("started for loadDataByGivenDateAndTodayDate");
		Gson gson = new Gson(); 
		String[] values = year.split("-");
		if(values.length !=3) {
			return gson.toJson("Enter year in YYYY-MM-DD format"); 
		}
		if(!monthValidation(Integer.valueOf(values[1]))) {
			return gson.toJson("Month should be given between 01 to 12 only ");
		}
		if(Integer.valueOf(Integer.valueOf(values[1]))>31) {
			return gson.toJson("Month should be given between 01 to 30 or 31 only ");
		}
		List<Stock> byByGivendateTilldate = loadService.loadDataByGivenDateAndTodayDate(String.valueOf(year));
		if(CollectionUtils.isEmpty(byByGivendateTilldate)) {
			cacheService.clearCache();
			cacheService.initialLoadData(String.valueOf(year));
			byByGivendateTilldate = loadService.loadDataByGivenDateAndTodayDate(String.valueOf(year));
		}
		String json = gson.toJson(byByGivendateTilldate); 
		logger.info("completed for loadDataByGivenDateAndTodayDate{}",json);
		return json;
	}
	

	@ApiOperation(value = "Request by given date and today date",  notes = "User Story 2 : Acceptance criteria 2 : Specific date and Exchange")
	@RequestMapping(method = RequestMethod.GET, value = "/byBySpecificMonthAndDateAndExchge/{year}/{exchange}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String loadDataByYearSpecificMonthAndDateAndExchge(@PathVariable(value = "year") String year,@PathVariable(value = "exchange")String exchange) {
		logger.info("started for loadDataByYearSpecificMonthAndDateAndExchge");
		Gson gson = new Gson(); 
		String[] values = year.split("-");
	    List<String> list = new ArrayList<>();
	    list.add("GBP");
	    list.add("USD");
	    list.add("HKD");
	    
	    if(!list.contains(exchange)) {
	    	return gson.toJson("Exchange should be given GBP or USD or HKD ");
	    }
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
