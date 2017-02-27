package com.erian.spring.boot.grid.api;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erian.spring.boot.grid.common.constants.MediaTypes;
import com.erian.spring.boot.grid.domain.BaseResult;
import com.erian.spring.boot.grid.domain.GridData;
import com.erian.spring.boot.grid.domain.GridSet;
import com.erian.spring.boot.grid.domain.ReadSet;
import com.erian.spring.boot.grid.domain.Status;
import com.erian.spring.boot.grid.service.SocketConnection;

@RestController
@RequestMapping("/api/grid")
public class GridController {

	@Value("${device.ip}")
	private String ip;
	@Value("${device.port}")
	private Integer port;

	@Value("${device.command.setStatus}")
	private String setStatus;

	@Value("${device.command.initCheck}")
	private String initCheck;

	@Value("${device.command.setRead}")
	private String setRead;

	@Value("${device.command.readSet}")
	private String readSet;

	@Value("${device.command.read}")
	private String readValues;

	@Autowired
	SocketConnection device;


	private static Logger logger = LoggerFactory.getLogger(GridController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "Welcome to GridSimulator";
	}

	@RequestMapping(value = "status/{status}", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public BaseResult<Status> changDeviceStatus(@NotNull @PathVariable("status") String status) {

		BaseResult<Status> result = null;
		String command = String.format(setStatus, status);

		String[] results = SocketConnection.requestData(ip, port, command);

		if (results != null && results.length > 0) {
			Status stat = new Status(results[0]);
			result = new BaseResult<Status>("200", "OK", stat);
		} else {
			result = new BaseResult<Status>("400", "No response Data");
		}

		return result;
	}

	@RequestMapping(value = "request", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public BaseResult<String> requestByCommand(
			@RequestParam(name = "command", defaultValue = "meas:volt:ac?;:meas:curr:ac?;:meas:pow:ac?;:meas:freq?\n") String command) {

		BaseResult<String> result = null;
		String results = device.callDevice(ip, port, command);

		if (StringUtils.isEmpty(results))
			result = new BaseResult<String>("400", "No Reponse data");
		else
			result = new BaseResult<String>("200", "OK", results);

		return result;
	}

	/**
	 * check initial status
	 * 
	 * @return
	 *
	 */
	@RequestMapping(value = "init", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public BaseResult<Status> initCheck() {
		BaseResult<Status> result = null;
		String results = SocketConnection.callDevice(ip, port, initCheck);

		if (StringUtils.isEmpty(results)) {
			Status stat = new Status(results);
			result = new BaseResult<Status>("200", "OK", stat);
		} else {
			result = new BaseResult<Status>("400", "No response Data");
		}
		return result;
	}

	/**
	 * sour:freq %1$f;:volt:ac %2$f;:volt:lim:ac %3$f;:POW:prot %4$f;:curr %5$f;
	 * sour:freq?;:volt:ac?;:volt:lim:ac?;:POW:prot?;:curr:lim?;:outp?\n
	 */
	@RequestMapping(value = "setRead", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public BaseResult<ReadSet> setValues(@RequestBody @Valid GridSet set) {

		BaseResult<ReadSet> result = null;

		String command = String.format(setRead, set.frequency, set.voltage, set.voltageLimit, set.powerLimit,
				set.currentLimit);

		String[] results = SocketConnection.requestData(ip, port, command);

		if (results != null && results.length > 0) {

			ReadSet data = new ReadSet(Double.parseDouble(results[0]), Double.parseDouble(results[1]),
					Double.parseDouble(results[2]), Double.parseDouble(results[3]), Double.parseDouble(results[4]),
					results[5], "gridSimulator");
			result = new BaseResult<ReadSet>("200", "OK", data);
		} else {
			result = new BaseResult<ReadSet>("400", "No response Data");
		}
		return result;
	}

	// sour:freq?;:volt:ac?;:volt:lim:ac?;:POW:prot?;:curr:lim?;:outp?\n
	@RequestMapping(value = "readSet", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public BaseResult<ReadSet> readSetValues() {

		BaseResult<ReadSet> result = null;

		String[] results = SocketConnection.requestData(ip, port, readSet);

		if (results != null && results.length > 0) {

			ReadSet data = new ReadSet(Double.parseDouble(results[0]), Double.parseDouble(results[1]),
					Double.parseDouble(results[2]), Double.parseDouble(results[3]), Double.parseDouble(results[4]),
					results[5], "gridSimulator");
			result = new BaseResult<ReadSet>("200", "OK", data);
		} else {
			result = new BaseResult<ReadSet>("400", "No response Data");
		}
		return result;
	}

	/**
	 * meas:volt:ac?;:meas:curr:ac?;:meas:pow:ac?;:meas:freq?;outp?
	 * 
	 * @return
	 *
	 */
	@RequestMapping(value = "readValues", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public BaseResult<GridData> readDeviceValues() {

		BaseResult<GridData> result = null;

		String[] results = SocketConnection.requestData(ip, port, readSet);

		if (results != null && results.length > 0) {

			GridData data = new GridData(Double.parseDouble(results[0]), Double.parseDouble(results[1]),
					Double.parseDouble(results[2]), Double.parseDouble(results[3]), results[4], "gridSimulator");
			result = new BaseResult<GridData>("200", "OK", data);
		} else {
			result = new BaseResult<GridData>("400", "No response Data");
		}
		return result;
	}

}