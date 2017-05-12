package com.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.HSYBDao;
import com.model.hsyb.DDJGBInfo;
import com.model.hsyb.DDJGTJBKeyValue;
import com.opensymphony.xwork2.ActionSupport;

public class HSYBAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private int Id;
	
	private String names="";
	private String Sbid="";
	private String Name="";
	private String Skid="";
	private String SDate="";
	private String EDate="";
	
	public JSONObject result;
	public JSONArray list;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void setResult(JSONObject result) {
		this.result = result;
	}

	public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSbid() {
        return Sbid;
    }
    public void setSbid(String sbid) {
        Sbid = sbid;
    }

    public String getSkid() {
        return Skid;
    }
    public void setSkid(String skid) {
        Skid = skid;
    }

    public String getSDate() {
        return SDate;
    }
    public void setSDate(String sDate) {
        SDate = sDate;
    }

    public String getEDate() {
        return EDate;
    }
    public void setEDate(String eDate) {
        EDate = eDate;
    }

    public JSONArray getList() {
        return list;
    }
    public void setList(JSONArray list) {
        this.list = list;
    }

    //获取调度结果统计表
    public String getHSYBJGBData() throws Exception {
    	if(SDate == null || SDate.isEmpty() || EDate == null || EDate.isEmpty()) {
    		return ERROR;
    	}
    	List<DDJGBInfo> all = null;
        HSYBDao conn = new HSYBDao();
        try {
            all = conn.getHSYBJGBData(SDate, EDate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取调度结果统计表
    public String getHSYBJGTJBData0() throws Exception {
    	if(SDate == null || SDate.isEmpty() || EDate == null || EDate.isEmpty()) {
    		return ERROR;
    	}
    	List<DDJGTJBKeyValue> all = null;
        HSYBDao conn = new HSYBDao();
        try {
            all = conn.getHSYBJGTJBData0(SDate, EDate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取调度结果统计表
    public String getHSYBJGTJBData1() throws Exception {
    	if(SDate == null || SDate.isEmpty()
    			|| EDate == null || EDate.isEmpty())
    	{
    		return ERROR;
    	}
    	List<DDJGTJBKeyValue> all = null;
        HSYBDao conn = new HSYBDao();
        try {
            all = conn.getHSYBJGTJBData1(SDate, EDate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
  	//获取调度结果统计表
    public String getHSYBJGTJBData2() throws Exception {
    	if(SDate == null || SDate.isEmpty()
    			|| EDate == null || EDate.isEmpty())
    	{
    		return ERROR;
    	}
    	List<DDJGTJBKeyValue> all = null;
        HSYBDao conn = new HSYBDao();
        try {
            all = conn.getHSYBJGTJBData2(SDate, EDate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取调度过程图
    public String getHSYBGCTData() throws Exception {
    	if(SDate == null || SDate.isEmpty()
    			|| EDate == null || EDate.isEmpty())
    	{
    		return ERROR;
    	}
    	List<DDJGBInfo> all = null;
        HSYBDao conn = new HSYBDao();
        try {
            all = conn.getHSYBGCTData(SDate, EDate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
	
}
