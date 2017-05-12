package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.DepartmentInfo;
import com.util.ConnectionPool;


public class DepartmentDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
 public DepartmentDao() throws SQLException{
	this.conn=ConnectionPool.getConnection();
 }

 public void closeConn() {
	 ConnectionPool.release(this.conn, this.ps, this.rs);
 }

 public List<DepartmentInfo> getSearchInfo(int page,int rows,String Order,String name) throws SQLException{
     if(page == 0) page = 1;
     int start=(page-1)*rows;
	 int end=start+rows;
	 
	 List<DepartmentInfo> all=new ArrayList<DepartmentInfo>();
	 String sql="select * from sys_bm where name like '"+name+"%' order by id limit ?,?";
	 this.ps=this.conn.prepareStatement(sql);
	 this.ps.setInt(1, start);
	 this.ps.setInt(2, end);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 DepartmentInfo info=new DepartmentInfo();
		 info.setId(rs.getInt(1));
		 info.setPid(rs.getInt(2));
		 info.setName(rs.getString(3));
		 info.setDes(rs.getString(4));
		 info.setUnuseflag(rs.getString(5));
		 all.add(info);
	 }
	 return all;
 }
 
 public List<DepartmentInfo> getAllInfo() throws SQLException{
 
	 List<DepartmentInfo> all=new ArrayList<DepartmentInfo>();
	 String sql="select * from sys_bm order by id";
	 this.ps=this.conn.prepareStatement(sql);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 DepartmentInfo info=new DepartmentInfo();
		 info.setId(rs.getInt(1));
		 info.setPid(rs.getInt(2));
		 info.setName(rs.getString(3));
		 info.setDes(rs.getString(4));
		 info.setUnuseflag(rs.getString(5));
		 all.add(info);
	 }
	 return all;
 }
 
 public int getSearchRows(String name) throws SQLException{
		
	 String sql="select count(*) from sys_bm where name like '"+name+"%'";
	 this.ps=this.conn.prepareStatement(sql);
	 int rows=0;
	 this.rs=ps.executeQuery();
	 	if(rs.next()){
	 		rows=rs.getInt(1);
	 	}
	 return rows;
 }
 
 public int update(DepartmentInfo Info) throws SQLException{
	  String sql="update Sys_bm set name=?,des=?,unuseflag=? where id=?";
	  
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setString(1, Info.getName());
	  ps.setString(2, Info.getDes());
	  ps.setString(3, Info.getUnuseflag());
	  ps.setInt(4, Info.getId());
	  //ps.setInt(4, Integer.parseInt(Info.getId()));
	  
	  return ps.executeUpdate();
}
 
 public int add(DepartmentInfo Info) throws SQLException{
	  String sql="insert into Sys_bm(pid,name,des,unuseflag) values(?,?,?,?)";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setInt(1, 0);
	  ps.setString(2, Info.getName());
	  ps.setString(3, Info.getDes());
	  ps.setString(4, Info.getUnuseflag());  
	  return ps.executeUpdate();
	  }
 
 public int deleteById(int id) throws SQLException{
	  String sql="delete from sys_bm where id=?";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setInt(1, id);
	  return ps.executeUpdate();
	  }
 
}



