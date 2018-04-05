package org.erian.examples.bootapi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.erian.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the Project database table.
 * 
 */
@Entity
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
public class Project extends IdEntity implements Serializable {
	public static final long serialVersionUID = 1L;

	
	public String name;
	
	public String description;
	
	public String createdBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date createdOn = Clock.DEFAULT.getCurrentDate();

	public String logo;

	public String updatedBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")

	public Date updatedOn;

	public int userId;
	
	public Project() {
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}