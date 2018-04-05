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
public class ModbusTcpController {

	
	public static final String PROJECT_PATH = "/api/tcp/";
	
	private static Logger logger = LoggerFactory.getLogger(ModbusTcpController.class);

	@Autowired
	private ModbusTcpService tcpService;
	
	@RequestMapping(value = "/api/tcps",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public List<ModbusTCP> listAllModbusTCP() {
		List<ModbusTCP> tcps = tcpService.findAll();
		return tcps;
	}

	@RequestMapping(value = "/api/tcps/{id}", method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public ModbusTCP listOneModbusTCP(@PathVariable("id") Integer id) {
		ModbusTCP tcp = tcpService.findOne(id);
		return tcp;
	}

//	Content-Type: application/json;charset=UTF-8
	@RequestMapping(value = "/api/tcps", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
	@Monitored
	public ModbusTCP createModbusTCP(@RequestBody ModbusTCP tcp, UriComponentsBuilder uriBuilder) {
		
		tcp = tcpService.saveModbusTCP(tcp);
			
		return tcp;
	}
	
	@RequestMapping(value = "/api/tcps/{id}", method = RequestMethod.DELETE)
	@Monitored
	public void deleteModbusTCP(@NotNull @PathVariable("id") Integer id) {
		tcpService.deleteModbusTCP(id);
	}

//	@RequestMapping(value = "/api/tcps", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
//	@Monitored
	public void modifyModbusTCP(@RequestBody ModbusTCP tcp,
			@RequestParam(value = "token", required = false) String token) {
		tcpService.modifyModbusTCP(tcp);
	}
}
