package com.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import com.model.dbjc.DBFBTInfo;
import com.model.dbjc.DBJCFindInfo;
import com.model.dbjc.DBNBBTJInfo;
import com.model.dbjc.DBNGCXJInfo;
import com.model.dbjc.DBSDBBTJInfo;
import com.model.dbjc.DJRTDataDetails;
import com.model.dbjc.KSWDataDetails;
import com.model.dbjc.SLRTDataDetails;
import com.model.dbjc.SYSWKSWGXTInfo;
import com.util.ConnectionPool;

public class DBJCDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
    public DBJCDao() throws SQLException {
        this.conn=ConnectionPool.getConnection();
    }
    
    public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
 
    public List<DBJCFindInfo> getSearchInfo(int page,int rows,String sbid,String name,String skid,String sdate,String edate) throws SQLException {
    	if(page == 0) page = 1;
    	int start=(page-1)*rows;
    	int end=start+rows;
    	
    	String findwhere = "damd.id<>0";
    	if(!sbid.equals("")) findwhere += " and damd.id_device='"+sbid+"'";  
    	if((!sdate.equals(""))&&(!edate.equals(""))) findwhere += " and damd.time >= '"+sdate+" 00:00:00' and damd.time <= '"+edate+" 23:59:59'";
    	
    	List<DBJCFindInfo> all=new ArrayList<DBJCFindInfo>();
    	String sql="select damd.id,damd.time,damp.name,dev.name,sec.name,damd.level," +
    	        "damd.osmoticpressure,damd.seepageflow,damd.temperature,damd.ordernumber" +
    			" from damdatas as damd" +
    	        " left join devices as dev on dev.code=damd.id_device" + 
    	        " left join dammonitoringpoints as damp on damp.id_device=damd.id_device" +
    	        " left join sections as sec on sec.id=damp.id_section" +
    			" where " + findwhere + " order by damd.id limit ?,?";
    	if((!skid.equals(""))||(!name.equals(""))) {  
    	    String dmfindwhere=" and damp.id_device=damd.id_device";
            if(!skid.equals("")) dmfindwhere += " and damp.id_section='"+skid+"'";  
            if(!name.equals("")) dmfindwhere += " and damp.name like '%"+name+"%'";
            
            sql= "select damd.id,damd.time,damp.name,dev.name,sec.name,damd.level," +
                    "damd.osmoticpressure,damd.seepageflow,damd.temperature,damd.ordernumber" +
                    " from damdatas as damd,dammonitoringpoints as damp" +
                    " left join devices as dev on dev.code=damp.id_device" + 
                    " left join sections as sec on sec.id=damp.id_section" +
                    " where " + findwhere + dmfindwhere + " order by damd.id limit ?,?";
    	}
    	this.ps=this.conn.prepareStatement(sql);
    	this.ps.setInt(1, start);
    	this.ps.setInt(2, end);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    DBJCFindInfo info=new DBJCFindInfo();
    		info.setId(rs.getInt(1));
    		info.setTime(rs.getString(2));
    		info.setJCDName(rs.getString(3));
    		info.setSBName(rs.getString(4));
    		info.setDMName(rs.getString(5));
    		info.setSW(rs.getDouble(6));
    		info.setSY(rs.getDouble(7));
    		info.setSL(rs.getDouble(8));
    		info.setWD(rs.getDouble(9));
    		info.setIndex(rs.getInt(10));
    		all.add(info);
    	}
    	return all;
    }
 
    public int getSearchRows(String sbid,String name,String skid,String sdate,String edate) throws SQLException {
        String findwhere = "damd.id<>0";
        if(!sbid.equals("")) findwhere += " and damd.id_device='"+sbid+"'";
        if((!sdate.equals(""))&&(!edate.equals(""))) findwhere += " and damd.time >= '"+sdate+" 00:00:00' and damd.time <= '"+edate+" 23:59:59'";
        
        String sql="select count(*) from damdatas as damd" +
                " left join devices as dev on dev.code=damd.id_device" + 
                " left join dammonitoringpoints as damp on damp.id_device=damd.id_device" +
                " left join sections as sec on sec.id=damp.id_section" +
                " where " + findwhere;
        if((!skid.equals(""))||(!name.equals(""))) {
            String dmfindwhere=" and damp.id_device=damd.id_device";
            if(!skid.equals("")) dmfindwhere += " and damp.id_section='"+skid+"'";
            if(!name.equals("")) dmfindwhere += " and damp.name like '%"+name+"%'";
            
            sql= "select count(*) from damdatas as damd,dammonitoringpoints as damp" +
                    " left join devices as dev on dev.code=damp.id_device" + 
                    " left join sections as sec on sec.id=damp.id_section" +
                    " where " + findwhere + dmfindwhere;
        }
        this.ps=this.conn.prepareStatement(sql);
        int rows=0;
        this.rs=ps.executeQuery();
 	    if(rs.next()){
 	        rows=rs.getInt(1);
	 	}
 	    return rows;
    }
    
    public List<DBJCFindInfo> getSearchInfo(String skid,String sdate,String edate) throws SQLException {
        List<DBJCFindInfo> all=new ArrayList<DBJCFindInfo>();
        
        String findwhere = "damd.id<>0";
        if((!sdate.equals(""))&&(!edate.equals(""))) findwhere += " and damd.time >= '"+sdate+" 00:00:00' and damd.time <= '"+edate+" 23:59:59'";
        String dmfindwhere=" and damp.id_device=damd.id_device";
        if(!skid.equals("")) dmfindwhere += " and damp.id_section='"+skid+"'";
        
        String sql= "select damd.id,damd.osmoticpressure" +
                " from damdatas as damd,dammonitoringpoints as damp" +
                " left join devices as dev on dev.code=damp.id_device" + 
                " left join sections as sec on sec.id=damp.id_section" +
                " where " + findwhere + dmfindwhere + " order by damd.id";
        
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            DBJCFindInfo info=new DBJCFindInfo();
            info.setId(rs.getInt(1));
            info.setSY(rs.getDouble(2));
            all.add(info);
        }
        return all;
    }
    
    //根据断面ID获取大坝传感器参数
    public List<DJRTDataDetails> getDBRTData(String dbdm) throws SQLException {
        List<DJRTDataDetails> all=new ArrayList<DJRTDataDetails>();
        String sql="select * from db_rt_sysw as drs where id<>0";
        if(!dbdm.equals("")) sql += " and dm="+dbdm;
        sql += " order by name asc";
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            DJRTDataDetails info = new DJRTDataDetails();
            info.setName(rs.getString(2));
            info.setSysw(rs.getDouble(3));
            info.setMsgc(rs.getDouble(4));
            info.setGkgc(rs.getDouble(5));
            info.setPlms(rs.getDouble(6));
            info.setWd(rs.getDouble(7));
            info.setMcubh(rs.getInt(8));
            info.setMcutd(rs.getInt(9));
            info.setDm(rs.getInt(10));
            all.add(info);
        }
        return all;
    }
    
    //获取实时库水位、库容量
    public List<KSWDataDetails> getKSWRTData() throws SQLException {
        List<KSWDataDetails> all=new ArrayList<KSWDataDetails>();
        String sql="select syksw,xyksw,krl,yhll,fdll,rkll from syq_rt_ksw";
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            KSWDataDetails info = new KSWDataDetails();
            info.setSysw(rs.getDouble(1));
            info.setXysw(rs.getDouble(2));
            info.setKrl(rs.getDouble(3));
            info.setYhll(rs.getDouble(4));
            info.setFdll(rs.getDouble(5));
            info.setRkll(rs.getDouble(6));
            all.add(info);
        }
        return all;
    }
    
    //获取所有渗流量
    public List<SLRTDataDetails> getDBRTData() throws SQLException {
        List<SLRTDataDetails> all=new ArrayList<SLRTDataDetails>();
        String sql="select * from db_rt_lsy order by name asc";
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            SLRTDataDetails info = new SLRTDataDetails();
            info.setName(rs.getString(2));
            info.setLsyll(rs.getDouble(3));
            info.setPlms(rs.getDouble(4));
            info.setWd(rs.getDouble(5));
            info.setMcubh(rs.getInt(6));
            info.setMcutd(rs.getInt(7));
            all.add(info);
        }
        return all;
    }
 
	// 获取大坝详细实时数据
	public List<DJRTDataDetails> getDBRTDataDetails(String names)
			throws SQLException {
		List<DJRTDataDetails> all = new ArrayList<DJRTDataDetails>();
		String sql = "select * from db_rt_sysw as drs where drs.name ='"+ names + "'";
		this.ps = this.conn.prepareStatement(sql);
		this.rs = ps.executeQuery();
		while (rs.next()) {
			DJRTDataDetails info = new DJRTDataDetails();
			info.setName(rs.getString(2));
			info.setSysw(rs.getDouble(3));
			info.setMsgc(rs.getDouble(4));
			info.setGkgc(rs.getDouble(5));
			info.setPlms(rs.getDouble(6));
			info.setWd(rs.getDouble(7));
			info.setMcubh(rs.getInt(8));
			info.setMcutd(rs.getInt(9));
			info.setDm(rs.getInt(10));
			all.add(info);
		}
		return all;
	}
	
	// 获取大坝详细实时数据
    public List<SLRTDataDetails> getSLRTDataDetails(String names)throws SQLException {
        List<SLRTDataDetails> all = new ArrayList<SLRTDataDetails>();
        String sql = "select * from db_rt_lsy as lsy where lsy.name ='"+ names + "'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            SLRTDataDetails info = new SLRTDataDetails();
            info.setName(rs.getString(2));
            info.setLsyll(rs.getDouble(3));
            info.setPlms(rs.getDouble(4));
            info.setWd(rs.getDouble(5));
            info.setMcubh(rs.getInt(6));
            info.setMcutd(rs.getInt(7));
            all.add(info);
        }
        return all;
    }
	
	//根据序号获取值
	private double[] getValue(ResultSet rs,int aIndex) {
	    double dtemp[]=new double[2];
	    for(int i=0;i<dtemp.length;i++) dtemp[i] = 0.00;
	    
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int column=rsmd.getColumnCount();
            if(aIndex <= column) {
                String stemp=rs.getString(aIndex);
                if(stemp != null) {
                    if(stemp.indexOf(",") >= 0) {  //渗流量
                        String datas[]=stemp.split(",");
                        if(datas.length >= 2) {
                            dtemp[0] = Double.valueOf(datas[0].trim());  //渗压水位或渗流量
                            dtemp[1] = Double.valueOf(datas[1].trim());  //水温（量水堰）
                        }
                    } else {  //渗压水位
                        dtemp[0] = Double.valueOf(stemp);  //渗压水位或渗流量
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	    
	    return dtemp;
	}
	
	//根据序号获取列表中的值
	private double getDDValue(double[] datas, int aIndex) {
        double dtemp=0.0;
        
        if(aIndex < datas.length) dtemp = datas[aIndex];
        
        return dtemp;
    }
	private String getDDValue(String[] datas, int aIndex) {
        String stemp="";
        
        if(aIndex < datas.length) stemp = datas[aIndex];
        
        return stemp;
    }
	
	//根据时间获取参数类
	private DBSDBBTJInfo getInfo(List<DBSDBBTJInfo> list, String time) {
	    DBSDBBTJInfo info=null;
	    
	    for(int i=0;i<list.size();i++) {
	        DBSDBBTJInfo temp=list.get(i);
	        String temptime=temp.getTime().trim();
	        if(temptime.equals(time)) {
	            info = temp;
	            break;
	        }
	    }
	    
	    return info;
	}
	
	//获取时段、日、月、年报表统计和过程线
	public List<DBSDBBTJInfo> getSDBBTJData(String cgqtype,String state,String names,String sdate,String edate,String tablename) throws SQLException {
        List<DBSDBBTJInfo> all = new ArrayList<DBSDBBTJInfo>();
        
        if(names.equals("")) return all;
        if(sdate.equals("")) return all;
        if(edate.equals("")) return all;
        
        DecimalFormat df=new DecimalFormat("0.00");  
        
        String datas[]=names.split(",");
        
        boolean flag=false;
        if(edate.equals("-1")) {
            edate = sdate;
            flag = true;
        }
        
        //计算两日期相关天数
        long day=0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");    
        try {
            Date begindate = sdf.parse(sdate);
            Date enddate = sdf.parse(edate);    
            day = (enddate.getTime()-begindate.getTime())/(24*60*60*1000);
            day++;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        //进行列表初始化
        try {
            Calendar calendar=Calendar.getInstance();
            Date begindate = sdf.parse(sdate);
            calendar.setTime(begindate);
            
            for(int i=0;i<day;i++) {
                DBSDBBTJInfo info = new DBSDBBTJInfo();
                info.setTime(sdf.format(calendar.getTime()));
                all.add(info);
                
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        //组合SQL语句
        String sql = "select distinct db.rq";
        sql += ",(select ksw.hour7 from syq_his_ksw as ksw where ksw.rq=db.rq and ksw.code='001') as sysw";  //上游水位
        sql += ",(select ksw.hour7 from syq_his_ksw as ksw where ksw.rq=db.rq and ksw.code='002') as xysw";  //下游水位
        sql += ",(select syq.jyl_day from syq_his_jyl as syq where syq.rq=db.rq and syq.code='001') as rjyl";  //日降雨量
        
        String namelist="";
        for(int i=0;i<datas.length;i++) {
            String str=datas[i].trim();
            sql += ",max(case db.name when '"+str+"' then db.hour7 else 0.0 end) as sysw"+(i+1);
            if(namelist.equals("")) {
                namelist = "'"+str+"'";
            } else {
                namelist += ",'"+str+"'";
            }
        }
        
        sql += " from "+tablename+" as db where db.rq >= '"+sdate+"' and db.rq <= '"+edate+"'";
        if(!namelist.equals("")) sql += " and db.name in("+namelist+")";
        sql += " group by db.rq order by db.rq asc";
        
        double[] maxvalue=new double[55];  //最大值
        String[] maxtime=new String[55];  //最大值时间
        double[] minvalue=new double[55];  //最小值
        String[] mintime=new String[55];  //最小值时间
        double[] avgvalue=new double[55];  //平均值
        
        for(int i=0;i<maxvalue.length;i++) maxvalue[i] = 0.0;
        for(int i=0;i<maxtime.length;i++) maxtime[i] = sdate;
        for(int i=0;i<minvalue.length;i++) minvalue[i] = 888888.88;
        for(int i=0;i<mintime.length;i++) mintime[i] = sdate;
        for(int i=0;i<avgvalue.length;i++) avgvalue[i] = 0.0;
        
        this.ps = this.conn.prepareStatement(sql);
        this.rs = this.ps.executeQuery();
        while (this.rs.next()) {
            String time=rs.getString(1);  //时间
            DBSDBBTJInfo info=getInfo(all, time);
            if(info != null) {
                info.setTime(time);
                info.setSyksw(rs.getString(2));  //上游库水位
                info.setXyksw(rs.getString(3));  //下游库水位
                info.setRjyl(rs.getString(4));  //日降雨量
                
                //处理上游库水位相关数据
                double dtemp=rs.getDouble(2);  //上游库水位
                if(dtemp > maxvalue[0]) {  //获取最大值和时间
                    maxvalue[0] = dtemp; 
                    maxtime[0] = rs.getString(1);  //时间
                }
                if(dtemp < minvalue[0]) {  //获取最小值和时间
                    minvalue[0] = dtemp; 
                    mintime[0] = rs.getString(1);  //时间
                }
                avgvalue[0] += dtemp;  //平均值
                
                //处理下游库水位相关数据
                dtemp=rs.getDouble(3);  //下游库水位
                if(dtemp > maxvalue[1]) {  //获取最大值和时间
                    maxvalue[1] = dtemp; 
                    maxtime[1] = rs.getString(1);  //时间
                }
                if(dtemp < minvalue[1]) {  //获取最小值和时间
                    minvalue[1] = dtemp; 
                    mintime[1] = rs.getString(1);  //时间
                }
                avgvalue[1] += dtemp;  //平均值
                
                //处理日降雨量相关数据
                dtemp=rs.getDouble(4);  //日降雨量
                if(dtemp > maxvalue[2]) {  //获取最大值和时间
                    maxvalue[2] = dtemp; 
                    maxtime[2] = rs.getString(1);  //时间
                }
                if(dtemp < minvalue[2]) {  //获取最小值和时间
                    minvalue[2] = dtemp; 
                    mintime[2] = rs.getString(1);  //时间
                }
                avgvalue[2] += dtemp;  //平均值
                
                //利用反射机制给对象赋值
                Field[] fields=info.getClass().getDeclaredFields();  
                for(int i=4; i<fields.length; i++) {
                    Field field=fields[i];
                    try {
                        double[] values=getValue(this.rs, i+1);
                        dtemp = values[0];  //渗压水位或渗流量
                        
                        if(cgqtype.equals("1")) {  //渗压
                        } else if(cgqtype.equals("2")) {  //渗流（因有水温，所以需要特殊处理）
                            if(i == 4) {
                                values = getValue(this.rs, 5);
                                dtemp = values[0];  //渗压水位或渗流量
                            } else if(i == 5) {
                                values = getValue(this.rs, 5);
                                dtemp = values[1];  //水温（量水堰）
                            } else if(i == 6) {
                                values = getValue(this.rs, 6);
                                dtemp = values[0];  //渗压水位或渗流量
                            } else if(i == 7) {
                                values = getValue(this.rs, 6);
                                dtemp = values[1];  //水温（量水堰）
                            }
                        }
                        
                        field.set(info, df.format(dtemp));
                        
                        if(dtemp > maxvalue[i-1]) {  //获取最大值和时间
                            maxvalue[i-1] = dtemp; 
                            maxtime[i-1] = rs.getString(1);  //时间
                        }
                        if(dtemp < minvalue[i-1]) {  //获取最小值和时间
                            minvalue[i-1] = dtemp; 
                            mintime[i-1] = rs.getString(1);  //时间
                        }
                        avgvalue[i-1] += dtemp;  //平均值
                        
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        int row = all.size();
        if(row > 0) {
            for(int i=0;i<avgvalue.length;i++) avgvalue[i] = avgvalue[i] / (row*1.0);  //平均值
        }
        
        if((!flag)&&(!names.equals(""))) {  //时段、月统计才有，日统计没有
            //最大值
            DBSDBBTJInfo info = new DBSDBBTJInfo();
            info.setTime("最大值");
            info.setSyksw(df.format(maxvalue[0]));  //上游库水位
            info.setXyksw(df.format(maxvalue[1]));  //下游库水位
            info.setRjyl(df.format(maxvalue[2]));  //日降雨量
            //利用反射机制给对象赋值
            Field[] fields=info.getClass().getDeclaredFields();  
            for(int i=4; i<fields.length; i++) {
                Field field=fields[i];
                try {
                    field.set(info, df.format(getDDValue(maxvalue, i-1)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            all.add(info);
            
            //最大值出现时间
            info = new DBSDBBTJInfo();
            info.setTime("最大值出现时间");
            info.setSyksw(maxtime[0]);  //上游库水位
            info.setXyksw(maxtime[1]);  //下游库水位
            info.setRjyl(maxtime[2]);  //日降雨量
            //利用反射机制给对象赋值
            fields=info.getClass().getDeclaredFields();  
            for(int i=4; i<fields.length; i++) {
                Field field=fields[i];
                try {
                    field.set(info, getDDValue(maxtime, i-1));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            all.add(info);
            
            //最小值
            info = new DBSDBBTJInfo();
            info.setTime("最小值");
            
            double dtemp=minvalue[0];
            if(dtemp == 888888.88) dtemp=0.0;
            info.setSyksw(df.format(dtemp));  //上游库水位
            
            dtemp=minvalue[1];
            if(dtemp == 888888.88) dtemp=0.0;
            info.setXyksw(df.format(dtemp));  //下游库水位
            
            dtemp=minvalue[2];
            if(dtemp == 888888.88) dtemp=0.0;
            info.setRjyl(df.format(dtemp));  //日降雨量
            
            //利用反射机制给对象赋值
            fields=info.getClass().getDeclaredFields();  
            for(int i=4; i<fields.length; i++) {
                Field field=fields[i];
                try {
                    dtemp = getDDValue(minvalue, i-1);
                    if(dtemp == 888888.88) dtemp=0.0;
                    field.set(info, df.format(dtemp));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            all.add(info);
            
            //最小值出现时间
            info = new DBSDBBTJInfo();
            info.setTime("最小值出现时间");
            info.setSyksw(mintime[0]);  //上游库水位
            info.setXyksw(mintime[1]);  //下游库水位
            info.setRjyl(mintime[2]);  //日降雨量
            //利用反射机制给对象赋值
            fields=info.getClass().getDeclaredFields();  
            for(int i=4; i<fields.length; i++) {
                Field field=fields[i];
                try {
                    field.set(info, getDDValue(mintime, i-1));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            all.add(info);
            
            //平均值
            info = new DBSDBBTJInfo();
            info.setTime("平均值");
            info.setSyksw(df.format(avgvalue[0]));  //上游库水位
            info.setXyksw(df.format(avgvalue[1]));  //下游库水位
            info.setRjyl(df.format(avgvalue[2]));  //日降雨量
            //利用反射机制给对象赋值
            fields=info.getClass().getDeclaredFields();  
            for(int i=4; i<fields.length; i++) {
                Field field=fields[i];
                try {
                    field.set(info, df.format(getDDValue(avgvalue, i-1)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            all.add(info);
        }
        
        return all;
    }
	
	//根据时间进行赋值
	private void makeNBBTJData(List<DBNBBTJInfo> list, String time, double value, double rjyl) {
	    String datas[]=time.split("-");
	    if(datas.length < 3) return;
	    
	    String smonth=datas[1].trim();  //月份
	    String sday=datas[2].trim();  //日
	    
	    int imonth=Integer.valueOf(smonth);
	    int iday=Integer.valueOf(sday);
	    
	    int day=iday-1;
	    if(day >= list.size()) return;
	    
	    //根据日期获取对象
	    DBNBBTJInfo info=list.get(day);
	    if(info == null) return;
	    
	    //根据月份给对象赋值
        DecimalFormat df=new DecimalFormat("0.00");
	    
	    info.setRjyl(df.format(rjyl));  //日降雨最
	    
	    switch(imonth) {
	    case 1: info.setSysw1(df.format(value)); break;
	    case 2: info.setSysw2(df.format(value)); break;
	    case 3: info.setSysw3(df.format(value)); break;
	    case 4: info.setSysw4(df.format(value)); break;
	    case 5: info.setSysw5(df.format(value)); break;
	    case 6: info.setSysw6(df.format(value)); break;
	    case 7: info.setSysw7(df.format(value)); break;
	    case 8: info.setSysw8(df.format(value)); break;
	    case 9: info.setSysw9(df.format(value)); break;
	    case 10: info.setSysw10(df.format(value)); break;
	    case 11: info.setSysw11(df.format(value)); break;
	    case 12: info.setSysw12(df.format(value)); break;
	    }
	}
	
	//获取年报表统计（针对单个传感器）
    public List<DBNBBTJInfo> getNBBTJData(String cgqtype,String state,String names,String sdate,String edate,String tablename) throws SQLException {
        List<DBNBBTJInfo> all = new ArrayList<DBNBBTJInfo>();
        if(tablename.equals("")) return all;
        
        String cgqname="渗压";
        if(cgqtype.equals("1")) {
            cgqname = "渗压";
        } else if(cgqtype.equals("2")) {
            cgqname = "渗流";
        }
        
        //初始化年报表数据
        for(int i=0;i<31;i++) {
            DBNBBTJInfo info = new DBNBBTJInfo();
            info.setTime((i+1)+"日");
            all.add(info);
        }
        
        //组合SQL语句
        String sql = "select distinct db.rq" +
                ",(select jyl_day from syq_his_jyl as syq where syq.rq=db.rq and syq.code='001') as rjyl" + 
                ",db.hour7 from "+tablename+" as db where db.rq >= '"+sdate+"' and db.rq <= '"+edate+
                "'  and db.name='"+names+"' group by db.rq,db.hour7 order by db.rq asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = this.ps.executeQuery();
        while (this.rs.next()) {
            String time=rs.getString(1);  //时间
            double rjyl=rs.getDouble(2);  //日降雨量
            String hour7=rs.getString(3);  //渗压或渗流量/水温
            
            double value=0.0;
            //double wdvalue=0.0;  //水温（量水堰）
            if(hour7.indexOf(",") >= 0) {  //渗流（因渗流量有水温）
                String datas[]=hour7.split(",");
                if(datas.length >= 2) {
                    value = Double.valueOf(datas[0].trim());
                    //wdvalue = Double.valueOf(datas[1].trim());
                }
            } else {  //渗压
                value = Double.valueOf(hour7);
            }
            
            makeNBBTJData(all, time, value, rjyl);
        }
        
        //最大值、最大值时间、最小值、最小值时间、平均值、年统计处理
        double[] maxvalue=new double[12];  //最大值
        String[] maxtime=new String[12];  //最大值时间
        double[] minvalue=new double[12];  //最小值
        String[] mintime=new String[12];  //最小值时间
        double[] avgvalue=new double[12];  //平均值
        
        for(int i=0;i<maxvalue.length;i++) maxvalue[i] = 0.0;
        for(int i=0;i<maxtime.length;i++) maxtime[i] = "1日";
        for(int i=0;i<minvalue.length;i++) minvalue[i] = 888888.88;
        for(int i=0;i<mintime.length;i++) mintime[i] = "1日";
        for(int i=0;i<avgvalue.length;i++) avgvalue[i] = 0.0;
        
        double nmaxvalue=0.00;  //年最大值 
        String nmaxtime="1月1日";  //年最大值时间
        double nminvalue=888888.88;  //年最小值
        String nmintime="1月1日";  //年最小值时间
        double navgvalue=0.0;  //年平均值
        int count=0;
        
        //统计最大值、最小值、平均值
        for(int i=0;i<all.size();i++) {
            DBNBBTJInfo info=all.get(i);
            String time=info.getTime().trim();
            //time = time.replace("日", "");
            
            //利用反射机制进行数据处理
            Field[] fields=info.getClass().getDeclaredFields();  
            for(int j=2; j<fields.length; j++) {
                Field field=fields[j];
                try {
                    String stemp=(String)field.get(info);
                    if(!stemp.equals("")) {
                        double dtemp=Double.valueOf(stemp);
                        
                        if(dtemp > nmaxvalue) {  //获取年最大值和时间
                            nmaxvalue = dtemp;
                            nmaxtime = (j-1)+"月"+time;
                        }
                        if(dtemp < nminvalue) {  //获取年最小值和时间
                            nminvalue = dtemp;
                            nmintime = (j-1)+"月"+time;
                        }
                        navgvalue += dtemp;  //年平均值
                        count++;
                        
                        if(dtemp > maxvalue[j-2]) {  //获取最大值和时间
                            maxvalue[j-2] = dtemp;
                            maxtime[j-2] = time;
                        }
                        if(dtemp < minvalue[j-2]) {  //获取最小值和时间
                            minvalue[j-2] = dtemp;
                            mintime[j-2] = time;
                        }
                        avgvalue[j-2] += dtemp;  //平均值
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        //最大值
        DBNBBTJInfo info = new DBNBBTJInfo();
        info.setTime("最大值");
        //利用反射机制给对象赋值
        Field[] fields=info.getClass().getDeclaredFields();  
        for(int i=2; i<fields.length; i++) {
            Field field=fields[i];
            try {
                field.set(info, df.format(getDDValue(maxvalue, i-2)));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        all.add(info);
        
        //最大值出现时间
        info = new DBNBBTJInfo();
        info.setTime("最大值出现时间");
        //利用反射机制给对象赋值
        fields=info.getClass().getDeclaredFields();  
        for(int i=2; i<fields.length; i++) {
            Field field=fields[i];
            try {
                field.set(info, getDDValue(maxtime, i-2));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        all.add(info);
        
        //最小值
        info = new DBNBBTJInfo();
        info.setTime("最小值");
        //利用反射机制给对象赋值
        fields=info.getClass().getDeclaredFields();  
        for(int i=2; i<fields.length; i++) {
            Field field=fields[i];
            try {
                double dtemp=getDDValue(minvalue, i-2);
                if(dtemp == 888888.88) dtemp=0.0;
                field.set(info, df.format(dtemp));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        all.add(info);
        
        //最小值出现时间
        info = new DBNBBTJInfo();
        info.setTime("最小值出现时间");
        //利用反射机制给对象赋值
        fields=info.getClass().getDeclaredFields();  
        for(int i=2; i<fields.length; i++) {
            Field field=fields[i];
            try {
                field.set(info, getDDValue(mintime, i-2));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        all.add(info);
        
        //平均值
        info = new DBNBBTJInfo();
        info.setTime("平均值");
        //利用反射机制给对象赋值
        fields=info.getClass().getDeclaredFields();  
        for(int i=2; i<fields.length; i++) {
            Field field=fields[i];
            try {
                double dtemp=getDDValue(avgvalue, i-2) / 31.0;
                field.set(info, df.format(dtemp));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        all.add(info);
        
        //年统计
        info = new DBNBBTJInfo();
        info.setTime("年统计");
        info.setSysw1("最高"+cgqname);
        info.setSysw2("");
        info.setSysw3(df.format(nmaxvalue));
        info.setSysw4("");
        info.setSysw5("最低"+cgqname);
        info.setSysw6("");
        
        double dtemp=nminvalue;
        if(dtemp == 888888.88) dtemp=0.0;
        info.setSysw7(df.format(dtemp));
        
        info.setSysw8("");
        info.setSysw9("平均"+cgqname);
        info.setSysw10("");
        if(count == 0) {
            info.setSysw11("0.00");
        } else {
            info.setSysw11(df.format(navgvalue / (count*1.0)));
        }
        info.setSysw12("");
        all.add(info);
        
        //年统计
        info = new DBNBBTJInfo();
        info.setTime("年统计");
        info.setSysw1("最高"+cgqname+"日期");
        info.setSysw2("");
        info.setSysw3(nmaxtime);
        info.setSysw4("");
        info.setSysw5("最低"+cgqname+"日期");
        info.setSysw6("");
        info.setSysw7(nmintime);
        info.setSysw8("");
        info.setSysw9("");
        info.setSysw10("");
        info.setSysw11("");
        info.setSysw12("");
        all.add(info);
        
        return all;
    }
    
    //获取年过程线
    public List<DBNGCXJInfo> getNGCXData(String cgqtype,String names,String sdate,String edate,String tablename) throws SQLException {
        List<DBNGCXJInfo> all = new ArrayList<DBNGCXJInfo>();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
        Date beginDate=null,endDate=null;
        long day=0;
        try {
            beginDate = sdf.parse(sdate);
            endDate = sdf.parse(edate);    
            day = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);    
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //初始化
        Calendar calendar=Calendar.getInstance(); 
        calendar.setTime(beginDate); 
        for(int i=0;i<day;i++) {
            DBNGCXJInfo info=new DBNGCXJInfo();
            info.setTime(sdf.format(calendar.getTime()));
            all.add(info);
            
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        //组合SQL语句
        String sql = "select distinct db.rq" +
                ",(select ksw.hour7 from syq_his_ksw as ksw where ksw.rq=db.rq and ksw.code='001') as sysw" +
                ",(select ksw.hour7 from syq_his_ksw as ksw where ksw.rq=db.rq and ksw.code='002') as xysw" +
        		",db.hour7" +
                ",(select jyl_day from syq_his_jyl as syq where syq.rq=db.rq and syq.code='001') as rjyl" + 
        		" from "+tablename+" as db where db.rq >= '"+sdate+"' and db.rq <= '"+edate+
                "'  and db.name='"+names+"' group by db.rq order by db.rq asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = this.ps.executeQuery();
        while (this.rs.next()) {
            String time=rs.getString(1);
            double syksw=rs.getDouble(2);
            double xyksw=rs.getDouble(3);
            String hour7=rs.getString(4); 
            double rjyl=rs.getDouble(5);  //日降雨量
            
            double value=0.0;
            //double wdvalue=0.0;  //水温（量水堰）
            if(hour7.indexOf(",") >= 0) {
                String datas[]=hour7.split(",");
                if(datas.length >= 2) {
                    value = Double.valueOf(datas[0].trim());
                    //wdvalue = Double.valueOf(datas[1].trim());
                }
            } else {
                value = Double.valueOf(hour7);
            }
            
            makeNGCXData(all, time, syksw, xyksw, value, rjyl);
        }
        
        return all;
    }
    
    //处理年过程线数据
    private void makeNGCXData(List<DBNGCXJInfo> list,String time,double syksw,double xyksw,double sysw,double rjyl) {
        for(int i=0;i<list.size();i++) {
            DBNGCXJInfo info=list.get(i);
            String timetemp=info.getTime().trim();
            if(timetemp.equals(time)) {
                info.setSyksw(syksw);
                info.setXyksw(xyksw);
                info.setSysw(sysw);
                info.setRjyl(rjyl);
                break;
            }
        }
    }
    
    /** 
     * 导出时段、日、月报表统计到excel 
     * @return 
     * @throws Exception 
     */ 
     public String exportSDBBTJToExcel(String cgqtype,String state,String names,String sdate,String edate,String tablename)throws Exception  {
         List<DBSDBBTJInfo> list=getSDBBTJData(cgqtype, state, names, sdate, edate, tablename);
         
         String cgqname="渗压";
         if(cgqtype.equals("1")) {
             cgqname = "渗压";
         } else if(cgqtype.equals("2")) {
             cgqname = "渗流";
         }
     
         String datas[]=sdate.split("-");
         String year=datas[0].trim();
         String month=datas[1].trim();
         
         //设置表头：对Excel每列取名
         datas = names.split(",");
         String []tableHeader=new String[datas.length+3];
         tableHeader[0] = "时间";
         tableHeader[1] = "上游水位";
         tableHeader[2] = "下游水位";
         for(int i=0;i<datas.length;i++) {
             tableHeader[i+3] = datas[i];
         }
         
         short cellNumber=(short)tableHeader.length;//表的列数 
         HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个excel 
         HSSFCell cell = null;   //Excel的列 
         HSSFRow row = null; //Excel的行 
         HSSFCellStyle style = workbook.createCellStyle();   //设置表头的类型 
         style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
         HSSFCellStyle style1 = workbook.createCellStyle();  //设置数据类型 
         style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
         HSSFFont font = workbook.createFont();  //设置字体 
         
         String sheetTitle="年报表统计",reportName="year_report";;
         if(state.equals("1")) {
             sheetTitle = sdate+"至"+edate+" 那板水库"+cgqname+"时段报表统计";
             reportName = "dbsd_report";
         } else if(state.equals("2")) {
             sheetTitle = sdate+" 那板水库"+cgqname+"日报表统计";
             reportName = "dbday_report";
         } else if(state.equals("3")) {
             sheetTitle = year+"年"+month+"月 那板水库"+cgqname+"月报表统计";
             reportName = "dbmonth_report";
         }
         
         HSSFSheet sheet = workbook.createSheet(sheetTitle);   //创建一个sheet 
         HSSFHeader header = sheet.getHeader();//设置sheet的头 
         try {              
             //根据是否取出数据，设置header信息 
             if(list.size() < 1 ) { 
                 header.setCenter("查无数据"); 
             } else { 
                 header.setCenter(sheetTitle);
                 row = sheet.createRow(0);
                 row.setHeight((short)400);
                 for(int k = 0;k < cellNumber;k++) {
                     cell = row.createCell(k);  //创建第0行第k列
                     cell.setCellValue(tableHeader[k]);  //设置第0行第k列的值
                     sheet.setColumnWidth(k,  5000);  //设置列的宽度
                     font.setColor(HSSFFont.BOLDWEIGHT_BOLD);   // 设置单元格字体的颜色
                     font.setFontHeight((short)250);   //设置单元字体高度
                     style1.setFont(font);  //设置字体风格
                     cell.setCellStyle(style1); 
                 } 
                 
                 // 给excel填充数据这里需要编写 
                 for(int i = 0 ;i < list.size() ;i++) {       
                     DBSDBBTJInfo info = (DBSDBBTJInfo)list.get(i);
                     row = sheet.createRow((short) (i + 1));//创建第i+1行 
                     row.setHeight((short)400);//设置行高 
                     
                     //利用反射机制给对象赋值
                     Field[] fields=info.getClass().getDeclaredFields();  
                     for(int j=0; j<fields.length; j++) {
                         if(j < datas.length+3) {
                             Field field=null;
                             if(j < 3)  {
                                 field=fields[j];
                             } else {
                                 field=fields[j+1];
                             }
                             
                             try {
                                 cell = row.createCell(j);  //创建第i+1行第0列 
                                 cell.setCellValue((String)field.get(info));  //设置第i+1行第0列的值 
                                 cell.setCellStyle(style);  //设置风格 
                             } catch (IllegalArgumentException e) {
                                 e.printStackTrace();
                             } catch (IllegalAccessException e) {
                                 e.printStackTrace();
                             }
                         }
                     }
                 }
             }
         } catch (Exception e) { 
             e.printStackTrace(); 
         } 
         
         HttpServletResponse response = null;  //创建一个HttpServletResponse对象
         OutputStream out = null;  //创建一个输出流对象
         try { 
             response = ServletActionContext.getResponse();  //初始化HttpServletResponse对象
             out = response.getOutputStream();
             response.setHeader("Content-disposition","attachment; filename="+reportName+".xls");  //filename是下载的xls的名，建议最好用英文
             response.setContentType("application/msexcel;charset=UTF-8");  //设置类型
             response.setHeader("Pragma","No-cache");  //设置头
             response.setHeader("Cache-Control","no-cache");  //设置头
             response.setDateHeader("Expires", 0);  //设置日期头
             workbook.write(out); 
             out.flush(); 
         } catch (IOException e) { 
             e.printStackTrace(); 
         }  finally { 
             try{ 
                 if(out != null) out.close(); 
             } catch(IOException e) { 
                 e.printStackTrace(); 
             } 
         }
         return null; 
     }
    
    /** 
    * 导出年报表统计到excel 
    * @return 
    * @throws Exception 
    */ 
    public String exportNBBTJToExcel(String cgqtype,String state,String names,String sdate,String edate,String tablename)throws Exception  {
        List<DBNBBTJInfo> list=getNBBTJData(cgqtype, state, names, sdate, edate, tablename);
        
        String cgqname="渗压";
        if(cgqtype.equals("1")) {
            cgqname = "渗压";
        } else if(cgqtype.equals("2")) {
            cgqname = "渗流";
        }
    
        String datas[]=sdate.split("-");
        String year=datas[0].trim();
        
        //设置表头：对Excel每列取名 
        String []tableHeader={"日期","一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"}; 
        
        short cellNumber=(short)tableHeader.length;//表的列数 
        HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个excel 
        HSSFCell cell = null;   //Excel的列 
        HSSFRow row = null; //Excel的行 
        HSSFCellStyle style = workbook.createCellStyle();   //设置表头的类型 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFCellStyle style1 = workbook.createCellStyle();  //设置数据类型 
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFFont font = workbook.createFont();  //设置字体 
        HSSFSheet sheet = workbook.createSheet(year+"年那板水库【"+names+"】"+cgqname+"年报表统计");   //创建一个sheet 
        HSSFHeader header = sheet.getHeader();//设置sheet的头 
        try {              
            //根据是否取出数据，设置header信息 
            if(list.size() < 1 ) { 
                header.setCenter("查无数据"); 
            } else { 
                header.setCenter(year+"年那板水库【"+names+"】"+cgqname+"年报表统计");
                row = sheet.createRow(0);
                row.setHeight((short)400);
                for(int k = 0;k < cellNumber;k++) {
                    cell = row.createCell(k);  //创建第0行第k列
                    cell.setCellValue(tableHeader[k]);  //设置第0行第k列的值
                    sheet.setColumnWidth(k,  5000);  //设置列的宽度
                    font.setColor(HSSFFont.BOLDWEIGHT_BOLD);   // 设置单元格字体的颜色
                    font.setFontHeight((short)250);   //设置单元字体高度
                    style1.setFont(font);  //设置字体风格
                    cell.setCellStyle(style1); 
                } 
                
                // 合并单元格   
                CellRangeAddress range = new CellRangeAddress(37, 38, 0, 0);   
                sheet.addMergedRegion(range); 
                for(int i=0;i<6;i++) {
                    range = new CellRangeAddress(37, 37, i*2+1, i*2+2);
                    sheet.addMergedRegion(range); 
                }
                for(int i=0;i<6;i++) {
                    range = new CellRangeAddress(38, 38, i*2+1, i*2+2);
                    sheet.addMergedRegion(range); 
                }
                
                // 给excel填充数据这里需要编写 
                for(int i = 0 ;i < list.size() ;i++) {       
                    DBNBBTJInfo info = (DBNBBTJInfo)list.get(i);
                    row = sheet.createRow((short) (i + 1));//创建第i+1行 
                    row.setHeight((short)400);//设置行高 
                    
                    //利用反射机制给对象赋值
                    Field[] fields=info.getClass().getDeclaredFields();  
                    for(int j=0; j<fields.length-1; j++) {
                        Field field=null;
                        if(j < 1) {
                            field=fields[j];
                        } else {
                            field=fields[j+1];
                        }
                        try {
                            cell = row.createCell(j);  //创建第i+1行第0列 
                            cell.setCellValue((String)field.get(info));  //设置第i+1行第0列的值 
                            cell.setCellStyle(style);  //设置风格 
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        
        HttpServletResponse response = null;  //创建一个HttpServletResponse对象
        OutputStream out = null;  //创建一个输出流对象
        try { 
            response = ServletActionContext.getResponse();  //初始化HttpServletResponse对象
            out = response.getOutputStream();
            response.setHeader("Content-disposition","attachment; filename=dbyear_report.xls");  //filename是下载的xls的名，建议最好用英文
            response.setContentType("application/msexcel;charset=UTF-8");  //设置类型
            response.setHeader("Pragma","No-cache");  //设置头
            response.setHeader("Cache-Control","no-cache");  //设置头
            response.setDateHeader("Expires", 0);  //设置日期头
            workbook.write(out); 
            out.flush(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }  finally { 
            try{ 
                if(out != null) out.close(); 
            } catch(IOException e) { 
                e.printStackTrace(); 
            } 
        }
        return null; 
    }
    
    //获取分布图
    public List<DBFBTInfo> getFBTData(String Name,String names,String tablename) throws SQLException {
        List<DBFBTInfo> all = new ArrayList<DBFBTInfo>();
        
        if(names.equals("")) return all;
        
        String ksws[]=names.split(",");
        
        for(int i=0;i<ksws.length;i++) {
            String ksw = ksws[i];
            
            //组合SQL语句
            String sql = "select distinct rq,hour7 from "+tablename+" where name='"+Name+
                    "' and rq in (select distinct rq from syq_his_ksw where code='001' and (";
            
            String fwstr="";
            for(int j=0;j<24;j++) {
                if(fwstr.equals("")) {
                    fwstr = "hour"+j+" in ("+ksw+")";
                } else {
                    fwstr += " or hour"+j+" in ("+ksw+")";
                }
            }
            
            sql += fwstr + ") order by rq asc) group by rq order by rq asc";
            
            DBFBTInfo info=new DBFBTInfo();
            info.setKsw(ksw);  //库水位
            
            this.ps = this.conn.prepareStatement(sql);
            this.rs = this.ps.executeQuery();
            while (this.rs.next()) {
                info.getTimelist().add(rs.getString(1));  //时间
                
                double[] values=getValue(this.rs, 2);
                info.getSyswlist().add(values[0]);  //渗压水位
            }
            
            all.add(info);
        }
        
        //处理其他数据为零
        ArrayList<String> timelist=getTimeList(all);
        if(timelist.size() > 0) {
            for(int i=0;i<all.size();i++) {
                DBFBTInfo info=all.get(i);
                if(info.getTimelist().size() <= 0) {
                    info.getTimelist().addAll(timelist);
                    for(int j=0;j<info.getTimelist().size();j++) info.getSyswlist().add(0.00);  //添加默认渗压水位
                }
            }
        }
        
        return all;
    }
    
    //获取时间列表
    private ArrayList<String> getTimeList(List<DBFBTInfo> list) {
        ArrayList<String> timelist=new ArrayList<String>();
        
        for(int i=0;i<list.size();i++) {
            DBFBTInfo info=list.get(i);
            if(info.getTimelist().size() > 0) {
                timelist.addAll(info.getTimelist());
                break;
            }
        }
        
        return timelist;
    }
    
    //渗压水位-库水位关系图
    public List<SYSWKSWGXTInfo> getSYSWKSWGXTData(String Name,String years,String tablename) throws SQLException {
        List<SYSWKSWGXTInfo> all = new ArrayList<SYSWKSWGXTInfo>();
        if(years.equals("")) return all;
        
		String ns[] = years.split(",");
        for(int i=0;i<ns.length;i++) {
            String year = ns[i];
            
            String sql="";
            SYSWKSWGXTInfo info = new SYSWKSWGXTInfo();
            info.setYear(year);  // 年份
            
            //查询日期、上游库水位、下游库水位
            sql = "select rq";
            sql += ",max(case code when '001' then hour7 else 0.0 end) as syksw";
            sql += ",max(case code when '002' then hour7 else 0.0 end) as xyksw";
            sql += " from syq_his_ksw where rq>='" + year + "-01-01' and rq<='" + year + "-12-31' group by rq order by rq asc";
            this.ps = this.conn.prepareStatement(sql);
            this.rs = this.ps.executeQuery();
            while (this.rs.next()) {
                info.getRqlist().add(rs.getString(1));  //日期
                
                double dtemp=rs.getDouble(2)-rs.getDouble(3);
                info.getKswlist().add(dtemp);  //上、下游库水位差
                info.getSyswlist().add(0.00);
            }
            all.add(info);
            
            //查询渗压水位
            sql = "select rq,hour7 from "+tablename+" where name='" + Name + "' and rq>='" + year + "-01-01' and rq<='" + year + "-12-31' order by rq asc";
            this.ps = this.conn.prepareStatement(sql);
            this.rs = this.ps.executeQuery();
            while (this.rs.next()) {
                String rq=rs.getString(1);  //日期
                
                double[] values=getValue(this.rs, 2);
                double sysw=values[0];  //渗压水位
                
                makeSYSW(info, rq, sysw);  //处理渗压水位
            }
        }
        
        return all;
    }
    
    //处理渗压水位
    private void makeSYSW(SYSWKSWGXTInfo info, String rq, double sysq) {
        for(int i=0;i<info.getRqlist().size();i++) {
            String irq=info.getRqlist().get(i);
            if(irq.equals(rq)) {  //日期相同，则更新渗压水位
                info.getSyswlist().set(i, sysq);
            }
        }
    }
    
    //获取渗压水位浸润线
    public List<String> getSYSWJRXData(String SDate,String names,String tablename) throws SQLException {
        List<String> all = new ArrayList<String>();
        if(names.equals("")) return all;
        
        String sql="";
        
        String ns[] = names.split(",");
        for(int i=0;i<ns.length;i++) {
            String name = ns[i];
            if(sql.equals("")) {
                sql = "select max(case name when '"+name+"' then hour7 else 0.0 end) as sysw";
            } else {
                sql += ",max(case name when '"+name+"' then hour7 else 0.0 end) as sysw"; 
            }
        }
        
        sql += ",(select max(case when code='001' and rq='"+SDate+"' then hour7 else 0.0 end) from syq_his_ksw)";
        sql += " from "+tablename+" where rq='"+SDate+"'";
        
        this.ps = this.conn.prepareStatement(sql);
        this.rs = this.ps.executeQuery();
        while (this.rs.next()) {
            for(int i=0;i<ns.length+1;i++) {
                all.add(rs.getString(i+1));
            }
            break;
        }
        
        return all;
    }
    
    //获取渗压水位等势线
    public List<DJRTDataDetails> getSYSWDSXData(String SDate, String tablename) throws SQLException {
        List<DJRTDataDetails> all = new ArrayList<DJRTDataDetails>();
        
        String sql="select name,hour7 from "+tablename+" where rq='"+SDate+"'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = this.ps.executeQuery();
        while (this.rs.next()) {
            DJRTDataDetails info = new DJRTDataDetails();
            info.setName(rs.getString(1));  //名称
            info.setSysw(rs.getDouble(2));  //渗压水位
            all.add(info);
        }
        
        return all;
    }
    
}



