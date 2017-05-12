package com.model.dbjc;

public class SLRTDataDetails {
	
	private String name = "";
	private double lsyll = 0.0;
	private double ysst = 0.0;  
	private double plms = 0.0;
	private double wd = 0.0;
	private int mcubh = 0;
	private int mcutd = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLsyll() {
        return lsyll;
    }

    public void setLsyll(double lsyll) {
        this.lsyll = lsyll;
    }

    public double getYsst() {
        return ysst;
    }

    public void setYsst(double ysst) {
        this.ysst = ysst;
    }

    public double getPlms() {
		return plms;
	}

	public void setPlms(double plms) {
		this.plms = plms;
	}

	public double getWd() {
		return wd;
	}

	public void setWd(double wd) {
		this.wd = wd;
	}


	public int getMcutd() {
		return mcutd;
	}

	public void setMcutd(int mcutd) {
		this.mcutd = mcutd;
	}

	public int getMcubh() {
		return mcubh;
	}

	public void setMcubh(int mcubh) {
		this.mcubh = mcubh;
	}	
	
}
