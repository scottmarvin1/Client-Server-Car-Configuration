package client;

import java.net.*;
import java.io.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Iterator;
import model.Automobile;

/**
 * DefaultSocketClient class. This is to make the client multithreaded.
 * Sends properties object to server and can do multiple times. Server
 * sends back a list of available models in the fleet for designing. Client
 * selects the model they want and selects options using SelectCarOptionClass
 * 
 * @version   release5 19 June 2018
 * @author    Gregory Scott Marvin
 */
public class DefaultSocketClient extends Thread {
	private static final String DEFAULT_FILE = 
			"/Users/ScottMarvin/Documents/workspace/kbbClient/propertiesRefModel.txt";
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket socket;
	private String host;
	private int port;
	
	public DefaultSocketClient(String host, int port) {
		setPort(port);
		setHost(host);
	}
	
	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}
	
	public boolean openConnection() {
		try {
			//System.out.println("Opening connection in client.");
			socket = new Socket(host, port);
		} catch (IOException e) {
			System.out.println("In the client:");
			System.out.println("Unable to connect to " + host + " at " + String.valueOf(port));
			return false;
		}
		try {
			//System.out.println("creating streams in the client.");
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
		} catch (Exception e) {
			System.out.println("In the client:");
			System.out.println("Unable to obtain stream from " + host + " at " + String.valueOf(port));
			return false;
		}
		return true;
	}
	
	public void handleSession() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String fromServer = "";
		String fileName = "";
		String toServer = "";
		Properties prop = null;
		ArrayList<String> modelList = null;
		Automobile auto = null;
		
		try {
			fromServer = (String) in.readObject();
			System.out.println("Server:");
			System.out.println(fromServer);
		} catch (Exception e) {
			System.out.println("Error, could not get response from the server.");
		}
		
		while (!fromServer.equals("Thank you!")) {
			menu();
			
			try {
				toServer = input.readLine();
			} catch (IOException e) {
				System.out.println("Error, could not get input from user.");
			}
			
			try {
				out.writeObject(toServer);
			} catch (Exception e) {
				System.out.println("Error, could not write object to server");
			}
			
			//after selection is made, continue if server is still live
			while (true) {
				if (toServer.equals("1")) {
					try {
						fromServer = (String) in.readObject();
						System.out.println("Server:");
						System.out.println(fromServer);
					} catch (Exception e) {
						System.out.println("Error, could not get response from the server(1).");
					}
					propertiesPrompt();
					
					try {
						fileName = input.readLine();
					} catch (IOException e) {
						System.out.println("Error, could not get input from user.");
					}
					if (fileName.equals("")) {
						fileName = DEFAULT_FILE;
					}
					prop = createPropertiesObj(fileName);
					
					try {
						out.writeObject(prop);
					} catch (Exception e) {
						System.out.println("Error, could not write object to server");
					}
					break; // allows for multiple entries of properties files
				} else if (toServer.equals("2")) {
					try {
						fromServer = (String) in.readObject();
						System.out.println("Server:");
						System.out.println(fromServer);
						
					} catch (Exception e) {
						System.out.println("Error, could not get response from the server.(2)");
					}
					
					try {
						modelList = (ArrayList<String>) in.readObject();
						showList(modelList);
						
					} catch (Exception e) {
						System.out.println("Error, could not get response from the server.(2.5)");
					}
					
					configInstructions();
					
					try {
						toServer = input.readLine();
					} catch (IOException e) {
						System.out.println("Error, could not get input from user.");
					}
					
					try {
						out.writeObject(toServer);
					} catch (Exception e) {
						System.out.println("Error, could not write object to server");
					}
					
					try {
						fromServer = (String) in.readObject();
						System.out.println("Server:");
						System.out.println(fromServer);
						auto = (Automobile) in.readObject();
						auto.printAttributes();
					} catch (Exception e) {
						System.out.println("Error, could not get response from the server.(3)");
					}
					
					SelectCarOption designAuto = new SelectCarOption(auto);
					designAuto.makeSelection();
					designAuto.showChoices();
					
					toServer = "done";
					try {
						out.writeObject(toServer);
					} catch (Exception e) {
						System.out.println("Error, could not write object to server");
					}
					
					try {
						fromServer = (String) in.readObject();
						System.out.println("Server:");
						System.out.println(fromServer);
					} catch (Exception e) {
						System.out.println("Error, could not get response from the server.");
					}
					break; //allows loop to be exited when done
				}
			}// end while true for making multiple selections
		} // while ends when server sends Thank you!	
	}
	
	public void closeSession() {
		try {
			System.out.println("client closing session.");
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("In the client:");
			System.out.println("Error Closing socket...");
		}
	}
	
	public void menu() {
		
		System.out.println("Please make a selection from the following");
		System.out.println("\t1. Upload a properties file.");
		System.out.println("\t2. Configure an automobile.");
		System.out.println("(please enter an interger)");
		System.out.print("option: ");
	
	}
	
	public void propertiesPrompt() {
		System.out.println("Please enter the full path name to a properties file:");
		System.out.println("(Or press enter for a default file)");
	}
	
	public void configInstructions() {
		System.out.println("Please select the number that corresponds to");
		System.out.println("the Automobile of your choice.");
		System.out.println("(please enter an integer)");
		System.out.print("selection: ");
	}

	public void showList(ArrayList<String> modelList) {
		Iterator<String> iter = modelList.iterator();
		int i = 1;
		
		if (modelList.size() == 0) {
			System.out.println("It doesn't seem like there are any Automobiles available.");
		} else {
			while (iter.hasNext()) {
				System.out.println(String.valueOf(i) + ". " + iter.next());
				i++;
			}
		}
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
	
	public void configurePrompt() {
		System.out.println("Please select a number that corresponds to the");
		System.out.println("Automobile you would like to configure.");
		System.out.println("(please enter an interger)");
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
}
