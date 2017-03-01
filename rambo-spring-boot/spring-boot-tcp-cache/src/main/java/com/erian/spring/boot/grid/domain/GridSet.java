package com.erian.spring.boot.grid.domain;


import javax.validation.constraints.NotNull;



public class GridSet{
	@NotNull
	public Double frequency = 50.0;
	@NotNull
	public Double voltage = 115.0;
	@NotNull
	public Double voltageLimit = 150.0;
	@NotNull
	public Double powerProtection = 10000.0;
	@NotNull
	public Double currentLimit = 20.0;
	
	public String deviceStatus = null;
	
	public GridSet() {
		super();
	}

	public GridSet(Double frequency, Double voltage, Double voltageLimit, Double powerProtection, Double currentLimit) {
		super();
		this.frequency = frequency;
		this.voltage = voltage;
		this.voltageLimit = voltageLimit;
		this.powerProtection = powerProtection;
		this.currentLimit = currentLimit;
	}
	
}