package com.model;

/**
 * 水雨情数据记录
 * @author Administrator
 *
 */
public class WaterregimeInfo {
	
	private String id;          //ID号
	private double level;       //水位
	private double capacity;    //库容量
	private double rainfall_s;  //小时降雨量
	private double rainfall_d;  //日降雨量
	private double evaporation; //蒸发量
	private double level_fluid; //液位
	private double rate;        //流量	
	private String id_mp;       //所属测点ID
	private String name_mp;     //所属测点名称
	private String id_reservoir;//所属水库ID
	private String name_res;    //所属水库ID
	private String time;        //采集时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getLevel() {
		return level;
	}
	public void setLevel(double level) {
		this.level = level;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public double getRainfall_s() {
		return rainfall_s;
	}
	public void setRainfall_s(double rainfall_s) {
		this.rainfall_s = rainfall_s;
	}
	public double getRainfall_d() {
		return rainfall_d;
	}
	public void setRainfall_d(double rainfall_d) {
		this.rainfall_d = rainfall_d;
	}
	public double getEvaporation() {
		return evaporation;
	}
	public void setEvaporation(double evaporation) {
		this.evaporation = evaporation;
	}
	public double getLevel_fluid() {
		return level_fluid;
	}
	public void setLevel_fluid(double level_fluid) {
		this.level_fluid = level_fluid;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getId_mp() {
		return id_mp;
	}
	public void setId_mp(String id_mp) {
		this.id_mp = id_mp;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getName_mp() {
		return name_mp;
	}
	public void setName_mp(String name_mp) {
		this.name_mp = name_mp;
	}
	public String getId_reservoir() {
		return id_reservoir;
	}
	public void setId_reservoir(String id_reservoir) {
		this.id_reservoir = id_reservoir;
	}
	public String getName_res() {
		return name_res;
	}
	public void setName_res(String name_res) {
		this.name_res = name_res;
	}
}
