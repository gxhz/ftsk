package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.StaffInfo;
import com.util.ConnectionPool;


public class StaffDao_sqlserver {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
 public StaffDao_sqlserver() throws SQLException{
	this.conn=ConnectionPool.getConnection();
 }

 public void closeConn() {
	 ConnectionPool.release(this.conn, this.ps, this.rs);
 }

 public List<StaffInfo> getSearchInfo(int page,int rows,String Order,String gh,String name) throws SQLException{
     if(page == 0) page = 1;
     int start=(page-1)*rows;
	 int end=start+rows;
	 List<StaffInfo> all=new ArrayList<StaffInfo>();
	 String sql="select yg.* from " +
	 		"(select syg.*,row_number() over(order by syg.id) as rownumber from sys_yg as syg where gh like '%"+gh+"%' and name like '%"+name+"%') as yg" + 
	 		" where yg.rownumber > "+start+" and yg.rownumber <= " + end + "order by yg.id";
	 this.ps=this.conn.prepareStatement(sql);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 StaffInfo info=new StaffInfo();
		 info.setId(rs.getInt(1));
		 info.setGh(rs.getString(2));
		 info.setName(rs.getString(3));
		 info.setSex(rs.getString(4));
		 info.setSysuser(rs.getInt(5));
		 info.setPassword(rs.getString(6));
		 info.setPhone(rs.getString(7));
		 info.setEmail(rs.getString(8));
		 info.setDes(rs.getString(9));
		 info.setUnuseflag(rs.getString(10));
		 all.add(info);
	 }
	 return all;
 }
 
 public List<StaffInfo> getAllInfo() throws SQLException{
	 List<StaffInfo> all=new ArrayList<StaffInfo>();
	 String sql="select yg.* from sys_yg as yg";
	 this.ps=this.conn.prepareStatement(sql);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 StaffInfo info=new StaffInfo();
		 info.setId(rs.getInt(1));
		 info.setGh(rs.getString(2));
		 info.setName(rs.getString(3));
		 info.setSex(rs.getString(4));
		 info.setSysuser(rs.getInt(5));
		 info.setPassword(rs.getString(6));
		 info.setPhone(rs.getString(7));
		 info.setEmail(rs.getString(8));
		 info.setDes(rs.getString(9));
		 info.setUnuseflag(rs.getString(10));
		 all.add(info);
	 }
	 return all;
 }
 
 public int getAllRows() throws SQLException{
		
	 String sql="select count(*) from sys_yg as yg";
	 this.ps=this.conn.prepareStatement(sql);
	 int rows=0;
	 this.rs=ps.executeQuery();
	 	if(rs.next()){
	 		rows=rs.getInt(1);
	 		}
	
	 return rows;
 }
 
 public int getSearchRows(String gh,String name) throws SQLException{
		
	 String sql="select count(*) from sys_yg as yg where gh like '%"+gh+"%' and name like '%"+name+"%'";
	 this.ps=this.conn.prepareStatement(sql);
	 int rows=0;
	 this.rs=ps.executeQuery();
	 	if(rs.next()){
	 		rows=rs.getInt(1);
 		}
	
	 return rows;
 }
 
 public int update(StaffInfo info) throws SQLException{
	  String sql="update sys_yg set gh=?,name=?,sex=?,sysuser=?,password=?,phone=?,email=?,des=?,unuseflag=? where id=?";
	  
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setString(1, info.getGh());
	  ps.setString(2, info.getName());
	  ps.setString(3, info.getSex());
	  ps.setInt(4, info.getSysuser());
	  ps.setString(5, info.getPassword());
	  ps.setString(6, info.getPhone());
	  ps.setString(7, info.getEmail());
	  ps.setString(8, info.getDes());
	  ps.setString(9, info.getUnuseflag());
	  ps.setInt(10, info.getId());
	  return ps.executeUpdate();
}
 
 public int add(StaffInfo info) throws SQLException{
	  String sql="insert into sys_yg(gh,name,sex,sysuser,password,phone,email,des,unuseflag) values(?,?,?,?,?,?,?,?,?)";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setString(1, info.getGh());
	  ps.setString(2, info.getName());
	  ps.setString(3, info.getSex());
	  ps.setInt(4, info.getSysuser());
	  ps.setString(5, info.getPassword());
	  ps.setString(6, info.getPhone());
	  ps.setString(7, info.getEmail());
	  ps.setString(8, info.getDes());
	  ps.setString(9, info.getUnuseflag());
	  return ps.executeUpdate();
	  }
 
 public int deleteById(int id) throws SQLException{
	  String sql="delete from sys_yg where id=?";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setInt(1, id);
	  return ps.executeUpdate();
	  }
 
 //����Ա��Ȩ��
 public int saveYGQX(ArrayList<String> list) throws SQLException {
     String sql="select * from sys_qx where YG_ID="+list.get(0)+" and MK_ID="+list.get(1);
     this.ps=this.conn.prepareStatement(sql);
     this.rs=ps.executeQuery();
     if(this.rs.next()) {  //˵��Ȩ���Ѿ����ڣ��ɸ���
         sql = "update sys_qx set QX='"+list.get(2)+"' where YG_ID="+list.get(0)+" and MK_ID="+list.get(1);
     } else {  //˵��Ȩ��û�д��ڣ������
         sql = "insert into sys_qx(YG_ID,MK_ID,QX) value("+list.get(0)+","+list.get(1)+","+list.get(2)+")";
     }
     
     this.ps=this.conn.prepareStatement(sql);
     return ps.executeUpdate();
 }
 
 //��ȡԱ��Ȩ��
 public String getYGQX(ArrayList<String> list) throws SQLException {
     String stemp="";
     
     String sql="select QX from sys_qx where YG_ID="+list.get(0)+" and MK_ID="+list.get(1);
     this.ps=this.conn.prepareStatement(sql);
     this.rs=ps.executeQuery();
     if(this.rs.next()) {
         stemp = rs.getString(1);
     }
     
     return stemp;
 }
 
}