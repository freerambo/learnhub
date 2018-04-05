package org.erian.examples.bootapi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.erian.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
//@Table(name="modbus_rtu")
public class ModbusRTU extends IdEntity implements Serializable {
	public static final long serialVersionUID = 1L;
	
	public Integer deviceId;
	
	public String address;
	
	public Integer baudrate;
	
	public Integer databit;
	public Integer stopbit;
	
	public String parity;
	
	public String encoding;
	
	public String description;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date createdOn = Clock.DEFAULT.getCurrentDate();
	
	public String createdBy;
	
	public String updatedBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date updatedOn;

	public ModbusRTU() {
	}
}