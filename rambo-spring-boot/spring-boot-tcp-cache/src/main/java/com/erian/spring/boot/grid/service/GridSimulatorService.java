/*
 * Copyright: Energy Research Institute @ NTU
 * spring-boot-tcp-cache
 * com.erian.spring.boot.grid.service -> GridSimulatorService.java
 * Created on 1 Mar 2017-5:29:19 pm
 */
package com.erian.spring.boot.grid.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.erian.spring.boot.grid.domain.GridData;
import com.erian.spring.boot.grid.repository.GridSimulatorRepository;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu </a>
 * @version v 1.0 Create: 1 Mar 2017 5:29:19 pm
 */
@Service
public class GridSimulatorService {

	private final static Logger logger = LoggerFactory.getLogger(GridSimulatorService.class);
	
	@Value("${device.ip}")
	private String ip;
	@Value("${device.port}")
	private Integer port;
	
	@Value("${device.command.read}")
	private String readValues;
	
	GridSimulatorRepository gridRepository;

	@Autowired
	public void setGridRepository(GridSimulatorRepository gridRepository) {
		this.gridRepository = gridRepository;
	}

	@Scheduled(fixedRate = 60000, initialDelay=10000)
	public void saveDeviceDataDB() {
		String[] results = SocketConnection.requestData(ip, port, readValues);

		if (results != null && results.length > 0) {
			GridData data = new GridData(Double.parseDouble(results[0]), Double.parseDouble(results[1]),
					Double.parseDouble(results[2]), Double.parseDouble(results[3]), Double.parseDouble(results[4]),results[5],0, "gridSimulator");
			gridRepository.save(data);
			logger.info(data.toString());
		}	

	}
}
