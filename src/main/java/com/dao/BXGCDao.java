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
import java.util.ArrayList;
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

import com.model.bxgc.BXFBTInfo;
import com.model.bxgc.BXNBBTJInfo;
import com.model.bxgc.BXNGCXJInfo;
import com.model.bxgc.BXRTDataDetails;
import com.model.bxgc.BXSDBBTJInfo;
import com.model.bxgc.SXWYDCXDataDetails;
import com.model.bxgc.SXWYDCXDataDetails1;
import com.util.ConnectionPool;

public class BXGCDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
    public BXGCDao() throws SQLException {
        this.conn=ConnectionPool.getConnection();
    }
    
    public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
    
    //获取变形观测数据
    public List<BXRTDataDetails> getBXRTData(String name) throws SQLException {
        List<BXRTDataDetails> all=new ArrayList<BXRTDataDetails>();
        String sql="select * from bx_rt_wyl where id<>0";
        if(!name.equals("")) sql += " and name ='"+name+"'";
        sql += " order by name";
        this.ps=this.conn.prepareStatement(sql);
        this.rs=ps.executeQuery();
        while(rs.next()){
            BXRTDataDetails info = new BXRTDataDetails();
            info.setName(rs.getString(2));  //点名
            info.setHoffset(rs.getDouble(3));  //横向偏差
            info.setVoffset(rs.getDouble(4));  //纵向偏差
            info.setZoffset(rs.getDouble(5));  //高差
            all.add(info);
        }
        return all;
    }
    
    //根据序号获取值
    private double getValue(ResultSet rs,int aIndex) {
        double dtemp=0.0;
        
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int column=rsmd.getColumnCount();
            if(aIndex <= column) dtemp = rs.getDouble(aIndex);
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
    
    //获取时段、日、月报表统计
    public List<BXSDBBTJInfo> getBXSDBBTJData(String flag,String names,String sdate,String edate,String bname,String tablename) throws SQLException {
        List<BXSDBBTJInfo> all = new ArrayList<BXSDBBTJInfo>();
        
        if(names.equals("")) return all;
        if(sdate.equals("")) return all;
        if(edate.equals("")) return all;
        if(tablename.equals("")) return all;
        
        DecimalFormat df=new DecimalFormat("0.00");  
        
        String datas[]=names.split(",");
        
        //组合SQL语句
        String sql = "select distinct rqsj";
        
        String namelist="";
        for(int i=0;i<datas.length;i++) {
            String str=datas[i].trim();
            //sql += ",max(case name when '"+str+"' then hoffset else 0.0 end) as hoffset"+(i+1);
            sql += ",max(case name when '"+str+"' then "+bname+" else 0.0 end) as "+bname+(i+1);
            if(namelist.equals("")) {
                namelist = "'"+str+"'";
            } else {
                namelist += ",'"+str+"'";
            }
        }
        
        sql += " from "+tablename+" where rqsj >= '"+sdate+"' and rqsj <= '"+edate+"'";
        if(!namelist.equals("")) sql += " and name in("+namelist+")";
        sql += " group by rqsj order by rqsj asc";
        
        double maxvalue=0.0;  //最大值
        String maxname="";  //最大值测点
        String maxtime="";  //最大值时间
        double minvalue=888.0;  //最小值
        String minname="";  //最小值测点
        String mintime="";  //最小值时间
        double difvalue=0.0;  //水平位移量较差
        double[] yearsum=new double[55];  //本年总量（代数和）
        
        for(int i=0;i<yearsum.length;i++) yearsum[i] = 0.0;
        
        this.ps = this.conn.prepareStatement(sql);
        this.rs = this.ps.executeQuery();
        while (this.rs.next()) {
            String time=rs.getString(1);  //时间
            BXSDBBTJInfo info=new BXSDBBTJInfo();
            info.setTime(time);  //时间
            info.setLsday("1");  //历时
            
            yearsum[0] += 1;  //历时本年总量
            
            //利用反射机制给对象赋值
            Field[] fields=info.getClass().getDeclaredFields();  
            for(int i=2; i<fields.length; i++) {
                Field field=fields[i];
                try {
                    double dtemp = getValue(this.rs, i);
                    field.set(info, df.format(dtemp));
                    
                    //处理最大、最小、差值
                    if(dtemp > maxvalue) {  //获取最大值、测点、时间
                        if(i-2 < datas.length) {
                            maxvalue = dtemp; 
                            maxname = datas[i-2];
                            maxtime = time; 
                        }
                    }
                    if(dtemp < minvalue) {  //获取最小值、测点、时间
                        if(i-2 < datas.length) {
                            minvalue = dtemp; 
                            minname = datas[i-2];
                            mintime = time;
                        }
                    }
                    difvalue = 0.0;  //水平位移量较差
                    
                    yearsum[i-1] += dtemp;  //本年总量（代数和）
                    
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            all.add(info);
        }
        
        String stemp="本年";
        if(flag.equals("1")) {
            stemp = "时段";
        } else if(flag.equals("2")) {
            stemp = "日";
        } else if(flag.equals("3")) {
            stemp = "月";
        } else if(flag.equals("4")) {
            stemp = "本年";
        }
        
        //本年总量
        BXSDBBTJInfo info = new BXSDBBTJInfo();
        info.setTime(stemp+"总量");
        info.setLsday(df.format(yearsum[0]));  //历时
        //利用反射机制给对象赋值
        Field[] fields=info.getClass().getDeclaredFields();  
        for(int i=2; i<fields.length; i++) {
            Field field=fields[i];
            try {
                field.set(info, df.format(getDDValue(yearsum, i-1)));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        all.add(info);
        
        info = new BXSDBBTJInfo();
        info.setTime(stemp+"特征值统计");
        info.setLsday("最大值");
        info.setHoffset1("测点号");
        info.setHoffset2("日期");
        info.setHoffset3("最小值");
        info.setHoffset4("测点号");
        info.setHoffset5("日期");
        info.setHoffset6("水平位移量较差");
        all.add(info);
        
        info = new BXSDBBTJInfo();
        info.setTime(stemp+"特征值统计");
        info.setLsday(df.format(maxvalue));  //最大值
        info.setHoffset1(maxname);  //测点号
        info.setHoffset2(maxtime);  //日期
        info.setHoffset3(df.format(minvalue));  //最小值
        info.setHoffset4(minname);  //测点号
        info.setHoffset5(mintime);  //日期
        info.setHoffset6(df.format(difvalue));  //水平位移量较差
        all.add(info);
        
        //备注
        info = new BXSDBBTJInfo();
        info.setTime("备注");
        info.setLsday("1.正负号规定：向下游、向左岸为正；反之为负。<br>2."+stemp+"总量为代数和。");  //备注内空
        all.add(info);
        
        return all;
    }
    
    /** 
     * 导出时段、日、月报表统计到excel 
     * @return 
     * @throws Exception 
     */ 
     public String exportBXSDBBTJToExcel(String flag,String names,String sdate,String edate,String bname,String tablename)throws Exception  {
         List<BXSDBBTJInfo> list=getBXSDBBTJData(flag, names, sdate, edate, bname, tablename);
         
         String datas[]=sdate.split("-");
         String year=datas[0].trim();
         String month=datas[1].trim();
         
         //设置表头：对Excel每列取名
         datas = names.split(",");
         String []tableHeader=new String[datas.length+2];
         tableHeader[0] = "时间";
         tableHeader[1] = "历时(天)";
         for(int i=0;i<datas.length;i++) {
             tableHeader[i+2] = datas[i];
         }
         
         short cellNumber=(short)tableHeader.length;//表的列数 
         HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个excel 
         HSSFCell cell = null;   //Excel的列 
         HSSFRow row = null; //Excel的行 
         HSSFCellStyle style = workbook.createCellStyle();   //设置表头的类型 
         //style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
         HSSFCellStyle style1 = workbook.createCellStyle();  //设置数据类型 
         //style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
         HSSFFont font = workbook.createFont();  //设置字体 
         
         String strname="横向偏差";
         if(bname.equals("hoffset")) {
             strname="横向偏差";
         } else if(bname.equals("voffset")) {
             strname="纵向偏差";
         } else if(bname.equals("zoffset")) {
             strname="高差";
         }
         
         String sheetTitle="年报表统计",reportName="year_report";;
         if(flag.equals("1")) {
             sheetTitle = sdate+"至"+edate+" 那板水库"+strname+"时段报表统计";
             reportName = "bxsd_report";
         } else if(flag.equals("2")) {
             sheetTitle = sdate+" 那板水库"+strname+"日报表统计";
             reportName = "bxday_report";
         } else if(flag.equals("3")) {
             sheetTitle = year+"年"+month+"月 那板水库"+strname+"月报表统计";
             reportName = "bxmonth_report";
         } else if(flag.equals("4")) {
             sheetTitle = year+"年 那板水库"+strname+"年报表统计";
             reportName = "bxyear_report";
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
                     style1.setWrapText(true);  //设置自动换行
                     cell.setCellStyle(style1); 
                 } 
                 
                 // 合并特征值统计
                 CellRangeAddress range = new CellRangeAddress(list.size()-2, list.size()-2+1, 0, 0);   
                 sheet.addMergedRegion(range);
                 //合并水平位移较量差
                 if(datas.length >= 6) {
                     range = new CellRangeAddress(list.size()-2, list.size()-2, 7, datas.length+1);   
                     sheet.addMergedRegion(range);
                     range = new CellRangeAddress(list.size()-1, list.size()-1, 7, datas.length+1);   
                     sheet.addMergedRegion(range);
                 }
                 //合并备注
                 range = new CellRangeAddress(list.size(), list.size(), 1, datas.length+1);   
                 sheet.addMergedRegion(range);
                 
                 // 给excel填充数据这里需要编写 
                 for(int i = 0 ;i < list.size() ;i++) {       
                     BXSDBBTJInfo info = (BXSDBBTJInfo)list.get(i);
                     row = sheet.createRow((short) (i + 1));//创建第i+1行 
                     row.setHeight((short)400);//设置行高 
                     
                     //利用反射机制给对象赋值
                     Field[] fields=info.getClass().getDeclaredFields();  
                     for(int j=0; j<fields.length; j++) {
                         if(j < datas.length+2) {
                             Field field=fields[j];
                             try {
                                 cell = row.createCell(j);  //创建第i+1行第0列 
                                 
                                 String stemp=(String)field.get(info);
                                 stemp = stemp.replace("<br>", "\n");
                                 
                                 cell.setCellValue(stemp);  //设置第i+1行第0列的值 
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
     
     //根据时间进行赋值
     private void makeBXNBBTJData(List<BXNBBTJInfo> list, String time, double value) {
         String datas[]=time.split("-");
         if(datas.length < 3) return;
         
         String smonth=datas[1].trim();  //月份
         String sday=datas[2].trim();  //日
         
         int imonth=Integer.valueOf(smonth);
         int iday=Integer.valueOf(sday);
         
         int day=iday-1;
         if(day >= list.size()) return;
         
         //根据日期获取对象
         BXNBBTJInfo info=list.get(day);
         if(info == null) return;
         
         //根据月份给对象赋值
         DecimalFormat df=new DecimalFormat("0.00");
         switch(imonth) {
         case 1: info.setHoffset1(df.format(value)); break;
         case 2: info.setHoffset2(df.format(value)); break;
         case 3: info.setHoffset3(df.format(value)); break;
         case 4: info.setHoffset4(df.format(value)); break;
         case 5: info.setHoffset5(df.format(value)); break;
         case 6: info.setHoffset6(df.format(value)); break;
         case 7: info.setHoffset7(df.format(value)); break;
         case 8: info.setHoffset8(df.format(value)); break;
         case 9: info.setHoffset9(df.format(value)); break;
         case 10: info.setHoffset10(df.format(value)); break;
         case 11: info.setHoffset11(df.format(value)); break;
         case 12: info.setHoffset12(df.format(value)); break;
         }
     }
     
     //获取年报表统计
     public List<BXNBBTJInfo> getBXNBBTJData(String names,String sdate,String edate,String bname,String tablename) throws SQLException {
         List<BXNBBTJInfo> all = new ArrayList<BXNBBTJInfo>();
         
         if(tablename.equals("")) return all;
         
         double nsum=0.0;  //年总量（代数和）
         
         //初始化年报表数据
         for(int i=0;i<31;i++) {
             BXNBBTJInfo info = new BXNBBTJInfo();
             info.setTime((i+1)+"日");
             all.add(info);
         }
         
         //组合SQL语句
         //String sql = "select distinct rqsj,hoffset from bx_his_wyl where rqsj >= '"+sdate+"' and rqsj <= '"+edate+
         if(bname.equals("")) bname = "hoffset";
         String sql = "select distinct rqsj,"+bname+" from "+tablename+" where rqsj >= '"+sdate+"' and rqsj <= '"+edate+
                 "'  and name='"+names+"' group by rqsj,hoffset order by rqsj asc";
         this.ps = this.conn.prepareStatement(sql);
         this.rs = this.ps.executeQuery();
         while (this.rs.next()) {
             String time=rs.getString(1);
             double hoffset=rs.getDouble(2);
             nsum += hoffset;
             makeBXNBBTJData(all, time, hoffset);
         }
         
         double[] monthsum=new double[12];  //月总量（代数和）
         double[] maxvalue=new double[12];  //最大值
         String[] maxtime=new String[12];  //最大值时间
         double[] minvalue=new double[12];  //最小值
         String[] mintime=new String[12];  //最小值时间
         
         for(int i=0;i<monthsum.length;i++) monthsum[i] = 0.0;
         for(int i=0;i<maxvalue.length;i++) maxvalue[i] = 0.0;
         for(int i=0;i<maxtime.length;i++) maxtime[i] = "";
         for(int i=0;i<minvalue.length;i++) minvalue[i] = 888.0;
         for(int i=0;i<mintime.length;i++) mintime[i] = "";
         
         double nmaxvalue=0.00;  //年最大值 
         String nmaxtime="";  //年最大值时间
         double nminvalue=888.0;  //年最小值
         String nmintime="";  //年最小值时间
         
         //统计最大值、最小值
         for(int i=0;i<all.size();i++) {
             BXNBBTJInfo info=all.get(i);
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
                         
                         if(dtemp > maxvalue[j-1]) {  //获取最大值和时间
                             maxvalue[j-1] = dtemp; 
                             maxtime[j-1] = time;
                         }
                         if(dtemp < minvalue[j-1]) {  //获取最小值和时间
                             minvalue[j-1] = dtemp; 
                             mintime[j-1] = time;
                         }
                         
                         monthsum[j-1] += dtemp;  //处理月总量
                     }
                 } catch (IllegalArgumentException e) {
                     e.printStackTrace();
                 } catch (IllegalAccessException e) {
                     e.printStackTrace();
                 }
             }
         }
         
         DecimalFormat df=new DecimalFormat("0.00");
         
         //月总量（代数和）
         BXNBBTJInfo info = new BXNBBTJInfo();
         info.setTime("月总量（代数和）");
         //利用反射机制给对象赋值
         Field[] fields=info.getClass().getDeclaredFields();  
         for(int i=1; i<fields.length; i++) {
             Field field=fields[i];
             try {
                 double dtemp=getDDValue(monthsum, i-1);
                 if(dtemp == 0.0) {
                     field.set(info, "");
                 } else {
                     field.set(info, df.format(dtemp));
                 }
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
         }
         all.add(info);
         
         //最大值
         info = new BXNBBTJInfo();
         info.setTime("最大值");
         //利用反射机制给对象赋值
         fields=info.getClass().getDeclaredFields();  
         for(int i=1; i<fields.length; i++) {
             Field field=fields[i];
             try {
                 double dtemp=getDDValue(maxvalue, i-1);
                 if(dtemp == 0.0) {
                     field.set(info, "");
                 } else {
                     field.set(info, df.format(dtemp));
                 }
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
         }
         all.add(info);
         
         //最大值出现时间
         info = new BXNBBTJInfo();
         info.setTime("最大值日期");
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
         info = new BXNBBTJInfo();
         info.setTime("最小值");
         //利用反射机制给对象赋值
         fields=info.getClass().getDeclaredFields();  
         for(int i=1; i<fields.length; i++) {
             Field field=fields[i];
             try {
                 double dtemp=getDDValue(minvalue, i-1);
                 if(dtemp == 888.0) {
                     field.set(info, "");
                 } else {
                     field.set(info, df.format(dtemp));
                 }
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
         }
         all.add(info);
         
         //最小值出现时间
         info = new BXNBBTJInfo();
         info.setTime("最小值日期");
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
         
         //年统计
         info = new BXNBBTJInfo();
         info.setTime("年统计");
         info.setHoffset1("最大水平位移量");
         info.setHoffset2(df.format(nmaxvalue));
         info.setHoffset3("最大日期");
         info.setHoffset4(nmaxtime);
         info.setHoffset5("最小水平位移量");
         if(nminvalue == 888.0) nminvalue = 0.0;
         info.setHoffset6(df.format(nminvalue));
         info.setHoffset7("最小日期");
         info.setHoffset8(nmintime);
         info.setHoffset9("年总量（代数和）");
         info.setHoffset10("");
         info.setHoffset11(df.format(nsum));
         info.setHoffset12("");
         all.add(info);
         
         return all;
     }
     
     /** 
      * 导出年报表至Excel
      * @return 
      * @throws Exception 
      */ 
      public String exportBXNBBTJToExcel(String names,String sdate,String edate,String bname,String tablename)throws Exception  {
          List<BXNBBTJInfo> list=getBXNBBTJData(names, sdate, edate, bname, tablename);
          
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
          HSSFSheet sheet = workbook.createSheet(year+"年那板水库【"+names+"】观测点年报表统计");   //创建一个sheet 
          HSSFHeader header = sheet.getHeader();//设置sheet的头 
          try {              
              //根据是否取出数据，设置header信息 
              if(list.size() < 1 ) { 
                  header.setCenter("查无数据"); 
              } else { 
                  header.setCenter(year+"年那板水库【"+names+"】观测点年报表统计");
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
                  CellRangeAddress range = new CellRangeAddress(37, 37, 9, 10);   
                  sheet.addMergedRegion(range); 
                  range = new CellRangeAddress(37, 37, 11, 12);   
                  sheet.addMergedRegion(range); 
                  
                  // 给excel填充数据这里需要编写 
                  for(int i = 0 ;i < list.size() ;i++) {       
                      BXNBBTJInfo info = (BXNBBTJInfo)list.get(i);
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
              response.setHeader("Content-disposition","attachment; filename=bxyear_report.xls");  //filename是下载的xls的名，建议最好用英文
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
      
      //获取年过程线
      public List<BXNGCXJInfo> getBXNGCXData(String names,String sdate,String edate,String bname,String tablename) throws SQLException {
          List<BXNGCXJInfo> all = new ArrayList<BXNGCXJInfo>();
          
         if(tablename.equals("")) return all;
          
          //组合SQL语句
          //String sql = "select distinct rqsj,hoffset from bx_his_wyl where rqsj >= '"+sdate+"' and rqsj <= '"+edate+
          String sql = "select distinct rqsj,"+bname+" from "+tablename+" where rqsj >= '"+sdate+"' and rqsj <= '"+edate+
                  "'  and name='"+names+"' group by rqsj,hoffset order by rqsj asc";
          this.ps = this.conn.prepareStatement(sql);
          this.rs = this.ps.executeQuery();
          while (this.rs.next()) {
              String time=rs.getString(1);
              double hoffset=rs.getDouble(2);
              
              BXNGCXJInfo info=new BXNGCXJInfo();
              info.setTime(time);
              info.setHoffset(hoffset);
              all.add(info);
          }
          
          return all;
      }
      
      //获取分布图
      public List<BXFBTInfo> getBXFBTData(String names,String sdate,String edate,String bname,String tablename) throws SQLException {
          List<BXFBTInfo> all = new ArrayList<BXFBTInfo>();
          
          if(names.equals("")) return all;
          if(names.indexOf(",") < 0) return all;
          if(tablename.equals("")) return all;
          
          String datas[]=names.split(",");
          names = "";
          for(int i=0;i<datas.length;i++) {
              if(names.equals("")) {
                  names = "'"+datas[i]+"'";
              } else {
                  names += ",'"+datas[i]+"'";
              }
          }
          
          //组合SQL语句
          String sql = "select distinct name,rqsj,"+bname+" from "+tablename+" where rqsj >= '"+sdate+"' and rqsj <= '"+edate+
                  "'  and name in ("+names+") group by name,rqsj order by rqsj,name asc";
          this.ps = this.conn.prepareStatement(sql);
          this.rs = this.ps.executeQuery();
          while (this.rs.next()) {
              makeFBT(all, rs, datas);  //处理数据
          }
          
          return all;
      }
      
      private void makeFBT(List<BXFBTInfo> list, ResultSet rs, String datas[]) throws SQLException {
          boolean flag = false;
          
          String rsname=rs.getString(1);
          String rstime=rs.getString(2);
          double zoffset=rs.getDouble(3);
          
          int index=0;
          for(int i=0;i<datas.length;i++) {
              if(rsname.equals(datas[i])) index = i;
          }
          
          for(int i=0;i<list.size();i++) {
              BXFBTInfo info=list.get(i);
              String itime=info.getTime();
              
              //根据日期查找记录
              if(itime.equals(rstime)) {
                  if(index == 0) {
                      info.setZoffset1(zoffset);
                  } else if(index == 1) {
                      info.setZoffset2(zoffset);
                  } else if(index == 2) {
                      info.setZoffset3(zoffset);
                  } else if(index == 3) {
                      info.setZoffset4(zoffset);
                  } else if(index == 4) {
                      info.setZoffset5(zoffset);
                  } else if(index == 5) {
                      info.setZoffset6(zoffset);
                  } else if(index == 6) {
                      info.setZoffset7(zoffset);
                  } else if(index == 7) {
                      info.setZoffset8(zoffset);
                  } else if(index == 8) {
                      info.setZoffset9(zoffset);
                  } else if(index == 9) {
                      info.setZoffset10(zoffset);
                  } else if(index == 10) {
                      info.setZoffset11(zoffset);
                  } else if(index == 11) {
                      info.setZoffset12(zoffset);
                  } else if(index == 12) {
                      info.setZoffset13(zoffset);
                  } else if(index == 13) {
                      info.setZoffset14(zoffset);
                  } else if(index == 14) {
                      info.setZoffset15(zoffset);
                  } else if(index == 15) {
                      info.setZoffset16(zoffset);
                  } else if(index == 16) {
                      info.setZoffset17(zoffset);
                  } else if(index == 17) {
                      info.setZoffset18(zoffset);
                  } else if(index == 18) {
                      info.setZoffset19(zoffset);
                  } else if(index == 19) {
                      info.setZoffset20(zoffset);
                  }
                  
                  flag = true;
                  break;
              }
          }
          
          if(!flag) {
              BXFBTInfo info=new BXFBTInfo();
              info.setTime(rstime);
              if(index == 0) {
                  info.setZoffset1(zoffset);
              } else if(index == 1) {
                  info.setZoffset2(zoffset);
              } else if(index == 2) {
                  info.setZoffset3(zoffset);
              } else if(index == 3) {
                  info.setZoffset4(zoffset);
              } else if(index == 4) {
                  info.setZoffset5(zoffset);
              } else if(index == 5) {
                  info.setZoffset6(zoffset);
              } else if(index == 6) {
                  info.setZoffset7(zoffset);
              } else if(index == 7) {
                  info.setZoffset8(zoffset);
              } else if(index == 8) {
                  info.setZoffset9(zoffset);
              } else if(index == 9) {
                  info.setZoffset10(zoffset);
              } else if(index == 10) {
                  info.setZoffset11(zoffset);
              } else if(index == 11) {
                  info.setZoffset12(zoffset);
              } else if(index == 12) {
                  info.setZoffset13(zoffset);
              } else if(index == 13) {
                  info.setZoffset14(zoffset);
              } else if(index == 14) {
                  info.setZoffset15(zoffset);
              } else if(index == 15) {
                  info.setZoffset16(zoffset);
              } else if(index == 16) {
                  info.setZoffset17(zoffset);
              } else if(index == 17) {
                  info.setZoffset18(zoffset);
              } else if(index == 18) {
                  info.setZoffset19(zoffset);
              } else if(index == 19) {
                  info.setZoffset20(zoffset);
              }
              list.add(info);
          }
          
      }
      
      //获取竖向位移等值线数据
      public List<BXRTDataDetails> getSXWYDCXData(String sdate,String tablename) throws SQLException {
          List<BXRTDataDetails> all=new ArrayList<BXRTDataDetails>();
          
          if(tablename.equals("")) return all;
          
          String sql="select distinct name,hoffset,voffset,zoffset from "+tablename+" where rqsj='"+sdate+"' group by name order by name asc";
          this.ps=this.conn.prepareStatement(sql);
          this.rs=ps.executeQuery();
          while(rs.next()){
              BXRTDataDetails info = new BXRTDataDetails();
              info.setName(rs.getString(1));  //点名
              info.setHoffset(rs.getDouble(2));  //横向偏差
              info.setVoffset(rs.getDouble(3));  //纵向偏差
              info.setZoffset(rs.getDouble(4));  //高差
              all.add(info);
          }
          return all;
      }
      
      //获取竖向位移等值线数据
      public List<SXWYDCXDataDetails> getSXWYDCXData1(String sdate,String tablename) throws SQLException {
          List<SXWYDCXDataDetails> all=new ArrayList<SXWYDCXDataDetails>();
          
          if(tablename.equals("")) return all;
          
          List<SXWYDCXDataDetails> all1=new ArrayList<SXWYDCXDataDetails>();
          List<SXWYDCXDataDetails> all2=new ArrayList<SXWYDCXDataDetails>();
          
          String sql="select distinct name,hoffset,voffset,zoffset from "+tablename+" where rqsj='"+sdate+
                  "' and name in('W11','W12','W13','W21','W22','W23','W31','W32','W33','W41','W42','W43','W51','W52','w53','W61','W62','W63')" +
                  " group by name order by name asc";
          this.ps=this.conn.prepareStatement(sql);
          this.rs=ps.executeQuery();
          while(rs.next()) {
              boolean flag=false;
              String name=rs.getString(1);  //观测点名
              double value=rs.getDouble(4);  //高差
              
              for(int i=0;i<all1.size();i++) {
                  SXWYDCXDataDetails para=all1.get(i);
                  ArrayList<SXWYDCXDataDetails1> list=para.getNlist();
                  for(int j=0;j<list.size();j++) {
                      SXWYDCXDataDetails1 para1=list.get(j);
                      
                      double value1=para1.getValue();
                      if(value == value1) {  //添加到同一列表
                          SXWYDCXDataDetails1 data1=new SXWYDCXDataDetails1();
                          data1.setName(name);
                          data1.setValue(value);
                          para.getNlist().add(data1);
                          
                          flag = true;
                          
                          break;
                      }
                  }
              }
              
              if(!flag) {  //添加新点
                  SXWYDCXDataDetails data=new SXWYDCXDataDetails();
                  
                  SXWYDCXDataDetails1 data1=new SXWYDCXDataDetails1();
                  data1.setName(name);
                  data1.setValue(value);
                  
                  data.getNlist().add(data1);
                  
                  all1.add(data);
              }
              
          }
          
          sql="select distinct name,hoffset,voffset,zoffset from bx_his_wyl where rqsj='"+sdate+
                  "' and name in('WA1','WA2','WA3','WB1','WB2','WB3','WC1','WC2','WC3')" +
                  " group by name order by name asc";
          this.ps=this.conn.prepareStatement(sql);
          this.rs=ps.executeQuery();
          while(rs.next()) {
              boolean flag=false;
              String name=rs.getString(1);  //观测点名
              double value=rs.getDouble(4);  //高差
              
              for(int i=0;i<all2.size();i++) {
                  SXWYDCXDataDetails para=all2.get(i);
                  ArrayList<SXWYDCXDataDetails1> list=para.getNlist();
                  for(int j=0;j<list.size();j++) {
                      SXWYDCXDataDetails1 para1=list.get(j);
                      
                      double value1=para1.getValue();
                      if(value == value1) {  //添加到同一列表
                          SXWYDCXDataDetails1 data1=new SXWYDCXDataDetails1();
                          data1.setName(name);
                          data1.setValue(value);
                          para.getNlist().add(data1);
                          
                          flag = true;
                          
                          break;
                      }
                  }
              }
              
              if(!flag) {  //添加新点
                  SXWYDCXDataDetails data=new SXWYDCXDataDetails();
                  
                  SXWYDCXDataDetails1 data1=new SXWYDCXDataDetails1();
                  data1.setName(name);
                  data1.setValue(value);
                  
                  data.getNlist().add(data1);
                  
                  all2.add(data);
              }
              
          }
          
          all.addAll(all1);
          all.addAll(all2);
          
          //把只有一个点的列表去掉
          while(true) {
              boolean flag=false;
              
              for(int i=0;i<all.size();i++) {
                  SXWYDCXDataDetails para=all.get(i);
                  if(para.getNlist().size() <= 1) {
                      all.remove(i);
                      flag = true;
                      break;
                  }
              }
              
              if(!flag) break;
          }
          
          return all;
      }
 
}



