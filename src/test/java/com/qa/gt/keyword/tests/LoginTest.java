package com.qa.gt.keyword.tests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.qa.gt.keyword.engine.KeyWordEngine;

public class LoginTest {
	
	public KeyWordEngine keyWordEngine;
	
	@Test
	public void loginTest() throws IOException {
		keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution("Login");
	}
	
}
