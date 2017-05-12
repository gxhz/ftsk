package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.CGQInfo;
import com.model.StaffInfo;
import com.opensymphony.xwork2.ActionContext;
import com.util.ConnectionPool;


public class StaffDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
 public StaffDao() throws SQLException{
	this.conn=ConnectionPool.getConnection();
 }

 public void closeConn() {
	 ConnectionPool.release(this.conn, this.ps, this.rs);
 }

 //获取所有员工信息
 public List<StaffInfo> getSearchInfo(int page,int rows,String Order,String gh,String name) throws SQLException{
     if(page == 0) page = 1;
     int start=(page-1)*rows;
	 int end=start+rows;
	 List<StaffInfo> all=new ArrayList<StaffInfo>();
	 String sql="select yg.* from sys_yg as yg where gh like '"+gh+"%' and name like '"+name+"%' and sysuser<>1 order by id limit ?,?";
     this.ps=this.conn.prepareStatement(sql);
     this.ps.setInt(1, start);
     this.ps.setInt(2, end);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 StaffInfo info=new StaffInfo();
		 info.setId(rs.getInt(1));
		 info.setGh(rs.getString(2));
		 info.setName(rs.getString(3));
		 info.setSex(rs.getString(4));
		 info.setSysuser(rs.getInt(5));
		 if(info.getSysuser() == 1) {
		     info.setUserType("管理员");
		 } else if(info.getSysuser() == 2) {
		     info.setUserType("普通员工");
		 } else {
		     info.setUserType("未知类型");
		 }
		 info.setPassword(rs.getString(6));
		 info.setPhone(rs.getString(7));
		 info.setEmail(rs.getString(8));
		 info.setDes(rs.getString(9));
		 info.setUnuseflag(rs.getString(10));
		 all.add(info);
	 }
	 return all;
 }
 
 //获取所有员工信息
 public List<StaffInfo> getAllInfo() throws SQLException{
	 List<StaffInfo> all=new ArrayList<StaffInfo>();
	 String sql="select yg.* from sys_yg as yg where yg.sysuser <> 1";
	 this.ps=this.conn.prepareStatement(sql);
	 this.rs=ps.executeQuery();
	 while(rs.next()){
		 StaffInfo info=new StaffInfo();
		 info.setId(rs.getInt(1));
		 info.setGh(rs.getString(2));
		 info.setName(rs.getString(3));
		 info.setSex(rs.getString(4));
		 info.setSysuser(rs.getInt(5));
		 if(info.getSysuser() == 1) {
             info.setUserType("管理员");
         } else if(info.getSysuser() == 2) {
             info.setUserType("普通员工");
         } else {
             info.setUserType("未知类型");
         }
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
 
 //修改员工
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
 
 //添加员工
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
 
 //初始化员工默认权限
 public void initYGQX(String qxyggh) throws SQLException {
     //删除所有此员工的权限
     String sql="delete from sys_qx where YG_ID="+qxyggh;
     this.ps=this.conn.prepareStatement(sql);
     this.ps.executeUpdate();
     
     //重新初始化此员工权限
     String qxlist[]= {
             "101","111","112","113","121","122","123","131","132","133","141","142","143","151",  //水雨情监测
             "201","202","203","204","205","206","207","211","212","213","221","222","223","231","232","233","241","242","243","251",  //大坝监测
             "301","311","312","313","314","321","322","323","324","331",  //变形观测
             "401","402","411","421",  //闸门监控
             "50",  //洪水预报
             "60",  //防洪调度
             "701","711","721","731"  //报警信息
             //"80","81",  //信息平台
             //"90","91","92","93"  //系统设置
             };
     for(int i=0;i<qxlist.length;i++) {
         sql="insert into sys_qx(YG_ID,MK_ID,QX) values("+qxyggh+","+qxlist[i]+",'1')";
         this.ps=this.conn.prepareStatement(sql);
         this.ps.executeUpdate();
     }
 }
 
 public int deleteById(int id) throws SQLException{
	  String sql="delete from sys_yg where id=?";
	  this.ps=this.conn.prepareStatement(sql);
	  ps.setInt(1, id);
	  return ps.executeUpdate();
	  }
 
     public int saveYGQX(ArrayList<String> list) throws SQLException {
         String sql="select * from sys_qx where YG_ID="+list.get(0)+" and MK_ID="+list.get(1);
         this.ps=this.conn.prepareStatement(sql);
         this.rs=ps.executeQuery();
         if(this.rs.next()) { 
             sql = "update sys_qx set QX='"+list.get(2)+"' where YG_ID="+list.get(0)+" and MK_ID="+list.get(1);
         } else { 
             sql = "insert into sys_qx(YG_ID,MK_ID,QX) value("+list.get(0)+","+list.get(1)+","+list.get(2)+")";
         }
         
         this.ps=this.conn.prepareStatement(sql);
         return ps.executeUpdate();
     }
 
     //根据员工ID、模块ID获取操作权限
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
 
     //获取所有传感器信息
     public List<CGQInfo> getAllCGQMsg(int page,int rows) throws SQLException{
         if(page == 0) page = 1;
         int start=(page-1)*rows;
         int end=start+rows;
         List<CGQInfo> all=new ArrayList<CGQInfo>();
         String sql="select id,name,case(type) when 1 then '渗压计' when 2 then '量水堰' else '渗压计' end as type from cgq_info order by id limit ?,?";
         this.ps=this.conn.prepareStatement(sql);
         this.ps.setInt(1, start);
         this.ps.setInt(2, end);
         this.rs=ps.executeQuery();
         while(rs.next()){
             CGQInfo info=new CGQInfo();
             info.setId(rs.getInt(1));
             info.setName(rs.getString(2));
             info.setType(rs.getString(3));
             all.add(info);
         }
         return all;
     }
     public List<CGQInfo> GetCGQList(String type) throws SQLException{
         List<CGQInfo> all=new ArrayList<CGQInfo>();
         String sql="select id,name from cgq_info where type="+type+" order by id";
         this.ps=this.conn.prepareStatement(sql);
         this.rs=ps.executeQuery();
         while(rs.next()){
             CGQInfo info=new CGQInfo();
             info.setId(rs.getInt(1));
             info.setName(rs.getString(2));
             all.add(info);
         }
         return all;
     }
 
     //获取所有传感器总数
     public int getAllCGQRows() throws SQLException{
         String sql="select count(*) from cgq_info";
         this.ps=this.conn.prepareStatement(sql);
         int rows=0;
         this.rs=ps.executeQuery();
            if(rs.next()){
                rows=rs.getInt(1);
            }
         return rows;
     }
     
     //添加传感器信息
     public int addCGQMsg(CGQInfo info) throws SQLException{
         String sql="insert into cgq_info(name,type) values(?,?)";
         this.ps=this.conn.prepareStatement(sql);
         ps.setString(1, info.getName());
         ps.setInt(2, Integer.valueOf(info.getType()));
         return ps.executeUpdate();
         }
     
     //修改传感器信息
     public int editCGQMsg(CGQInfo info) throws SQLException{
         String sql="update cgq_info set name=?,type=? where id=?";
         
         this.ps=this.conn.prepareStatement(sql);
         ps.setString(1, info.getName());
         ps.setInt(2, Integer.valueOf(info.getType()));
         ps.setInt(3, info.getId());
         return ps.executeUpdate();
    }
     
     //删除传感器信息
     public int delCGQMsg(int id) throws SQLException{
         String sql="delete from cgq_info where id=?";
         this.ps=this.conn.prepareStatement(sql);
         ps.setInt(1, id);
         return ps.executeUpdate();
         }
     
     //获取所有传感器信息，包括渗压计和量水堰
     public List<CGQInfo> getAllCGQList(String type) throws SQLException{
         List<CGQInfo> all=new ArrayList<CGQInfo>();
         String sql="select id,name from db_rt_sysw order by name";
         if(type.equals("1")) {  //渗压计
             sql="select id,name from db_rt_sysw order by name";
         } else if(type.equals("2")) {  //量水堰
             sql="select id,name from db_rt_lsy order by name";
         }
         this.ps=this.conn.prepareStatement(sql);
         this.rs=ps.executeQuery();
         while(rs.next()){
             CGQInfo info=new CGQInfo();
             info.setId(rs.getInt(1));
             info.setName(rs.getString(2));
             info.setType(type);
             all.add(info);
         }
         return all;
     }
     
     //登录
     public int makeLogin(String name,String pass) throws SQLException{
         String sql="select Id,GH,SYSUSER from sys_yg as yg where GH='"+name+"' and PASSWORD ='"+pass+"'";
         this.ps=this.conn.prepareStatement(sql);
         int id=0;
         this.rs=ps.executeQuery();
         if(rs.next()) {
              id=rs.getInt(1);
              ActionContext.getContext().getSession().put("sysuser", rs.getInt(3));
         }
         return id;
     }
 
}