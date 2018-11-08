/* 
 * Gregory Scott Marvin
 * 
 * CIS 35B Lab 1
 * 
 */

package util;

import java.io.*;
import java.util.*;
import model.Automobile;
import exception.AutoException;


/**
 * FileIO class. Reads data from a .txt file to build a new instance of a
 * Automotive object. This object is returned from the buildAutoObject 
 * method, or propertiesParser. There is also a serializeObj method which 
 * writes the object to a .ser file for archiving. Lastly there is a deserializeObj 
 * which reads a serialized Automotive object from a .ser file and returns the 
 * Automotive object for use.
 * 
 * @version   release5 13 June 2018
 * @author    Gregory Scott Marvin
 */
public class FileIO {
	private Automobile auto = new Automobile();
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public Properties createPropertiesObject(String fileName) {
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
	
	public Automobile propertiesParser(Properties properties) {
		/*Properties properties = new Properties();
		try {
			FileInputStream in = new FileInputStream(fileName);
			properties.load(in);
		} catch (FileNotFoundException e) {
			System.out.println("Error, could not locate file: " + fileName);
			System.out.println("Please check filename and path and try again.");
		} catch (IOException e) {
			System.out.println("Error in reading in properties object.");
		}*/
		String model = properties.getProperty("CarModel");
		if (!model.equals(null)) {
			// finish getting the automobile properties
			
			auto.setModel(model);
			auto.setMake(properties.getProperty("CarMake"));
			auto.setPrice(Float.parseFloat(properties.getProperty("CarPrice")));
			int optionSetLength = Integer.parseInt(properties.getProperty("OpSetNumbers"));
			auto.createOptionSet(optionSetLength);
			
			for (int i = 1; i <= optionSetLength; i++) {
			
				
				auto.setOptionSetName(i - 1, properties.getProperty("Option" + String.valueOf(i)));
				int optionLength = Integer.parseInt(properties.getProperty("Op" +
						  												   String.valueOf(i) +
						 												   "Numbers"));
				
				auto.createOption(i - 1, optionLength);
				
				for (int j = 65; j < optionLength + 65; j++) {
				
					auto.setOptionName(i - 1, 
									   j - 65, 
									   properties.getProperty("OptionValue" +
															  String.valueOf(i) +
															  Character.toString((char) j)));
			
					auto.setOptionPrice(i - 1, 
									    j - 65, 
									    Float.parseFloat(properties.getProperty("OptionPrice" +
												  			   				    String.valueOf(i) +
												  		  	   					Character.toString((char) j))));
				}
			}
		}
		return auto;
	}
	
	// buildAutoObject. Builds Automotive object from file and returns the object
	public Automobile buildAutoObject(String fileName) {
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader buffer = new BufferedReader(file);
			float price;

			// read 1st line in file, contains model name
			String model = buffer.readLine(); 
			// set auto name to model from text file
			auto.setName(model);
			
			buffer.mark(0);
			
			try {
				// read 2nd line, which contains the base price
				price = lineToFloat(buffer.readLine());
				
				// set base price to converted float value from file
				auto.setPrice(price);
			} 
			catch (AutoException e) {
				Object myFix = e.fix(e.getErrorno());
				if (!myFix.toString().equals("Fix not performed")) {
					price = Float.parseFloat(myFix.toString());
					auto.setPrice(price);
				} 
				else {
					auto.setPrice(0.0f);
				}
				buffer.reset();
			}
			
			// read 3rd line, contains size of the contained OptionSet in Automotive
			int opSetSize = Integer.parseInt(buffer.readLine());
			
			// create OptionSet for auto
			auto.createOptionSet(opSetSize);

			// read 4th line of the file, contains the Name of the first OptionSet
			String opSetName = buffer.readLine();
			// set OptionSet name for first OptionSet
			auto.setOptionSetName(0, opSetName);
			
			// read 5th line of file, contains the number of Options for the option set
			int optSize = Integer.parseInt(buffer.readLine());
			// create Option for opset
			auto.createOption(0, optSize);
			
			// read 6th line of file, contains 10 names of colors each followed by their price
			// split line using , as delimiter, each name and price has its own index
			String[] tokens = buffer.readLine().split(", ");
			// go through tokens and assign to Option name and price
			for(int i = 0; i < tokens.length - 1; i += 2) {
				auto.setOptionName(0, i / 2, tokens[i]);
				auto.setOptionPrice(0, i / 2, Float.parseFloat(tokens[i + 1]));
			}
			
			// read 7th line of the file, contains the Name of the next OptionSet
			String op2SetName = buffer.readLine();
			// set OptionSet name for second OptionSet
			auto.setOptionSetName(1, op2SetName);
			
			// read 8th line of file for number of options in OptionSet
			int opt2Size = Integer.parseInt(buffer.readLine());
			// create Option for this opset
			auto.createOption(1, opt2Size);
			
			// read 9th line from file for Option names and associated price
			// again using String.split() method to tokenize string
			String[] tokens2 = buffer.readLine().split(", ");
			// go through tokens and assign to Option name and price
			for(int i = 0; i < tokens2.length - 1; i += 2) {
				auto.setOptionName(1, i / 2, tokens2[i]);
				auto.setOptionPrice(1, i / 2, Float.parseFloat(tokens2[i + 1]));
			}
			
			// read 10th line from file, contains name for next OptionSet
			String op3SetName = buffer.readLine();
			// set OptionSet name for third OptionSet
			auto.setOptionSetName(2, op3SetName);
			
			// read 11th line from file for number of options in OptionSet
			int opt3Size = Integer.parseInt(buffer.readLine());
			// create Option for this opset
			auto.createOption(2, opt3Size);
			
			// read 12th line from file and tokenize using String.split()
			String[] tokens3 = buffer.readLine().split(", ");
			// go through tokens and assign to appropriate name and price
			for(int i = 0; i < tokens3.length - 1; i += 2) {
				auto.setOptionName(2, i / 2, tokens3[i]);
				auto.setOptionPrice(2, i / 2, Float.parseFloat(tokens3[i + 1]));
			}
			
			// read 13th line from file for name in next OptionSet
			String op4SetName = buffer.readLine();
			// set OptionSet name for 4th option set
			auto.setOptionSetName(3, op4SetName);
			
			// read 14th line from file for number of options in OptionSet
			int opt4Size = Integer.parseInt(buffer.readLine());
			// create Option for this OptionSet
			auto.createOption(3, opt4Size);
			
			// read 15th line from file and tokenize using String.split()
			String[] tokens4 = buffer.readLine().split(", ");
			// go through tokens and assign to appropriate name and price
			for(int i = 0; i < tokens4.length - 1; i += 2) {
				auto.setOptionName(3, i / 2, tokens4[i]);
				auto.setOptionPrice(3, i / 2, Float.parseFloat(tokens4[i + 1]));
			}
			
			// read 16th line from file for name of next OptionSet
			String op5SetName = buffer.readLine();
			// set name for 5th OptionSet
			auto.setOptionSetName(4, op5SetName);
			
			// read 17th line from file for number of Options in OptionSet
			int opt5Size = Integer.parseInt(buffer.readLine());
			// create Option for this OptionSet
			auto.createOption(4, opt5Size);
			
			// read 18th line from file and tokenize using String.split()
			String[] tokens5 = buffer.readLine().split(", ");
			// go through tokens and assign to appropriate name and price
			for(int i = 0; i < tokens5.length - 1; i += 2) {
				auto.setOptionName(4, i / 2, tokens5[i]);
				auto.setOptionPrice(4, i / 2, Float.parseFloat(tokens5[i + 1]));
			}
			buffer.close();
			
		} 
		catch (IOException e) {
			System.out.println("Error: " + e.toString());
		}
		return auto;
	}
	
	public float lineToFloat(String line) throws AutoException {
		float myFloat = 0;
		try {
			myFloat = Float.parseFloat(line);
			if (myFloat <= 5000.00f) {
				throw new NumberFormatException();
			}
		} 
		catch (NumberFormatException e) {
			throw new AutoException(2, "Invalid float value");
		}
		finally {
			
		}
		return myFloat;
	}
	
	// serializeObj. Recieves an Automotive object, and serializes it to file
	public void serializeObj(Automobile auto) {
		try {
			output = new ObjectOutputStream(new FileOutputStream("/Users/ScottMarvin/Documents/workspace/CIS35BLAB1/auto.ser"));
			output.writeObject(auto);
			output.close();
		}
		catch(IOException e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	// deserializeObj. Deserializes an Automotive object from file and returns the object
	public Automobile deserializeObj() {
		try {
			input = new ObjectInputStream(new FileInputStream("/Users/ScottMarvin/Documents/workspace/CIS35BLAB1/auto.ser"));
			auto = (Automobile) input.readObject();
		}
		catch(IOException e) {
			System.out.println("Error: " + e.toString());
		}
		catch(ClassNotFoundException nf) {
			System.out.println("Error class not found.");
		}
		return auto;
	}
}
