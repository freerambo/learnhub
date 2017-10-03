package org.rambo.spring.boot.project.service.exception;

public enum ErrorCode {

	BAD_REQUEST(400, 400), 
	UNAUTHORIZED(401, 401), 
	FORBIDDEN(403, 403), 
	INTERNAL_SERVER_ERROR(500, 500);

	public int code;
	public int httpStatus;

	ErrorCode(int code, int httpStatus) {
		this.code = code;
		this.httpStatus = httpStatus;
	}

}
