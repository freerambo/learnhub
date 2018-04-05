/*
 * Copyright: Energy Research Institute @ NTU
 * microservice-comm
 * org.erian.examples.bootapi.dto -> ModbusTcpRequest.java
 * Created on 4 Sep 2017-10:12:44 pm
 */
package org.erian.examples.bootapi.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.erian.examples.bootapi.domain.*;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  4 Sep 2017 10:12:44 pm
 */
public class EthernetIpRequest {

	public String ip;
	public Integer port;
	public String devicePath;
	public String datapointPath;
	public Integer length;

	
	public EthernetIpRequest(){}
	
	
	public EthernetIpRequest(Device device, DataPoint dp, EthernetIP ip){
		
		this.devicePath = device.path;
		this.datapointPath = dp.path;
		this.length = dp.length;
		this.ip = ip.ip;
		this.port = ip.port;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
