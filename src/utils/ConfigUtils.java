/**
 * 
 */
package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Ninad
 *
 */
public class ConfigUtils {

	public static Properties properties;
	
	public static Properties getProperties(){
		if(properties==null){
			try {
				FileInputStream input = new FileInputStream("resources/common.properties");
				properties = new Properties();
				properties.load(input);
				return properties;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			return properties;
		}
		return null;
	}
}
