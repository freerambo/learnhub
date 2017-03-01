package com.erian.spring.boot.grid.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * response of status
 */
public class Status {

	public String status;
	public String tst;

	public Status() {
		super();
	}

	public Status(String status) {
		super();
		this.status = status;
	}
	

	public Status(String tst, String status) {
		super();
		this.status = status;
		this.tst = tst;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
