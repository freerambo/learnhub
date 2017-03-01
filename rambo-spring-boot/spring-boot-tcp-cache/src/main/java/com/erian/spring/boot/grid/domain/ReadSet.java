package com.erian.spring.boot.grid.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReadSet extends IdEntity{
	@NotNull
	public Double frequency;
	@NotNull
	public Double voltage;
	@NotNull
	public Double voltageLimit;
	@NotNull
	public Double powerProtection;
	@NotNull
	public Double currentLimit;
	
	public String deviceStatus;
	public String deviceId = "gridsimulator";
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date timestamp;
	
	public ReadSet() {
		super();
	}
	
	public ReadSet(Double frequency, Double voltage, Double voltageLimit, Double powerProtection, Double currentLimit,
			String deviceStatus, String deviceId) {
		super();
		this.frequency = frequency;
		this.voltage = voltage;
		this.voltageLimit = voltageLimit;
		this.powerProtection = powerProtection;
		this.currentLimit = currentLimit;
		this.deviceStatus = deviceStatus;
		this.deviceId = deviceId;
		timestamp = new Date();
	}

	
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}