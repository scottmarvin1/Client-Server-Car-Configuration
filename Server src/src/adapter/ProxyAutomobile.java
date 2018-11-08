package adapter;

import model.*;
import util.FileIO;
import java.util.*;
import scale.*;

public abstract class ProxyAutomobile {
	
	private Automobile myAuto;
	protected static LinkedHashMap<String, Automobile> myFleet;
	protected static ArrayList<String> modelList;
	
	public void buildAuto(String fileName, String fileType) {
		FileIO builder = new FileIO();
		
		if (fileType.equals("configuration")) {
		
			myAuto = builder.buildAutoObject(fileName);
			myFleet.put(myAuto.getName(), myAuto);
			
		} else if (fileType.equals("properties")) {
			Properties prop = builder.createPropertiesObject(fileName);
			myAuto = builder.propertiesParser(prop);
			
		} else {
			System.out.println("Invalid file type. Please enter:");
			System.out.println("\tconfiguration - for reference model file");
			System.out.println("\tproperties - for a properties file");
		}
	}
	
	public void buildWithProperties(Properties properties) {
		FileIO builder = new FileIO();
		
		myAuto = builder.propertiesParser(properties);
		myFleet.put(myAuto.getName(), myAuto);
		modelList.add(myAuto.getName());
	}
	
	public void printAuto() {
		myAuto.printAttributes();
	}
	
	public void printOneAuto(String name) {
		Collection<Automobile> autoList = myFleet.values();
		Iterator<Automobile> iter = autoList.iterator();
		Automobile auto;
		boolean found = false;
		
		while(iter.hasNext() && !found) {
			auto = iter.next();
			if (auto.getName().equals(name)) {
				found = true;
				auto.printAttributes();
			}
		}
		if (!found) {
			System.out.println("Sorry, could not find automobile: " + name + "\n");
		}
	}
	
	public void createFleet(int fleetSize) {
		myFleet = new LinkedHashMap<String, Automobile>(fleetSize);
		modelList = new ArrayList<String>(5);
	}
	
	public void addToFleet(String name, Automobile auto) {
		myFleet.put(name, auto);
		modelList.add(auto.getName());
	}
	
	public int getSize() {
		return myFleet.size();
	}
	
	public ArrayList<String> displayModels() {
		return modelList;
	}
	
	public Automobile sendAuto(int index) {
		String modelName = modelList.get(index);
		return myFleet.get(modelName);
	}
	
	public void printFleet() {
		Collection<Automobile> autoList = myFleet.values();
		Iterator<Automobile> iter = autoList.iterator();
		Automobile auto;
		while(iter.hasNext()) {
			auto = iter.next();
			auto.printAttributes();
			System.out.print("Total Price with options selected: $");
			System.out.printf("%.2f\n\n", auto.getTotalPrice());
		}
	}
	
	public void updateOptionSetName(String optionSetName, String newName) {
		int index = myAuto.findOptionSetIndex(optionSetName);
		
		if (index != 1000) {
			myAuto.updateOptionSetName(index, newName);
		}
	}
	
	public void updateOptionPrice(String optionSetName, String option, float newPrice) {
		int opSetIndex = myAuto.findOptionSetIndex(optionSetName);
		
		if (opSetIndex != 1000) {
			int opIndex = myAuto.findOptionIndex(option, opSetIndex);
			if (opIndex != 1000) {
				myAuto.updateOptionPrice(opSetIndex, opIndex, newPrice);
			}	
		}
	}
	
	public void operation(String[] input) {
		EditOptions edit = new EditOptions(Integer.valueOf(input[0]), 
										   Integer.valueOf(input[1]), 
										   myFleet);
		edit.start(input);
	}
}
