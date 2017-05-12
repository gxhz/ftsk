package com.model.dbjc;

import java.util.ArrayList;

//渗压水位-库水位关系图

public class SYSWKSWGXTInfo {
    
	public String year="2015";  //年份
	
	public ArrayList<String> rqlist=new ArrayList<String>();  //日期列表
	public ArrayList<Double> syswlist = new ArrayList<Double>(); // 渗压水位列表
	public ArrayList<Double> kswlist = new ArrayList<Double>();  // 库水位列表
	
    public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
    public ArrayList<String> getRqlist() {
        return rqlist;
    }
    public void setRqlist(ArrayList<String> rqlist) {
        this.rqlist = rqlist;
    }
    public ArrayList<Double> getSyswlist() {
        return syswlist;
    }
    public void setSyswlist(ArrayList<Double> syswlist) {
        this.syswlist = syswlist;
    }
	public ArrayList<Double> getKswlist() {
		return kswlist;
	}
	public void setKswlist(ArrayList<Double> kswlist) {
		this.kswlist = kswlist;
	}
	
}
