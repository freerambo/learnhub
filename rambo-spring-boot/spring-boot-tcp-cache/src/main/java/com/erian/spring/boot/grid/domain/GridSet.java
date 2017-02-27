package com.erian.spring.boot.grid.domain;


import javax.validation.constraints.NotNull;



public class GridSet{
	@NotNull
	public Double frequency;
	@NotNull
	public Double voltage;
	@NotNull
	public Double voltageLimit;
	@NotNull
	public Double powerLimit;
	@NotNull
	public Double currentLimit;

	public GridSet() {
		super();
	}

	public GridSet(Double frequency, Double voltage, Double voltageLimit, Double powerLimit, Double currentLimit) {
		super();
		this.frequency = frequency;
		this.voltage = voltage;
		this.voltageLimit = voltageLimit;
		this.powerLimit = powerLimit;
		this.currentLimit = currentLimit;
	}
	
}