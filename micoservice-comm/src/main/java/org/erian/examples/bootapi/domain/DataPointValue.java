package org.erian.examples.bootapi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.erian.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the DataPointValue database table.
 * 
 */
@Entity
@Table(name="DataPointValue")
@NamedQuery(name="DataPointValue.findAll", query="SELECT d FROM DataPointValue d")
public class DataPointValue extends IdEntity implements Serializable {
	public static final long serialVersionUID = 1L;


	public Integer dataPointId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date timestamp = Clock.DEFAULT.getCurrentDate();

	public String value;

	public DataPointValue() {
	}

	public DataPointValue(Integer dpId, String val) {
		this.dataPointId = dpId;
		this.value = val;
	}
	

}