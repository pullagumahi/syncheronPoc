package com.gcmb.hsbc;

public enum Month {
   JAN(31),
   FEB(29),
   MAR(31),
   APR(30),
   MAY(31),
   JUNE(30),
   JUL(31),
   AUG(31),
   SEP(30),
   OCT(31),
   NOV(30),
   DEC(31);
	
	 private final int monthLastDay;
	  
	  Month(int code) {
	      this.monthLastDay = code;
	  }
		  
	  public int getMonthLastDay() {
	      return this.monthLastDay;
	  }
}
