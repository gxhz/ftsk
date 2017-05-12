package com.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.RzDao;
import com.model.RzInfo;
import com.opensymphony.xwork2.ActionSupport;

public class RzAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private String order;
	private String sort;
	public JSONObject result;
	public JSONArray list;
	
	private int Id;
	private String Memo;
	private String Time;
	private String YGID;
	private String YGName;
	
	private String STime;
	private String ETime;
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

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
	
	public void setList(JSONArray array) {
		this.list = array;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}
	
	public String getMemo() {
        return Memo;
    }
    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getTime() {
        return Time;
    }
    public void setTime(String time) {
        Time = time;
    }

    public String getYGID() {
        return YGID;
    }
    public void setYGID(String yGID) {
        YGID = yGID;
    }
    
    public String getYGName() {
        return YGName;
    }
    public void setYGName(String yGName) {
        YGName = yGName;
    }

    public String getSTime() {
        return STime;
    }
    public void setSTime(String sTime) {
        STime = sTime;
    }

    public String getETime() {
        return ETime;
    }
    public void setETime(String eTime) {
        ETime = eTime;
    }

    //��ѯ��־
	public String getRz() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<RzInfo> all = null;
		RzDao conn=new RzDao();
		try {
			all=conn.getSearchInfo(this.getPage(), this.getRows(), this.STime, this.ETime);
			int allrows=conn.getSearchRows(this.STime, this.ETime);
			map.put("total", allrows);
			map.put("rows", all);	
			
			this.setResult(JSONObject.fromObject(map));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.closeConn();
		}
		return SUCCESS;
	}
	
	public String delRz() throws Exception {
		RzDao conn=new RzDao();
		Map<String,Object> map=new HashMap<String, Object>();
	 	try {
	 		conn.deleteById(this.getId());
			map.put("success", true);
			map.put("msg", "成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "失败！");
			e.printStackTrace();
		}finally{
			conn.closeConn();
		}
	 	
	 	this.setResult(JSONObject.fromObject(map));
		 	
		return SUCCESS;
	}
	
	public String addRz() throws Exception {
		RzDao conn=new RzDao();
		RzInfo info=new RzInfo();
	 	Map<String,Object> map=new HashMap<String, Object>();
	 	try {
	 	   Calendar calendar=Calendar.getInstance();
	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 	    
	 	   info.setMemo(this.Memo);
	 	   info.setTime(sdf.format(calendar.getTime()));
	 	   info.setYGID("001");
	 	   
	 	   conn.add(info);
           map.put("success", true);
           map.put("msg", "成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "失败！");
			e.printStackTrace();
		}finally{
			conn.closeConn();
		}
	 	this.setResult(JSONObject.fromObject(map));

		return SUCCESS;
	}
	
}
