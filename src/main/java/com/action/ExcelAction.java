package com.action;  
  
import java.io.FileOutputStream;
import java.io.InputStream;   
import java.io.FileInputStream; 

import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.action.WaterlevelAction;
import com.dao.WaterlevelDao;
import com.model.WaterlevelInfo;

public class ExcelAction extends ActionSupport{  
	
	private static final long serialVersionUID = 1L;
        
    public InputStream getExcelFile() throws FileNotFoundException{
    	
        try  
        {  
        	createExcel();
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    		
    	    File file = new File("data.xls");
    	    InputStream is = new FileInputStream(file);
	    
    	    return is;
    	}
    
    public String createExcel() throws Exception {
    	
        HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet("日报表");    
        HSSFRow row = sheet.createRow((int) 0);  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("序号");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("时间");  
        cell.setCellStyle(style);  
        cell = row.createCell(2); 
        cell.setCellValue("水库");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("监测点");  
        cell.setCellStyle(style);  
        cell = row.createCell(4);  
        cell.setCellValue("水位(mm)");  
        cell.setCellStyle(style);
  
		List<WaterlevelInfo> all = null;
		WaterlevelDao conn=new WaterlevelDao();
		all=conn.getAllInfo();
        for (int i = 0; i < all.size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            WaterlevelInfo info = (WaterlevelInfo) all.get(i);  

            row.createCell(0).setCellValue((int) info.getId()); 
            cell = row.createCell(1);  
            cell.setCellValue(info.getTime());  
            row.createCell(2).setCellValue(info.getName());  
            row.createCell(3).setCellValue(info.getPoint());  
            row.createCell(4).setCellValue((double) info.getWaterline()); 
        }  
        try  
        {  
            FileOutputStream fout = new FileOutputStream("data.xls");  
            wb.write(fout);  
            fout.close(); 
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        return SUCCESS;
    } 
} 