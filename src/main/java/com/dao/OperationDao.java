package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.OperationInfo;
import com.util.ConnectionPool;


public class OperationDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
 public OperationDao() throws SQLException{
	this.conn=ConnectionPool.getConnection();
 }

 public void closeConn() {
	 ConnectionPool.release(this.conn, this.ps, this.rs);
 }
 
 public List<OperationInfo> getSearchInfo(int page,int rows,String Order,int mkid) throws SQLException{
     if(page == 0) page = 1;
	 int start=(page-1)*rows;
	 int end=start+rows;
	 	 
	 List<OperationInfo> all=new ArrayList<OperationInfo>();
	 String sql="select * from sys_cz where mk_id=? order by id limit ?,?";
	 this.ps=this.conn.prepareStatement(sql);
	 this.ps.setInt(1, mkid);
	 this.ps.setInt(2, start);
	 this.ps.setInt(3, end);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 OperationInfo info=new OperationInfo();
		 info.setId(rs.getInt(1));
		 info.setMk_id(rs.getInt(2));
		 info.setName(rs.getString(3));
		 info.setDes(rs.getString(4));
		 info.setUnuseflag(rs.getString(5));
		 all.add(info);
	 }
	 return all;
 }
 
 public List<OperationInfo> getAllInfo(int mkid) throws SQLException{
	 
	 List<OperationInfo> all=new ArrayList<OperationInfo>();
	 String sql="select * from sys_cz where mk_id=?";
	 this.ps=this.conn.prepareStatement(sql);
	 this.ps.setInt(1, mkid);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 OperationInfo info=new OperationInfo();
		 info.setId(rs.getInt(1));
		 info.setMk_id(rs.getInt(2));
		 info.setName(rs.getString(3));
		 info.setDes(rs.getString(4));
		 info.setUnuseflag(rs.getString(5));
		 all.add(info);
	 }
	 return all;
 }
 
 public int getSearchRows(int mkid) throws SQLException{
		
	 String sql="select count(*) from sys_cz where mk_id=?";
	 this.ps=this.conn.prepareStatement(sql);
	 this.ps.setInt(1,mkid);
	 int rows=0;
	 this.rs=ps.executeQuery();
	 	if(rs.next()){
	 		rows=rs.getInt(1);
	 	}
	
	 return rows;
 }
 
 public int update(OperationInfo info) throws SQLException{
	 
	  String sql="update sys_cz set name=?,des=?,unuseflag=? where id=?";  
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setString(1, info.getName());
	  ps.setString(2, info.getDes());
	  ps.setString(3, info.getUnuseflag());
	  ps.setInt(4, info.getId());
	  return ps.executeUpdate();
}
 
 public int add(OperationInfo info) throws SQLException{
	  String sql="insert into sys_cz(mk_id,name,des,unuseflag) values(?,?,?,?)";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setInt(1, info.getMk_id());
	  ps.setString(2, info.getName());
	  ps.setString(3, info.getDes());
	  ps.setString(4, info.getUnuseflag());  
	  return ps.executeUpdate();
	  }
 
 public int deleteById(int id) throws SQLException{
	  String sql="delete from sys_cz where id=?";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setInt(1, id);
	  return ps.executeUpdate();
	  }
 
}



