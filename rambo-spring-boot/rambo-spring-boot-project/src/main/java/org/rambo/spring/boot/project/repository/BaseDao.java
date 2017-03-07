/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.rambo.spring.boot.project.repository;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 
 * @description   Base Dao, All other DAO will extends it
 * @version currentVersion(1.0)  
 * @author Yuanbo Zhu  
 * @createtime May 11, 2016 3:59:09 PM
 */

/* * Annotation to exclude repository interfaces from being picked up 
 * and thus in consequence getting an instance being created. 
 */
@NoRepositoryBean
public abstract interface BaseDao<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

}
