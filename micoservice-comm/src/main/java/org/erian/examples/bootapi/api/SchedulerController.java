/*
 * Copyright: Energy Research Institute @ NTU
 * microservice-comm
 * org.erian.examples.bootapi.api -> wre.java
 * Created on 10 Nov 2017-1:35:34 pm
 */
package org.erian.examples.bootapi.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.erian.examples.bootapi.api.support.ErrorResult;
import org.erian.examples.bootapi.config.ConfigureQuartz;
import org.erian.examples.bootapi.dto.ScheduleModel;
import org.erian.examples.bootapi.service.scheduler.DynamicJob;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class SchedulerController {
	
	@Autowired
	private SchedulerFactoryBean schedFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulerController.class);
	
	private static final Map<String,Long> freqMap = Maps.newConcurrentMap();
	static{
		freqMap.put("SEC01", 1000L);
		freqMap.put("SEC02", 2000L);
		freqMap.put("SEC03", 3000L);
		freqMap.put("SEC04", 4000L);
		freqMap.put("SEC05", 5000L);
		freqMap.put("MIN01", 60000L);
		freqMap.put("MIN02", 12000L);
		freqMap.put("MIN03", 18000L);
		freqMap.put("MIN04", 24000L);
		freqMap.put("MIN05", 30000L);
	}		
			
	@RequestMapping(value="/get",method=RequestMethod.GET)
    public List<ScheduleModel> getVal(@RequestParam(value="key", defaultValue="DEFAULT") String key) throws SchedulerException {
		Map<String, String> mapOfKeyValue = new HashMap<String, String>();
		mapOfKeyValue.put(key, key);
//		StringBuilder s = new StringBuilder();
		Scheduler scheduler = schedFactory.getScheduler();
		List<ScheduleModel> rs = Lists.newArrayList();
		for (String groupName : scheduler.getJobGroupNames()) {
			     for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
			    	  
					  String jobName = jobKey.getName();
					  String jobGroup = jobKey.getGroup();
					  //get job's trigger
					  List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
					  for(Trigger t: triggers){
						  TriggerKey tk = t.getKey();
						  Date nextFireTime = t.getNextFireTime();
						  
						  ScheduleModel sm = new ScheduleModel();
						  sm.job = jobName;
						  sm.jgroup = jobGroup;
						  sm.trigger = tk.getName();
						  sm.tgroup = tk.getGroup();
//						  sm.startTime = t.getStartTime();
//						  sm.fireTime = t.getNextFireTime();
						  rs.add(sm);
						 /* s.append("[jobName] : " + jobName + " [groupName] : "
								+ jobGroup + " -  [triggerName] : " + tk.getName()+ " -  [triggerGroup] : "
								  + tk.getGroup() + " - startTime : " + t.getStartTime()+ " - firetime :" + nextFireTime);
						  s.append("\r\n");*/
					  }
		  }
	   }
		return rs;
    }
	
	@RequestMapping(value="/start",method=RequestMethod.POST)
	public ErrorResult schedule(@RequestBody ScheduleModel model) {
		String scheduled = "Job " + model.job +" and trigger "+ model.trigger +" is Scheduled!!";
		int code = 200; 
		try {
			JobDetailFactoryBean jdfb = ConfigureQuartz.createJobDetail(DynamicJob.class);
			jdfb.setBeanName(model.job);
			jdfb.setGroup(model.jgroup);
			jdfb.afterPropertiesSet();
			JobDetail jd = jdfb.getObject();
			Long freq = freqMap.get(model.trigger);
			if(freq == null){
				logger.error("Invalid trigger "+model.trigger+", please check it again!!");
				code = 400; 
				scheduled = "Invalid trigger "+model.trigger+", please check it again!!";
			}
			SimpleTriggerFactoryBean stfb = ConfigureQuartz.createTrigger(jdfb.getObject(),freq);
			stfb.setBeanName(model.trigger);
			stfb.setGroup(model.tgroup +"-"+model.trigger+"-"+model.jgroup+"-"+model.job);
			stfb.afterPropertiesSet();
			
			Scheduler scheduler = schedFactory.getScheduler();
			Trigger trigger = stfb.getObject();

			if(scheduler.checkExists(trigger.getKey())){
				scheduler.unscheduleJob(trigger.getKey());
			}
			if(scheduler.checkExists(jd.getKey())){
				scheduler.deleteJob(jd.getKey());
			}
			schedFactory.getScheduler().scheduleJob(jdfb.getObject(), trigger);
		} catch (Exception e) {
			code = 400; 
			scheduled = "Could not schedule a trigger. "+ model.trigger + " - " + e.getMessage();
		}
		return new ErrorResult(code, scheduled);
	}
	
	@RequestMapping(value="/stop", method=RequestMethod.POST)
	public ErrorResult unschedule(@RequestBody ScheduleModel model) {
		String scheduled = "Job " + model.job +" and trigger "+ model.trigger +" is Unscheduled!!";
		int code = 200; 
		TriggerKey tkey = new TriggerKey(model.trigger,model.tgroup.split("-")[0] +"-"+model.trigger+"-"+model.jgroup+"-"+model.job);
		JobKey jkey = new JobKey(model.job,model.jgroup); 
		
		try {
			schedFactory.getScheduler().unscheduleJob(tkey);
			schedFactory.getScheduler().deleteJob(jkey);
		} catch (SchedulerException e) {
			code = 400;
			scheduled = "Error while unscheduling " + model.trigger + " - " + e.getMessage();
		}
		return new ErrorResult(code, scheduled);
	}
}