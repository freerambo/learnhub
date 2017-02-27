package com.erian.spring.boot.grid.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/*import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;*/

/**
 * 
 * Defined a unified JPA parent class 
 * universal id name, type, mapping column and generation strategy (auto incresement)
 * 
 * it may need an seperate sequence for oracle ID
 * @author rambo
 */
// JPA identifier 
//@MappedSuperclass
public abstract class IdEntity {

	protected  Long id;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
