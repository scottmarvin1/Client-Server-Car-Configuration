package server;

import java.net.*;
import java.io.*;
import java.util.Properties;

/**
 * DefaultSocketClient class. This is to make the server multithreaded.
 * Receives properties object from the client to build an automobile.
 * Server passes made automobile to client to customize with their desired
 * options.
 * 
 * @version   release5 13 June 2018
 * @author    Gregory Scott Marvin
 */
public class DefaultSocketClient extends Thread {
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket socket;
	private int port;
	private BuildCarModelOptions carBuilder = new BuildCarModelOptions();
	
	public DefaultSocketClient(Socket socket) {
		this.socket = socket;
		setPort(socket.getLocalPort());
		start();
	}

	public void run() {
		if (setUpStreams()) {
			handleSession();
		}
	}
	
	public boolean setUpStreams() {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			System.out.println("In Server:");
			System.out.println("Unable to connect streams through port: " + String.valueOf(port));
			return false;
		}
		return true;
	}
	
	public void handleSession() {
		String fromClient = "";
		String toClient = "";
		Properties clientPropObj;
		
		toClient = "=== Welcome, please follow the instructions on-screen. ===";
		
		try {
			out.writeObject(toClient);
		} catch (Exception e) {
			System.out.println("Error, could not write object to client.");
		}
		
		while (true) {
			try {
				fromClient = (String) in.readObject();
			} catch (Exception e) {
				System.out.println("Error, could not get response from client(1).");
			}
			
			if (fromClient.equals("1")) {
				toClient = "Response received.";
				
				try {
					out.writeObject(toClient);
				} catch (Exception e) {
					System.out.println("Error, could not write object to client.");
				}
			
				try {
					clientPropObj = (Properties) in.readObject();
					try {
						carBuilder.receivePropertiesObj(clientPropObj);
					} catch (Exception e) {
						System.out.println("Error couldnt pass prop obj");
					}
					
				} catch (Exception e) {
					System.out.println("Error, could not get response from client(2).");
				}
				
			} else if (fromClient.equals("2")) {
				toClient = "Displaying list of available autos:";
				
				try {
					out.writeObject(toClient);
					out.writeObject(carBuilder.sendModelList());
				} catch (Exception e) {
					System.out.println("Error, could not write object to client.");
				}
				
				try {
					fromClient = (String) in.readObject();
				} catch (Exception e) {
					System.out.println("Error, could not get response from client.(3)");
				}
				
				int index = Integer.valueOf(fromClient) - 1;
				toClient = "Sending over Auto: " + 
									carBuilder.sendAutomobile(index).getName();
				
				try {
					out.writeObject(toClient);
					out.writeObject(carBuilder.sendAutomobile(Integer.valueOf(fromClient) - 1));
				} catch (Exception e) {
					System.out.println("Error, could not write object to client.");
				} 
				
			} else if (fromClient.equals("done")) {
				toClient = "Thank you!";
				try {
					out.writeObject(toClient);
					closeSession();
				} catch (Exception e) {
					System.out.println("Error, could not write object to client.");
				}
				//break; // quits the server
			} else	{
				System.out.println("Error, not a valid selection");
			}
		}// end while
	}
	
	public void closeSession() {
		try {
			System.out.println("Server closing up session.");
			out.close();
			in.close();
			socket.close();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("In the server:");
			System.out.println("Error closing socket.");
		}
	}
	
	public void setPort(int port) {
		this.port = port;
	}
}
