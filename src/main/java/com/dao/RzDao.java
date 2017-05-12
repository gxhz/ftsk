package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.RzInfo;
import com.util.ConnectionPool;

public class RzDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
 public RzDao() throws SQLException{
	this.conn=ConnectionPool.getConnection();
 }

 public void closeConn() {
	 ConnectionPool.release(this.conn, this.ps, this.rs);
 }
 
 //根据查询条件获取日志
 public List<RzInfo> getSearchInfo(int page,int rows,String STime,String ETime) throws SQLException{
     if(page == 0) page = 1;
     int start=(page-1)*rows;
	 int end=start+rows;
	 List<RzInfo> all=new ArrayList<RzInfo>();
	 
	 String findwhere="rz.id <> 0";
	 if((STime == null)&&(ETime == null)) {
	     
	 } else if((!STime.equals(""))&&(!ETime.equals(""))) {
	     findwhere += " and time >= '"+STime+"' and time <= '"+ETime+"'";
	 }
	 
	 String sql="select id,memo,time,yg_id,(select name from sys_yg where id=rz.yg_id) " +
	 		"from sys_rz as rz where "+findwhere+" order by id limit "+start+","+end;
	 this.ps=this.conn.prepareStatement(sql);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 RzInfo info=new RzInfo();
		 info.setId(rs.getInt(1));
		 info.setMemo(rs.getString(2));
		 info.setTime(rs.getString(3));
		 info.setYGID(rs.getString(4));
		 info.setYGName(rs.getString(5));
		 all.add(info);
	 }
	 return all;
 }
 
    //根据查询条件获取记录条数
	public int getSearchRows(String STime, String ETime) throws SQLException {
	    String findwhere="id <> 0";
	     if((STime == null)&&(ETime == null)) {
	         
	     } else if((!STime.equals(""))&&(!ETime.equals(""))) {
	         findwhere += " and time >= '"+STime+"' and time <= '"+ETime+"'";
	     }
	     
		String sql = "select count(*) from sys_rz where " + findwhere;
		this.ps = this.conn.prepareStatement(sql);
		int rows = 0;
		this.rs = ps.executeQuery();
		if (rs.next()) {
			rows = rs.getInt(1);
		}

		return rows;
	}
 
 public int add(RzInfo info) throws SQLException{
	  String sql="insert into sys_rz(memo,time,yg_id) values(?,?,?)";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setString(1, info.getMemo());
	  ps.setString(2, info.getTime());
	  ps.setString(3, info.getYGID());
	  return ps.executeUpdate();
	  }
 
    //根据ID删除日志
	public int deleteById(int id) throws SQLException {
		String sql = "delete from sys_rz where id="+id;
		this.ps = this.conn.prepareStatement(sql);
		return ps.executeUpdate();
	}
 
}



