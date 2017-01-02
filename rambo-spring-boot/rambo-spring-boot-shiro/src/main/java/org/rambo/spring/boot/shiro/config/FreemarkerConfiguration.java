package org.rambo.spring.boot.shiro.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

/**
 * http://www.sojson.com/blog/143.html
 * 
 * http://stackoverflow.com/questions/34988679/how-to-access-spring-boot-properties-from-freemarker-template 
 * Overrides the default spring-boot configuration to allow adding shared variables to the freemarker context
 */
@Configuration
public class FreemarkerConfiguration extends FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration {

/*    @Value("${myProp}")
    private String myProp;*/

    @Override
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = super.freeMarkerConfigurer();

        Map<String, Object> sharedVariables = new HashMap<>();
        sharedVariables.put("shiro", new ShiroTags());
        configurer.setFreemarkerVariables(sharedVariables);

        return configurer;
    }
}