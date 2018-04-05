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
public class EthernetIpController {

	
	public static final String PROJECT_PATH = "/api/ip/";
	
	private static Logger logger = LoggerFactory.getLogger(EthernetIpController.class);

	@Autowired
	private EthernetIpService ipService;
	
	@RequestMapping(value = "/api/ips",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public List<EthernetIP> listAllEthernetIP() {
		List<EthernetIP> ips = ipService.findAll();
		return ips;
	}

	@RequestMapping(value = "/api/ips/{id}", method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public EthernetIP listOneEthernetIP(@PathVariable("id") Integer id) {
		EthernetIP ip = ipService.findOne(id);
		return ip;
	}

//	Content-Type: application/json;charset=UTF-8
	@RequestMapping(value = "/api/ips", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
	@Monitored
	public EthernetIP createEthernetIP(@RequestBody EthernetIP ip, UriComponentsBuilder uriBuilder) {
		
		ip = ipService.saveEthernetIP(ip);
			
		return ip;
	}
	
	@RequestMapping(value = "/api/ips/{id}", method = RequestMethod.DELETE)
	@Monitored
	public void deleteEthernetIP(@NotNull @PathVariable("id") Integer id) {
		ipService.deleteEthernetIP(id);
	}

//	@RequestMapping(value = "/api/ips", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
//	@Monitored
	public void modifyEthernetIP(@RequestBody EthernetIP ip,
			@RequestParam(value = "token", required = false) String token) {
		ipService.modifyEthernetIP(ip);
	}
}
