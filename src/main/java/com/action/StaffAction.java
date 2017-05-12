package com.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.StaffDao;
import com.model.CGQInfo;
import com.model.StaffInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class StaffAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private String order;
	private String sort;
	public JSONObject result;
	public JSONArray list;
	
	private int Id;
	private String Gh;
	private String Name;
	private String Sex;
	private int Sysuser;
	private String Password;
	private String Phone;
	private String Email;
	private String Des;
	private String Unuseflag;
	private String Bm;
	private String Zw;
	
	private String ygid;
	private String mkid;
	private String zzid;
	
	//传感器信息
	private String type;
	
	private String username="";
	private String userpass="";
	
	private String qxyggh="";  //初始化员工工号
	private String qxid="";  //权限ID
	
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

	public JSONArray getList() {
        return list;
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
	public String getGh() {
		return Gh;
	}
	public void setGh(String gh) {
		Gh = gh;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public int getSysuser() {
		return Sysuser;
	}
	public void setSysuser(int sysuser) {
		Sysuser = sysuser;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
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
	public String getBm() {
		return Bm;
	}
	public void setBm(String bm) {
		Bm = bm;
	}
	public String getZw() {
		return Zw;
	}
	public void setZw(String zw) {
		Zw = zw;
	}

	public String getYgid() {
        return ygid;
    }
    public void setYgid(String ygid) {
        this.ygid = ygid;
    }

    public String getMkid() {
        return mkid;
    }
    public void setMkid(String mkid) {
        this.mkid = mkid;
    }

    public String getZzid() {
        return zzid;
    }
    public void setZzid(String zzid) {
        this.zzid = zzid;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getQxyggh() {
        return qxyggh;
    }

    public void setQxyggh(String qxyggh) {
        this.qxyggh = qxyggh;
    }

    public String getQxid() {
        return qxid;
    }

    public void setQxid(String qxid) {
        this.qxid = qxid;
    }

    public String getStaffInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<StaffInfo> all = null;
		StaffDao conn=new StaffDao();
		try {
			if(this.getName() != null && !this.getName().equals("")){
			}
			else
				this.setName("");
			if(this.getGh() != null && !this.getGh().equals("")){
			}
			else
				this.setGh("");
			all=conn.getSearchInfo(this.getPage(),this.getRows(),this.getOrder(),this.getGh(),this.getName());
			int allrows=conn.getSearchRows(this.getGh(),this.getName());
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
	
	public String getStaffName() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<StaffInfo> all = null;
		StaffDao conn=new StaffDao();
		try {
			all=conn.getAllInfo();
			int allrows=conn.getAllRows();
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
	
	public String delStaffInfo() throws Exception {
		StaffDao conn=new StaffDao();
		Map<String,Object> map=new HashMap<String, Object>();
		 	try {
		 		conn.deleteById(this.getId());
				map.put("success", true);
				map.put("msg", "删除成功");
			} catch (Exception e) {
				map.put("success", false);
				map.put("msg", "删除失败");
				e.printStackTrace();
			}finally{
				conn.closeConn();
			}
		 	
		 	this.setResult(JSONObject.fromObject(map));
		 	
		return SUCCESS;
	}
	
	//检测员工是否存在
	public String checkInfo(String name) throws Exception{
		List<StaffInfo> all = null;
		StaffDao conn=new StaffDao();
		try {
			all=conn.getAllInfo();			
			JSONArray array = JSONArray.fromObject(all);
			for(int i=0;i<array.size();i++){
				JSONObject jo = (JSONObject) array.get(i);
				if(name.equals(jo.get("gh"))){
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
	
	//添加员工
	public String addStaffInfo() throws Exception {
		StaffDao conn=new StaffDao();
		StaffInfo info=new StaffInfo();
		info.setGh(this.getGh());
		info.setName(this.getName());
		info.setSex(this.getSex());
		info.setSysuser(this.getSysuser());
		info.setPassword(this.getPassword());
		info.setPhone(this.getPhone());
		info.setEmail(this.getEmail());
		info.setDes(this.getDes());
		info.setUnuseflag(this.getUnuseflag());
	 	Map<String,Object> map=new HashMap<String, Object>();
	 	try {
	 		if(!this.checkInfo(this.getGh()).equals("fail")){
		 		conn.add(info);
				map.put("success", true);
				map.put("msg", "添加成功");
	 		}else{
				map.put("success", false);
				map.put("msg", "该信息已存在");
	 		}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "添加失败");
			e.printStackTrace();
		} finally {
			conn.closeConn();
		}
	 	
	 	this.setResult(JSONObject.fromObject(map));

		return SUCCESS;
	}
	
	//修改员工
	public String editStaffInfo() throws Exception {
		StaffDao conn=new StaffDao();
		StaffInfo info=new StaffInfo();
		info.setId(this.getId());
		info.setGh(this.getGh());
		info.setName(this.getName());
		info.setSex(this.getSex());
		info.setSysuser(this.getSysuser());
		info.setPassword(this.getPassword());
		info.setPhone(this.getPhone());
		info.setEmail(this.getEmail());
		info.setDes(this.getDes());
		info.setUnuseflag(this.getUnuseflag());
	 	Map<String,Object> map=new HashMap<String, Object>();
	 	try {
	 		conn.update(info);
			map.put("success", true);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "添加失败");
			e.printStackTrace();
		}finally{
			conn.closeConn();
		}
	 	this.setResult(JSONObject.fromObject(map));

		return SUCCESS;
	}
	
	//保存员工权限
	public String saveYGQX() throws Exception {
	    StaffDao conn=new StaffDao();
	    ArrayList<String> list=new ArrayList<String>();
	    list.add(ygid.substring(0, ygid.length()-1));
	    list.add(mkid);
	    list.add(zzid);
	    
	    Map<String,Object> map=new HashMap<String, Object>();
        try {
            conn.saveYGQX(list);
            map.put("success", true);
            map.put("msg", "保存权限成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "保存权限失败");
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));
	    
	    return SUCCESS;
	}
	
	//获取员工权限
	public String geYGQX() throws Exception {
	    StaffDao conn=new StaffDao();
        ArrayList<String> list=new ArrayList<String>();
        list.add(ygid.substring(0, ygid.length()-1));
        list.add(mkid);
        
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            String qx=conn.getYGQX(list);
            if(qx.equals("")) {
                map.put("success", false);
                map.put("msg", "没有找到权限");
            } else {
                map.put("success", true);
                map.put("msg", qx);
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "获权限失败");
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));
	    
	    return SUCCESS;
	}
	
	//获取员工权限
    public String getZCQX() throws Exception {
        StaffDao conn=new StaffDao();
        ArrayList<String> list=new ArrayList<String>();
        list.add((Integer)ActionContext.getContext().getSession().get("userid")+"");
        list.add(qxid);
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            String qx=conn.getYGQX(list);
            int sysuser=(Integer)ActionContext.getContext().getSession().get("sysuser");
            if(sysuser == 1) qx = "1";  //如果是管理员，不需要进行权限判断
            if(qx.equals("1")) {
                map.put("success", true);
                map.put("msg", qx);
            } else {
                map.put("success", false);
                map.put("msg", "");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "获权限失败");
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));
        
        return SUCCESS;
    }
	
	//获取所有传感器信息
	public String getAllCGQMsg() throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        List<CGQInfo> all = null;
        StaffDao conn=new StaffDao();
        try {
            all=conn.getAllCGQMsg(this.getPage(),this.getRows());
            int allrows=conn.getAllCGQRows();
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
	public String GetCGQList() throws Exception {
        List<CGQInfo> all = null;
        StaffDao conn=new StaffDao();
        try {
            all=conn.GetCGQList(this.getType());
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
	
	//添加传感器信息
	public String addCGQMsg() throws Exception {
        StaffDao conn=new StaffDao();
        CGQInfo info=new CGQInfo();
        info.setName(this.getName());
        info.setType(this.getType());
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            conn.addCGQMsg(info);
            map.put("success", true);
            map.put("msg", "添加成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "添加失败");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));

        return SUCCESS;
    }
	
	//修改传感器信息
	public String editCGQMsg() throws Exception {
        StaffDao conn=new StaffDao();
        CGQInfo info=new CGQInfo();
        info.setId(this.getId());
        info.setName(this.getName());
        info.setType(this.getType());
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            conn.editCGQMsg(info);
            map.put("success", true);
            map.put("msg", "修改成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "修改失败");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));

        return SUCCESS;
    }
	
	//删除传感器信息
	public String delCGQMsg() throws Exception {
        StaffDao conn=new StaffDao();
        Map<String,Object> map=new HashMap<String, Object>();
        
        try {
            conn.delCGQMsg(this.getId());
            map.put("success", true);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "删除失败");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
            
        this.setResult(JSONObject.fromObject(map));
            
        return SUCCESS;
    }
	
	//获取所有传感器信息
	public String getAllCGQList() throws Exception {
        List<CGQInfo> all = null;
        StaffDao conn=new StaffDao();
        try {
            all=conn.getAllCGQList(this.getType());
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
	
	//登录
	public String makeLogin() throws Exception {
        StaffDao conn=new StaffDao();
        Map<String,Object> map=new HashMap<String, Object>();
        
        try {
            int id=conn.makeLogin(username, userpass);
            if(id > 0)  {
                ActionContext.getContext().getSession().put("userid", id);
                ActionContext.getContext().getSession().put("loginname", username);
                
                map.put("success", true);
                map.put("msg", "success");
            } else {
                map.put("success", false);
                map.put("msg", "failed");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "failed");
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
            
        this.setResult(JSONObject.fromObject(map));
            
        return SUCCESS;
    }
	
	//初始化员工权限
	public String initYGQX() throws Exception {
        StaffDao conn=new StaffDao();
        Map<String,Object> map=new HashMap<String, Object>();
        
        try {
            conn.initYGQX(this.qxyggh);
            map.put("success", true);
            map.put("msg", "初始化权限成功！");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "初始化权限失败！");
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
            
        this.setResult(JSONObject.fromObject(map));
            
        return SUCCESS;
    }
	
}
