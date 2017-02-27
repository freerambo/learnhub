package com.erian.spring.boot.grid.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;


/**    
 *meas:volt:ac?;:meas:curr:ac?;:meas:pow:ac?;:meas:freq?;outp?
 */
public class GridData extends IdEntity {

	
	@NotNull
	public Double voltage;
	@NotNull
	public Double current;
	@NotNull
	public Double realPower;
	@NotNull
	public Double frequency;
	@NotNull
	public String deviceStatus;
	
	public String deviceId = "gridsimulator";

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date timestamp;
	
	
	
	public GridData() {
		super();
	}
	
	
	public GridData(Double voltage, Double current, Double realPower, Double frequency, String deviceStatus,
			String deviceId) {
		super();
		this.voltage = voltage;
		this.current = current;
		this.realPower = realPower;
		this.frequency = frequency;
		this.deviceStatus = deviceStatus;
		this.deviceId = deviceId;
		timestamp = new Date();
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


}
