package org.erian.examples.bootapi.service.scheduler;

import java.util.List;

import org.erian.examples.bootapi.domain.DataPoint;
import org.erian.examples.bootapi.domain.DataPointValue;
import org.erian.examples.bootapi.domain.Device;
import org.erian.examples.bootapi.service.DataPointService;
import org.erian.examples.bootapi.service.DataPointValueService;
import org.erian.examples.bootapi.service.DeviceService;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * function description： job process 
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  14 Nov 2017 6:04:40 pm
 */

@Component
public class JobProcess {

	@Autowired
	private DataPointService dpService;
	@Autowired
	private DeviceService dService;
	@Autowired
	private DataPointValueService dpvService;
	
	private final static Logger logger = LoggerFactory.getLogger(JobProcess.class);
	
	@Transactional
	void process(JobKey jk){
		String group = jk.getGroup();
		String job = jk.getName();
		Integer id = Integer.valueOf(job);	
		switch(group){
			case "device":
				this.processDevice(id);
				break;
			case "datapoint":
				this.processDatapoint(id);
				break;	
			case "project":
				this.processProject(id);
				break;	
			default:
				logger.warn(" unsupported Job group " + group + "; Job -" + job);
		}
	}
	
	private void processDatapoint(Integer id){
		String val = dpService.readDataPoint(id);
		if(null != val && !val.isEmpty()){
			DataPointValue dpv = new DataPointValue(id,val);
//			we don't save the data for test purpose 
//			dpvService.saveDataPointValue(dpv); 
			logger.info("processing data - " + dpv.toString());
		}else{
			logger.info("processing data - is null" );
		}

	} 
	private void processDevice(Integer id){
		List<DataPoint> dps = dpService.findByDevice(id);
		for(DataPoint dp: dps)
			processDatapoint(dp.id);
	} 
	private void processProject(Integer id){
		List<Device> ds = dService.findByProject(id);
		for(Device d: ds)
			processDevice(d.id);
	} 
}
