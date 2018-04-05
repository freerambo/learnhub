/*
 * Copyright: Energy Research Institute @ NTU
 * microservice-comm
 * org.erian.examples.bootapi.dto -> ModbusTcpRequest.java
 * Created on 4 Sep 2017-10:12:44 pm
 */
package org.erian.examples.bootapi.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.dto.ModbusRtuRequest.FuctionCode;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu </a>
 * @version v 1.0 Create: 4 Sep 2017 10:12:44 pm
 */
public class ModbusTcpRequest {

	public String ip;
	public Integer port;
	public Integer unitId;
	public Integer ref;
	public Integer length;
	public String fCode;

	public ModbusTcpRequest() {
	}

	public ModbusTcpRequest(Device device, DataPoint dp, ModbusTCP tcp) {

		this.unitId = device.address;
		this.ref = dp.address;
		this.length = dp.length;
		this.ip = tcp.ip;
		this.port = tcp.port;
		this.fCode = this.fCode(dp);
	}

	String fCode(DataPoint dp) {

		String fCode = null;
		if ("boolean".equalsIgnoreCase(dp.type)) {

			if (dp.readOnly)
				fCode = FuctionCode.F01.toString();
			else if (dp.writeOnly)
				fCode = FuctionCode.F05.toString();
			else
				fCode = FuctionCode.F02.toString();

		} else {
			if (dp.readOnly)
				fCode = FuctionCode.F03.toString();
			else if (dp.writeOnly)
				fCode = FuctionCode.F06.toString();
			else
				fCode = FuctionCode.F04.toString();
		}
		return fCode;
	}

	enum FuctionCode {
		F01, F02, F03, F04, F05, F06
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
