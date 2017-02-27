package com.erian.spring.boot.grid.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Base response
 *
 * @author Yuanbo
 */
public class BaseResult<T>{

	private String code;
	private String message;
	private T data;
	
	
	public BaseResult() {
	}

	public BaseResult(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public BaseResult(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
