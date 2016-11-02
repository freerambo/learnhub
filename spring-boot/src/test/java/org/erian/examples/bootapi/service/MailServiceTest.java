package org.erian.examples.bootapi.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.erian.examples.bootapi.BootApiApplication;
import org.erian.examples.bootapi.service.mail.MailService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApiApplication.class)
@DirtiesContext
public class MailServiceTest {

	@Autowired
	private MailService mailService;

	@Test
	public void sendEmail() {
		mailService.sendMail("88515221@qq.com", "test@gmail.com", "Test", "content");		
	}

	
	
	
}
