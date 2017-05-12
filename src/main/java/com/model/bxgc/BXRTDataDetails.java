package com.model.bxgc;

public class BXRTDataDetails {
	
	private String name = "";
	private double hoffset = 0.0;  //横向偏差
	private double voffset = 0.0;  //纵向偏差
	private double zoffset = 0.0;  //高差

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public double getHoffset() {
        return hoffset;
    }

    public void setHoffset(double hoffset) {
        this.hoffset = hoffset;
    }

    public double getVoffset() {
        return voffset;
    }

    public void setVoffset(double voffset) {
        this.voffset = voffset;
    }

    public double getZoffset() {
        return zoffset;
    }

    public void setZoffset(double zoffset) {
        this.zoffset = zoffset;
    }

}
