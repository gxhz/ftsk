package com.model.dbjc;

import java.util.ArrayList;

//大坝安全分布图
public class DBFBTInfo {
    
	public String ksw="0.00";  //库水位
	public ArrayList<String> timelist=new ArrayList<String>();  //时间列表
	public ArrayList<Double> syswlist=new ArrayList<Double>();  //渗压水位列表
	
    public String getKsw() {
        return ksw;
    }
    public void setKsw(String ksw) {
        this.ksw = ksw;
    }
    public ArrayList<String> getTimelist() {
        return timelist;
    }
    public void setTimelist(ArrayList<String> timelist) {
        this.timelist = timelist;
    }
    public ArrayList<Double> getSyswlist() {
        return syswlist;
    }
    public void setSyswlist(ArrayList<Double> syswlist) {
        this.syswlist = syswlist;
    }
	
}
