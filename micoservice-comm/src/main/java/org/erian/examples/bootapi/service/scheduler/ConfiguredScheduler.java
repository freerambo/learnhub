package org.erian.examples.bootapi.service.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.map.HashedMap;
import org.erian.examples.bootapi.domain.DataPoint;
import org.erian.examples.bootapi.domain.DataPointValue;
import org.erian.examples.bootapi.domain.Project;
import org.erian.examples.bootapi.service.exception.ErrorCode;
import org.erian.examples.bootapi.service.exception.ServiceException;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


/**
 * function description：Scheduler to store the data point values regularly according to 
 * user configuration in the DataPoint interval setting
 *
 * @author <a href="mailto:chenwoon.thong@ntu.edu.sg">JT  </a>
 * @version v 1.0 
 * Create:  18 Sep 2017 11:30:28 pm
 */

//@Component
public class ConfiguredScheduler implements Job {
	//private static String apiUrl = "http://localhost:8080/api/dp/read/";
    //private static final String postUrl = "http://localhost:8080/api/dpv";
    private static String dpUrl = "http://172.21.76.225:8080/api/dp/read/";
    private static final String dpvUrl = "http://172.21.76.225:8080/api/dpv";
    private static final String dpsUrl = "http://172.21.76.225:8080/api/dataPoints";
    private static final String projectsUrl = "http://172.21.76.225:8080/api/projects/";
    
    private Scheduler scheduler;
    private Map jobTrigger = new HashedMap();
        
    private final static String PROJECTID = "1001";
    //private final static String PROJECTID = "1111";
    private final static String SEC05 = "SEC05";
    private final static String MIN01 = "MIN01";
    private final static String CRONSEC05 = "0/5 * * * * ?";
    private final static String CRONMIN01 = "0 0/1 * * * ?";
    
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		List<DataPoint> dps = (List<DataPoint>) dataMap.get("DataPoint");

    	Iterator<DataPoint> dpsIterator = dps.iterator();
    	while (dpsIterator.hasNext()) {
    		DataPoint dp = dpsIterator.next();
    		String postUrl = dpUrl + dp.id.toString();
    		RestTemplate restTemplate = new RestTemplate();
    		
    		try {
    			ResponseEntity<String> response = restTemplate.getForEntity(postUrl, String.class);
    			String value = response.getBody();
    			DataPointValue dpv = new DataPointValue();
    			dpv.dataPointId = dp.id;
    			dpv.value = value;
    			dpv.timestamp = new Date();
    			RestTemplate restPostTemplate = new RestTemplate();
    			ResponseEntity<String> postResp = restPostTemplate.postForEntity(dpvUrl, dpv, String.class);
    			//System.out.println(postResp);
    		} catch (RestClientException e) {
    			e.printStackTrace();
    		}
    		
    	}
    	
	} 
	
	@PostConstruct
	public void initialize() {
		setupJobTrigger();
		loadSchedulers();
	}
	
	public void stopScheduler() {
		if (scheduler != null) {
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setupJobTrigger() {
		List<DataPoint> dataPointSec05 = new ArrayList<DataPoint>();
		List<DataPoint> dataPointMin01 = new ArrayList<DataPoint>();
		
		List<DataPoint> dps = getDataPointsForProject(PROJECTID);
		Iterator<DataPoint> dpsIterator = dps.iterator();
		while (dpsIterator.hasNext()) {
			DataPoint dp = dpsIterator.next();
			switch(dp.freq) {
				case SEC05:
					dataPointSec05.add(dp);
					break;
				case MIN01:
					dataPointMin01.add(dp);
					break;
			}
		}
		
		/* Only add to the map if there is dataPoint for this interval */
		if(dataPointSec05.size() > 0) {
			/* Scheduler Job for 5 seconds interval */
			JobDataMap mapSec05 = new JobDataMap();
			mapSec05.put("DataPoint", dataPointSec05);
			JobKey jobSchedulerSec05 = new JobKey(SEC05, "group1");
			JobDetail schedulerServiceSec05 = JobBuilder.newJob(ConfiguredScheduler.class)
		    		.withIdentity(jobSchedulerSec05)
		    		.usingJobData(mapSec05)
		    		.build();
			
			Trigger triggerSec05 = TriggerBuilder
	    			.newTrigger()
	    			.withIdentity(SEC05, "group1")
	    			.withSchedule(
	    					CronScheduleBuilder.cronSchedule(CRONSEC05))
	    			.build();
		
			jobTrigger.put(SEC05, new Object[] {schedulerServiceSec05, triggerSec05});
		}
		
		/* Only add to the map if there is dataPoint for this interval */
		if(dataPointMin01.size() > 0) {
			/* Scheduler Job for 1 min interval */
			JobDataMap mapMin01 = new JobDataMap();
			mapMin01.put("DataPoint", dataPointMin01);
			JobKey jobSchedulerMin01 = new JobKey(MIN01, "group1");
			JobDetail schedulerServiceMin01 = JobBuilder.newJob(ConfiguredScheduler.class)
		    		.withIdentity(jobSchedulerMin01)
		    		.usingJobData(mapMin01)
		    		.build();
			
			Trigger triggerMin01 = TriggerBuilder
	    			.newTrigger()
	    			.withIdentity(MIN01, "group1")
	    			.withSchedule(
	    					CronScheduleBuilder.cronSchedule(CRONMIN01))
	    			.build();
			
			jobTrigger.put(MIN01, new Object[] {schedulerServiceMin01, triggerMin01});
		}
	}
	
	private void loadSchedulers() {
		
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			for(Object key: jobTrigger.keySet()) {
				Object[] jobDetailsTrigger = (Object[]) jobTrigger.get(key);
				scheduler.scheduleJob((JobDetail)jobDetailsTrigger[0], (Trigger)jobDetailsTrigger[1]);
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/* Get the list of DataPoint in an array. This method does not need to be here */
	private List<DataPoint> fetchDataPointsFromAPI() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<DataPoint[]> response = restTemplate.getForEntity(
	            dpsUrl, 
	            DataPoint[].class
	        );
	    DataPoint[] dataPoints = response.getBody();
	    return Arrays.asList(dataPoints);
	}
	
	/* Get DataPoints for only one project */
	private List<DataPoint> getDataPointsForProject(String projectId) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<Project> respProject = restTemplate.getForEntity(
		            projectsUrl + projectId, 
		            Project.class
		        );
			System.out.println(respProject);
			if(!respProject.hasBody()) {
				System.out.println("The Project[" + projectId + "] is not exist");
				throw new ServiceException("The Project[" + projectId + "] is not exist", ErrorCode.BAD_REQUEST);
			}
		} catch (RestClientException e) {
			System.out.println("The Project[" + projectId + "] has error");
			throw new ServiceException("The Project[" + projectId + "] has error", ErrorCode.BAD_REQUEST);
		}
		
		List<DataPoint> dpForProject = new ArrayList<DataPoint>();
	    
	    List<DataPoint> dps = fetchDataPointsFromAPI();
	    Iterator<DataPoint> dpsIterator = dps.iterator();
    	while (dpsIterator.hasNext()) {
    		DataPoint dp = dpsIterator.next();
    		/* Find out the dataPoints for theprojectId */
    		if(dp.device.projectId == Integer.parseInt(projectId)) {
    			dpForProject.add(dp);
    		}
    	}
	    return dpForProject;
	}
	
}
