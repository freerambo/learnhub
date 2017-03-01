/*
 * Copyright: Energy Research Institute @ NTU
 * spring-boot-tcp-cache
 * com.erian.spring.boot.grid.repository -> sda.java
 * Created on 1 Mar 2017-5:24:01 pm
 */
package com.erian.spring.boot.grid.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.erian.spring.boot.grid.domain.GridData;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  1 Mar 2017 5:24:01 pm
 */

public interface GridSimulatorRepository extends MongoRepository<GridData, String> {

    public List<GridData> findByDeviceId(String deviceId);

}
