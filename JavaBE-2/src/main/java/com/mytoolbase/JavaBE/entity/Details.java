package com.mytoolbase.JavaBE.entity;

import java.util.ArrayList;

public class Details {
	
	private Integer lag;
	private String system;
	private ArrayList<Float> asks;
	private ArrayList<Float> bids;
	public Integer getLag() {
		return lag;
	}
	public void setLag(Integer lag) {
		this.lag = lag;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public ArrayList<Float> getAsks() {
		return asks;
	}
	public void setAsks(ArrayList<Float> asks) {
		this.asks = asks;
	}
	public ArrayList<Float> getBids() {
		return bids;
	}
	public void setBids(ArrayList<Float> bids) {
		this.bids = bids;
	}
	
	

}
