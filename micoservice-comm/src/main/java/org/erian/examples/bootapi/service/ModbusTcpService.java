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
public class ModbusTcpService {

	private static Logger logger = LoggerFactory.getLogger(ModbusTcpService.class);

	@Autowired
	private ModbusTcpDao tcpDao;
	
	@Transactional(readOnly = true)
	public List<ModbusTCP> findAll() {
		return tcpDao.findAll();
	}

	@Transactional(readOnly = true)
	public ModbusTCP findOne(Integer id) {
		return tcpDao.findOne(id);
	}


	@Transactional
	public ModbusTCP saveModbusTCP(ModbusTCP tcp) {

		return tcpDao.save(tcp);
	}

	@Transactional
	public ModbusTCP modifyModbusTCP(ModbusTCP tcp) {

		ModbusTCP orginalModbusTCP = tcpDao.findOne(tcp.id);

		if (orginalModbusTCP == null) {
			logger.error(tcp.id + "  is not exist");
			throw new ServiceException("The ModbusTCP is not exist", ErrorCode.BAD_REQUEST);
		}
		return tcpDao.save(tcp);
	}

	@Transactional
	public void deleteModbusTCP(Integer id) {
		ModbusTCP tcp = tcpDao.findOne(id);

		if (tcp == null) {
			logger.error( id + " ModbusTCP which is not exist");
			throw new ServiceException("The ModbusTCP is not exist", ErrorCode.BAD_REQUEST);
		}

		tcpDao.delete(id);
	}
}
