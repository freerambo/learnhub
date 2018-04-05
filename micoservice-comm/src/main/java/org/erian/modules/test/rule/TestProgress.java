package org.erian.modules.test.rule;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestProgress extends TestWatcher {

	@Override
	protected void starting(Description description) {
		System.out.println("\n[Test Case starting] " + description.getTestClass().getSimpleName() + "."
				+ description.getMethodName() + "()\n");
	}

	@Override
	protected void finished(Description description) {
		System.out.println("\n[Test Case finished] " + description.getTestClass().getSimpleName() + "."
				+ description.getMethodName() + "()\n");
	}

}
