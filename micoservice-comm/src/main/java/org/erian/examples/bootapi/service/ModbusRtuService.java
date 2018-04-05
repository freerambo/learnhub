package org.erian.examples.bootapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.repository.*;
import org.erian.examples.bootapi.service.exception.ErrorCode;
import org.erian.examples.bootapi.service.exception.ServiceException;

@Service
public class ModbusRtuService {

	private static Logger logger = LoggerFactory.getLogger(ModbusRtuService.class);

	@Autowired
	private ModbusRtuDao rtuDao;
	
	@Transactional(readOnly = true)
	public List<ModbusRTU> findAll() {
		return rtuDao.findAll();
	}

	@Transactional(readOnly = true)
	public ModbusRTU findOne(Integer id) {
		return rtuDao.findOne(id);
	}


	@Transactional
	public ModbusRTU saveModbusRTU(ModbusRTU rtu) {

		return rtuDao.save(rtu);
	}

	@Transactional
	public ModbusRTU modifyModbusRTU(ModbusRTU rtu) {

		ModbusRTU orginalModbusRTU = rtuDao.findOne(rtu.id);

		if (orginalModbusRTU == null) {
			logger.error(rtu.id + "  is not exist");
			throw new ServiceException("The ModbusRTU is not exist", ErrorCode.BAD_REQUEST);
		}
		return rtuDao.save(rtu);
	}

	@Transactional
	public void deleteModbusRTU(Integer id) {
		ModbusRTU rtu = rtuDao.findOne(id);

		if (rtu == null) {
			logger.error( id + " ModbusRTU which is not exist");
			throw new ServiceException("The ModbusRTU is not exist", ErrorCode.BAD_REQUEST);
		}

		rtuDao.delete(id);
	}
}
