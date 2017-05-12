package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.dao.WaterlevelDao;
import com.model.WaterlevelInfo;
import com.opensymphony.xwork2.ActionSupport;

public class WaterlevelAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private String order;
	private String sort;
	public JSONObject result;
	public JSONArray list;
	private int Id;
	private String Name;
	private String Point;
	private double Waterline;
	private float Hourlyrainfall;
	private float Daylyrainfall;
	private float Evaporation;
	private Date Time;
	private Date Starttime;
	private Date Endtime;
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
	
	public void setList(JSONArray list) {
		this.list = list;
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
	public String getPoint() {
		return Point;
	}
	public void setPoint(String point) {
		Point = point;
	}
	public double getWaterline() {
		return Waterline;
	}
	public void setWaterline(double waterline) {
		Waterline = waterline;
	}
	public float getHourlyrainfall() {
		return Hourlyrainfall;
	}
	public void setHourlyrainfall(float hourlyrainfall) {
		Hourlyrainfall = hourlyrainfall;
	}
	public float getDaylyrainfall() {
		return Daylyrainfall;
	}
	public void setDaylyrainfall(float daylyrainfall) {
		Daylyrainfall = daylyrainfall;
	}
	public float getEvaporation() {
		return Evaporation;
	}
	public void setEvaporation(float evaporation) {
		Evaporation = evaporation;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date time) {
		Time = time;
	}
	public Date getStarttime() {
		return Starttime;
	}
	public void setStarttime(Date starttime) {
		Starttime = starttime;
	}
	public Date getEndtime() {
		return Endtime;
	}
	public void setEndtime(Date endtime) {
		Endtime = endtime;
	}	
	public String getUnuseflag() {
		return Unuseflag;
	}
	public void setUnuseflag(String unuseflag) {
		Unuseflag = unuseflag;
	}


	public String getWaterlevelInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<WaterlevelInfo> all = null;
		WaterlevelDao conn=new WaterlevelDao();
		try {
			if(this.getName() != null && !this.getName().equals("")){
			}
			else
				this.setName("");
			if(this.getPoint() != null && !this.getPoint().equals("")){
			}
			else
				this.setPoint("");
			all=conn.getSearchInfo(this.getPage(),this.getRows(),this.getOrder(),this.getName(),this.getPoint());
			int allrows=conn.getSearchRows(this.getName(),this.getPoint(),this.getStarttime(),this.getEndtime());
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
	
	public String getAllInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<WaterlevelInfo> all = null;
		WaterlevelDao conn=new WaterlevelDao();
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
	
	public String delWaterlevelInfo() throws Exception {
		WaterlevelDao conn=new WaterlevelDao();
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
	
	public String addWaterlevelInfo() throws Exception {
		WaterlevelDao conn=new WaterlevelDao();
		WaterlevelInfo info=new WaterlevelInfo();
		info.setId(this.getId());
		info.setName(this.getName());
		info.setPoint(this.getPoint());
		info.setWaterline(this.getWaterline());
		info.setHourlyrainfall(this.getDaylyrainfall());
		info.setDaylyrainfall(this.getDaylyrainfall());
		info.setEvaporation(this.getEvaporation());
		info.setUnuseflag(this.getUnuseflag());
	 	Map<String,Object> map=new HashMap<String, Object>();
	 	try {
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
	
	public String editStaffInfo() throws Exception {
		WaterlevelDao conn=new WaterlevelDao();
		WaterlevelInfo info=new WaterlevelInfo();
		info.setId(this.getId());
		info.setName(this.getName());
		info.setPoint(this.getPoint());
		info.setWaterline(this.getWaterline());
		info.setHourlyrainfall(this.getDaylyrainfall());
		info.setDaylyrainfall(this.getDaylyrainfall());
		info.setEvaporation(this.getEvaporation());
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
