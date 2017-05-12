package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.dao.DepartmentDao;
import com.model.DepartmentInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DepartmentAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private String order;
	private String sort;
	public JSONObject result;
	public JSONArray list;
	private int Id;
	private int Pid;
	private String Name;
	private String Des;
	private String Unuseflag;
	
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
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDes() {
		return Des;
	}
	public void setDes(String des) {
		Des = des;
	}
	public String getUnuseflag() {
		return Unuseflag;
	}
	public void setUnuseflag(String unuseflag) {
		Unuseflag = unuseflag;
	}

	public String getDepartmentInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<DepartmentInfo> all = null;
		DepartmentDao conn=new DepartmentDao();
		try {
			if(this.getName() != null && !this.getName().equals("")){
			}
			else
				this.setName("");
			all=conn.getSearchInfo(this.getPage(),this.getRows(),this.getOrder(),this.getName());
			int allrows=conn.getSearchRows(this.getName());
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
	
	public String getDepartmentName() throws Exception {
		List<DepartmentInfo> all = null;
		DepartmentDao conn=new DepartmentDao();
		try {
			all=conn.getAllInfo();			
			JSONArray array = JSONArray.fromObject(all);
			this.setList(array);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.closeConn();
		}
		return SUCCESS;
	}
	
}
