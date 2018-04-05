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
public class ProjectService {

	private static Logger logger = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private DeviceService deviceService;
	
	@Transactional(readOnly = true)
	public List<Project> findAll() {
		return projectDao.findAll();
	}

	@Transactional(readOnly = true)
	public Project findOne(Integer id) {
		return projectDao.findOne(id);
	}

	@Transactional
	public Project saveProject(Project project) {

		return projectDao.save(project);
	}

	@Transactional
	public Project modifyProject(Project project) {

		Project orginalProject = projectDao.findOne(project.id);

		if (orginalProject == null) {
			logger.error(project.id + "  is not exist");
			throw new ServiceException("The Project is not exist", ErrorCode.BAD_REQUEST);
		}
		return projectDao.save(project);
	}

	@Transactional
	public void deleteProject(Integer id) {
		Project project = projectDao.findOne(id);

		if (project == null) {
			logger.error( id + " Project which is not exist");
			throw new ServiceException("The Project is not exist", ErrorCode.BAD_REQUEST);
		}

		projectDao.delete(id);
		
		deviceService.deleteByProject(id);
		
	}
}
