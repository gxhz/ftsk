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
import java.util.Date;
import java.util.List;

import com.model.KSWInfo;
import com.model.phone.BXGCMonthInfo;
import com.model.phone.DBSYSWHisInfo;
import com.model.phone.YLZJYLHisInfo;
import com.model.phone.ZGQMonthInfo;
import com.model.syq.CurDayJYLInfo;
import com.util.ConnectionPool;

public class PhoneDao {
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	private Connection conn = null;
	
	public PhoneDao() throws SQLException {
		this.conn = ConnectionPool.getConnection();
	}
	
	public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
	
	//登录
	public String makePhoneLogin(String name, String pass, String imsi) throws SQLException {
		String stemp="";
        
		String sql="select yg.USERFlag,yg.PhoneImsi from sys_yg as yg where yg.PHONE='"+name+"' and yg.PASSWORD='"+pass+"'";
		this.ps = this.conn.prepareStatement(sql);
		this.rs = ps.executeQuery();
		if(rs.next()) {
		    String userflag=rs.getString(1);  //是否需要手机验证码标志
		    if(userflag.equals("2")) {  //特殊帐号，不需要手机验证码
		        stemp = "man";
		    } else {  //普通帐号，需要手机验证码
		        String phoneimsi=rs.getString(2);  //手机SIM串号
		        if(phoneimsi.equals(imsi)) {  //手机SIM串号正确
		            if(userflag.equals("1")) {  //普通用户，已激活
                        stemp = "ok";
                    } else {  //普通用户，未激活
                        stemp = "lock";
                    }
		        } else {  //手机SIM串号不正确
		            stemp = "nosim";
		        }
		    }
		}
		
		return stemp;
	}
	
	//注册申请
	public String sumitRegMsg(String name,String pass, String sim, String uname, String usex, String uemail) throws SQLException {
	    String stemp="";
	    
	    String sql="select yg.id from sys_yg as yg where yg.PHONE='"+name+"' or yg.PhoneImsi='"+sim+"'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        if(rs.next()) {  //手机号或手机SIM串号已被注册过，则不能再注册
            stemp = "already";
        } else {  //手机号和手机SIM串号都没有被注册过，则进行注册操作
            this.rs.close();
            this.ps.close();
            
            sql = "insert into sys_yg(GH,NAME,SEX,SYSUSER,PASSWORD,PHONE,EMAIL,DES,UNUSEFLAG,USERFlag,PhoneImsi) " +
            		"value('"+name+"','"+uname+"','"+usex+"',2,'"+pass+"','"+name+"','"+uemail+"','手机注册用户','启用',0,'"+sim+"')";
            this.ps=this.conn.prepareStatement(sql);
            int value=this.ps.executeUpdate();
            if(value > 0) {
                stemp = "ok";  //注册成功
            }
        }
	    
	    return stemp;
	}
	
	//获取实时库水位
	public String getRTKSW() throws SQLException {
        String stemp="";
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        String sql="select rtksw.syksw,rtksw.xyksw,rtksw.krl from syq_rt_ksw as rtksw";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            stemp = df.format(rs.getDouble(1))+","+df.format(rs.getDouble(2))+","+df.format(rs.getDouble(3));
            break;
        }
        
        return stemp;
    }
	
	//获取当天24小时实时库水位
    public String getCurDayKSW(String sdate,String edate) throws SQLException {
        String stemp="";
        
        List<KSWInfo> all = new ArrayList<KSWInfo>();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String curdate=sdf.format(calendar.getTime());
        
        if((!sdate.equals(""))&&(!edate.equals(""))) {
            curdate = sdate.substring(0, sdate.indexOf(" "));  //获取年月日，去掉时分秒
        } else {
            sdate = curdate+" 00:00:00";
            edate = curdate+" 23:59:59";
        }
        
        int hour=calendar.get(Calendar.HOUR_OF_DAY);  //当前小时
        hour = 23;
        //创建监时列表
        for(int i=0;i<=hour;i++) {
            KSWInfo info=new KSWInfo();
            String shour=(100+i)+"";
            shour = shour.substring(1, shour.length());
            info.setTime(curdate + " " + shour + ":00:00");  //时间
            info.setKsw("0.00");
            info.setWsw("0.00");
            all.add(info);
        }
        
        String sql="select * from syq_his_ksw where rq >='" + sdate + "' and rq <='" + edate + "'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeValue(all, rs);
        }
        
        //组合数据
        for(int i=0;i<all.size();i++) {
            KSWInfo info=all.get(i);
            
            if(stemp.equals("")) {
                stemp = info.getKsw()+","+info.getWsw()+","+info.getTime();
            } else {
                stemp += "|"+info.getKsw()+","+info.getWsw()+","+info.getTime();
            }
        }
        
        return stemp;
    }
    
    //更新列表数据
    private void makeValue(List<KSWInfo> list, ResultSet rs) throws SQLException {
        //根据时间、编号进行数据列表更新
        String code=rs.getString(2);  //code编号字段值
        //String name=rs.getString(3);  //name名称字段值
        String rq=rs.getString(4);  //rq日期字段值(年月日，没有时间)
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        //根据时间找到列表中的对象
        for(int i=0;i<24;i++) {
            String stemp=(100+i)+"";
            stemp = stemp.substring(1, stemp.length());
            String temprq = rq + " " + stemp + ":00:00";
            
            double value=rs.getDouble(i+5);  //0-23小时的值
            
            for(int j=0;j<list.size();j++) {
                KSWInfo info=list.get(j);
                String inforq=info.getTime().trim();  //日期(年月日时分秒)
                if(inforq.indexOf(temprq) >= 0) {  //找到相应日期的列表对象
                    //根据code编号进行判断需要更新对象哪个值
                    if(code.equals("001")) {  //上游库水位
                        info.setKsw(df.format(value));  //上游水位
                    } else if(code.equals("002")) {  //下游库水位
                        info.setWsw(df.format(value));  //下游水位
                    } else if(code.equals("003")) {  //库容量
                        info.setSkkr(df.format(value));  //水库容量
                    } else if(code.equals("004")) {  //溢洪流量
                        info.setPhll(df.format(value));  //排洪流量
                    } else if(code.equals("005")) {  //发电流量
                        info.setFdll(df.format(value));  //发电流量
                    } else if(code.equals("006")) {  //入库流量
                        info.setRkll(df.format(value));  //入库流量
                    } else if(code.equals("007")) {  //机组总出力
                        info.setJzzcl(df.format(value));  //机组总出力
                    }
                    break;
                }
            }
        }
    }
    
    //获取当天24小时降雨量
    public String getCurDayJYL(String sdate,String edate,String jylname) throws SQLException {
        String stemp="";
        
        List<CurDayJYLInfo> all = new ArrayList<CurDayJYLInfo>();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String curdate=sdf.format(calendar.getTime());
        
        if((!sdate.equals(""))&&(!edate.equals(""))) {
            curdate = sdate.substring(0, sdate.indexOf(" "));  //获取年月日，去除时分秒
        } else {
            sdate = curdate+" 00:00:00";
            edate = curdate+" 23:59:59";
        }
        
        int hour=calendar.get(Calendar.HOUR_OF_DAY);  //当前小时
        hour = 23;
        //创建监时列表
        for(int i=0;i<=hour;i++) {
            CurDayJYLInfo info=new CurDayJYLInfo();
            String shour=(100+i)+"";
            shour = shour.substring(1, shour.length());
            info.setTime(curdate + " " + shour + ":00:00");  //时间
            info.setBs("0.00");
            info.setNj("0.00");
            info.setNd("0.00");
            info.setGl("0.00");
            info.setHm("0.00");
            info.setBl("0.00");
            info.setPh("0.00");
            info.setBx("0.00");
            all.add(info);
        }
        
        String sql="select * from syq_his_jyl where rq >='" + sdate + "' and rq <='" + edate + "' and name='"+jylname+"'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeJYLValue(all, rs);
        }
        
        //组合数据
        for(int i=0;i<all.size();i++) {
            CurDayJYLInfo info=all.get(i);
            
            if(stemp.equals("")) {
                if(jylname.equals("坝首")) {
                    stemp = info.getBs()+","+info.getTime();
                } else if(jylname.equals("那禁")) {
                    stemp = info.getNj()+","+info.getTime();
                } else if(jylname.equals("那荡")) {
                    stemp = info.getNd()+","+info.getTime();
                } else if(jylname.equals("枯蒌")) {
                    stemp = info.getGl()+","+info.getTime();
                } else if(jylname.equals("汪门")) {
                    stemp = info.getHm()+","+info.getTime();
                } else if(jylname.equals("婆利")) {
                    stemp = info.getBl()+","+info.getTime();
                } else if(jylname.equals("平何")) {
                    stemp = info.getPh()+","+info.getTime();
                } else if(jylname.equals("百姓")) {
                    stemp = info.getBx()+","+info.getTime();
                }
            } else {
                if(jylname.equals("坝首")) {
                    stemp += "|"+info.getBs()+","+info.getTime();
                } else if(jylname.equals("那禁")) {
                    stemp += "|"+info.getNj()+","+info.getTime();
                } else if(jylname.equals("那荡")) {
                    stemp += "|"+info.getNd()+","+info.getTime();
                } else if(jylname.equals("枯蒌")) {
                    stemp += "|"+info.getGl()+","+info.getTime();
                } else if(jylname.equals("汪门")) {
                    stemp += "|"+info.getHm()+","+info.getTime();
                } else if(jylname.equals("婆利")) {
                    stemp += "|"+info.getBl()+","+info.getTime();
                } else if(jylname.equals("平何")) {
                    stemp += "|"+info.getPh()+","+info.getTime();
                } else if(jylname.equals("百姓")) {
                    stemp += "|"+info.getBx()+","+info.getTime();
                }
            }
        }
        
        return stemp;
    }
    
    //更新列表数据
    private void makeJYLValue(List<CurDayJYLInfo> list, ResultSet rs) throws SQLException {
        //根据时间、编号进行数据列表更新
        String code=rs.getString(2);  //code编号字段值
        //String name=rs.getString(3);  //name名称字段值
        String rq=rs.getString(4);  //rq日期字段值(年月日，没有时间)
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        //根据时间找到列表中的对象
        for(int i=0;i<24;i++) {
            String stemp=(100+i)+"";
            stemp = stemp.substring(1, stemp.length());
            String temprq = rq + " " + stemp + ":00:00";
            
            double value=rs.getDouble(i+5);  //0-23小时的值
            
            for(int j=0;j<list.size();j++) {
                CurDayJYLInfo info=list.get(j);
                String inforq=info.getTime().trim();  //日期(年月日时分秒)
                if(inforq.indexOf(temprq) >= 0) {  //找到相应日期的列表对象
                    //根据code编号进行判断需要更新对象哪个值
                    if(code.equals("001")) {  //坝首
                        info.setBs(df.format(value));  //坝首
                    } else if(code.equals("002")) {  //那禁
                        info.setNj(df.format(value));  //那禁
                    } else if(code.equals("003")) {  //那荡
                        info.setNd(df.format(value));  //那荡
                    } else if(code.equals("004")) {  //枯蒌
                        info.setGl(df.format(value));  //枯蒌
                    } else if(code.equals("005")) {  //汪门
                        info.setHm(df.format(value));  //汪门
                    } else if(code.equals("006")) {  //婆利
                        info.setBl(df.format(value));  //婆利
                    } else if(code.equals("007")) {  //平何
                        info.setPh(df.format(value));  //平何
                    } else if(code.equals("008")) {  //百姓
                        info.setBx(df.format(value));  //百姓
                    }
                    break;
                }
            }
        }
    }
    
    //获取实时渗压水位
    public String getRTSYSW(String dmnames) throws SQLException {
        String stemp="";
        
        if(dmnames.equals("")) return stemp;
        String dmnamelist[]=dmnames.split(",");
        
        boolean flag=false;  //默认查询的是渗压水位
        if(dmnames.indexOf("SLL") >= 0) flag = true;  //查渗流量
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        String sql="select ksw.syksw";
        for(int i=0;i<dmnamelist.length;i++) {
            sql += ",max(case sysw.name when '"+dmnamelist[i]+"' then sysw.sysw else 0.00 end)";
        }
        sql += " from syq_rt_ksw as ksw,db_rt_sysw as sysw";
        
        if(flag) {
            sql = "select ksw.syksw";
            for(int i=0;i<dmnamelist.length;i++) {
                sql += ",max(case lsy.name when '"+dmnamelist[i]+"' then lsy.lsyll else 0.00 end)";
            }
            sql += " from syq_rt_ksw as ksw,db_rt_lsy as lsy";
        }
        
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            stemp = df.format(rs.getDouble(1))+","+df.format(rs.getDouble(1));
            for(int i=0;i<dmnamelist.length;i++) stemp += ","+df.format(rs.getDouble(i+2));
            break;
        }
        
        return stemp;
    }
    
    //获取实时降雨量
    public String getRTJYL() throws SQLException {
        String stemp="";
        
        DecimalFormat df=new DecimalFormat("0.00");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        
        String ylzcodelist[]= {"001", "002", "003", "004", "005", "006", "007", "008"};
        
        String sql="select ksw.syksw,ksw.krl";
        for(int i=0;i<ylzcodelist.length;i++) {
            sql += ",max(case jyl.code when '"+ylzcodelist[i]+"' then jyl.jyl_day else 0.00 end)";
        }
        sql += " from syq_rt_ksw as ksw,syq_rt_jyl as jyl";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            //日期,库水位,库容量
            stemp = sdf.format(calendar.getTime())+","+df.format(rs.getDouble(1))+","+df.format(rs.getDouble(2));
            //8个雨量站日降雨量
            for(int i=0;i<ylzcodelist.length;i++) stemp += ","+df.format(rs.getDouble(i+3));
            break;
        }
        
        return stemp;
    }
    
    //获取历史渗压水位
    public String getHisSYSW(String dmnames, String sdate, String edate) throws SQLException {
        String stemp="";
        
        if(dmnames.equals("")) return stemp;
        String dmnamelist[]=dmnames.split(",");
        
        List<DBSYSWHisInfo> all = new ArrayList<DBSYSWHisInfo>();
        
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
        String syswnames="";
        
        try {
            Calendar calendar=Calendar.getInstance();
            Date begindate = sdf.parse(sdate);
            calendar.setTime(begindate);
            
            for(int i=0;i<day;i++) {
                for(int j=0;j<24;j++) {
                    for(int k=0;k<dmnamelist.length;k++) {
                        DBSYSWHisInfo info = new DBSYSWHisInfo();
                        
                        String hour=100+j+"";
                        hour = hour.substring(1, hour.length());
                        
                        info.setTime(sdf.format(calendar.getTime())+" "+hour+":00:00");
                        info.setName(dmnamelist[k]);
                        all.add(info);
                        
                        if(syswnames.equals("")) {
                            syswnames = "'"+dmnamelist[k]+"'";
                        } else {
                            syswnames += ",'"+dmnamelist[k]+"'";
                        }
                    }
                }
                
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        //根据日期获取库水位历史数据（上游水位）
        String sql="select ksw.id,ksw.code,ksw.name,ksw.rq,ksw.hour0,ksw.hour1,ksw.hour2,ksw.hour3,ksw.hour4";
        sql += ",ksw.hour5,ksw.hour6,ksw.hour7,ksw.hour8,ksw.hour9,ksw.hour10,ksw.hour11,ksw.hour12,ksw.hour13";
        sql += ",ksw.hour14,ksw.hour15,ksw.hour16,ksw.hour17,ksw.hour18,ksw.hour19,ksw.hour20,ksw.hour21";
        sql += ",ksw.hour22,ksw.hour23 from syq_his_ksw as ksw where ksw.rq>='"+sdate+"' and ksw.rq<='"+edate+"'";
        sql += " and ksw.code='001' order by ksw.rq desc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeHisKSW(all, rs);
        }
        
        //根据日期获取渗压水位（断面所有渗压水位）
        sql="select sysw.id,sysw.name,sysw.rq,sysw.hour0,sysw.hour1,sysw.hour2,sysw.hour3,sysw.hour4";
        sql += ",sysw.hour5,sysw.hour6,sysw.hour7,sysw.hour8,sysw.hour9,sysw.hour10,sysw.hour11,sysw.hour12,sysw.hour13";
        sql += ",sysw.hour14,sysw.hour15,sysw.hour16,sysw.hour17,sysw.hour18,sysw.hour19,sysw.hour20,sysw.hour21";
        sql += ",sysw.hour22,sysw.hour23 from db_his_sysw as sysw where sysw.rq>='"+sdate+"' and sysw.rq<='"+edate+"'";
        sql += " and sysw.name in("+syswnames+") order by sysw.name asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeHisSYSW(all, rs);
        }
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        //重组数据
        for(int i=0;i<all.size();i++) {
            DBSYSWHisInfo info=all.get(i);
            if(stemp.equals("")) {
                stemp = info.getName()+","+info.getTime()+","+df.format(info.getKsw())+","+df.format(info.getSysw());
            } else {
                stemp += "|"+info.getName()+","+info.getTime()+","+df.format(info.getKsw())+","+df.format(info.getSysw());
            }
        }
        
        return stemp;
    }
    
    //更新列表数据
    private void makeHisKSW(List<DBSYSWHisInfo> list, ResultSet rs) throws SQLException {
        //根据时间、编号进行数据列表更新
        String rq=rs.getString(4);  //rq日期字段值(年月日，没有时间)
        
        //根据时间找到列表中的对象
        for(int i=0;i<24;i++) {
            String stemp=(100+i)+"";
            stemp = stemp.substring(1, stemp.length());
            String temprq = rq + " " + stemp + ":00:00";
            
            double value=rs.getDouble(i+5);  //0-23小时的值
            
            for(int j=0;j<list.size();j++) {
                DBSYSWHisInfo info=list.get(j);
                String inforq=info.getTime().trim();  //日期(年月日时分秒)
                if(inforq.indexOf(temprq) >= 0) {  //找到相应日期的列表对象
                    info.setKsw(value);  //更新库水位
                }
            }
        }
    }
    
    //更新列表数据
    private void makeHisSYSW(List<DBSYSWHisInfo> list, ResultSet rs) throws SQLException {
        //根据时间、编号进行数据列表更新
        String name=rs.getString(2);  //name渗压水位名称
        String rq=rs.getString(3);  //rq日期字段值(年月日，没有时间)
        
        //根据时间找到列表中的对象
        for(int i=0;i<24;i++) {
            String stemp=(100+i)+"";
            stemp = stemp.substring(1, stemp.length());
            String temprq = rq + " " + stemp + ":00:00";
            
            //double value=rs.getDouble(i+4);  //0-23小时的值
            double value=0.0;
            String str=rs.getString(i+4);  //0-23小时的值
            if(str.indexOf(",") >= 0) {  //说明是量水堰数据(渗流量,水温)
                str = str.substring(0, str.indexOf(","));  //取渗流量值
                value = Double.valueOf(str);
            } else {  //说明是渗压水位
                value = Double.valueOf(str);
            }
            
            for(int j=0;j<list.size();j++) {
                DBSYSWHisInfo info=list.get(j);
                String inforq=info.getTime().trim();  //日期(年月日时分秒)
                if(inforq.indexOf(temprq) >= 0) {  //找到相应日期的列表对象
                    String infoname=info.getName().trim();  //渗压水位名称
                    if(infoname.indexOf(name) >= 0) {  //找到相应名称的列表对象
                        info.setSysw(value);  //更新渗压水位
                    }
                }
            }
        }
    }
    
    //获取历史降雨量
    public String getHisJYL(String ylznames, String sdate, String edate) throws SQLException {
        String stemp="";
        
        if(ylznames.equals("")) return stemp;
        String ylznamelist[]=ylznames.split(",");
        
        List<YLZJYLHisInfo> all = new ArrayList<YLZJYLHisInfo>();
        
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
        String ylzname="";
        
        try {
            Calendar calendar=Calendar.getInstance();
            Date begindate = sdf.parse(sdate);
            calendar.setTime(begindate);
            
            for(int i=0;i<day;i++) {
                for(int j=0;j<24;j++) {
                    for(int k=0;k<ylznamelist.length;k++) {
                        YLZJYLHisInfo info = new YLZJYLHisInfo();
                        
                        String hour=100+j+"";
                        hour = hour.substring(1, hour.length());
                        
                        info.setTime(sdf.format(calendar.getTime())+" "+hour+":00:00");
                        info.setName(ylznamelist[k]);
                        all.add(info);
                        
                        if(ylzname.equals("")) {
                            ylzname = "'"+ylznamelist[k]+"'";
                        } else {
                            ylzname += ",'"+ylznamelist[k]+"'";
                        }
                    }
                }
                
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        //根据日期获取库水位历史数据（上游水位）
        String sql="select ksw.id,ksw.code,ksw.name,ksw.rq,ksw.hour0,ksw.hour1,ksw.hour2,ksw.hour3,ksw.hour4";
        sql += ",ksw.hour5,ksw.hour6,ksw.hour7,ksw.hour8,ksw.hour9,ksw.hour10,ksw.hour11,ksw.hour12,ksw.hour13";
        sql += ",ksw.hour14,ksw.hour15,ksw.hour16,ksw.hour17,ksw.hour18,ksw.hour19,ksw.hour20,ksw.hour21";
        sql += ",ksw.hour22,ksw.hour23 from syq_his_ksw as ksw where ksw.rq>='"+sdate+"' and ksw.rq<='"+edate+"'";
        sql += " and ksw.code='001' order by ksw.rq desc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeJYLHisKSW(all, rs);
        }
        
        //根据日期获取库水位历史数据（库容量）
        sql="select ksw.id,ksw.code,ksw.name,ksw.rq,ksw.hour0,ksw.hour1,ksw.hour2,ksw.hour3,ksw.hour4";
        sql += ",ksw.hour5,ksw.hour6,ksw.hour7,ksw.hour8,ksw.hour9,ksw.hour10,ksw.hour11,ksw.hour12,ksw.hour13";
        sql += ",ksw.hour14,ksw.hour15,ksw.hour16,ksw.hour17,ksw.hour18,ksw.hour19,ksw.hour20,ksw.hour21";
        sql += ",ksw.hour22,ksw.hour23 from syq_his_ksw as ksw where ksw.rq>='"+sdate+"' and ksw.rq<='"+edate+"'";
        sql += " and ksw.code='003' order by ksw.rq desc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeHisKRL(all, rs);
        }
        
        //根据日期获取降雨量
        sql="select jyl.id,jyl.name,jyl.rq,jyl.hour0,jyl.hour1,jyl.hour2,jyl.hour3,jyl.hour4";
        sql += ",jyl.hour5,jyl.hour6,jyl.hour7,jyl.hour8,jyl.hour9,jyl.hour10,jyl.hour11,jyl.hour12,jyl.hour13";
        sql += ",jyl.hour14,jyl.hour15,jyl.hour16,jyl.hour17,jyl.hour18,jyl.hour19,jyl.hour20,jyl.hour21";
        sql += ",jyl.hour22,jyl.hour23,jyl.jyl_day from syq_his_jyl as jyl where jyl.rq>='"+sdate+"' and jyl.rq<='"+edate+"'";
        sql += " and jyl.name in("+ylzname+") order by jyl.name asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeHisJYL(all, rs);
        }
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        //重组数据
        for(int i=0;i<all.size();i++) {
            YLZJYLHisInfo info=all.get(i);
            if(stemp.equals("")) {
                stemp = info.getName()+","+info.getTime()+","+df.format(info.getKsw())+","+df.format(info.getKrl())+","+df.format(info.getSjyl())+","+df.format(info.getRjyl());
            } else {
                stemp += "|"+info.getName()+","+info.getTime()+","+df.format(info.getKsw())+","+df.format(info.getKrl())+","+df.format(info.getSjyl())+","+df.format(info.getRjyl());
            }
        }
        
        return stemp;
    }
    
    //更新列表数据
    private void makeJYLHisKSW(List<YLZJYLHisInfo> list, ResultSet rs) throws SQLException {
        //根据时间、编号进行数据列表更新
        String rq=rs.getString(4);  //rq日期字段值(年月日，没有时间)
        
        //根据时间找到列表中的对象
        for(int i=0;i<24;i++) {
            String stemp=(100+i)+"";
            stemp = stemp.substring(1, stemp.length());
            String temprq = rq + " " + stemp + ":00:00";
            
            double value=rs.getDouble(i+5);  //0-23小时的值
            
            for(int j=0;j<list.size();j++) {
                YLZJYLHisInfo info=list.get(j);
                String inforq=info.getTime().trim();  //日期(年月日时分秒)
                if(inforq.indexOf(temprq) >= 0) {  //找到相应日期的列表对象
                    info.setKsw(value);  //更新库水位
                }
            }
        }
    }
    
    //更新列表数据
    private void makeHisKRL(List<YLZJYLHisInfo> list, ResultSet rs) throws SQLException {
        //根据时间、编号进行数据列表更新
        String rq=rs.getString(4);  //rq日期字段值(年月日，没有时间)
        
        //根据时间找到列表中的对象
        for(int i=0;i<24;i++) {
            String stemp=(100+i)+"";
            stemp = stemp.substring(1, stemp.length());
            String temprq = rq + " " + stemp + ":00:00";
            
            double value=rs.getDouble(i+5);  //0-23小时的值
            
            for(int j=0;j<list.size();j++) {
                YLZJYLHisInfo info=list.get(j);
                String inforq=info.getTime().trim();  //日期(年月日时分秒)
                if(inforq.indexOf(temprq) >= 0) {  //找到相应日期的列表对象
                    info.setKrl(value);  //更新库容量
                }
            }
        }
    }
    
    //更新列表数据
    private void makeHisJYL(List<YLZJYLHisInfo> list, ResultSet rs) throws SQLException {
        //根据时间、编号进行数据列表更新
        String name=rs.getString(2);  //name雨量站名称
        String rq=rs.getString(3);  //rq日期字段值(年月日，没有时间)
        
        //根据时间找到列表中的对象
        for(int i=0;i<24;i++) {
            String stemp=(100+i)+"";
            stemp = stemp.substring(1, stemp.length());
            String temprq = rq + " " + stemp + ":00:00";
            
            double value=rs.getDouble(i+4);  //0-23小时的值
            
            for(int j=0;j<list.size();j++) {
                YLZJYLHisInfo info=list.get(j);
                String inforq=info.getTime().trim();  //日期(年月日时分秒)
                if(inforq.indexOf(temprq) >= 0) {  //找到相应日期的列表对象
                    String infoname=info.getName().trim();  //渗压水位名称
                    if(infoname.indexOf(name) >= 0) {  //找到相应名称的列表对象
                        info.setSjyl(value);  //更新时降雨量
                        info.setRjyl(rs.getDouble(28));  //更新日降雨量
                    }
                }
            }
        }
    }
    
    //获取月库水位
    public String getMonthKSW(String sdate,String edate) throws SQLException {
        String stemp="";
        
        List<KSWInfo> all = new ArrayList<KSWInfo>();
        
        //初始化数据
        long days=0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date begindate=sdf.parse(sdate);
            Date enddate=sdf.parse(edate);
            days = (enddate.getTime()-begindate.getTime()) / (24 * 60 * 60 * 1000) + 1;
            
            Calendar calendar=Calendar.getInstance();
            
            for(int i=0;i<days;i++) {
                calendar.setTime(begindate);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                
                KSWInfo info=new KSWInfo();
                info.setTime(sdf.format(calendar.getTime()));  //时间
                info.setKsw("0.00");
                info.setWsw("0.00");
                all.add(info);
            }
        } catch (Exception e) {
        }
        
        String sql="select ksw.code,ksw.rq,ksw.hour7 from syq_his_ksw as ksw where rq >='" + sdate + "' and rq <='" + edate + "'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeMonthValue(all, rs);
        }
        
        //组合数据
        for(int i=0;i<all.size();i++) {
            KSWInfo info=all.get(i);
            
            if(stemp.equals("")) {
                stemp = info.getKsw()+","+info.getWsw()+","+info.getTime();
            } else {
                stemp += "|"+info.getKsw()+","+info.getWsw()+","+info.getTime();
            }
        }
        
        return stemp;
    }
    
    //更新列表数据
    private void makeMonthValue(List<KSWInfo> list, ResultSet rs) throws SQLException {
        //根据时间、编号进行数据列表更新
        String code=rs.getString(1);  //code编号字段值
        String rq=rs.getString(2);  //rq日期字段值(年月日，没有时间)
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        //根据时间找到列表中的对象
        double value=rs.getDouble(3);  //0-23小时的值
        
        for(int j=0;j<list.size();j++) {
            KSWInfo info=list.get(j);
            String inforq=info.getTime().trim();  //日期(年月日时分秒)
            if(inforq.indexOf(rq) >= 0) {  //找到相应日期的列表对象
                //根据code编号进行判断需要更新对象哪个值
                if(code.equals("001")) {  //上游库水位
                    info.setKsw(df.format(value));  //上游水位
                } else if(code.equals("002")) {  //下游库水位
                    info.setWsw(df.format(value));  //下游水位
                } else if(code.equals("003")) {  //库容量
                    info.setSkkr(df.format(value));  //水库容量
                } else if(code.equals("004")) {  //溢洪流量
                    info.setPhll(df.format(value));  //排洪流量
                } else if(code.equals("005")) {  //发电流量
                    info.setFdll(df.format(value));  //发电流量
                } else if(code.equals("006")) {  //入库流量
                    info.setRkll(df.format(value));  //入库流量
                } else if(code.equals("007")) {  //机组总出力
                    info.setJzzcl(df.format(value));  //机组总出力
                }
                break;
            }
        }
    }
    
    //获取月降雨量
    public String getMonthJYL(String sdate,String edate,String jylname) throws SQLException {
        String stemp="";
        
        List<CurDayJYLInfo> all = new ArrayList<CurDayJYLInfo>();
        
        //初始化数据
        long days=0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date begindate=sdf.parse(sdate);
            Date enddate=sdf.parse(edate);
            days = (enddate.getTime()-begindate.getTime()) / (24 * 60 * 60 * 1000) + 1;
            
            Calendar calendar=Calendar.getInstance();
            
            for(int i=0;i<days;i++) {
                calendar.setTime(begindate);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                
                CurDayJYLInfo info=new CurDayJYLInfo();
                info.setTime(sdf.format(calendar.getTime()));  //时间
                info.setBs("0.00");
                info.setNj("0.00");
                info.setNd("0.00");
                info.setGl("0.00");
                info.setHm("0.00");
                info.setBl("0.00");
                info.setPh("0.00");
                info.setBx("0.00");
                all.add(info);
            }
        } catch (Exception e) {
        }
        
        String sql="select jyl.code,jyl.rq,jyl.hour7 from syq_his_jyl as jyl where rq >='" + sdate + "' and rq <='" + edate + "' and name='"+jylname+"'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeMonthJYLValue(all, rs);
        }
        
        //组合数据
        for(int i=0;i<all.size();i++) {
            CurDayJYLInfo info=all.get(i);
            
            if(stemp.equals("")) {
                if(jylname.equals("坝首")) {
                    stemp = info.getBs()+","+info.getTime();
                } else if(jylname.equals("那禁")) {
                    stemp = info.getNj()+","+info.getTime();
                } else if(jylname.equals("那荡")) {
                    stemp = info.getNd()+","+info.getTime();
                } else if(jylname.equals("枯蒌")) {
                    stemp = info.getGl()+","+info.getTime();
                } else if(jylname.equals("汪门")) {
                    stemp = info.getHm()+","+info.getTime();
                } else if(jylname.equals("婆利")) {
                    stemp = info.getBl()+","+info.getTime();
                } else if(jylname.equals("平何")) {
                    stemp = info.getPh()+","+info.getTime();
                } else if(jylname.equals("百姓")) {
                    stemp = info.getBx()+","+info.getTime();
                }
            } else {
                if(jylname.equals("坝首")) {  //坝首
                    stemp += "|"+info.getBs()+","+info.getTime();
                } else if(jylname.equals("那禁")) {  //那禁
                    stemp += "|"+info.getNj()+","+info.getTime();
                } else if(jylname.equals("那荡")) {  //那荡
                    stemp += "|"+info.getNd()+","+info.getTime();
                } else if(jylname.equals("枯蒌")) {  //枯蒌
                    stemp += "|"+info.getGl()+","+info.getTime();
                } else if(jylname.equals("汪门")) {  //汪门
                    stemp += "|"+info.getHm()+","+info.getTime();
                } else if(jylname.equals("婆利")) {  //婆利
                    stemp += "|"+info.getBl()+","+info.getTime();
                } else if(jylname.equals("平何")) {  //平何
                    stemp += "|"+info.getPh()+","+info.getTime();
                } else if(jylname.equals("百姓")) {  //百姓
                    stemp += "|"+info.getBx()+","+info.getTime();
                }
            }
        }
        
        return stemp;
    }
    
    //更新列表数据
    private void makeMonthJYLValue(List<CurDayJYLInfo> list, ResultSet rs) throws SQLException {
        //根据时间、编号进行数据列表更新
        String code=rs.getString(1);  //code编号字段值
        String rq=rs.getString(2);  //rq日期字段值(年月日，没有时间)
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        //根据时间找到列表中的对象
        double value=rs.getDouble(3);  //0-23小时的值
        
        for(int i=0;i<list.size();i++) {
            CurDayJYLInfo info=list.get(i);
            String inforq=info.getTime().trim();  //日期(年月日时分秒)
            if(inforq.indexOf(rq) >= 0) {  //找到相应日期的列表对象
                //根据code编号进行判断需要更新对象哪个值
                if(code.equals("001")) {  //坝首
                    info.setBs(df.format(value));  //坝首
                } else if(code.equals("002")) {  //那禁
                    info.setNj(df.format(value));  //那禁
                } else if(code.equals("003")) {  //那荡
                    info.setNd(df.format(value));  //那荡
                } else if(code.equals("004")) {  //枯蒌
                    info.setGl(df.format(value));  //枯蒌
                } else if(code.equals("005")) {  //汪门
                    info.setHm(df.format(value));  //汪门
                } else if(code.equals("006")) {  //婆利
                    info.setBl(df.format(value));  //婆利
                } else if(code.equals("007")) {  //平何
                    info.setPh(df.format(value));  //平何
                } else if(code.equals("008")) {  //百姓
                    info.setBx(df.format(value));  //百姓
                }
                break;
            }
        }
    }
    
    //检查APP是否有新程序
    public boolean checkAPPUpdate(String version) {
        boolean btemp=false;
        
        try {
            String sql = "select id,version from appsoftversion limit 1";
            this.ps = this.conn.prepareStatement(sql);
            this.rs = ps.executeQuery();
            while (rs.next()) {
                String value=rs.getString(2);  //APP版本号
                if(!version.equals(value)) btemp = true;  //版本号不相同，需要升级
                break;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return btemp;
    }
    
    //获取实时变形观测数据
    public String getBXGCRTData() throws SQLException {
        String stemp="";
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        String sql="select wyl.name,wyl.hoffset,wyl.voffset,wyl.zoffset from bx_rt_wyl as wyl order by wyl.name asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            if(stemp.equals("")) {
                stemp = rs.getString(1)+","+df.format(rs.getDouble(2))+","+df.format(rs.getDouble(3))+","+df.format(rs.getDouble(4));
            } else {
                stemp += "|"+rs.getString(1)+","+df.format(rs.getDouble(2))+","+df.format(rs.getDouble(3))+","+df.format(rs.getDouble(4));
            }
        }
        
        return stemp;
    }
    
    //获取闸门监控实时数据
    public String getZMJKRTData(String zmindex) throws SQLException {
        String stemp="";
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        String sql="select " +
        		"zm.gzzw,zm.gzzmqt,zm.gzzmss,zm.gzzmxj,zm.gzzmsx,zm.gzzmxx,zm.gzzmycjd," +
        		"zm.jxzw,zm.jxzmqt,zm.jxzmss,zm.jxzmxj,zm.jxzmsx,zm.jxzmxx,zm.jxzmycjd," +
        		"zm.rq from zm_rt_state as zm where zm.zm_id="+zmindex;
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            stemp = df.format(rs.getDouble(1))+","+df.format(rs.getDouble(2))+","+df.format(rs.getDouble(3))+","+df.format(rs.getDouble(4))
                    +","+df.format(rs.getDouble(5))+","+df.format(rs.getDouble(6))+","+df.format(rs.getDouble(7))+","+df.format(rs.getDouble(8))
                    +","+df.format(rs.getDouble(9))+","+df.format(rs.getDouble(10))+","+df.format(rs.getDouble(11))+","+df.format(rs.getDouble(12))
                    +","+df.format(rs.getDouble(13))+","+df.format(rs.getDouble(14))+","+rs.getString(15);
            break;
        }
        
        return stemp;
    }
    
    //获取历史变形观测数据
    public String getHisBXGCData(String sdate, String edate) throws SQLException {
        String stemp="";
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        String sql="select wyl.name,wyl.hoffset,wyl.voffset,wyl.zoffset,wyl.rqsj from bx_his_wyl as wyl " +
        		" where wyl.rqsj >='"+sdate+"' and wyl.rqsj <='"+edate+"' order by wyl.rqsj,wyl.name asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            if(stemp.equals("")) {
                stemp = rs.getString(1)+","+df.format(rs.getDouble(2))+","+df.format(rs.getDouble(3))+","+df.format(rs.getDouble(4))+","+rs.getString(5);
            } else {
                stemp += "|"+rs.getString(1)+","+df.format(rs.getDouble(2))+","+df.format(rs.getDouble(3))+","+df.format(rs.getDouble(4))+","+rs.getString(5);
            }
        }
        
        return stemp;
    }
    
    //获取闸门监控历史数据
    public String getZMJLHisData(String sdate, String edate, String zmindex) throws Exception {
        String stemp="";
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        String sql="select " +
                "case when zm.zmflag=1 then '工作闸门' when zm.zmflag=2 then '检修闸门' else '未知' end," + 
                "case when zm.zmzt=0 then '上升' when zm.zmzt=1 then '下降' when zm.zmzt=2 then '停止' else '未知' end," + 
                "zm.kssj,zm.jssj,zm.zmkd from zm_his_state as zm where zm.kssj>='"+sdate+"' and zm.kssj <='"+edate+
                "' and zm.zmindex="+zmindex+" order by zm.kssj asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            String kssj = rs.getString(3);
            String jssj = rs.getString(4);
            
            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数    
            long nh = 1000 * 60 * 60;// 一小时的毫秒数    
            long nm = 1000 * 60;// 一分钟的毫秒数  
            long ns = 1000;// 一秒钟的毫秒数    
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date d1=sdf.parse(kssj);  
            Date d2=sdf.parse(jssj);
            long diff = d2.getTime()-d1.getTime();
            long day = diff / nd;// 计算差多少天    
            long hour = diff % nd / nh + day * 24;// 计算差多少小时    
            long min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            
            String str=day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟"+ sec + "秒";
            
            if(stemp.equals("")) {
                stemp = rs.getString(1)+","+rs.getString(2)+","+str+","+df.format(rs.getDouble(5));
            } else {
                stemp += "|"+rs.getString(1)+","+rs.getString(2)+","+str+","+df.format(rs.getDouble(5));
            }
        }
        
        return stemp;
    }
    
    //获取所有观测点名称
    public String getBXGCName() throws SQLException {
        String stemp="";
        
        String sql="select distinct wyl.name from bx_rt_wyl as wyl order by wyl.name asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            if(stemp.equals("")) {
                stemp = rs.getString(1);
            } else {
                stemp += ","+rs.getString(1);
            }
        }
        
        return stemp;
    }
    
    //获取变形观测点一个月的位移数据
    public String getMonthBXGC(String sdate, String edate, String gcdname) throws Exception {
        String stemp="";
        
        List<BXGCMonthInfo> all = new ArrayList<BXGCMonthInfo>();
        
        //初始化数据
        long days=0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date begindate=sdf.parse(sdate);
            Date enddate=sdf.parse(edate);
            days = (enddate.getTime()-begindate.getTime()) / (24 * 60 * 60 * 1000) + 1;
            
            Calendar calendar=Calendar.getInstance();
            
            for(int i=0;i<days;i++) {
                calendar.setTime(begindate);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                
                BXGCMonthInfo info=new BXGCMonthInfo();
                info.setTime(sdf.format(calendar.getTime()));  //时间
                all.add(info);
            }
        } catch (Exception e) {
        }
        
        String sql="select bx.hoffset,bx.voffset,bx.zoffset,bx.rqsj from bx_his_wyl as bx " +
        		"where bx.rqsj>='"+sdate+"' and bx.rqsj <='"+edate+"' and bx.name='"+gcdname+"' order by bx.rqsj asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeMonthBXGC(all, rs);
        }
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        //组合数据
        for(int i=0;i<all.size();i++) {
            BXGCMonthInfo info=all.get(i);
            String time=info.getTime();
            double hoffset=info.getHoffset();
            double voffset=info.getVoffset();
            double zoffset=info.getZoffset();
            
            if(stemp.equals("")) {
                stemp = df.format(hoffset)+","+df.format(voffset)+","+df.format(zoffset)+","+time;
            } else {
                stemp += "|"+df.format(hoffset)+","+df.format(voffset)+","+df.format(zoffset)+","+time;
            }
        }
        
        return stemp;
    }
    
    //处理月变形观测数据
    private void makeMonthBXGC(List<BXGCMonthInfo> list, ResultSet rs) {
        try {
            for(int i=0;i<list.size();i++) {
                BXGCMonthInfo info=list.get(i);
                String time=info.getTime().trim();
                
                String rstime=rs.getString(4);
                if(time.indexOf(rstime) >= 0) {
                    info.setHoffset(rs.getDouble(1));
                    info.setVoffset(rs.getDouble(2));
                    info.setZoffset(rs.getDouble(3));
                    break;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    //获取闸门监控一个月数据数据
    public String getMonthZMJK(String sdate, String edate) throws SQLException {
        String stemp="";
        
        ArrayList<String> list=new ArrayList<String>();
        
        String sql="select zm.zmzt,zm.kssj,zm.jssj,zm.zmindex,zm.zmflag " +
        		"from zm_his_state as zm " +
                "where zm.kssj >='" + sdate + "' and zm.kssj <='" + edate + "'" +  
        		"order by zm.kssj asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeMonthZMJK(list, rs);
        }
        
        //重组数据
        for(int i=0;i<list.size();i++) {
            String str=list.get(i);
            String ds[]=str.split(",");
            
            String zmzt=ds[0].trim();
            String zmindex=ds[1].trim();
            String zmflag=ds[2].trim();
            String sz=ds[3].trim();
            
            String zmztvalue="";
            if(zmzt.equals("0")) {
                zmztvalue = "上升";
            } else if(zmzt.equals("1")) {
                zmztvalue = "下降";
            } else if(zmzt.equals("2")) {
                zmztvalue = "停止";
            } else {
                zmztvalue = "未知";
            }
            
            String zmindexvalue="";
            if(zmindex.equals("1")) {
                zmindexvalue = "1#";
            } else if(zmindex.equals("2")) {
                zmindexvalue = "2#";
            } else {
                zmindexvalue = "未知";
            }
            
            String zmflagvalue="";
            if(zmflag.equals("1")) {
                zmflagvalue = "工作闸门";
            } else if(zmflag.equals("2")) {
                zmflagvalue = "检修闸门";
            } else {
                zmflagvalue = "未知";
            }
            
            long lsz=Long.valueOf(sz);
            
            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数    
            long nh = 1000 * 60 * 60;// 一小时的毫秒数    
            long nm = 1000 * 60;// 一分钟的毫秒数  
            //long ns = 1000;// 一秒钟的毫秒数   
            
            long diff = lsz;
            long day = diff / nd;// 计算差多少天    
            long hour = diff % nd / nh + day * 24;// 计算差多少小时    
            long min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            //long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            
            //sz = day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟"+ sec + "秒";
            double dsz=hour+(min - day * 24 * 60) / 60.0;
            
            DecimalFormat df=new DecimalFormat("0.00");
            sz = df.format(dsz);
            
            String value=zmindexvalue+zmflagvalue+zmztvalue+"时长,"+sz;
            if(stemp.equals("")) {
                stemp = value;
            } else {
                stemp += "|"+value;
            }
        }
        
        return stemp;
    }
    
    //处理月闸门状态信息
    private void makeMonthZMJK(ArrayList<String> list, ResultSet rs) {
        try {
            boolean flag=false;
            
            String rszmzt=rs.getString(1);
            String rszmindex=rs.getString(4);
            String rszmflag=rs.getString(5);
            String rskssj=rs.getString(2);
            String rsjssj=rs.getString(3);
            
            String stemp=rszmzt+","+rszmindex+","+rszmflag;
            
            //计算时长
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date d1=sdf.parse(rskssj);  
            Date d2=sdf.parse(rsjssj);
            long diff = d2.getTime()-d1.getTime();
            
            for(int i=0;i<list.size();i++) {
                String str=list.get(i);
                String ds[]=str.split(",");
                
                String zmzt=ds[0].trim();
                String zmindex=ds[1].trim();
                String zmflag=ds[2].trim();
                String sz=ds[3].trim();
                
                if((zmzt.equals(rszmzt))&&(zmindex.equals(rszmindex))&&(zmflag.equals(rszmflag))) {
                    long lsz=Long.valueOf(sz);
                    lsz += diff;
                    
                    list.set(i, stemp+","+lsz);  //时长累计
                    
                    flag = true;
                    break;
                }
            }
            
            if(!flag) {
                list.add(stemp+","+diff);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    //获取所有传感器名称
    public String getAllZGQName() throws SQLException {
        String stemp="";
        
        String sql="select distinct sysw.name from db_rt_sysw as sysw " + 
                " union" +
                " select distinct lsy.name from db_rt_lsy as lsy " +
                " order by 1 asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            if(stemp.equals("")) {
                stemp = rs.getString(1);
            } else {
                stemp += ","+rs.getString(1);
            }
        }
        
        return stemp;
    }
    
    //获取传感器一个月数据
    public String getZGQQXData(String sdate, String edate, String zgqname) throws Exception {
        String stemp="";
        
        List<ZGQMonthInfo> all = new ArrayList<ZGQMonthInfo>();
        
        //初始化数据
        long days=0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date begindate=sdf.parse(sdate);
            Date enddate=sdf.parse(edate);
            days = (enddate.getTime()-begindate.getTime()) / (24 * 60 * 60 * 1000) + 1;
            
            Calendar calendar=Calendar.getInstance();
            
            for(int i=0;i<days;i++) {
                calendar.setTime(begindate);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                
                ZGQMonthInfo info=new ZGQMonthInfo();
                info.setTime(sdf.format(calendar.getTime()));  //时间
                all.add(info);
            }
        } catch (Exception e) {
        }
        
        String sql="select sysw.hour7,sysw.rq from db_his_sysw as sysw " +
                "where sysw.rq>='"+sdate+"' and sysw.rq <='"+edate+"' and sysw.name='"+zgqname+"' order by sysw.rq asc";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeMonthZGQ(all, rs);
        }
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        //组合数据
        for(int i=0;i<all.size();i++) {
            ZGQMonthInfo info=all.get(i);
            String time=info.getTime();
            double value=info.getValue();
            
            if(stemp.equals("")) {
                stemp = df.format(value)+","+time;
            } else {
                stemp += "|"+df.format(value)+","+time;
            }
        }
        
        return stemp;
    }
    
    //处理月变形观测数据
    private void makeMonthZGQ(List<ZGQMonthInfo> list, ResultSet rs) {
        try {
            for(int i=0;i<list.size();i++) {
                ZGQMonthInfo info=list.get(i);
                String time=info.getTime().trim();
                
                String rstime=rs.getString(2);
                if(time.indexOf(rstime) >= 0) {
                    String str=rs.getString(1);
                    if(str.indexOf(",") >= 0) {  //量水堰数据
                        str = str.substring(0, str.indexOf(","));
                        info.setValue(Double.valueOf(str));
                    } else {  //渗压水位数据
                        info.setValue(Double.valueOf(str));
                    }
                    
                    break;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    //获取当天24小时传感器数据
    public String getZGQHisQXData(String sdate,String edate,String zgqname) throws SQLException {
        String stemp="";
        
        List<KSWInfo> all = new ArrayList<KSWInfo>();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String curdate=sdf.format(calendar.getTime());
        
        if((!sdate.equals(""))&&(!edate.equals(""))) {
            curdate = sdate.substring(0, sdate.indexOf(" "));  //获取年月日，去掉时分秒
        } else {
            sdate = curdate+" 00:00:00";
            edate = curdate+" 23:59:59";
        }
        
        int hour=calendar.get(Calendar.HOUR_OF_DAY);  //当前小时
        hour = 23;
        //创建监时列表
        for(int i=0;i<=hour;i++) {
            KSWInfo info=new KSWInfo();
            String shour=(100+i)+"";
            shour = shour.substring(1, shour.length());
            info.setTime(curdate + " " + shour + ":00:00");  //时间
            info.setKsw("0.00");
            all.add(info);
        }
        
        String sql="select hour0,hour1,hour2,hour3,hour4,hour5,hour6,hour7,hour8," +
        		"hour9,hour10,hour11,hour12,hour13,hour14,hour15,hour16,hour17," +
        		"hour18,hour19,hour20,hour21,hour22,hour23" +
        		" from db_his_sysw where rq >='" + sdate + "' and rq <='" + edate + "' and name='"+zgqname+"'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            for(int i=0;i<all.size();i++) {
                KSWInfo info=all.get(i);
                
                String str=rs.getString(i+1);
                if(str.indexOf(",") >= 0) {  //量水堰
                    str = str.substring(0, str.indexOf(","));
                    info.setKsw(str);
                } else {  //渗压水位
                    info.setKsw(str);
                }
            }
            break;
        }
        
        //组合数据
        for(int i=0;i<all.size();i++) {
            KSWInfo info=all.get(i);
            
            if(stemp.equals("")) {
                stemp = info.getKsw()+","+info.getTime();
            } else {
                stemp += "|"+info.getKsw()+","+info.getTime();
            }
        }
        
        return stemp;
    }
    
    //获取所有雨量站名称
    public String getAllYLZName() throws SQLException {
        String stemp="";
        
        String sql="select distinct jyl.name from syq_rt_jyl as jyl";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            if(stemp.equals("")) {
                stemp = rs.getString(1);
            } else {
                stemp += ","+rs.getString(1);
            }
        }
        
        return stemp;
    }
	
}
