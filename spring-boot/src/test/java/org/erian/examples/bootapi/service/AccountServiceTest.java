package org.erian.examples.bootapi.service;

import org.erian.examples.bootapi.service.AccountService;
import org.junit.Test;

public class AccountServiceTest {

	@Test
	public void hash() throws Exception {
		System.out.println("hashPassword:" + AccountService.hashPassword("erian"));
	}

}
