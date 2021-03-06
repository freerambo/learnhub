package org.rambo.spring.boot.project.api.support;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
//import org.apache.shiro.authz.UnauthenticatedException;
//import org.apache.shiro.authz.UnauthorizedException;
import org.rambo.spring.boot.project.common.constants.MediaTypes;
import org.rambo.spring.boot.project.common.utils.IPUtils;
import org.rambo.spring.boot.project.common.utils.JsonMapper;
import org.rambo.spring.boot.project.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.collect.Maps;

@ControllerAdvice(annotations = { RestController.class })
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	private JsonMapper jsonMapper = new JsonMapper();

	@ExceptionHandler(value = { ServiceException.class})
	public final ResponseEntity<ErrorResult> handleServiceException(ServiceException ex, HttpServletRequest request) {
		// 注入servletRequest，用于出错时打印请求URL与来源地址
		logError(ex, request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
		ErrorResult result = new ErrorResult(ex.errorCode.code, ex.getMessage());
		return new ResponseEntity<ErrorResult>(result, headers, HttpStatus.valueOf(ex.errorCode.httpStatus));
	}
	
/*	@ExceptionHandler(value = {UnauthenticatedException.class})
	public final ResponseEntity<ErrorResult> handleUnAuthenticException(UnauthenticatedException ex, HttpServletRequest request) {
		return handleShiroException(ex, request);
	}

	@ExceptionHandler(value = {UnauthorizedException.class })
	public final ResponseEntity<ErrorResult> handleUnAuthorizeException(UnauthorizedException ex, HttpServletRequest request) {
		return handleShiroException(ex, request);
	}*/
	
	/**
	 * 
	 * @function: shiro exception handler
	 * @param ex
	 * @param request
	 * @return
	 * @author: Rambo Zhu     8 Mar 2017 5:21:38 pm
	 */
	public ResponseEntity<ErrorResult> handleShiroException(ShiroException ex, HttpServletRequest request) {
		// 注入servletRequest，用于出错时打印请求URL与来源地址
		logError(ex, request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
		ErrorResult result = new ErrorResult(401, ex.getMessage());
		return new ResponseEntity<ErrorResult>(result, headers, HttpStatus.valueOf(401));
	}
	
	@ExceptionHandler(value = { Exception.class })
	public final ResponseEntity<ErrorResult> handleGeneralException(Exception ex, HttpServletRequest request) {
		logError(ex, request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
		ErrorResult result = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		return new ResponseEntity<ErrorResult>(result, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 重载ResponseEntityExceptionHandler的方法，加入日志
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		logError(ex);

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
		}

		return new ResponseEntity<Object>(body, headers, status);
	}

	public void logError(Exception ex) {
		Map<String, String> map = Maps.newHashMap();
		map.put("message", ex.getMessage());
		logger.error(jsonMapper.toJson(map), ex);
	}

	public void logError(Exception ex, HttpServletRequest request) {
		Map<String, String> map = Maps.newHashMap();
		map.put("message", ex.getMessage());
		map.put("from", IPUtils.getClientAddress(request));
		String queryString = request.getQueryString();
		map.put("path", queryString != null ? (request.getRequestURI() + "?" + queryString) : request.getRequestURI());

		logger.error(jsonMapper.toJson(map), ex);
	}
}
