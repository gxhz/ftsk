package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.OperationDao;
import com.model.OperationInfo;
import com.opensymphony.xwork2.ActionSupport;

public class OperationAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private String order;
	private String sort;
	public JSONObject result;
	private int Id;
	private int Mkid;
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

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}
	public int getMk_id() {
		return Mkid;
	}
	public void setMkid(int mkid) {
		this.Mkid = mkid;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public String getDes() {
		return Des;
	}
	public void setDes(String des) {
		this.Des = des;
	}
	public String getUnuseflag() {
		return Unuseflag;
	}
	public void setUnuseflag(String unuseflag) {
		this.Unuseflag = unuseflag;
	}

	public String getOperationInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<OperationInfo> all = null;
		OperationDao conn=new OperationDao();
		try {
			all=conn.getSearchInfo(this.getPage(),this.getRows(),this.getOrder(),this.getMk_id());
			int allrows=conn.getSearchRows(this.getMk_id());
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
	
	public String getOperationName() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<OperationInfo> all = null;
		OperationDao conn=new OperationDao();
		try {
			all=conn.getAllInfo(this.getMk_id());
			int allrows=conn.getSearchRows(this.getMk_id());
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
	
	
	public String delOperationInfo() throws Exception {
		OperationDao conn=new OperationDao();
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
	
	
	public String checkInfo(String name) throws Exception{
		List<OperationInfo> all = null;
		OperationDao conn=new OperationDao();
		try {
			all=conn.getAllInfo(this.getMk_id());		
			JSONArray array = JSONArray.fromObject(all);
			for(int i=0;i<array.size();i++){
				JSONObject jo = (JSONObject) array.get(i);
				if(name.equals(jo.get("name"))){
					return "fail";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.closeConn();
		}
		return SUCCESS;
	}
	
	public String addOperationInfo() throws Exception {
		OperationDao conn=new OperationDao();
		OperationInfo info=new OperationInfo();
		info.setMk_id(this.getMk_id());
		info.setName(this.getName());
		info.setDes(this.getDes());
		info.setUnuseflag(this.getUnuseflag());
	 	Map<String,Object> map=new HashMap<String, Object>();
	 	try {
	 		if(!this.checkInfo(this.getName()).equals("fail")){
		 		conn.add(info);
				map.put("success", true);
				map.put("msg", "成功！");
	 		}else{
				map.put("success", false);
				map.put("msg", "已存在！");
	 		}
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
	
	public String editOperationInfo() throws Exception {
		OperationDao conn=new OperationDao();
		OperationInfo info=new OperationInfo();
		info.setMk_id(this.getMk_id());
		info.setId(this.getId());
		info.setName(this.getName());
		info.setDes(this.getDes());
		info.setUnuseflag(this.getUnuseflag());

	 	Map<String,Object> map=new HashMap<String, Object>();
	 	try {
	 		conn.update(info);
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
