package org.erian.examples.bootapi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.erian.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class DataPoint extends IdEntity implements Serializable {
	public static final long serialVersionUID = 1L;

	
	public String name;
	
	public String type;
	
	public String description;
	
	public String path;
	
	public Integer address;
	
	public Integer length;
	
	public String freq;
	
	public String inputExpression;
	
	public String outputExpression;
	
/*	@Column(name="DeviceID")
	public Integer deviceId;*/
	
/*	@Column(name="Readable")
	public Integer readable;
	
	@Column(name="Writable")
	public Integer writable;*/
	
	public boolean readOnly;
	
	public boolean writeOnly;
	
	public String unit;
	
	public String setValue;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date createdOn = Clock.DEFAULT.getCurrentDate();

	public String createdBy;

	public String updatedBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date updatedOn;

	@ManyToOne
	@JoinColumn(name = "deviceId")
	public Device device;
	
	public DataPoint() {
	}
}