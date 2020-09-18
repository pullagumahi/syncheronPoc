package com.gcmb.hsbc.model;

import java.util.List;

public class Stock {

	private String base;
	private Rates rates;
	private String date;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Rates getRates() {
		return rates;
	}

	public void setRates(Rates rates) {
		this.rates = rates;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
