package org.sparkexample.test;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

public class WordCountTaskTest {

	Logger logger = LoggerFactory.getLogger(WordCountTaskTest.class);

	@Test
	public void test() throws URISyntaxException {
		String inputFile = getClass().getResource("test.txt").toURI().toString();
		logger.info(inputFile);
//		new WordCountTask().run(inputFile);
	}
}
