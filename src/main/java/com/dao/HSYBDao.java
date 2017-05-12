package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.model.hsyb.DDJGBInfo;
import com.model.hsyb.DDJGTJBKeyValue;
import com.util.ConnectionPool;

public class HSYBDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
	private DecimalFormat  df=new  DecimalFormat("0.00");  
	
    public HSYBDao() throws SQLException {
        this.conn=ConnectionPool.getConnection();
    }
    
    public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
 
    public static final int ONE_DAY = 3600 * 1000 * 24;
    
    //获取调度结果表
    public List<DDJGBInfo> getHSYBJGBData(String SDate,String EDate) throws SQLException, ParseException {
        List<DDJGBInfo> all = new ArrayList<DDJGBInfo>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(SDate));
        long start_ts = calendar.getTimeInMillis();
        calendar.setTime(sdf.parse(EDate));
        long end_ts = calendar.getTimeInMillis();
        long days = (end_ts - start_ts) / ONE_DAY;
        for(long d = 0; d <= days; d++) {
        	long temp_ts = start_ts + (d * ONE_DAY);
        	calendar.setTimeInMillis(temp_ts);
        	String temp_date = sdf.format(calendar.getTime());
	        for(int i = 0; i < 24; i++) {
	        	DDJGBInfo tempData = new DDJGBInfo();
	        	if(i < 10) {
	        		tempData.setHour("0"+i+":00");
	        	} else {
	        		tempData.setHour(i+":00");
	        	}
	        	tempData.setTime(temp_date+" "+tempData.getHour());
	        	
	        	tempData.setSc_jyl(df.format(Math.random()*i)+"");
	        	tempData.setYb_jyl(df.format(Math.random()*i)+"");
	        	
	        	tempData.setSc_jyl(df.format(Math.random()+1)+"");
	        	tempData.setYb_jyl(df.format(Math.random()+2)+"");
	        	
	        	tempData.setSc_rkll(df.format(Math.random()+5)+"");
	        	tempData.setYb_rkll(df.format(Math.random()+6)+"");
	        	
	        	tempData.setSc_ckll(df.format(Math.random()+9)+"");
                tempData.setYb_ckll(df.format(Math.random()+10)+"");
	        	
	        	tempData.setSc_ksw(df.format(Math.random()+13)+"");
	        	tempData.setYb_ksw(df.format(Math.random()+14)+"");
	        	
	        	all.add(tempData);
	        }
        }
        
        return all;
    }
    
    //获取调度结果统计表
    public List<DDJGTJBKeyValue> getHSYBJGTJBData0(String SDate,String EDate) throws SQLException {
        List<DDJGTJBKeyValue> all = new ArrayList<DDJGTJBKeyValue>();
        
        DDJGTJBKeyValue tempData = new DDJGTJBKeyValue();
        tempData.setKey0("预报开始时间");
        tempData.setValue0("2017-05-03 00:00");
        tempData.setKey1("预报时段");
        tempData.setValue1("24");
        tempData.setKey2("预见期");
        tempData.setValue2("8");
        all.add(tempData);
        
        tempData = new DDJGTJBKeyValue();
        tempData.setKey0("预报结束时间");
        tempData.setValue0("2017-05-03 23:59");
        tempData.setKey1("预报模型");
        tempData.setValue1("模型1");
        tempData.setKey2("");
        tempData.setValue2("");
        all.add(tempData);
        
        return all;
    }
    
    //获取调度结果统计表
    public List<DDJGTJBKeyValue> getHSYBJGTJBData1(String SDate,String EDate) throws SQLException {
        List<DDJGTJBKeyValue> all = new ArrayList<DDJGTJBKeyValue>();
        
        DDJGTJBKeyValue tempData = new DDJGTJBKeyValue();
        tempData.setKey0("当前累计降雨量");
        tempData.setValue0("68");
        tempData.setKey1("");
        tempData.setValue1("");
        tempData.setKey2("");
        tempData.setValue2("");
        all.add(tempData);
        
        tempData = new DDJGTJBKeyValue();
        tempData.setKey0("当前最大入库");
        tempData.setValue0("15");
        tempData.setKey1("当前最大入库时间");
        tempData.setValue1("2017-05-06 11:00");
        tempData.setKey2("当前总入库量");
        tempData.setValue2("180");
        all.add(tempData);
        
        tempData = new DDJGTJBKeyValue();
        tempData.setKey0("当前最高库水位");
        tempData.setValue0("210");
        tempData.setKey1("当前最高库水位时间");
        tempData.setValue1("2017-05-06 13:00");
        tempData.setKey2("当前最高库水位相应库容");
        tempData.setValue2("1920394");
        all.add(tempData);
        
        return all;
    }
    
    //获取调度结果统计表
    public List<DDJGTJBKeyValue> getHSYBJGTJBData2(String SDate,String EDate) throws SQLException {
        List<DDJGTJBKeyValue> all = new ArrayList<DDJGTJBKeyValue>();
        
        DDJGTJBKeyValue tempData = new DDJGTJBKeyValue();
        tempData.setKey0("预报累计降雨量");
        tempData.setValue0("73");
        tempData.setKey1("");
        tempData.setValue1("");
        tempData.setKey2("");
        tempData.setValue2("");
        all.add(tempData);
        
        tempData = new DDJGTJBKeyValue();
        tempData.setKey0("预报最大入库");
        tempData.setValue0("15");
        tempData.setKey1("预报最大入库时间");
        tempData.setValue1("2017-05-06 11:00");
        tempData.setKey2("预报总入库量");
        tempData.setValue2("180");
        all.add(tempData);
        
        tempData = new DDJGTJBKeyValue();
        tempData.setKey0("预报最高库水位");
        tempData.setValue0("210");
        tempData.setKey1("预报最高库水位时间");
        tempData.setValue1("2017-05-06 13:00");
        tempData.setKey2("预报最高库水位相应库容");
        tempData.setValue2("1920394");
        all.add(tempData);
        
        return all;
    }
    
    //获取调度过程图
    public List<DDJGBInfo> getHSYBGCTData(String SDate,String EDate) throws SQLException {
        List<DDJGBInfo> all = new ArrayList<DDJGBInfo>();
        
        for(int i = 0; i < 24; i++)
        {
        	DDJGBInfo tempData = new DDJGBInfo();
        	tempData.setHour(i+":00");
        	tempData.setTime(SDate+" "+tempData.getHour());
        	all.add(tempData);
        }
        
        return all;
    }
}



