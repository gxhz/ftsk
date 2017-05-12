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

import com.model.JYLInfo;
import com.model.KSWInfo;
import com.model.SyqInfo;
import com.model.syq.CurDayJYLInfo;
import com.model.syq.JYLNBBTJInfo;
import com.model.syq.JYLRTDataInfo;
import com.model.syq.JYLSDBBTJInfo;
import com.model.syq.JYLSDGCXJInfo;
import com.model.syq.KSWNBBTJInfo;
import com.model.syq.KSWNGCXJInfo;
import com.model.syq.KSWSDBBTJInfo;
import com.util.ConnectionPool;

/**
 * 水雨情
 * @author Administrator
 *
 */
public class SYQJCDao {
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	private Connection conn = null;
	
	public SYQJCDao() throws SQLException {
		this.conn = ConnectionPool.getConnection();
	}
	
	//获取库水位实时数据
	public List<SyqInfo> getSWAction() throws SQLException {
		List<SyqInfo> all = new ArrayList<SyqInfo>();
		
		SyqInfo info=new SyqInfo();
		info.setSwt("当前坝首水位(m)");  //上游水位
		info.setSwv("0.0");  //上游水位值
		info.setGrt("库容(万m3)");
		info.setGrv("0.0");  //库容值
		info.setLlt("尾水水位(m)");  //下游水位
		info.setLlv("0.0");  //下游水位值
		all.add(info);
		
		info=new SyqInfo();
        info.setSwt("昨日八时水位(m)");
        info.setSwv("0.0");  //昨日八时水位值
        info.setGrt("溢洪流量(m3/s)");
        info.setGrv("0.0");  //溢洪流量值
        info.setLlt("发电流量(m3/s)");
        info.setLlv("0.0");  //发电流量值
        all.add(info);
        
        info=new SyqInfo();
        info.setSwt("坝道水位(GPRS)");
        info.setSwv("0.0");  //坝首水位值
        info.setGrt("尾水水位(GPRS)");
        info.setGrv("0.0");  //尾水水位值
        info.setLlt("入库流量(m3/s)");
        info.setLlv("0.0");  //入库流量值
        all.add(info);
		
        DecimalFormat df=new DecimalFormat("0.00");
        
		String sql="select syksw,xyksw,krl,yhll,fdll,rkll,jzzcl from syq_rt_ksw";
		this.ps = this.conn.prepareStatement(sql);
		this.rs = ps.executeQuery();
		while (rs.next()) {
		    info = all.get(0);
		    info.setSwv(df.format(rs.getDouble(1)));  //上游水位
		    info.setGrv(df.format(rs.getDouble(3)));  //库容量
		    info.setLlv(df.format(rs.getDouble(2)));  //下游水位
		    
		    info = all.get(1);
		    info.setSwv("0.00");  //昨日八时水位值
		    info.setGrv(df.format(rs.getDouble(4)));  //溢洪流量值
            info.setLlv(df.format(rs.getDouble(5)));  //发电流量值
            
            info = all.get(2);
            info.setSwv("0.00");  //坝首水位值
            info.setGrv("0.00");  //尾水水位值
            info.setLlv(df.format(rs.getDouble(6)));  //入库流量值
            
            break;
		}
		return all;
	}
	
	//获取降雨量实时数据
    public List<JYLInfo> getYLAction() throws SQLException {
        List<JYLInfo> all = new ArrayList<JYLInfo>();
        
        DecimalFormat df=new DecimalFormat("0.00");
        
        JYLInfo info=new JYLInfo();
        info.setName1("坝首");
        info.setGprsv1("0.00");  //坝首GPRS值
        info.setValue1("0.00");  //坝首超短波值
        info.setName2("那禁");
        info.setGprsv2("0.00");  //那禁GPRS值
        info.setValue2("0.00");  //那禁超短波值
        all.add(info);
        
        info=new JYLInfo();
        info.setName1("那荡");
        info.setGprsv1("0.00");  //那荡GPRS值
        info.setValue1("0.00");  //那荡超短波值
        info.setName2("枯蒌");
        info.setGprsv2("0.00");  //枯蒌GPRS值
        info.setValue2("0.00");  //枯蒌超短波值
        all.add(info);
        
        info=new JYLInfo();
        info.setName1("汪门");
        info.setGprsv1("0.00");  //汪门GPRS值
        info.setValue1("0.00");  //汪门超短波值
        info.setName2("婆利");
        info.setGprsv2("0.00");  //婆利GPRS值
        info.setValue2("0.00");  //婆利超短波值
        all.add(info);
        
        info=new JYLInfo();
        info.setName1("平何");
        info.setGprsv1("0.00");  //平何GPRS值
        info.setValue1("0.00");  //平何超短波值
        info.setName2("百姓");
        info.setGprsv2("0.00");  //百姓GPRS值
        info.setValue2("0.00");  //百姓超短波值
        all.add(info);
        
        info=new JYLInfo();
        info.setName1("平均降雨量（GPRS）");
        info.setGprsv1("0.00");  //平均降雨量值
        info.setValue1("");
        info.setName2("平均降雨量（超短波）");
        info.setGprsv2("0.00");  //平均降雨量
        info.setValue2("");
        all.add(info);
        
        double avggprs=0.0;
        
        String sql="select code,jyl_day from syq_rt_jyl";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            String code=rs.getString(1);  //编号
            double rjyl=rs.getDouble(2);  //日降雨量
            
            info = all.get(0);
            if(code.equals("001")) info.setGprsv1(df.format(rjyl));  //坝首GPRS值
            info.setValue1("0.00");  //坝首超短波值
            if(code.equals("002")) info.setGprsv2(df.format(rjyl));  //那禁GPRS值
            info.setValue2("0.00");  //那禁超短波值
            
            info = all.get(1);
            if(code.equals("003")) info.setGprsv1(df.format(rjyl));  //那荡GPRS值
            info.setValue1("0.00");  //那荡超短波值
            if(code.equals("004")) info.setGprsv2(df.format(rjyl));  //枯蒌GPRS值
            info.setValue2("0.00");  //枯蒌超短波值
            
            info = all.get(2);
            if(code.equals("005")) info.setGprsv1(df.format(rjyl));  //汪门GPRS值
            info.setValue1("0.00");  //汪门超短波值
            if(code.equals("006")) info.setGprsv2(df.format(rjyl));  //婆利GPRS值
            info.setValue2("0.00");  //婆利超短波值
            
            info = all.get(3);
            if(code.equals("007")) info.setGprsv1(df.format(rjyl));  //平何GPRS值
            info.setValue1("0.00");  //平何超短波值
            if(code.equals("008")) info.setGprsv2(df.format(rjyl));  //百姓GPRS值
            info.setValue2("0.00");  //百姓超短波值
            
            avggprs += rjyl;  //GPRS降雨量累计
        }
        
        info = all.get(4);
        info.setName1("平均降雨量（GPRS）");
        info.setGprsv1(df.format(avggprs / 8.0));  //平均降雨量值
        info.setValue1("");
        info.setName2("平均降雨量（超短波）");
        info.setGprsv2("0.00");  //平均降雨量
        info.setValue2("");
        
        return all;
    }
    
    //获取当天库水位、库容量相关信息
    public List<KSWInfo> getKSWAction() throws SQLException {
        List<KSWInfo> all = new ArrayList<KSWInfo>();
        
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        
        String curdate=sdf.format(calendar.getTime());
        String sdate=curdate + " 00:00:00";
        String edate=curdate + "23:59:59";
        
        int hour=calendar.get(Calendar.HOUR_OF_DAY);  //当前小时
        //创建监时列表
        for(int i=0;i<=hour;i++) {
            KSWInfo info=new KSWInfo();
            String stemp=(100+(hour-i))+"";
            stemp = stemp.substring(1, stemp.length());
            info.setTime(curdate + " " + stemp + ":00:00");  //时间
            all.add(info);
        }
        
        String sql="select * from syq_his_ksw where rq >='" + sdate + "' and rq <='" + edate + "'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeValue(all, rs);
        }
        
        return all;
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
    
    //获取当天24小时降雨量信息
    public List<CurDayJYLInfo> getJYLAction() throws SQLException {
        List<CurDayJYLInfo> all = new ArrayList<CurDayJYLInfo>();
        
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        
        String curdate=sdf.format(calendar.getTime());
        String sdate=curdate + " 00:00:00";
        String edate=curdate + "23:59:59";
        
        int hour=calendar.get(Calendar.HOUR_OF_DAY);  //当前小时
        //创建监时列表
        for(int i=0;i<=hour;i++) {
            CurDayJYLInfo info=new CurDayJYLInfo();
            String stemp=(100+(hour-i))+"";
            stemp = stemp.substring(1, stemp.length());
            info.setTime(curdate + " " + stemp + ":00:00");  //时间
            all.add(info);
        }
        
        String sql="select * from syq_his_jyl where rq >='" + sdate + "' and rq <='" + edate + "'";
        this.ps = this.conn.prepareStatement(sql);
        this.rs = ps.executeQuery();
        while (rs.next()) {
            makeJYLValue(all, rs);
        }
        
        return all;
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
    
    //根据时间获取参数类
    private KSWSDBBTJInfo getInfo(List<KSWSDBBTJInfo> list, String time) {
        KSWSDBBTJInfo info=null;
        
        for(int i=0;i<list.size();i++) {
            KSWSDBBTJInfo temp=list.get(i);
            String temptime=temp.getTime().trim();
            if(temptime.equals(time)) {
                info = temp;
                break;
            }
        }
        
        return info;
    }
    
    //获取库水位时段、日、月、年报表统计和过程线
    public List<KSWSDBBTJInfo> getKSWSDBBTJData(String sdate,String edate) throws SQLException {
        List<KSWSDBBTJInfo> all = new ArrayList<KSWSDBBTJInfo>();
        
        if(sdate.equals("")) return all;
        if(edate.equals("")) return all;
        
        DecimalFormat df=new DecimalFormat("0.00");  
        
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
                KSWSDBBTJInfo info = new KSWSDBBTJInfo();
                info.setTime(sdf.format(calendar.getTime()));
                all.add(info);
                
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        //组合SQL语句
        String sql = "select distinct ksw.rq";
        sql += ",max(case ksw.code when '001' then ksw.hour7 else 0.0 end) as syksw";  //上游水位
        sql += ",max(case ksw.code when '002' then ksw.hour7 else 0.0 end) as xyksw";  //下游水位
        sql += ",max(case ksw.code when '003' then ksw.hour7 else 0.0 end) as krl";  //库容量
        sql += ",max(case ksw.code when '004' then ksw.hour7 else 0.0 end) as yhll";  //溢洪流量
        sql += ",max(case ksw.code when '005' then ksw.hour7 else 0.0 end) as fdll";  //发电流量
        sql += ",max(case ksw.code when '006' then ksw.hour7 else 0.0 end) as rkll";  //入库流量
        sql += ",max(case ksw.code when '007' then ksw.hour7 else 0.0 end) as jzzcl";  //机组总出力
        sql += ",(select syq.jyl_day from syq_his_jyl as syq where syq.rq=ksw.rq and syq.code='001') as rjyl";  //降雨量（坝首）
        sql += " from syq_his_ksw as ksw where ksw.rq >= '"+sdate+"' and ksw.rq <= '"+edate+"'";
        sql += " group by ksw.rq order by ksw.rq asc";
        
        double[] maxvalue=new double[10];  //最大值
        String[] maxtime=new String[10];  //最大值时间
        double[] minvalue=new double[10];  //最小值
        String[] mintime=new String[10];  //最小值时间
        double[] avgvalue=new double[10];  //平均值
        
        for(int i=0;i<maxvalue.length;i++) maxvalue[i] = 0.0;
        for(int i=0;i<maxtime.length;i++) maxtime[i] = sdate;
        for(int i=0;i<minvalue.length;i++) minvalue[i] = 888888.88;
        for(int i=0;i<mintime.length;i++) mintime[i] = sdate;
        for(int i=0;i<avgvalue.length;i++) avgvalue[i] = 0.0;
        
        int count=0;
        this.ps = this.conn.prepareStatement(sql);
        this.rs = this.ps.executeQuery();
        while (this.rs.next()) {
            count++;  //记录数
            
            String time=rs.getString(1);  //时间
            KSWSDBBTJInfo info=getInfo(all, time);
            if(info != null) {
                info.setTime(time);
                info.setSyksw(df.format(rs.getDouble(2)));  //上游水位
                info.setXyksw(df.format(rs.getDouble(3)));  //下游水位
                info.setKrl(df.format(rs.getDouble(4)));  //库容量
                info.setYhll(df.format(rs.getDouble(5)));  //溢洪流量
                info.setFdll(df.format(rs.getDouble(6)));  //发电流量
                info.setRkll(df.format(rs.getDouble(7)));  //入库流量
                info.setJzzcl(df.format(rs.getDouble(8)));  //机组总出力
                info.setRjyl(df.format(rs.getDouble(9)));  //日降雨量
                
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
                
                //处理库容量相关数据
                dtemp=rs.getDouble(4);  //库容量
                if(dtemp > maxvalue[2]) {  //获取最大值和时间
                    maxvalue[2] = dtemp; 
                    maxtime[2] = rs.getString(1);  //时间
                }
                if(dtemp < minvalue[2]) {  //获取最小值和时间
                    minvalue[2] = dtemp; 
                    mintime[2] = rs.getString(1);  //时间
                }
                avgvalue[2] += dtemp;  //平均值
                
                //处理溢洪流量相关数据
                dtemp=rs.getDouble(5);  //溢洪流量
                if(dtemp > maxvalue[3]) {  //获取最大值和时间
                    maxvalue[3] = dtemp; 
                    maxtime[3] = rs.getString(1);  //时间
                }
                if(dtemp < minvalue[3]) {  //获取最小值和时间
                    minvalue[3] = dtemp; 
                    mintime[3] = rs.getString(1);  //时间
                }
                avgvalue[3] += dtemp;  //平均值
                
                //处理发电流量相关数据
                dtemp=rs.getDouble(6);  //发电流量
                if(dtemp > maxvalue[4]) {  //获取最大值和时间
                    maxvalue[4] = dtemp; 
                    maxtime[4] = rs.getString(1);  //时间
                }
                if(dtemp < minvalue[4]) {  //获取最小值和时间
                    minvalue[4] = dtemp; 
                    mintime[4] = rs.getString(1);  //时间
                }
                avgvalue[4] += dtemp;  //平均值
                
                //处理入库流量相关数据
                dtemp=rs.getDouble(7);  //发电流量
                if(dtemp > maxvalue[5]) {  //获取最大值和时间
                    maxvalue[5] = dtemp; 
                    maxtime[5] = rs.getString(1);  //时间
                }
                if(dtemp < minvalue[5]) {  //获取最小值和时间
                    minvalue[5] = dtemp; 
                    mintime[5] = rs.getString(1);  //时间
                }
                avgvalue[5] += dtemp;  //平均值
                
                //处理机组总出力相关数据
                dtemp=rs.getDouble(8);  //机组总出力
                if(dtemp > maxvalue[6]) {  //获取最大值和时间
                    maxvalue[6] = dtemp; 
                    maxtime[6] = rs.getString(1);  //时间
                }
                if(dtemp < minvalue[6]) {  //获取最小值和时间
                    minvalue[6] = dtemp; 
                    mintime[6] = rs.getString(1);  //时间
                }
                avgvalue[6] += dtemp;  //平均值
                
            }
        }
        
        int row = all.size();
        row = count;
        if(row > 0) {
            for(int i=0;i<avgvalue.length;i++) avgvalue[i] = avgvalue[i] / (row*1.0);  //平均值
        }
        
        if(!flag) {  //时段、月统计才有，日统计没有
            //最大值
            KSWSDBBTJInfo info = new KSWSDBBTJInfo();
            info.setTime("最大值");
            info.setSyksw(df.format(maxvalue[0]));  //上游库水位
            info.setXyksw(df.format(maxvalue[1]));  //下游库水位
            info.setKrl(df.format(maxvalue[2]));  //库容量
            info.setYhll(df.format(maxvalue[3]));  //溢洪流量
            info.setFdll(df.format(maxvalue[4]));  //发电流量
            info.setRkll(df.format(maxvalue[5]));  //入库流量
            info.setJzzcl(df.format(maxvalue[6]));  //机组总出力
            all.add(info);
            
            //最大值出现时间
            info = new KSWSDBBTJInfo();
            info.setTime("最大值出现时间");
            info.setSyksw(maxtime[0]);  //上游库水位
            info.setXyksw(maxtime[1]);  //下游库水位
            info.setKrl(maxtime[2]);  //库容量
            info.setYhll(maxtime[3]);  //溢洪流量
            info.setFdll(maxtime[4]);  //发电流量
            info.setRkll(maxtime[5]);  //入库流量
            info.setJzzcl(maxtime[6]);  //机组总出力
            all.add(info);
            
            //最小值
            for(int i=0;i<minvalue.length;i++) if(minvalue[i] == 888888.88) minvalue[i]=0.0;
            
            info = new KSWSDBBTJInfo();
            info.setTime("最小值");
            info.setSyksw(df.format(minvalue[0]));  //上游库水位
            info.setXyksw(df.format(minvalue[1]));  //下游库水位
            info.setKrl(df.format(minvalue[2]));  //库容量
            info.setYhll(df.format(minvalue[3]));  //溢洪流量
            info.setFdll(df.format(minvalue[4]));  //发电流量
            info.setRkll(df.format(minvalue[5]));  //入库流量
            info.setJzzcl(df.format(minvalue[6]));  //机组总出力
            all.add(info);
            
            //最小值出现时间
            info = new KSWSDBBTJInfo();
            info.setTime("最小值出现时间");
            info.setSyksw(mintime[0]);  //上游库水位
            info.setXyksw(mintime[1]);  //下游库水位
            info.setKrl(mintime[2]);  //库容量
            info.setYhll(mintime[3]);  //溢洪流量
            info.setFdll(mintime[4]);  //发电流量
            info.setRkll(mintime[5]);  //入库流量
            info.setJzzcl(mintime[6]);  //机组总出力
            all.add(info);
            
            //平均值
            info = new KSWSDBBTJInfo();
            info.setTime("平均值");
            info.setSyksw(df.format(avgvalue[0]));  //上游库水位
            info.setXyksw(df.format(avgvalue[1]));  //下游库水位
            info.setKrl(df.format(avgvalue[2]));  //库容量
            info.setYhll(df.format(avgvalue[3]));  //溢洪流量
            info.setFdll(df.format(avgvalue[4]));  //发电流量
            info.setRkll(df.format(avgvalue[5]));  //入库流量
            info.setJzzcl(df.format(avgvalue[6]));  //机组总出力
            all.add(info);
        }
        
        return all;
    }
    
    /** 
     * 导出库水位时段、日、月报表统计到excel 
     * @return 
     * @throws Exception 
     */ 
     public String exportKSWSDBBTJToExcel(String state,String sdate,String edate)throws Exception  {
         List<KSWSDBBTJInfo> list=getKSWSDBBTJData(sdate, edate);
         
         String datas[]=sdate.split("-");
         String year=datas[0].trim();
         String month=datas[1].trim();
         
         //设置表头：对Excel每列取名
         String []tableHeader=new String[8];
         tableHeader[0] = "时间";
         tableHeader[1] = "水库水位";
         tableHeader[2] = "入库流量";
         tableHeader[3] = "排洪流量";
         tableHeader[4] = "尾水位";
         tableHeader[5] = "发电流量";
         tableHeader[6] = "水库库容";
         tableHeader[7] = "机组总出力";
         
         short cellNumber=(short)tableHeader.length;//表的列数 
         HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个excel 
         HSSFCell cell = null;   //Excel的列 
         HSSFRow row = null; //Excel的行 
         HSSFCellStyle style = workbook.createCellStyle();   //设置表头的类型 
         style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
         HSSFCellStyle style1 = workbook.createCellStyle();  //设置数据类型 
         style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
         HSSFFont font = workbook.createFont();  //设置字体 
         
         String sheetTitle="年报表统计",reportName="year_report";
         if(state.equals("1")) {
             sheetTitle = sdate+"至"+edate+" 那板水库库水位时段报表统计";
             reportName = "kswsd_report";
         } else if(state.equals("2")) {
             sheetTitle = sdate+" 那板水库库水位日报表统计";
             reportName = "kswday_report";
         } else if(state.equals("3")) {
             sheetTitle = year+"年"+month+"月 那板水库库水位月报表统计";
             reportName = "kswmonth_report";
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
                     KSWSDBBTJInfo info = (KSWSDBBTJInfo)list.get(i);
                     row = sheet.createRow((short) (i + 1));//创建第i+1行 
                     row.setHeight((short)400);//设置行高 
                     
                     //利用反射机制给对象赋值
                     Field[] fields=info.getClass().getDeclaredFields();  
                     for(int j=0; j<fields.length; j++) {
                         Field field=fields[j];
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
     
     //根据时间进行赋值
     private void makeNBBTJData(List<KSWNBBTJInfo> list, String time, double value) {
         String datas[]=time.split("-");
         if(datas.length < 3) return;
         
         String smonth=datas[1].trim();  //月份
         String sday=datas[2].trim();  //日
         
         int imonth=Integer.valueOf(smonth);
         int iday=Integer.valueOf(sday);
         
         int day=iday-1;
         if(day >= list.size()) return;
         
         //根据日期获取对象
         KSWNBBTJInfo info=list.get(day);
         if(info == null) return;
         
         //根据月份给对象赋值
         DecimalFormat df=new DecimalFormat("0.00");
         
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
     
     //获取库水位年报表统计（针对单个统计点）
     public List<KSWNBBTJInfo> getKSWNBBTJData(String state,String code,String sdate,String edate) throws SQLException {
         List<KSWNBBTJInfo> all = new ArrayList<KSWNBBTJInfo>();
         
         //初始化年报表数据
         for(int i=0;i<31;i++) {
             KSWNBBTJInfo info = new KSWNBBTJInfo();
             info.setTime((i+1)+"日");
             all.add(info);
         }
         
         //组合SQL语句
         String sql = "select distinct ksw.rq,ksw.hour7 from syq_his_ksw as ksw " +
         		"where ksw.rq >= '"+sdate+"' and ksw.rq <= '"+edate+"' and ksw.code='"+code+"'"+
                 "  group by ksw.rq,ksw.hour7 order by ksw.rq asc";
         this.ps = this.conn.prepareStatement(sql);
         this.rs = this.ps.executeQuery();
         while (this.rs.next()) {
             String time=rs.getString(1);  //时间
             String hour7=rs.getString(2);  //相应值
             
             double value=Double.valueOf(hour7);
             
             makeNBBTJData(all, time, value);
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
             KSWNBBTJInfo info=all.get(i);
             String time=info.getTime().trim();
             //time = time.replace("日", "");
             
             //利用反射机制进行数据处理
             Field[] fields=info.getClass().getDeclaredFields();  
             for(int j=1; j<fields.length; j++) {
                 Field field=fields[j];
                 try {
                     String stemp=(String)field.get(info);
                     if(!stemp.equals("")) {
                         double dtemp=Double.valueOf(stemp);
                         
                         if(dtemp > nmaxvalue) {  //获取年最大值和时间
                             nmaxvalue = dtemp; 
                             nmaxtime = j+"月"+time;
                         }
                         if(dtemp < nminvalue) {  //获取年最小值和时间
                             nminvalue = dtemp; 
                             nmintime = j+"月"+time;
                         }
                         navgvalue += dtemp;  //年平均值
                         count++;
                         
                         if(dtemp > maxvalue[j-1]) {  //获取最大值和时间
                             maxvalue[j-1] = dtemp; 
                             maxtime[j-1] = time;
                         }
                         if(dtemp < minvalue[j-1]) {  //获取最小值和时间
                             minvalue[j-1] = dtemp; 
                             mintime[j-1] = time;
                         }
                         avgvalue[j-1] += dtemp;  //平均值
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
         KSWNBBTJInfo info = new KSWNBBTJInfo();
         info.setTime("最大值");
         //利用反射机制给对象赋值
         Field[] fields=info.getClass().getDeclaredFields();  
         for(int i=1; i<fields.length; i++) {
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
         info = new KSWNBBTJInfo();
         info.setTime("最大值出现时间");
         //利用反射机制给对象赋值
         fields=info.getClass().getDeclaredFields();  
         for(int i=1; i<fields.length; i++) {
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
         info = new KSWNBBTJInfo();
         info.setTime("最小值");
         //利用反射机制给对象赋值
         fields=info.getClass().getDeclaredFields();  
         for(int i=1; i<fields.length; i++) {
             Field field=fields[i];
             try {
                 double dtemp=getDDValue(minvalue, i-1);
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
         info = new KSWNBBTJInfo();
         info.setTime("最小值出现时间");
         //利用反射机制给对象赋值
         fields=info.getClass().getDeclaredFields();  
         for(int i=1; i<fields.length; i++) {
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
         info = new KSWNBBTJInfo();
         info.setTime("平均值");
         //利用反射机制给对象赋值
         fields=info.getClass().getDeclaredFields();  
         for(int i=1; i<fields.length; i++) {
             Field field=fields[i];
             try {
                 //double dtemp=getDDValue(avgvalue, i-1) / 31.0;
                 double dtemp=0.00;
                 if(count > 0) dtemp = getDDValue(avgvalue, i-1) / (count * 1.0);
                 field.set(info, df.format(dtemp));
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
         }
         all.add(info);
         
         //年统计
         info = new KSWNBBTJInfo();
         info.setTime("年统计");
         info.setSysw1("最高");
         info.setSysw2("");
         info.setSysw3(df.format(nmaxvalue));
         info.setSysw4("");
         info.setSysw5("最低");
         info.setSysw6("");
         
         double dtemp=nminvalue;
         if(dtemp == 888888.88) dtemp=0.0;
         info.setSysw7(df.format(dtemp));
         
         info.setSysw8("");
         info.setSysw9("均值");
         info.setSysw10("");
         if(count == 0) {
             info.setSysw11("0.0");
         } else {
             info.setSysw11(df.format(navgvalue / (count*1.0)));
         }
         info.setSysw12("");
         all.add(info);
         
         //年统计
         info = new KSWNBBTJInfo();
         info.setTime("年统计");
         info.setSysw1("最高日期");
         info.setSysw2("");
         info.setSysw3(nmaxtime);
         info.setSysw4("");
         info.setSysw5("最低日期");
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
     
     /** 
      * 导出库水位年报表统计到excel 
      * @return 
      * @throws Exception 
      */ 
      public String exportKSWNBBTJToExcel(String state,String code,String name,String sdate,String edate)throws Exception  {
          List<KSWNBBTJInfo> list=getKSWNBBTJData(state, code, sdate, edate);
          
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
          HSSFSheet sheet = workbook.createSheet(year+"年那板水库【"+name+"】年报表统计");   //创建一个sheet 
          HSSFHeader header = sheet.getHeader();//设置sheet的头 
          try {              
              //根据是否取出数据，设置header信息 
              if(list.size() < 1 ) { 
                  header.setCenter("查无数据"); 
              } else { 
                  header.setCenter(year+"年那板水库【"+name+"】年报表统计");
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
                      KSWNBBTJInfo info = (KSWNBBTJInfo)list.get(i);
                      row = sheet.createRow((short) (i + 1));//创建第i+1行 
                      row.setHeight((short)400);//设置行高 
                      
                      //利用反射机制给对象赋值
                      Field[] fields=info.getClass().getDeclaredFields();  
                      for(int j=0; j<fields.length; j++) {
                          Field field=fields[j];
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
              response.setHeader("Content-disposition","attachment; filename=kswyear_report.xls");  //filename是下载的xls的名，建议最好用英文
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
      
      //根据时间获取参数类
      private JYLSDBBTJInfo getJYLInfo(List<JYLSDBBTJInfo> list, String time) {
          JYLSDBBTJInfo info=null;
          
          for(int i=0;i<list.size();i++) {
              JYLSDBBTJInfo temp=list.get(i);
              String temptime=temp.getTime().trim();
              if(temptime.equals(time)) {
                  info = temp;
                  break;
              }
          }
          
          return info;
      }
      
      //获取降雨量时段、日、月、年报表统计和过程线
      public List<JYLSDBBTJInfo> getJYLSDBBTJData(String sdate,String edate) throws SQLException {
          List<JYLSDBBTJInfo> all = new ArrayList<JYLSDBBTJInfo>();
          
          if(sdate.equals("")) return all;
          if(edate.equals("")) return all;
          
          DecimalFormat df=new DecimalFormat("0.00");  
          
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
                  JYLSDBBTJInfo info = new JYLSDBBTJInfo();
                  info.setTime(sdf.format(calendar.getTime()));
                  all.add(info);
                  
                  calendar.add(Calendar.DAY_OF_MONTH, 1);
              }
          } catch (ParseException e) {
              e.printStackTrace();
          }
          
          //组合SQL语句
          String sql = "select distinct jyl.rq";
          sql += ",max(case jyl.code when '001' then jyl.hour7 else 0.0 end) as bs";  //坝首
          sql += ",max(case jyl.code when '002' then jyl.hour7 else 0.0 end) as nl";  //那禁
          sql += ",max(case jyl.code when '003' then jyl.hour7 else 0.0 end) as nj";  //那荡
          sql += ",max(case jyl.code when '004' then jyl.hour7 else 0.0 end) as kl";  //枯蒌
          sql += ",max(case jyl.code when '005' then jyl.hour7 else 0.0 end) as wm";  //汪门
          sql += ",max(case jyl.code when '006' then jyl.hour7 else 0.0 end) as pl";  //婆利
          sql += ",max(case jyl.code when '007' then jyl.hour7 else 0.0 end) as ph";  //平何
          sql += ",max(case jyl.code when '007' then jyl.hour7 else 0.0 end) as bx";  //百姓
          sql += " from syq_his_jyl as jyl where jyl.rq >= '"+sdate+"' and jyl.rq <= '"+edate+"'";
          sql += " group by jyl.rq order by jyl.rq asc";
          
          double[] maxvalue=new double[10];  //最高
          String[] maxtime=new String[10];  //日期
          double[] sumvalue=new double[10];  //合计
          int[] jyr=new int[10];  //雨日
          
          for(int i=0;i<maxvalue.length;i++) maxvalue[i] = 0.0;
          for(int i=0;i<maxtime.length;i++) maxtime[i] = sdate;
          for(int i=0;i<sumvalue.length;i++) sumvalue[i] = 0.0;
          for(int i=0;i<jyr.length;i++) jyr[i] = 0;
          
          this.ps = this.conn.prepareStatement(sql);
          this.rs = this.ps.executeQuery();
          while (this.rs.next()) {
              String time=rs.getString(1);  //时间
              JYLSDBBTJInfo info=getJYLInfo(all, time);
              if(info != null) {
                  info.setTime(time);
                  info.setBs(df.format(rs.getDouble(2)));  //坝首
                  info.setNl(df.format(rs.getDouble(3)));  //那禁
                  info.setNj(df.format(rs.getDouble(4)));  //那荡
                  info.setKl(df.format(rs.getDouble(5)));  //枯蒌
                  info.setWm(df.format(rs.getDouble(6)));  //汪门
                  info.setPl(df.format(rs.getDouble(7)));  //婆利
                  info.setPh(df.format(rs.getDouble(8)));  //平何
                  info.setBx(df.format(rs.getDouble(9)));  //百姓
                  
                  double dtemp=rs.getDouble(2);  //坝首
                  if(dtemp > maxvalue[0]) {  //获取最大值和时间
                      maxvalue[0] = dtemp; 
                      maxtime[0] = rs.getString(1);  //时间
                  }
                  sumvalue[0] += dtemp;  //合计
                  if(dtemp > 0.0) jyr[0]++;  //雨日
                  
                  dtemp=rs.getDouble(3);  //那禁
                  if(dtemp > maxvalue[1]) {  //获取最大值和时间
                      maxvalue[1] = dtemp; 
                      maxtime[1] = rs.getString(1);  //时间
                  }
                  sumvalue[1] += dtemp;  //合计
                  if(dtemp > 0.0) jyr[1]++;  //雨日
                  
                  dtemp=rs.getDouble(4);  //那荡
                  if(dtemp > maxvalue[2]) {  //获取最大值和时间
                      maxvalue[2] = dtemp; 
                      maxtime[2] = rs.getString(1);  //时间
                  }
                  sumvalue[2] += dtemp;  //合计
                  if(dtemp > 0.0) jyr[2]++;  //雨日
                  
                  dtemp=rs.getDouble(5);  //枯蒌
                  if(dtemp > maxvalue[3]) {  //获取最大值和时间
                      maxvalue[3] = dtemp; 
                      maxtime[3] = rs.getString(1);  //时间
                  }
                  sumvalue[3] += dtemp;  //合计
                  if(dtemp > 0.0) jyr[3]++;  //雨日
                  
                  dtemp=rs.getDouble(6);  //汪门
                  if(dtemp > maxvalue[4]) {  //获取最大值和时间
                      maxvalue[4] = dtemp; 
                      maxtime[4] = rs.getString(1);  //时间
                  }
                  sumvalue[4] += dtemp;  //合计
                  if(dtemp > 0.0) jyr[4]++;  //雨日
                  
                  dtemp=rs.getDouble(7);  //婆利
                  if(dtemp > maxvalue[5]) {  //获取最大值和时间
                      maxvalue[5] = dtemp; 
                      maxtime[5] = rs.getString(1);  //时间
                  }
                  sumvalue[5] += dtemp;  //合计
                  if(dtemp > 0.0) jyr[5]++;  //雨日
                  
                  dtemp=rs.getDouble(8);  //平何
                  if(dtemp > maxvalue[6]) {  //获取最大值和时间
                      maxvalue[6] = dtemp; 
                      maxtime[6] = rs.getString(1);  //时间
                  }
                  sumvalue[6] += dtemp;  //合计
                  if(dtemp > 0.0) jyr[6]++;  //雨日
                  
                  dtemp=rs.getDouble(9);  //百姓
                  if(dtemp > maxvalue[7]) {  //获取最大值和时间
                      maxvalue[7] = dtemp; 
                      maxtime[7] = rs.getString(1);  //时间
                  }
                  sumvalue[7] += dtemp;  //合计
                  if(dtemp > 0.0) jyr[7]++;  //雨日
                  
              }
          }
          
          if(!flag) {  //时段、月统计才有，日统计没有
              //最高值
              JYLSDBBTJInfo info = new JYLSDBBTJInfo();
              info.setTime("最高");
              info.setBs(df.format(maxvalue[0]));  //坝首
              info.setNl(df.format(maxvalue[1]));  //那禁
              info.setNj(df.format(maxvalue[2]));  //那荡
              info.setKl(df.format(maxvalue[3]));  //枯蒌
              info.setWm(df.format(maxvalue[4]));  //汪门
              info.setPl(df.format(maxvalue[5]));  //婆利
              info.setPh(df.format(maxvalue[6]));  //平何
              info.setBx(df.format(maxvalue[7]));  //百姓
              all.add(info);
              
              //最高值日期
              info = new JYLSDBBTJInfo();
              info.setTime("日期");
              info.setBs(maxtime[0]);  //坝首
              info.setNl(maxtime[1]);  //那禁
              info.setNj(maxtime[2]);  //那荡
              info.setKl(maxtime[3]);  //枯蒌
              info.setWm(maxtime[4]);  //汪门
              info.setPl(maxtime[5]);  //婆利
              info.setPh(maxtime[6]);  //平何
              info.setBx(maxtime[7]);  //百姓
              all.add(info);
              
              //合计
              info = new JYLSDBBTJInfo();
              info.setTime("合计");
              info.setBs(df.format(sumvalue[0]));  //坝首
              info.setNl(df.format(sumvalue[1]));  //那禁
              info.setNj(df.format(sumvalue[2]));  //那荡
              info.setKl(df.format(sumvalue[3]));  //枯蒌
              info.setWm(df.format(sumvalue[4]));  //汪门
              info.setPl(df.format(sumvalue[5]));  //婆利
              info.setPh(df.format(sumvalue[6]));  //平何
              info.setBx(df.format(sumvalue[7]));  //百姓
              all.add(info);
              
              //雨日
              info = new JYLSDBBTJInfo();
              info.setTime("雨日");
              info.setBs(jyr[0]+"");  //坝首
              info.setNl(jyr[1]+"");  //那禁
              info.setNj(jyr[2]+"");  //那荡
              info.setKl(jyr[3]+"");  //枯蒌
              info.setWm(jyr[4]+"");  //汪门
              info.setPl(jyr[5]+"");  //婆利
              info.setPh(jyr[6]+"");  //平何
              info.setBx(jyr[7]+"");  //百姓
              all.add(info);
              
          }
          
          return all;
      }
      
      /** 
       * 导出降雨量时段、日、月报表统计到excel 
       * @return 
       * @throws Exception 
       */ 
       public String exportJYLSDBBTJToExcel(String state,String sdate,String edate)throws Exception  {
           List<JYLSDBBTJInfo> list=getJYLSDBBTJData(sdate, edate);
           
           String datas[]=sdate.split("-");
           String year=datas[0].trim();
           String month=datas[1].trim();
           
           //设置表头：对Excel每列取名
           String []tableHeader=new String[9];
           tableHeader[0] = "时间";
           tableHeader[1] = "坝首";
           tableHeader[2] = "那禁";
           tableHeader[3] = "那荡";
           tableHeader[4] = "枯蒌";
           tableHeader[5] = "汪门";
           tableHeader[6] = "婆利";
           tableHeader[7] = "平何";
           tableHeader[8] = "百姓";
           
           short cellNumber=(short)tableHeader.length;//表的列数 
           HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个excel 
           HSSFCell cell = null;   //Excel的列 
           HSSFRow row = null; //Excel的行 
           HSSFCellStyle style = workbook.createCellStyle();   //设置表头的类型 
           style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
           HSSFCellStyle style1 = workbook.createCellStyle();  //设置数据类型 
           style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
           HSSFFont font = workbook.createFont();  //设置字体 
           
           String sheetTitle="年报表统计",reportName="year_report";
           if(state.equals("1")) {
               sheetTitle = sdate+"至"+edate+" 那板水库降雨量时段报表统计";
               reportName = "jylsd_report";
           } else if(state.equals("2")) {
               sheetTitle = sdate+" 那板水库降雨量日报表统计";
               reportName = "jylday_report";
           } else if(state.equals("3")) {
               sheetTitle = year+"年"+month+"月 那板水库降雨量月报表统计";
               reportName = "jylmonth_report";
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
                       JYLSDBBTJInfo info = (JYLSDBBTJInfo)list.get(i);
                       row = sheet.createRow((short) (i + 1));//创建第i+1行 
                       row.setHeight((short)400);//设置行高 
                       
                       //利用反射机制给对象赋值
                       Field[] fields=info.getClass().getDeclaredFields();  
                       for(int j=0; j<fields.length; j++) {
                           Field field=fields[j];
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
       
       //根据时间进行赋值
       private void makeJYLNBBTJData(List<JYLNBBTJInfo> list, String time, double value) {
           String datas[]=time.split("-");
           if(datas.length < 3) return;
           
           String smonth=datas[1].trim();  //月份
           String sday=datas[2].trim();  //日
           
           int imonth=Integer.valueOf(smonth);
           int iday=Integer.valueOf(sday);
           
           int day=iday-1;
           if(day >= list.size()) return;
           
           //根据日期获取对象
           JYLNBBTJInfo info=list.get(day);
           if(info == null) return;
           
           //根据月份给对象赋值
           DecimalFormat df=new DecimalFormat("0.00");
           
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
       
       //获取降雨量年报表统计（针对单个雨量站）
       public List<JYLNBBTJInfo> getJYLNBBTJData(String state,String code,String sdate,String edate) throws SQLException {
           List<JYLNBBTJInfo> all = new ArrayList<JYLNBBTJInfo>();
           
           //初始化年报表数据
           for(int i=0;i<31;i++) {
               JYLNBBTJInfo info = new JYLNBBTJInfo();
               info.setTime((i+1)+"日");
               all.add(info);
           }
           
           //组合SQL语句
           String sql = "select distinct jyl.rq,jyl.hour7 from syq_his_jyl as jyl " +
                  "where jyl.rq >= '"+sdate+"' and jyl.rq <= '"+edate+"' and jyl.code='"+code+"'"+
                   "  group by jyl.rq,jyl.hour7 order by jyl.rq asc";
           this.ps = this.conn.prepareStatement(sql);
           this.rs = this.ps.executeQuery();
           while (this.rs.next()) {
               String time=rs.getString(1);  //时间
               String hour7=rs.getString(2);  //相应值
               
               double value=Double.valueOf(hour7);
               
               makeJYLNBBTJData(all, time, value);
           }
           
           double[] maxvalue=new double[12];  //最高
           String[] maxtime=new String[12];  //日期
           double[] sumvalue=new double[12];  //合计
           Integer[] jyrvalue=new Integer[12];  //雨日
           
           for(int i=0;i<maxvalue.length;i++) maxvalue[i] = 0.0;
           for(int i=0;i<maxtime.length;i++) maxtime[i] = "1日";
           for(int i=0;i<sumvalue.length;i++) sumvalue[i] = 0.0;
           for(int i=0;i<jyrvalue.length;i++) jyrvalue[i] = 0;
           
           double nmaxvalue=0.00;  //年最大值 
           String nmaxtime="1月1日";  //年最大值时间
           double alljylvalue=0.0;  //年总降雨量
           int alljylday=0;  //年总降雨天数
           
           for(int i=0;i<all.size();i++) {
               JYLNBBTJInfo info=all.get(i);
               String time=info.getTime().trim();
               
               //利用反射机制进行数据处理
               Field[] fields=info.getClass().getDeclaredFields();  
               for(int j=1; j<fields.length; j++) {
                   Field field=fields[j];
                   try {
                       String stemp=(String)field.get(info);
                       if(!stemp.equals("")) {
                           double dtemp=Double.valueOf(stemp);
                           
                           if(dtemp > nmaxvalue) {
                               nmaxvalue = dtemp;  //年最大值
                               nmaxtime = j+"月"+time;  //年最大值时间
                           }
                           alljylvalue += dtemp;  //年总降雨量
                           if(dtemp > 0) alljylday ++;  //年总降雨量天数（降雨量大于0才算）
                           
                           if(dtemp > maxvalue[j-1]) {  //获取最大值和时间
                               maxvalue[j-1] = dtemp;  //最高
                               maxtime[j-1] = time;  //日期
                           }
                           sumvalue[j-1] += dtemp;  //合计
                           if(dtemp > 0) jyrvalue[j-1]++;  //雨日（降雨量大于0才算）
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
           JYLNBBTJInfo info = new JYLNBBTJInfo();
           info.setTime("最高");
           //利用反射机制给对象赋值
           Field[] fields=info.getClass().getDeclaredFields();  
           for(int i=1; i<fields.length; i++) {
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
           info = new JYLNBBTJInfo();
           info.setTime("日期");
           //利用反射机制给对象赋值
           fields=info.getClass().getDeclaredFields();  
           for(int i=1; i<fields.length; i++) {
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
           
           //合计
           info = new JYLNBBTJInfo();
           info.setTime("合计");
           //利用反射机制给对象赋值
           fields=info.getClass().getDeclaredFields();  
           for(int i=1; i<fields.length; i++) {
               Field field=fields[i];
               try {
                   double dtemp =getDDValue(sumvalue, i-1);
                   field.set(info, df.format(dtemp));
               } catch (IllegalArgumentException e) {
                   e.printStackTrace();
               } catch (IllegalAccessException e) {
                   e.printStackTrace();
               }
           }
           all.add(info);
           
           //年统计
           info = new JYLNBBTJInfo();
           info.setTime("年统计");
           info.setSysw1("最大");
           info.setSysw2("");
           info.setSysw3(df.format(nmaxvalue));
           info.setSysw4("");
           info.setSysw5("日期");
           info.setSysw6("");
           info.setSysw7(nmaxtime);
           info.setSysw8("");
           info.setSysw9("");
           info.setSysw10("");
           info.setSysw11("");
           info.setSysw12("");
           all.add(info);
           
           //年统计
           info = new JYLNBBTJInfo();
           info.setTime("年统计");
           info.setSysw1("总降水量");
           info.setSysw2("");
           info.setSysw3(df.format(alljylvalue));
           info.setSysw4("");
           info.setSysw5("总降水天数");
           info.setSysw6("");
           info.setSysw7(alljylday+"");
           info.setSysw8("");
           info.setSysw9("");
           info.setSysw10("");
           info.setSysw11("");
           info.setSysw12("");
           all.add(info);
           
           return all;
       }
       
       /** 
        * 导出库水位年报表统计到excel 
        * @return 
        * @throws Exception 
        */ 
        public String exportJYLNBBTJToExcel(String state,String code,String name,String sdate,String edate)throws Exception  {
            List<JYLNBBTJInfo> list=getJYLNBBTJData(state, code, sdate, edate);
            
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
            HSSFSheet sheet = workbook.createSheet(year+"年那板水库【"+name+"】年报表统计");   //创建一个sheet 
            HSSFHeader header = sheet.getHeader();//设置sheet的头 
            try {              
                //根据是否取出数据，设置header信息 
                if(list.size() < 1 ) { 
                    header.setCenter("查无数据"); 
                } else { 
                    header.setCenter(year+"年那板水库【"+name+"】年报表统计");
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
                    CellRangeAddress range = new CellRangeAddress(35, 36, 0, 0);   
                    sheet.addMergedRegion(range); 
                    for(int i=0;i<6;i++) {
                        range = new CellRangeAddress(35, 35, i*2+1, i*2+2);
                        sheet.addMergedRegion(range); 
                    }
                    for(int i=0;i<6;i++) {
                        range = new CellRangeAddress(36, 36, i*2+1, i*2+2);
                        sheet.addMergedRegion(range); 
                    }
                    
                    // 给excel填充数据这里需要编写 
                    for(int i = 0 ;i < list.size() ;i++) {       
                        JYLNBBTJInfo info = (JYLNBBTJInfo)list.get(i);
                        row = sheet.createRow((short) (i + 1));//创建第i+1行 
                        row.setHeight((short)400);//设置行高 
                        
                        //利用反射机制给对象赋值
                        Field[] fields=info.getClass().getDeclaredFields();  
                        for(int j=0; j<fields.length; j++) {
                            Field field=fields[j];
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
                response.setHeader("Content-disposition","attachment; filename=jylyear_report.xls");  //filename是下载的xls的名，建议最好用英文
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
        
        //获取库水位年过程线
        public List<KSWNGCXJInfo> getKSWNGCXData(String code,String sdate,String edate) throws SQLException {
            List<KSWNGCXJInfo> all = new ArrayList<KSWNGCXJInfo>();
            
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
                KSWNGCXJInfo info=new KSWNGCXJInfo();
                info.setTime(sdf.format(calendar.getTime()));
                all.add(info);
                
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            
            //组合SQL语句
            String sql = "select distinct ksw.rq,ksw.hour7" +
                    ",(select jyl_day from syq_his_jyl as syq where syq.rq=ksw.rq and syq.code='001') as rjyl" + 
                    " from syq_his_ksw as ksw where ksw.rq >= '"+sdate+"' and ksw.rq <= '"+edate+
                    "'  and ksw.code='"+code+"' group by ksw.rq order by ksw.rq asc";
            this.ps = this.conn.prepareStatement(sql);
            this.rs = this.ps.executeQuery();
            while (this.rs.next()) {
                String time=rs.getString(1);
                double hour7=rs.getDouble(2);
                double rjyl=rs.getDouble(3);  //日降雨量
                
                double  value = Double.valueOf(hour7);
                
                makeNGCXData(all, time, value, rjyl);
            }
            
            return all;
        }
        
        //处理年过程线数据
        private void makeNGCXData(List<KSWNGCXJInfo> list,String time,double value,double rjyl) {
            for(int i=0;i<list.size();i++) {
                KSWNGCXJInfo info=list.get(i);
                String timetemp=info.getTime().trim();
                if(timetemp.equals(time)) {
                    info.setValue(value);
                    info.setRjyl(rjyl);
                    break;
                }
            }
        }
        
        //根据时间获取参数类
        private JYLSDGCXJInfo getJYLGcx(List<JYLSDGCXJInfo> list, String time) {
            JYLSDGCXJInfo info=null;
            
            for(int i=0;i<list.size();i++) {
                JYLSDGCXJInfo temp=list.get(i);
                String temptime=temp.getTime().trim();
                if(temptime.equals(time)) {
                    info = temp;
                    break;
                }
            }
            
            return info;
        }
        
        //获取降雨量时段、日、月、年报过程线
        public List<JYLSDGCXJInfo> getJYLSDGCXData(String code,String sdate,String edate) throws SQLException {
            List<JYLSDGCXJInfo> all = new ArrayList<JYLSDGCXJInfo>();
            
            if(sdate.equals("")) return all;
            if(edate.equals("")) return all;
            
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
                    JYLSDGCXJInfo info = new JYLSDGCXJInfo();
                    info.setTime(sdf.format(calendar.getTime()));
                    all.add(info);
                    
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
            //组合SQL语句
            String sql = "select distinct jyl.rq";
            
            sql += ",(select ksw.hour7 from syq_his_ksw as ksw where ksw.rq=jyl.rq and ksw.code='001') as syksw";  //上游库水位
            sql += ",(select ksw.hour7 from syq_his_ksw as ksw where ksw.rq=jyl.rq and ksw.code='002') as xyksw";  //下游库水位
            
            String datas[]=code.split(",");
            for(int i=0;i<datas.length;i++) {
                String scode=datas[i];
                sql += ",max(case jyl.code when "+scode+" then jyl.hour7 else 0.0 end) as rjyl"+(i+1);  //降雨量
            }
            
            sql += " from syq_his_jyl as jyl where jyl.rq >= '"+sdate+"' and jyl.rq <= '"+edate+"'";
            sql += " group by jyl.rq order by jyl.rq asc";
            
            this.ps = this.conn.prepareStatement(sql);
            this.rs = this.ps.executeQuery();
            while (this.rs.next()) {
                String time=rs.getString(1);  //时间
                JYLSDGCXJInfo info=getJYLGcx(all, time);
                if(info != null) {
                    info.setTime(time);
                    info.setSyksw(rs.getDouble(2));  //上游库水位
                    info.setXyksw(rs.getDouble(3));  //下游库水位
                    
                    //利用反射机制给对象赋值
                    Field[] fields=info.getClass().getDeclaredFields();  
                    for(int i=3; i<fields.length; i++) {
                        Field field=fields[i];
                        try {
                            double value=getValue(this.rs, i+1);
                            field.set(info, value);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            
            return all;
        }
        
        //获取雨量站今日降雨量实时数据
        public List<JYLRTDataInfo> getJYLRTData() throws SQLException {
            List<JYLRTDataInfo> all = new ArrayList<JYLRTDataInfo>();
            
            //组合SQL语句
            String sql = "select code,name,jyl_day,jyl_year,jd,wd from syq_rt_jyl";
            
            this.ps = this.conn.prepareStatement(sql);
            this.rs = this.ps.executeQuery();
            while (this.rs.next()) {
                JYLRTDataInfo info=new JYLRTDataInfo();
                info.setCode(rs.getString(1));
                info.setName(rs.getString(2));
                info.setJyl_day(rs.getDouble(3));
                info.setJyl_year(rs.getDouble(4));
                info.setJd(rs.getDouble(5));
                info.setWd(rs.getDouble(6));
                all.add(info);
            }
            
            return all;
        }

    //根据序号获取值
    private double getValue(ResultSet rs,int aIndex) {
        double dtemp=0.00;
        
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int column=rsmd.getColumnCount();
            if(aIndex <= column) {
                String stemp=rs.getString(aIndex);
                if(stemp != null) {
                    dtemp = Double.valueOf(stemp);
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
     
	public void closeConn() {
		ConnectionPool.release(this.conn, this.ps, this.rs);
	}
	
	public int getSearchRows(String gh, String name) throws SQLException {
		String sql = "select count(*),yg.*,(select bm.NAME from sys_bm as bm where bm.Id=yg.BM_ID) as BM,(select zw.NAME from sys_zw as"
				+ " zw where zw.Id=yg.ZW_ID) as ZW from sys_yg as yg where gh like '"
				+ gh + "%' and name like '" + name + "%' order by id";
		this.ps = this.conn.prepareStatement(sql);
		int rows = 0;
		this.rs = ps.executeQuery();
		if (rs.next()) {
			rows = rs.getInt(1);
		}
		return rows;
	}
	
}
