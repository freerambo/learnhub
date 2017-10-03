package org.rambo.spring.boot.project.api.support;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.rambo.spring.boot.project.common.constants.MediaTypes;
import org.rambo.spring.boot.project.common.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * over ride default BasicErrorController of Spring boot, add the error log and 
 * an unified error handler.
 * 
 * @author rambo
 */
@Controller
public class ErrorPageController implements ErrorController {

	private static Logger logger = LoggerFactory.getLogger(ErrorPageController.class);

	@Value("${error.path:/error}")
	private String errorPath;

	private JsonMapper jsonMapper = new JsonMapper();

	private ErrorAttributes errorAttributes = new DefaultErrorAttributes();

	@RequestMapping(value = "${error.path:/error}",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ErrorResult handle(HttpServletRequest request) {
		Map<String, Object> attributes = getErrorAttributes(request);

		ErrorResult result = new ErrorResult();
		result.code = (int) attributes.get("status");
		result.message = (String) attributes.get("error");

		logError(attributes, request);

		return result;
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getErrorAttributes(requestAttributes, false);
	}

	private void logError(Map<String, Object> attributes, HttpServletRequest request) {
		attributes.put("from", request.getRemoteAddr());
		logger.error(jsonMapper.toJson(attributes));
	}

	@Override
	public String getErrorPath() {
		return this.errorPath;
	}
}
