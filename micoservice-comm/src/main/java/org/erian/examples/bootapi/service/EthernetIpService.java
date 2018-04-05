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
public class EthernetIpService {

	private static Logger logger = LoggerFactory.getLogger(EthernetIpService.class);

	@Autowired
	private EthernetIpDao ipDao;
	
	@Transactional(readOnly = true)
	public List<EthernetIP> findAll() {
		return ipDao.findAll();
	}

	@Transactional(readOnly = true)
	public EthernetIP findOne(Integer id) {
		return ipDao.findOne(id);
	}


	@Transactional
	public EthernetIP saveEthernetIP(EthernetIP ip) {

		return ipDao.save(ip);
	}

	@Transactional
	public EthernetIP modifyEthernetIP(EthernetIP ip) {

		EthernetIP orginalEthernetIP = ipDao.findOne(ip.id);

		if (orginalEthernetIP == null) {
			logger.error(ip.id + "  is not exist");
			throw new ServiceException("The EthernetIP is not exist", ErrorCode.BAD_REQUEST);
		}
		return ipDao.save(ip);
	}

	@Transactional
	public void deleteEthernetIP(Integer id) {
		EthernetIP ip = ipDao.findOne(id);

		if (ip == null) {
			logger.error( id + " EthernetIP which is not exist");
			throw new ServiceException("The EthernetIP is not exist", ErrorCode.BAD_REQUEST);
		}

		ipDao.delete(id);
	}
}
