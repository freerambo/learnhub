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
public class ModbusRtuController {

	
	public static final String PROJECT_PATH = "/api/rtu/";
	
	private static Logger logger = LoggerFactory.getLogger(ModbusRtuController.class);

	@Autowired
	private ModbusRtuService rtuService;
	
	@RequestMapping(value = "/api/rtus",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public List<ModbusRTU> listAllModbusRTU() {
		List<ModbusRTU> rtus = rtuService.findAll();
		return rtus;
	}

	@RequestMapping(value = "/api/rtus/{id}", method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public ModbusRTU listOneModbusRTU(@PathVariable("id") Integer id) {
		ModbusRTU rtu = rtuService.findOne(id);
		return rtu;
	}

//	Content-Type: application/json;charset=UTF-8
	@RequestMapping(value = "/api/rtus", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
	@Monitored
	public ModbusRTU createModbusRTU(@RequestBody ModbusRTU rtu, UriComponentsBuilder uriBuilder) {
		
		rtu = rtuService.saveModbusRTU(rtu);
			
		return rtu;
	}
	
	@RequestMapping(value = "/api/rtus/{id}", method = RequestMethod.DELETE)
	@Monitored
	public void deleteModbusRTU(@NotNull @PathVariable("id") Integer id) {
		rtuService.deleteModbusRTU(id);
	}

//	@RequestMapping(value = "/api/rtus", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
//	@Monitored
	public void modifyModbusRTU(@RequestBody ModbusRTU rtu,
			@RequestParam(value = "token", required = false) String token) {
		rtuService.modifyModbusRTU(rtu);
	}
}
