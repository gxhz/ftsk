package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;

import com.model.WaterlevelInfo;
import com.util.ConnectionPool;


public class WaterlevelDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
 public WaterlevelDao() throws SQLException{
	this.conn=ConnectionPool.getConnection();
 }

 public void closeConn() {
	 ConnectionPool.release(this.conn, this.ps, this.rs);
 }

 public List<WaterlevelInfo> getSearchInfo(int page,int rows,String Order,String name,String point) throws SQLException{
     if(page == 0) page = 1;
     int start=(page-1)*rows;
	 int end=start+rows;
	 List<WaterlevelInfo> all=new ArrayList<WaterlevelInfo>();
	 String sql="select * from waterlevel_sj where name like '"+name+"%' and point like '"+point+
	         "%' and time between '2015-05-04 00:00:00' and '2015-05-05 23:59:59' order by id limit ?,?";
	 this.ps=this.conn.prepareStatement(sql);
	 this.ps.setInt(1, start);
	 this.ps.setInt(2, end);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 WaterlevelInfo info=new WaterlevelInfo();
		 info.setId(rs.getInt(1));
		 info.setName(rs.getString(2));
		 info.setPoint(rs.getString(3));
		 info.setWaterline(rs.getDouble(4));
		 info.setHourlyrainfall(rs.getFloat(5));
		 info.setDaylyrainfall(rs.getFloat(6));
		 info.setEvaporation(rs.getFloat(7));
		 DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 info.setTime(format.format(rs.getDate(8)));
		 info.setUnuseflag(rs.getString(9));
		 all.add(info);
	 }
	 return all;
 }
 
 public List<WaterlevelInfo> getAllInfo() throws SQLException{
     DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	 List<WaterlevelInfo> all=new ArrayList<WaterlevelInfo>();
	 String sql="select * from waterlevel_sj where time between '2015-05-04 00:00:00' and '2015-05-05 23:59:59' order by id";
	 this.ps=this.conn.prepareStatement(sql);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 WaterlevelInfo info=new WaterlevelInfo();
		 info.setId(rs.getInt(1));
		 info.setName(rs.getString(2));
		 info.setPoint(rs.getString(3));
		 info.setWaterline(rs.getDouble(4));
		 info.setHourlyrainfall(rs.getFloat(5));
		 info.setDaylyrainfall(rs.getFloat(6));
		 info.setEvaporation(rs.getFloat(7));
		 info.setTime(df.format(rs.getDate(8)));
		 info.setUnuseflag(rs.getString(9));
		 info.setFx(rs.getString(10));
		 info.setFs(rs.getInt(11));
		 info.setWd(rs.getInt(12));
		 all.add(info);
	 }
	 return all;
 }
  
 public int getAllRows() throws SQLException{
		
	 String sql="select count(*) from waterlevel_sj";
	 this.ps=this.conn.prepareStatement(sql);
	 int rows=0;
	 this.rs=ps.executeQuery();
	 	if(rs.next()){
	 		rows=rs.getInt(1);
	 		}

	 return rows;
 }
 
 public int getSearchRows(String name,String point,Date starttime,Date endtime) throws SQLException{
		
	 String sql="select count(*) from waterlevel_sj where name like '"+name+"%' and point like '"+point+"%' and time between '2015-05-04 00:00:00' and '2015-05-05 23:59:59'";
	 this.ps=this.conn.prepareStatement(sql);
	 int rows=0;
	 this.rs=ps.executeQuery();
	 	if(rs.next()){
	 		rows=rs.getInt(1);
	 		}
	
	 return rows;
 }
 
 public int update(WaterlevelInfo info) throws SQLException{
	  String sql="update waterlevel_sj set name=?,point=?,waterline=?,hourlyrainfall=?,daylyrainfall=?,evaporation=?,time=?,unuseflag=? where id=?";
	  
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setString(1, info.getName());
	  ps.setString(2, info.getPoint());
	  ps.setDouble(3, info.getWaterline());
	  ps.setFloat(4, info.getHourlyrainfall());
	  ps.setFloat(5, info.getDaylyrainfall());
	  ps.setFloat(6, info.getEvaporation());
	  //ps.setDate(7, info.getTime());
	  ps.setString(8, info.getUnuseflag());
	  ps.setInt(9, info.getId());
	  return ps.executeUpdate();
}
 
 public int add(WaterlevelInfo info) throws SQLException{
	  String sql="insert into waterlevel_sj(name,point,waterline,hourlyrainfall,daylyrainfall,evaporation,time,unuseflag) values(?,?,?,?,?,?,?,?)";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setString(1, info.getName());
	  ps.setString(2, info.getPoint());
	  ps.setDouble(3, info.getWaterline());
	  ps.setFloat(4, info.getHourlyrainfall());
	  ps.setFloat(5, info.getDaylyrainfall());
	  ps.setFloat(6, info.getEvaporation());
	  //ps.setDate(7, info.getTime());
	  ps.setString(8, info.getUnuseflag());
	  return ps.executeUpdate();
	  }
 
 public int deleteById(int id) throws SQLException{
	  String sql="delete from waterlevel_sj where id=?";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setInt(1, id);
	  return ps.executeUpdate();
	  }
 
}