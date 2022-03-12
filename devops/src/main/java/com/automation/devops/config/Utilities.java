package com.automation.devops.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utilities{
	
	public String value;
	
	public String getpropdata(String key) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//application.properties"));
			value = p.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
