package com.selenium.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class DriverUtil {
	public static final String PROP_PROJECT_BASE_DIR = "project.basedir";
	public static final String FOLDER_DRIVER = "Driver";
	public static final String DEFAULT_PROPERTIES = "system.properties";
	private static Properties prod;

	/**
	 * @return the path of ie driver file.
	 */
	public static String getIeDriver() {
		String path = getKey(PROP_PROJECT_BASE_DIR) + File.separator
				+ FOLDER_DRIVER + File.separator + "IEDriverServer.exe";
		try {
			File driverIe = new File(path);
			if (driverIe.exists()) {
				return driverIe.getAbsolutePath();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * @return the path of chrome driver file
	 */
	public static String getChromeDriver() {
//		String path = getKey(PROP_PROJECT_BASE_DIR) + File.separator
//				+ FOLDER_DRIVER + File.separator + "chromedriver.exe";
            String path = "E:\\Selenium\\SeleniumProject\\Driver\\chromedriver.exe";
		try {
			File driverChrome = new File(path);
			if (driverChrome.exists()) {
				return driverChrome.getAbsolutePath();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * @return load the file system.properties
	 */
	public static Properties getProperties() {
		if (prod == null) {
			prod = new Properties();
			try {
				prod.load(DriverUtil.class.getClassLoader()
						.getResourceAsStream(DEFAULT_PROPERTIES));
			} catch (IOException e) {
				//
			}
		}
		return prod;
	}

	/**
	 * @param key
	 * @return get value of key
	 */
	public static String getKey(String key) {
		Object obj = getProperties().get(key);
		String value = "";
		if (obj != null)
			value = obj.toString();
		return value;
	}
        
        public static void main(String[] args)  {
            System.out.println(System.getProperty("user.dir"));
//            getChromeDriver();
        }
}