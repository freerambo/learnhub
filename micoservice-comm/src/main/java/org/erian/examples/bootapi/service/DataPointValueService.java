package org.erian.examples.bootapi.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.repository.*;
import org.erian.examples.bootapi.service.exception.ErrorCode;
import org.erian.examples.bootapi.service.exception.ServiceException;
import org.erian.modules.persistence.DynamicSpecifications;
import org.erian.modules.persistence.SearchFilter;
import org.erian.modules.persistence.SearchFilter.Operator;

@Service
public class DataPointValueService {

	private static Logger logger = LoggerFactory.getLogger(DataPointValueService.class);

	@Autowired
	private DataPointValueDao dataPointDao;
	
	@Transactional(readOnly = true)
	public List<DataPointValue> findAll() {
		return dataPointDao.findAll(new Sort(Direction.DESC, "id"));
	}
	
	@Transactional(readOnly = true)
	public List<DataPointValue> findDpValues(Integer dataPointId) {
		return dataPointDao.getByDataPointIdOrderByIdDesc(dataPointId);
	}
	
	
	@Transactional(readOnly = true)
	public DataPointValue findOne(Integer id) {
		return dataPointDao.findOne(id);
	}


	@Transactional
	public DataPointValue saveDataPointValue(DataPointValue dataPoint) {

		return dataPointDao.save(dataPoint);
	}

	@Transactional
	public DataPointValue modifyDataPointValue(DataPointValue dataPoint) {

		DataPointValue orginalDataPointValue = dataPointDao.findOne(dataPoint.id);

		if (orginalDataPointValue == null) {
			logger.error(dataPoint.id + "  is not exist");
			throw new ServiceException("The DataPointValue is not exist", ErrorCode.BAD_REQUEST);
		}
		return dataPointDao.save(dataPoint);
	}

	@Transactional
	public void deleteDataPointValue(Integer id) {
		DataPointValue dataPoint = dataPointDao.findOne(id);

		if (dataPoint == null) {
			logger.error( id + " DataPointValue which is not exist");
			throw new ServiceException("The DataPointValue is not exist", ErrorCode.BAD_REQUEST);
		}

		dataPointDao.delete(id);
	}
	
	
	public Page<DataPointValue> getDpValuesbyPage(Long dpId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<DataPointValue> spec = buildSpecification(dpId, searchParams);

		return dataPointDao.findAll(spec, pageRequest);
	}
	
	/**
	 * pagenation request
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 *  Dynamic Specifications 
	 */
	private Specification<DataPointValue> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<DataPointValue> spec = DynamicSpecifications.bySearchFilter(filters.values(), DataPointValue.class);
		return spec;
	}
}
