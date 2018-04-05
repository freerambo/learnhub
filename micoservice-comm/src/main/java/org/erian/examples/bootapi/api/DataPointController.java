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
public class DataPointController {

	
	public static final String PROJECT_PATH = "/api/dataPoints/";
	
	private static Logger logger = LoggerFactory.getLogger(DataPointController.class);

	@Autowired
	private DataPointService dpService;
	
	@RequestMapping(value = "/api/dataPoints",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public List<DataPoint> listAllDataPoint() {
		List<DataPoint> dataPoints = dpService.findAll();
		return dataPoints;
	}
	
	@RequestMapping(value = "/api/{deviceId}/dataPoints",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public List<DataPoint> listDataPointsByDevice(@NotNull @PathVariable("deviceId") Integer deviceId) {
		List<DataPoint> dataPoints = dpService.findByDevice(deviceId);
		return dataPoints;
	}
	
	@RequestMapping(value = "/api/project/{projectId}/dataPoints",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public List<DataPoint> listDataPointsByProject(@NotNull @PathVariable("projectId") Integer projectId) {
		List<DataPoint> dataPoints = dpService.findByProject(projectId);
		return dataPoints;
	}

	@RequestMapping(value = "/api/dataPoints/{id}", method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public DataPoint listOneDataPoint(@PathVariable("id") Integer id) {
		DataPoint dataPoint = dpService.findOne(id);
		return dataPoint;
	}

	@RequestMapping(value = "/api/dp/read/{dpId}", method=RequestMethod.GET)
	@Monitored
	public String readTagById(@PathVariable("dpId") Integer tagId) {
		
		return dpService.readDataPoint(tagId);
		
	}
	
	@RequestMapping(value = "/api/dp/set/{dpId}", method=RequestMethod.POST)
	@Monitored
	public String setTagById(@NotNull @PathVariable("dpId") Integer tagId, 
			@RequestParam(value = "command", required = false, defaultValue = "LOAD ON") String command) {
		
		return dpService.writeIpDataPoint(tagId, command+"\n");
	}
	
//	Content-Type: application/json;charset=UTF-8
	@RequestMapping(value = "/api/dataPoints", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
	@Monitored
	public DataPoint createDataPoint(@RequestBody DataPoint dataPoint, UriComponentsBuilder uriBuilder) {
		
		dataPoint = dpService.saveDataPoint(dataPoint);
			
		return dataPoint;
	}
	
	@RequestMapping(value = "/api/dataPoints/{id}", method = RequestMethod.DELETE)
	@Monitored
	public void deleteDataPoint(@NotNull @PathVariable("id") Integer id) {
		dpService.deleteDataPoint(id);
	}
	
	
	@RequestMapping(value = "/api/{deviceId}/dataPoints", method = RequestMethod.DELETE)
	@Monitored
	public void deleteDataPointsByDevice(@NotNull @PathVariable("deviceId") Integer deviceId) {
		dpService.deleteByDevice(deviceId);
	}

//	@RequestMapping(value = "/api/dataPoints", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
//	@Monitored
	public void modifyDataPoint(@RequestBody DataPoint dataPoint,
			@RequestParam(value = "token", required = false) String token) {
		
		dpService.modifyDataPoint(dataPoint);
	}
}
