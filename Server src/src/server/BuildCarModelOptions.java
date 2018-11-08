package server;

import java.net.*;
import java.io.*;
import adapter.*;
import model.Automobile;
import java.util.Properties;
import java.util.ArrayList;

/**
 * BuildCarModelOptions. Sets up the server and passes the serverSocket
 * to the multithreaded DefaultSocketClientClass. Allows for access of
 * the LinkedHashMap through use of AutoServer interface.
 * 
 * @version   release5 19 June 2018
 * @author    Gregory Scott Marvin
 */
public class BuildCarModelOptions {
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private AutoServer facilitator;
	private CreateFleet myFleet; 
	
	public BuildCarModelOptions(int port) {
		try {
			System.out.println("creating server socket");
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("In the server:");
			System.err.println("Could not listen on port: " + String.valueOf(port) + ".");
            System.exit(1);
		}
	}
	
	public BuildCarModelOptions() {
		facilitator = new BuildAuto(); //AutoServer
		myFleet = new BuildAuto();
		myFleet.createFleet(5);
	}
	
	public void serverSetUp() {
		while (true) {
			try {
				System.out.println("server accepting client socket");
				clientSocket = serverSocket.accept();
				DefaultSocketClient server = new DefaultSocketClient(clientSocket);
			} catch (IOException e) {
				System.out.println("In the server:");
				System.err.println("Failed to accept.");
				System.exit(1);
			}
		}
	}
	
	public void connectStreams() {
		try {
			System.out.println("server connecting streams");
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			System.out.println("In the server:");
			System.out.println("Unable to obtain stream...");
		}
	}
	
	public void receivePropertiesObj(Properties properties) {
		facilitator.buildWithProperties(properties);
	}
	
	public ArrayList<String> sendModelList() {
		return facilitator.displayModels();
	}
	
	public Automobile sendAutomobile(int index) {
		return facilitator.sendAuto(index);
	}
	
	public boolean existingAutos() {
		if (facilitator.displayModels().size() != 0) {
			return true;
		} else {
			return false;
		}
	}

}
