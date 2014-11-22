/**
 * 
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Ninad
 *
 */
public class FileUtils {

	public static String resourceFolder;
	public FileUtils(){
		if(resourceFolder==null){
			Properties prop = ConfigUtils.getProperties();
			resourceFolder = prop.getProperty("resources.folder");
		}
	}
	
	public BufferedReader readFileUsingBufferedReader(String filePath) throws FileNotFoundException{
		if(filePath!=null && !filePath.equals("")){
			return new BufferedReader(new FileReader(resourceFolder+filePath));
		}else {
			return null;
		}
	}
	
	public Scanner readFileUsingScanner(String filePath) throws FileNotFoundException{
		if(filePath!=null && !filePath.equals("")){
			return new Scanner(new File(resourceFolder+filePath));
		}else{
			return null;
		}
	}

	public BufferedWriter writeInFile(String fileName){
		File file = new File(resourceFolder+"/"+fileName);
		BufferedWriter writer = null;
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}
}
