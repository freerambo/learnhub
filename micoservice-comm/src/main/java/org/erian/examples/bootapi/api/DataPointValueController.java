package org.erian.examples.bootapi.api;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.service.*;
import org.erian.modules.constants.MediaTypes;
import org.javasimon.aop.Monitored;
@CrossOrigin
@RestController
public class DataPointValueController {

	
	public static final String PROJECT_PATH = "/api/dpv/";
	
	private static Logger logger = LoggerFactory.getLogger(DataPointValueController.class);

	@Autowired
	private DataPointValueService dpvService;
	
	@RequestMapping(value = "/api/dpvs",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public List<DataPointValue> listAllDataPointValue() {
		List<DataPointValue> dpv = dpvService.findAll();
		return dpv;
	}

	@RequestMapping(value = "/api/dpvs/{id}",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public List<DataPointValue> listOneDpvs(@PathVariable("id") Integer id) {
		return dpvService.findDpValues(id);
	}
	
	
	@RequestMapping(value = "/api/dpv/{id}", method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public DataPointValue listOneDataPointValue(@PathVariable("id") Integer id) {
		DataPointValue dpv = dpvService.findOne(id);
		return dpv;
	}

	
//	Content-Type: application/json;charset=UTF-8
	@RequestMapping(value = "/api/dpv", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
	@Monitored
	public DataPointValue createDataPointValue(@RequestBody DataPointValue dpv, UriComponentsBuilder uriBuilder) {
		
		dpv = dpvService.saveDataPointValue(dpv);
			
		return dpv;
	}
	
//	@RequestMapping(value = "/api/dpv/{id}", method = RequestMethod.DELETE)
//	@Monitored
	public void deleteDataPointValue(@NotNull @PathVariable("id") Integer id) {
		dpvService.deleteDataPointValue(id);
	}

//	@RequestMapping(value = "/api/dpv", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
//	@Monitored
	public void modifyDataPointValue(@RequestBody DataPointValue dpv,
			@RequestParam(value = "token", required = false) String token) {
		dpvService.modifyDataPointValue(dpv);
	}
}
