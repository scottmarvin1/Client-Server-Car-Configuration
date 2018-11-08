package client;

import java.net.*;
import java.io.*;
import java.util.Properties;

/**
 * CarModelOptionsIO. Creates a properties object to pass to server.
 * Extends default socket client so it is multithreaded.
 * 
 * @version   release5 19 June 2018
 * @author    Gregory Scott Marvin
 */
public class CarModelOptionsIO extends DefaultSocketClient {
	
	public CarModelOptionsIO(String host, int port) {
		super(host, port);
	}

	public Properties createPropertiesObj(String fileName) {
		Properties properties = new Properties();
		try {
			FileInputStream in = new FileInputStream(fileName);
			properties.load(in);
		} catch (FileNotFoundException e) {
			System.out.println("Error, could not locate file: " + fileName);
			System.out.println("Please check filename and path and try again.");
		} catch (IOException e) {
			System.out.println("Error in reading in properties object.");
		}
		return properties;
	}
}
