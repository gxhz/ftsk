package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.JobInfo;
import com.util.ConnectionPool;


public class JobDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
 public JobDao() throws SQLException{
	this.conn=ConnectionPool.getConnection();
 }

 public void closeConn() {
	 ConnectionPool.release(this.conn, this.ps, this.rs);
 }
 
 public List<JobInfo> getSearchInfo(int page,int rows,String Order,String name) throws SQLException{
     if(page == 0) page = 1;
     int start=(page-1)*rows;
	 int end=start+rows;
	 List<JobInfo> all=new ArrayList<JobInfo>();
	 String sql="select * from sys_zw where name like '"+name+"%' order by id limit ?,?";
	 this.ps=this.conn.prepareStatement(sql);
	 this.ps.setInt(1, start);
	 this.ps.setInt(2, end);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 JobInfo info=new JobInfo();
		 info.setId(rs.getInt(1));
		 info.setName(rs.getString(2));
		 info.setDes(rs.getString(3));
		 info.setUnuseflag(rs.getString(4));
		 all.add(info);
	 }
	 return all;
 }
 
 public List<JobInfo> getAllInfo() throws SQLException{
	 
	 List<JobInfo> all=new ArrayList<JobInfo>();
	 String sql="select * from sys_zw order by id";
	 this.ps=this.conn.prepareStatement(sql);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 JobInfo info=new JobInfo();
		 info.setId(rs.getInt(1));
		 info.setName(rs.getString(2));
		 info.setDes(rs.getString(3));
		 info.setUnuseflag(rs.getString(4));
		 all.add(info);
	 }
	 return all;
 }
 
	public int getSearchRows(String name) throws SQLException {
		String sql = "select count(*) from sys_zw where name like '" + name + "%'";
		this.ps = this.conn.prepareStatement(sql);
		int rows = 0;
		this.rs = ps.executeQuery();
		if (rs.next()) {
			rows = rs.getInt(1);
		}

		return rows;
	}
 
 public int update(JobInfo info) throws SQLException{
	  String sql="update Sys_zw set name=?,des=?,unuseflag=? where id=?";
	  
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setString(1, info.getName());
	  ps.setString(2, info.getDes());
	  ps.setString(3, info.getUnuseflag());
	  ps.setInt(4, info.getId());
	  return ps.executeUpdate();
}
 
 public int add(JobInfo info) throws SQLException{
	  String sql="insert into sys_zw(name,des,unuseflag) values(?,?,?)";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setString(1, info.getName());
	  ps.setString(2, info.getDes());
	  ps.setString(3, info.getUnuseflag());  
	  return ps.executeUpdate();
	  }
 
	public int deleteById(int id) throws SQLException {
		String sql = "delete from sys_zw where id=?";
		this.ps = this.conn.prepareStatement(sql);
		ps.setInt(1, id);
		return ps.executeUpdate();
	}
 
}



