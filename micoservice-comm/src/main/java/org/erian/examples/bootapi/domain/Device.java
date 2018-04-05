package org.erian.examples.bootapi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.erian.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Device extends IdEntity implements Serializable {
	public static final long serialVersionUID = 1L;

	public String name;
	
	public String type;
	
	public String description;
	
	public String protocol;
	
	public String path;
	
	public Integer address;
	
	public String freq;
	
	public Integer projectId;

	public String createdBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date createdOn  = Clock.DEFAULT.getCurrentDate();

	public String updatedBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date updatedOn;

	public String status;
	
	public Device() {
	}
	
	public Device(Integer id) {
		this.id = id;
	}
}