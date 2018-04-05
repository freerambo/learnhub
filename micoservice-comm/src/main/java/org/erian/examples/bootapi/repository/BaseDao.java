/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.erian.examples.bootapi.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

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
public interface BaseDao<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
