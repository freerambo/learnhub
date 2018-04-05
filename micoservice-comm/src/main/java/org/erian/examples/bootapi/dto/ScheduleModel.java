/*
 * Copyright: Energy Research Institute @ NTU
 * microservice-comm
 * org.erian.examples.bootapi.dto -> ModbusTcpRequest.java
 * Created on 4 Sep 2017-10:12:44 pm
 */
package org.erian.examples.bootapi.dto;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.dto.ModbusRtuRequest.FuctionCode;
import org.erian.modules.utils.Clock;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu </a>
 * @version v 1.0 Create: 4 Sep 2017 10:12:44 pm
 */
public class ScheduleModel {

	public String job;
	public String jgroup;
	public String trigger;
	public String tgroup;
	
	public ScheduleModel() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
