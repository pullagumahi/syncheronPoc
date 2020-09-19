package com.gcmb.hsbc;

public enum MonthVal {
	   JAN(1),
	   FEB(2),
	   MAR(3),
	   APR(4),
	   MAY(5),
	   JUNE(6),
	   JUL(7),
	   AUG(8),
	   SEP(9),
	   OCT(10),
	   NOV(11),
	   DEC(12);
	
	private final int monthCode;

	MonthVal(int code) {
		this.monthCode = code;
	}

	public int getMonthCode() {
		return this.monthCode;
	}
}
