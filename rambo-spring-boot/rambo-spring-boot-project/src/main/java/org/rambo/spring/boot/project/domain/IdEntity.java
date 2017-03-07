package org.rambo.spring.boot.project.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;


/**
 * 
 * Defined a unified JPA parent class 
 * universal id name, type, mapping column and generation strategy (auto incresement)
 * 
 * it may need an seperate sequence for oracle ID
 * @author rambo
 */
// JPA identifier 
public abstract class IdEntity {

	protected  String id;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
