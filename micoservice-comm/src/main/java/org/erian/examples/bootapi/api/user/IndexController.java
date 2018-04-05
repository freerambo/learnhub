/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.api -> ShiroController.java
 * Created on 2 Mar 2017-4:10:15 pm
 */
package org.erian.examples.bootapi.api.user;
import org.erian.modules.constants.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  2 Mar 2017 4:10:15 pm
 */

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value={"/","index"},method=RequestMethod.GET, produces=MediaTypes.JSON_UTF_8)
    public String index(){
    	return "welcome to rambo springboot project";
    }
}