package Core.Helper.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PersonalProperties {
	private static PersonalProperties instance = null;
	private Properties properties = new Properties();
	
	/**
	 * Use singleton pattern to create ReadConfig object one time and use
	 * anywhere
	 * 
	 * @param propertiesPath
	 * @return instance of ReadConfig object
	 * @since 1.0.0
	 * @author viettuts.vn
	 * 
	 */
	public static PersonalProperties getInstance(String propertiesPath) {
			if (instance == null) {
			 instance = new PersonalProperties();
			 instance.readConfig(propertiesPath);
			}
		return instance;
	}

	/**
	* get property with key
	*
	* @param key
	* @return value of key
	* @since 1.0.0
	* @author viettuts.vn
	*/
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	/**
	* read file .properties
	* 
	* @param propertiesPath
	* @since 1.0.0
	* @author viettuts.vn
	*/
	private void readConfig(String propertiesPath) {
		InputStream inputStream = null;
		try {
			 String currentDir = System.getProperty("user.dir") + File.separator + "src/test/resources/properties" + File.separator;
			 inputStream = new FileInputStream(currentDir + propertiesPath);
			 properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			 // close objects
			 try {
				  if (inputStream != null) {
				inputStream.close();
			  }
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		}
	}
}
