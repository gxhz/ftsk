package com.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.model.zmgz_alarm.AlarmDataInfo;
import com.model.zmgz_alarm.WXFBInfo;
import com.model.zmgz_alarm.ZMAlarmDataInfo;
import com.model.zmgz_alarm.ZMRTDataInfo;
import com.model.zmgz_alarm.ZMReportDataInfo;
import com.opensymphony.xwork2.ActionContext;
import com.util.ConnectionPool;

public class ZMGZ_AlarmDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
    public ZMGZ_AlarmDao() throws SQLException {
        this.conn=ConnectionPool.getConnection();
    }
    
    public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
 
    //获取闸门所有信息
    public List<ZMRTDataInfo> getZMMsg(String zmindex) throws SQLException {
        List<ZMRTDataInfo> all=new ArrayList<ZMRTDataInfo>();
        String sql="select * from zm_rt_state where zm_id="+zmindex;
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            ZMRTDataInfo info=new ZMRTDataInfo();
            info.setId(rs.getInt(1));
            info.setZm_id(rs.getInt(2));
            info.setGzzmss(rs.getDouble(3));
            info.setGzzmxj(rs.getDouble(4));
            info.setGzzmqt(rs.getDouble(5));
            info.setGzzmsx(rs.getDouble(6));
            info.setGzzmxx(rs.getDouble(7));
            info.setGzzmycjd(rs.getDouble(8));
            info.setJxzmss(rs.getDouble(9));
            info.setJxzmxj(rs.getDouble(10));
            info.setJxzmqt(rs.getDouble(11));
            info.setJxzmsx(rs.getDouble(12));
            info.setJxzmxx(rs.getDouble(13));
            info.setJxzmycjd(rs.getDouble(14));
            info.setSzd(rs.getDouble(15));
            info.setSw(rs.getDouble(16));
            info.setGzzw(rs.getDouble(17));
            info.setJxzw(rs.getDouble(18));
            info.setRq(rs.getString(19));
            all.add(info);
        }
        return all;
    }
    
    //获取闸门报表数据
    public List<ZMReportDataInfo> getZMReportData(int page,int rows, String zmindex, String zmflag, String sdate, String edate) throws Exception {
        List<ZMReportDataInfo> all=new ArrayList<ZMReportDataInfo>();
        if((sdate.equals(""))||(edate.equals(""))) return all;
        
        if(page == 0) page = 1;
        int start=(page-1)*rows;
        int end=rows;
        
        String sql="select id," +
        		"case(zmzt) when 0 then '上升' when 1 then '下降' when 2 then '停止' else '未知' end as zmzt," +
        		"kssj,jssj,sll,zmkd," +
        		"case(zmindex) when 1 then '1#闸门' when 2 then '2#闸门' else '未知' end as zmmc," +
        		"case(zmflag) when 1 then '工作闸门' when 2 then '检修闸门' else '未知' end as zmmc1" +
        		" from zm_his_state where id <> 0";
        if(!zmindex.equals("")) sql += " and zmindex in ("+zmindex+")";
        if(!zmflag.equals("")) sql += " and zmflag in ("+zmflag+")";
        if((!sdate.equals(""))&&(!edate.equals(""))) sql += " and kssj >='"+sdate+" 00:00:00' and kssj <='"+edate+" 23:59:59'";
        sql += " order by id";
        if(page != -1) sql +=  " limit "+start+","+end;  //表格显示需要用到分页处理
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            ZMReportDataInfo info=new ZMReportDataInfo();
            info.setId(rs.getInt(1));
            info.setZmzt(rs.getString(2));
            info.setKssj(rs.getString(3));
            info.setJssj(rs.getString(4));
            info.setSll(rs.getDouble(5));
            info.setZmkd(rs.getDouble(6));
            info.setZmmc(rs.getString(7));
            info.setZmmc1(rs.getString(8));
            all.add(info);
        }
        
        //计算时长
        for(int i=0;i<all.size();i++) {
            ZMReportDataInfo info=all.get(i);
            sdate = info.getKssj().trim();
            edate = info.getJssj().trim();
            
            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数    
            long nh = 1000 * 60 * 60;// 一小时的毫秒数    
            long nm = 1000 * 60;// 一分钟的毫秒数  
            long ns = 1000;// 一秒钟的毫秒数    
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date d1=sdf.parse(sdate);  
            Date d2=sdf.parse(edate);
            long diff = d2.getTime()-d1.getTime();
            long day = diff / nd;// 计算差多少天    
            long hour = diff % nd / nh + day * 24;// 计算差多少小时    
            long min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            
            String str=day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟"+ sec + "秒";
            info.setSz(str);
            
            double dtemp=hour+(min - day * 24 * 60) / 60.0;
            info.setSzh(dtemp);
        }
        
        return all;
    }
    public List<ZMReportDataInfo> getZMReportData1(String zmindex, String zmflag, String sdate, String edate) throws Exception {
        List<ZMReportDataInfo> all=new ArrayList<ZMReportDataInfo>();
        if((sdate.equals(""))||(edate.equals(""))) return all;
        
        String sql="select id," +
                "case(zmzt) when 0 then '上升' when 1 then '下降' when 2 then '停止' else '未知' end as zmzt," +
                "kssj,jssj,sll,zmkd," +
                "case(zmindex) when 1 then '1#闸门' when 2 then '2#闸门' else '未知' end as zmmc," +
                "case(zmflag) when 1 then '工作闸门' when 2 then '检修闸门' else '未知' end as zmmc1" +
                " from zm_his_state where id <> 0";
        if(!zmindex.equals("")) sql += " and zmindex in ("+zmindex+")";
        if(!zmflag.equals("")) sql += " and zmflag in ("+zmflag+")";
        if((!sdate.equals(""))&&(!edate.equals(""))) sql += " and kssj >='"+sdate+" 00:00:00' and kssj <='"+edate+" 23:59:59'";
        sql += " order by id";
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            ZMReportDataInfo info=new ZMReportDataInfo();
            info.setId(rs.getInt(1));
            info.setZmzt(rs.getString(2));
            info.setKssj(rs.getString(3));
            info.setJssj(rs.getString(4));
            info.setSll(rs.getDouble(5));
            info.setZmkd(rs.getDouble(6));
            info.setZmmc(rs.getString(7));
            info.setZmmc1(rs.getString(8));
            all.add(info);
        }
        
        //计算时长
        for(int i=0;i<all.size();i++) {
            ZMReportDataInfo info=all.get(i);
            sdate = info.getKssj().trim();
            edate = info.getJssj().trim();
            
            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数    
            long nh = 1000 * 60 * 60;// 一小时的毫秒数    
            long nm = 1000 * 60;// 一分钟的毫秒数  
            long ns = 1000;// 一秒钟的毫秒数    
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date d1=sdf.parse(sdate);  
            Date d2=sdf.parse(edate);
            long diff = d2.getTime()-d1.getTime();
            long day = diff / nd;// 计算差多少天    
            long hour = diff % nd / nh + day * 24;// 计算差多少小时    
            long min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            
            String str=day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟"+ sec + "秒";
            info.setSz(str);
            
            double dtemp=hour+(min - day * 24 * 60) / 60.0;
            info.setSzh(dtemp);
        }
        
        return all;
    }
    
    //获取闸门报警数据
    public List<ZMAlarmDataInfo> getZMAlarmData(int page,int rows,String zmindex, String zmflag, String sdate, String edate) throws Exception {
        List<ZMAlarmDataInfo> all=new ArrayList<ZMAlarmDataInfo>();
        if((sdate.equals(""))||(edate.equals(""))) return all;
        
        if(page == 0) page = 1;
        int start=(page-1)*rows;
        int end=rows;
        
        String sql="select id," +
                "case(zmzt) when 0 then '上限' when 1 then '下限' when 2 then '故障' else '未知' end as zmzt," +
                "kssj,jssj," +
                "case(zmindex) when 1 then '1#闸门' when 2 then '2#闸门' else '未知' end as zmmc," +
                "case(zmflag) when 1 then '工作闸门' when 2 then '检修闸门' else '未知' end as zmmc1," +
                "case(alarmflag) when 1 then '报警' when 2 then '恢复' else '未知' end as alarmflag" +
                " from zm_alarm_state where id <> 0";
        if(!zmindex.equals("")) sql += " and zmindex in ("+zmindex+")";
        if(!zmflag.equals("")) sql += " and zmflag in ("+zmflag+")";
        if((!sdate.equals(""))&&(!edate.equals(""))) sql += " and kssj >='"+sdate+" 00:00:00' and kssj <='"+edate+" 23:59:59'";
        sql += " order by id";
        if(page != -1) sql +=  " limit "+start+","+end;  //表格显示需要用到分页处理
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            ZMAlarmDataInfo info=new ZMAlarmDataInfo();
            info.setId(rs.getInt(1));
            info.setZmzt(rs.getString(2));
            info.setKssj(rs.getString(3));
            info.setJssj(rs.getString(4));
            info.setZmmc(rs.getString(5));
            info.setZmmc1(rs.getString(6));
            info.setBjzt(rs.getString(7));
            all.add(info);
        }
        
        //计算时长(表格显示需要用到时间计算)
        for(int i=0;i<all.size();i++) {
            ZMAlarmDataInfo info=all.get(i);
            sdate = info.getKssj().trim();
            edate = info.getJssj().trim();
            
            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数    
            long nh = 1000 * 60 * 60;// 一小时的毫秒数    
            long nm = 1000 * 60;// 一分钟的毫秒数  
            long ns = 1000;// 一秒钟的毫秒数    
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date d1=sdf.parse(sdate);  
            Date d2=sdf.parse(edate);
            long diff = d2.getTime()-d1.getTime();
            long day = diff / nd;// 计算差多少天    
            long hour = diff % nd / nh + day * 24;// 计算差多少小时    
            long min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            
            String str=day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟"+ sec + "秒";
            info.setSz(str);
            
            double dtemp=hour+(min - day * 24 * 60) / 60.0;
            info.setSzh(dtemp);
        }
        
        return all;
    }
    public List<Integer> getZMAlarmData(String zmindex, String zmflag, String sdate, String edate) throws Exception {
        List<Integer> all=new ArrayList<Integer>();
        if((sdate.equals(""))||(edate.equals(""))) return all;
        
        String sql="select id," +
                "case(zmzt) when 0 then '上限' when 1 then '下限' when 2 then '故障' else '未知' end as zmzt," +
                "kssj,jssj," +
                "case(zmindex) when 1 then '1#闸门' when 2 then '2#闸门' else '未知' end as zmmc," +
                "case(zmflag) when 1 then '工作闸门' when 2 then '检修闸门' else '未知' end as zmmc1," +
                "case(alarmflag) when 1 then '报警' when 2 then '恢复' else '未知' end as alarmflag" +
                " from zm_alarm_state where id <> 0";
        if(!zmindex.equals("")) sql += " and zmindex in ("+zmindex+")";
        if(!zmflag.equals("")) sql += " and zmflag in ("+zmflag+")";
        if((!sdate.equals(""))&&(!edate.equals(""))) sql += " and kssj >='"+sdate+" 00:00:00' and kssj <='"+edate+" 23:59:59'";
        sql += " order by id";
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        
        int num=0,sxsum=0,xxsum=0,gzsum=0;
        while(rs.next()) {
            String zmzt=rs.getString(2);  //闸门状态
            
            num += 1;
            if(zmzt.equals("上限")) sxsum += 1;
            if(zmzt.equals("下限")) xxsum += 1;
            if(zmzt.equals("故障")) gzsum += 1;
        }
        
        all.add(num);
        all.add(sxsum);
        all.add(xxsum);
        all.add(gzsum);
        
        return all;
    }
    
    //导出闸门报表数据
    public String exportZMReoprtToExcel(String zmindex, String zmflag, String sdate, String edate) throws Exception {
        List<ZMReportDataInfo> list=getZMReportData(-1, -1, zmindex, zmflag, sdate, edate);
        
        //设置表头：对Excel每列取名
        String []tableHeader=new String[7];
        tableHeader[0] = "闸门名称";
        tableHeader[1] = "闸门名称1";
        tableHeader[2] = "闸门状态";
        tableHeader[3] = "开始时间";
        tableHeader[4] = "结束时间";
        tableHeader[5] = "时长";
        tableHeader[6] = "闸门开度";
        
        short cellNumber=(short)tableHeader.length;//表的列数 
        HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个excel 
        HSSFCell cell = null;   //Excel的列 
        HSSFRow row = null; //Excel的行 
        HSSFCellStyle style = workbook.createCellStyle();   //设置表头的类型 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFCellStyle style1 = workbook.createCellStyle();  //设置数据类型 
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFFont font = workbook.createFont();  //设置字体 
        
        String sheetTitle="闸门报表统计",reportName="zm_report";;
        
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
                    ZMReportDataInfo info = (ZMReportDataInfo)list.get(i);
                    row = sheet.createRow((short) (i + 1));//创建第i+1行 
                    row.setHeight((short)400);//设置行高 
                    
                    cell = row.createCell(0);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getZmmc());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格 
                    
                    cell = row.createCell(1);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getZmmc1());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格 
                    
                    cell = row.createCell(2);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getZmzt());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
                    
                    cell = row.createCell(3);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getKssj());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
                    
                    cell = row.createCell(4);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getJssj());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
                    
                    cell = row.createCell(5);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getSz());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
                    
                    cell = row.createCell(6);  //创建第i+1行第0列 
                    cell.setCellValue((double)info.getZmkd());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
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
    
    //导出闸门报警数据
    public String exportZMAlarmToExcel(String zmindex, String zmflag, String sdate, String edate) throws Exception {
        List<ZMAlarmDataInfo> list=getZMAlarmData(-1, -1, zmindex, zmflag, sdate, edate);
        
        //设置表头：对Excel每列取名
        String []tableHeader=new String[7];
        tableHeader[0] = "闸门名称";
        tableHeader[1] = "闸门名称1";
        tableHeader[2] = "报警类型";
        tableHeader[3] = "开始时间";
        tableHeader[4] = "结束时间";
        tableHeader[5] = "时长";
        tableHeader[6] = "报警状态";
        
        short cellNumber=(short)tableHeader.length;//表的列数 
        HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个excel 
        HSSFCell cell = null;   //Excel的列 
        HSSFRow row = null; //Excel的行 
        HSSFCellStyle style = workbook.createCellStyle();   //设置表头的类型 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFCellStyle style1 = workbook.createCellStyle();  //设置数据类型 
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFFont font = workbook.createFont();  //设置字体 
        
        String sheetTitle="闸门报警记录",reportName="zm_alarm";;
        
        HSSFSheet sheet = workbook.createSheet(sheetTitle);   //创建一个sheet 
        HSSFHeader header = sheet.getHeader();  //设置sheet的头 
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
                    ZMAlarmDataInfo info = (ZMAlarmDataInfo)list.get(i);
                    row = sheet.createRow((short) (i + 1));  //创建第i+1行 
                    row.setHeight((short)400);  //设置行高 
                    
                    cell = row.createCell(0);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getZmmc());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格 
                    
                    cell = row.createCell(1);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getZmmc1());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格 
                    
                    cell = row.createCell(2);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getZmzt());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
                    
                    cell = row.createCell(3);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getKssj());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
                    
                    cell = row.createCell(4);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getJssj());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
                    
                    cell = row.createCell(5);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getSz());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
                    
                    cell = row.createCell(6);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getBjzt());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
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
    
    //获取报警所有信息
    public List<AlarmDataInfo> getAlarmMsg(int page, int rows, String bjlb, String sdate, String edate) throws SQLException {
        List<AlarmDataInfo> all=new ArrayList<AlarmDataInfo>();
        
        if(page == 0) page = 1;
        int start=(page-1)*rows;
        int end=rows;
        
        String sql="select id,bjsj,bjxx,case(bjzt) when 0 then '恢复' when 1 then '报警' else '报警' end as bjzt " +
        	"from alarm where bjsj >='"+sdate+" 00:00:00' and bjsj <='"+edate+" 23:59:59' and bjlb="+bjlb+" order by bjsj desc";
        if(page != -1) sql +=  " limit "+start+","+end;  //表格显示需要用到分页处理
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            AlarmDataInfo info=new AlarmDataInfo();
            info.setId(rs.getInt(1));
            info.setBjsj(rs.getString(2));
            info.setBjxx(rs.getString(3));
            info.setBjzt(rs.getString(4));
            all.add(info);
        }
        
        return all;
    }
    
    //导出报警数据
    public String exportAlarmToExcel(String bjlb, String sdate, String edate) throws Exception {
        List<AlarmDataInfo> list=getAlarmMsg(-1, -1, bjlb, sdate, edate);
        
        //设置表头：对Excel每列取名
        String []tableHeader=new String[3];
        tableHeader[0] = "报警时间";
        tableHeader[1] = "报警信息";
        tableHeader[2] = "报警状态";
        
        short cellNumber=(short)tableHeader.length;//表的列数 
        HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个excel 
        HSSFCell cell = null;   //Excel的列 
        HSSFRow row = null; //Excel的行 
        HSSFCellStyle style = workbook.createCellStyle();   //设置表头的类型 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFCellStyle style1 = workbook.createCellStyle();  //设置数据类型 
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFFont font = workbook.createFont();  //设置字体 
        
        String sheetTitle="大坝报警记录",reportName="db_alarm";
        if(bjlb.equals("0")) {
            sheetTitle = "大坝报警记录";
            reportName = "db_alarm";
        } else if(bjlb.equals("1")) {
            sheetTitle = "变形报警记录";
            reportName = "bx_alarm";
        } else if(bjlb.equals("2")) {
            sheetTitle = "水雨情报警记录";
            reportName = "syq_alarm";
        }
        
        HSSFSheet sheet = workbook.createSheet(sheetTitle);   //创建一个sheet 
        HSSFHeader header = sheet.getHeader();  //设置sheet的头 
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
                    AlarmDataInfo info = (AlarmDataInfo)list.get(i);
                    row = sheet.createRow((short) (i + 1));  //创建第i+1行 
                    row.setHeight((short)400);  //设置行高 
                    
                    cell = row.createCell(0);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getBjsj());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格 
                    
                    cell = row.createCell(1);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getBjxx());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格 
                    
                    cell = row.createCell(2);  //创建第i+1行第0列 
                    cell.setCellValue((String)info.getBjzt());  //设置第i+1行第0列的值 
                    cell.setCellStyle(style);  //设置风格
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
    
    //获取access_token
    private void makeAccess_token(String sappid, String ssecret) throws Exception {
        String appid="wx8925e9db1b2ef22f";
        String secret="c5d13a1624d8ff1b8eb55acc3d8bbd07";
        
        if(!sappid.equals("")) appid = sappid;
        if(!ssecret.equals("")) secret = ssecret;
        
        //String urlPath = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
        String urlPath = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+appid+"&corpsecret="+secret;
        //建立连接
        URL url=new URL(urlPath);
        HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
        //设置参数
        httpConn.setDoOutput(true);   //需要输出
        httpConn.setDoInput(true);   //需要输入
        httpConn.setUseCaches(false);  //不允许缓存
        httpConn.setRequestMethod("GET");   //设置GET方式连接
        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        //获得响应状态
        int resultCode=httpConn.getResponseCode();
        if(HttpURLConnection.HTTP_OK == resultCode){
            StringBuffer sb=new StringBuffer();
            String readLine=new String();
            BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
            while((readLine=responseReader.readLine())!=null){
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            String stemp=sb.toString();
            if(stemp.indexOf("access_token") >= 0) {
                try {
                    JSONArray jsonArray = JSONArray.fromObject("["+stemp+"]");
                    for(int i=0;i<jsonArray.size();i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        for(int j=0;j<jsonObject.names().size();j++) {
                            String name = jsonObject.names().getString(j);
                            String value = jsonObject.getString(name).trim();
                            if(name.equals("access_token")) {
                                stemp = value;
                                updateAccess_token(stemp);  //更新access_token
                                break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } 
    }
    
    //更新access_token
    private void updateAccess_token(String access_token) throws SQLException {
        if(access_token.equals("")) return;
        
        String sql="update wx_param set access_token='"+access_token+"'";
        this.ps = this.conn.prepareStatement(sql);
        this.ps.executeUpdate();
    }
    
    //从数据库获取access_token
    private String getAccess_token(int aType) throws SQLException {
        String stemp="";
        
        String sql="select appid,secret,access_token from wx_param";
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()) {
            if(aType == 0) {
                stemp = rs.getString(1)+","+rs.getString(2)+","+rs.getString(3);
            } else if(aType == 1) {
                stemp = rs.getString(3);
            }
            break;
        }
        
        return stemp;
    }
    
    //发布信息
    private boolean sendMsg(String msg, String access_token) throws Exception {
        boolean btemp=false;
        
        /*
        //订阅号群发消息
        String urlPath = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + access_token; 
        
        JSONObject requestobj = new JSONObject();
        
        JSONObject group_id = new JSONObject();
        group_id.put("group_id", "0");  //组号
        group_id.put("is_to_all", true);
        requestobj.put("filter", group_id);
        
        JSONObject content = new JSONObject();
        content.put("content", msg);  //发送内容
        requestobj.put("text", content);
        
        requestobj.put("msgtype", "text");  //固定参数
        */
        
        //企业号群发消息
        String urlPath="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+access_token;
        
        JSONObject requestobj = new JSONObject();
        
        requestobj.put("touser", "@all");  //成员ID列表
        requestobj.put("toparty", "@all");  //部门ID列表
        requestobj.put("totag", "@all");  //标签ID列表
        requestobj.put("msgtype", "text");  //消息类型
        requestobj.put("agentid", 2);  //企业应用的id
        
        JSONObject content = new JSONObject();
        content.put("content", msg);  //发送内容
        requestobj.put("text", content);
        
        requestobj.put("safe", 0);  //表示是否是保密消息
        
        String param = requestobj.toString();
        //建立连接
        URL url=new URL(urlPath);
        HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
        //设置参数
        httpConn.setDoOutput(true);   //需要输出
        httpConn.setDoInput(true);   //需要输入
        httpConn.setUseCaches(false);  //不允许缓存
        httpConn.setRequestMethod("POST");   //设置POST方式连接
        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        //建立输入流，向指向的URL传入参数
        DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
        dos.write(param.getBytes("UTF-8"));
        dos.flush();
        dos.close();
        //获得响应状态
        int resultCode=httpConn.getResponseCode();
        if(HttpURLConnection.HTTP_OK == resultCode) {
            StringBuffer sb=new StringBuffer();
            String readLine=new String();
            BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
            while((readLine=responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            String stemp=sb.toString();
            
            if(stemp.indexOf("errcode") >= 0) {
                String errcode="";
                
                try {
                    JSONArray jsonArray = JSONArray.fromObject("["+stemp+"]");
                    for(int i=0;i<jsonArray.size();i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        for(int j=0;j<jsonObject.names().size();j++) {
                            String name = jsonObject.names().getString(j);
                            String value = jsonObject.getString(name).trim();
                            if(name.equals("errcode")) {
                                errcode = value;
                                break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                if(errcode.equals("0")) btemp = true;  //42001为access_token过期
            }
        } 
        
        return btemp;
    }
    
    //发布微信信息
    public boolean sendWXMsg(String msg) throws Exception {
        boolean btemp=false;
        
        String stemp=getAccess_token(0);  //获取access_token
        String sdata[]=stemp.split(",");
        
        String appid="";  //appid
        String secret="";  //secret
        String access_token="";  //获取access_token
        
        if(sdata.length <= 2) {
            appid = sdata[0];  //appid
            secret = sdata[1];  //secret
        } else {
            appid=sdata[0];  //appid
            secret=sdata[1];  //secret
            access_token=sdata[2];  //获取access_token
        }
        
        btemp = sendMsg(msg, access_token);  //发布信息
        if(!btemp) {
            makeAccess_token(appid, secret);  //获取access_token
            access_token=getAccess_token(1);  //获取access_token
            btemp = sendMsg(msg, access_token);  //发布信息
        }
        
        return btemp;
    }
    
    //保存发布微信信息
    public int addWXMsg(String msg, int fbzt) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();   
        int id=0;
        Object obj=ActionContext.getContext().getSession().get("userid");
        if(obj != null) {
            String str=obj.toString();
            if((!str.equals(""))&&(!str.equals("{}"))) {
                id = Integer.valueOf(str);
            }
        }
        
        if(id != 0) {
            String sql="insert into wx_info(fbsj,fbrid,fbnr,fbzt) values(?,?,?,?)";
            this.ps=this.conn.prepareStatement(sql);
            ps.setString(1, sdf.format(calendar.getTime()));
            ps.setInt(2, id);
            ps.setString(3, msg);
            ps.setInt(4, fbzt);
            return ps.executeUpdate();
        } else {
            return 0;
        }
    }
    
    //获取微信发布信息
    public List<WXFBInfo> getWXFBMsg() throws SQLException {
        List<WXFBInfo> all=new ArrayList<WXFBInfo>();
        String sql="select wx.id,wx.fbsj,wx.fbrid,wx.fbnr," +
                "(select name from sys_yg as yg where yg.id=wx.fbrid)," + 
        		"case(wx.fbzt) when 1 then '发布成功' when 2 then '发布失败' else '未知状态' end as fbzt" +
        		" from wx_info as wx order by wx.fbsj desc";
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            WXFBInfo info=new WXFBInfo();
            info.setId(rs.getInt(1));
            info.setFbsj(rs.getString(2));
            info.setFbrid(rs.getInt(3));
            info.setFbnr(rs.getString(4));
            info.setFbr(rs.getString(5));
            info.setFbzt(rs.getString(6));
            all.add(info);
        }
        return all;
    }
    
    //发送邮件
    public boolean sendMail(String msg,String mail) {
        boolean btemp=false;
        
        String user="namnymiu62934";
        String password="uchw18300";
        
        String fromMail="namnymiu62934@163.com";
        String mailTitle="那板水库系统邮件";
        String mailContent=msg;
        
        try {
            String mails[]=mail.split(",");
            for(int i=0;i<mails.length;i++) {
                String toMail = mails[i].trim();
                
                Properties prop = new Properties();
                prop.setProperty("mail.host", "smtp.163.com");
                prop.setProperty("mail.transport.protocol", "smtp");
                prop.setProperty("mail.smtp.auth", "true");
                //使用JavaMail发送邮件的5个步骤
                //1、创建session
                Session session = Session.getInstance(prop);
                //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
                session.setDebug(false);
                //2、通过session得到transport对象
                Transport ts = session.getTransport();
                //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
                ts.connect("smtp.163.com", user, password);
                //4、创建邮件
                Message message = createSimpleMail(session, fromMail, toMail, mailTitle, mailContent);
                //5、发送邮件
                ts.sendMessage(message, message.getAllRecipients());
                ts.close();
            }
            
            btemp = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return btemp;
    }
    
    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     * @Anthor:孤傲苍狼
     *
     * @param session
     * @return
     * @throws Exception
     */ 
     private MimeMessage createSimpleMail(Session session, String fromMail, String toMail, String mailTitle, String msg) throws Exception {
         //创建邮件对象
         MimeMessage message = new MimeMessage(session);
         //指明邮件的发件人
         message.setFrom(new InternetAddress(fromMail));
         //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
         message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
         //邮件的标题
         message.setSubject(mailTitle);
         //邮件的文本内容
         message.setContent(msg, "text/html;charset=UTF-8");
         //返回创建好的邮件对象
         return message;
     }
     
     //推送手机APP信息
     public boolean sendAPPMsg(String msg) throws Exception {
         boolean btemp=false;
         
         Random rand = new Random();
         int num=rand.nextInt(100); //生成0-100以内的随机数
         
         msg = num+"@@@"+msg;
         btemp = sendGT(msg);  //个推推送
         if(btemp) snedJPush(msg);  //极光推送
         
         return btemp;
     }
     
     //个推推送
     private boolean sendGT(String msg) {
         boolean btemp=false;
         
         try {
             String appId="W3QkTUY4zw7PVJBV1hosd8";
             String appkey = "R6Ttxu5fmdAFEzAtz1YKV1";
             String master = "PH30cPfJAa73dEQ6vhknE3";
             
             //IGtPush push = new IGtPush(host, appkey, master);  //指定os域名
             IGtPush push = new IGtPush(appkey, master, true);  //推送os域名，Ip可选填，如果Ip不填程序自动检测用户网络，选择最快的域名连接下发
             
             TransmissionTemplate template = TransmissionTemplateDemo(msg, appId, appkey);
             
             AppMessage aapmsg=new AppMessage();  //指定应用群推送消息的消息体(相当于广播式推送消息)
             aapmsg.setData(template);  //推送消息消息内容
             aapmsg.setOffline(true);  //消息离线是否存储
             aapmsg.setOfflineExpireTime(24 * 1000 * 3600); //消息离线存储多久，单位为毫秒
             aapmsg.setPushNetWorkType(0);  //可选，判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
             
             //往所有aapId应用推送消息
             List<String> appIdList = new ArrayList<String>();
             appIdList.add(appId);
             aapmsg.setAppIdList(appIdList);  //指定推送的应用列表
             
             push.pushMessageToApp(aapmsg, "任务别名_toApp");
             //IPushResult ret = push.pushMessageToApp(aapmsg, "任务别名_toApp");
             //System.out.println(ret.getResponse().toString());
             
             btemp = true;

         } catch(Exception e) {
             e.printStackTrace();
         }
         
         return btemp;
     }
     
     //透传消息（适用于Android和IOS），数据经SDK传给您的客户端，由您写代码决定如何处理展现给用户
     private TransmissionTemplate TransmissionTemplateDemo(String stemp,String appId,String appkey) throws Exception {
         TransmissionTemplate template = new TransmissionTemplate();
         template.setAppId(appId);  //设定接收的应用
         template.setAppkey(appkey);  //用于鉴定身份是否合法
         template.setTransmissionType(2);  //收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
         template.setTransmissionContent(stemp);  //透传内容，不支持转义字符
         return template;
     }
     
     //极光推送
     private void snedJPush(String msg) {
         String masterSecret = "115a6279b519dcd183d34a4e";
         String appKey = "87a013a4a1ce94e8e6f9c619";
         
         //JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
         JPushClient jpushClient=new JPushClient(masterSecret, appKey);

         // For push, all you need do is to build PushPayload object.
         PushPayload payload = null;
         payload = buildPushObject_all_all_alert(msg);  //所有平台、所有设备
         if(payload == null) return;

         try {
             jpushClient.sendPush(payload);
             //PushResult result = jpushClient.sendPush(payload);
             //System.out.println("Got result - " + result);
         } catch (APIConnectionException e) {
             e.printStackTrace();
         } catch (APIRequestException e) {
             e.printStackTrace();
         }
     }
     
     //所有平台，所有设备，内容为 value 的通知。
     private PushPayload buildPushObject_all_all_alert(String value) {
         return PushPayload.newBuilder()
             .setPlatform(Platform.all())
             .setAudience(Audience.all())
             .setMessage(cn.jpush.api.push.model.Message.newBuilder()
                     .setMsgContent(value)
                     .build())
             .build();
         
     }
     
     //获取微信发布信息
     public List<WXFBInfo> getAPPMsg() throws SQLException {
         List<WXFBInfo> all=new ArrayList<WXFBInfo>();
         String sql="select wx.id,wx.fbsj,wx.fbrid,wx.fbnr," +
                 "(select name from sys_yg as yg where yg.id=wx.fbrid)," + 
                 "case(wx.fbzt) when 1 then '发布成功' when 2 then '发布失败' else '未知状态' end as fbzt" +
                 " from app_info as wx order by wx.fbsj desc";
         this.ps=this.conn.prepareStatement(sql);
         this.rs=ps.executeQuery();
         while(rs.next()){
             WXFBInfo info=new WXFBInfo();
             info.setId(rs.getInt(1));
             info.setFbsj(rs.getString(2));
             info.setFbrid(rs.getInt(3));
             info.setFbnr(rs.getString(4));
             info.setFbr(rs.getString(5));
             info.setFbzt(rs.getString(6));
             all.add(info);
         }
         return all;
     }
     
     //保存APP消息推送至数据库
     public int addAPPMsg(String msg, int fbzt) throws Exception {
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Calendar calendar = Calendar.getInstance();
         
         int id=0;
         Object obj=ActionContext.getContext().getSession().get("userid");
         if(obj != null) {
             String str=obj.toString();
             if((!str.equals(""))&&(!str.equals("{}"))) {
                 id = Integer.valueOf(str);
             }
         }
         
         if(id != 0) {
             String sql="insert into app_info(fbsj,fbrid,fbnr,fbzt) values(?,?,?,?)";
             this.ps=this.conn.prepareStatement(sql);
             ps.setString(1, sdf.format(calendar.getTime()));
             ps.setInt(2, id);
             ps.setString(3, msg);
             ps.setInt(4, fbzt);
             return ps.executeUpdate();
         } else {
             return 0;
         }
     }
     
     //发送短信
     public boolean sendSMS(String msg,String tel) {
         boolean btemp=false;
         
         try {
             String tels[]=tel.split(",");
             for(int i=0;i<tels.length;i++) {
                 String totel = tels[i].trim();
                 
                 sendOneSMS(msg, totel);  //发送短信
             }
             
             btemp = true;
         } catch(Exception e) {
             e.printStackTrace();
         }
         
         return btemp;
     }
     private void sendOneSMS(String msg,String mobile) {
         String urlpath = "http://utf8.sms.webchinese.cn"; 
         
         //NameValuePair格式
         String param="Uid=naban&Key=f7fa3513a0d1335d65b2&smsMob="+mobile+"&smsText="+msg;
         
         try {
             //建立连接
             URL url=new URL(urlpath);
             HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
             //设置参数
             httpConn.setDoOutput(true);   //需要输出
             httpConn.setDoInput(true);   //需要输入
             httpConn.setUseCaches(false);  //不允许缓存
             httpConn.setRequestMethod("POST");   //设置POST方式连接
             //设置请求属性
             httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
             //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
             httpConn.connect();
             //建立输入流，向指向的URL传入参数
             DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
             dos.write(param.getBytes("UTF-8"));
             dos.flush();
             dos.close();
             //获得响应状态
             int resultCode=httpConn.getResponseCode();
             if(HttpURLConnection.HTTP_OK == resultCode) {
                 StringBuffer sb=new StringBuffer();
                 String readLine=new String();
                 BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
                 while((readLine=responseReader.readLine())!=null) {
                     sb.append(readLine).append("\n");
                 }
                 responseReader.close();
//                 String stemp=sb.toString();
//                 
//                 System.out.println(stemp);
             } 
         } catch(Exception e) {
             e.printStackTrace();
         }
     }
	
}



