/* 
 * Gregory Scott Marvin
 * 
 * CIS 35B Lab 1
 * 
 */

package model;

import model.OptionSet.*;
import java.io.Serializable;
import java.util.*;

/**
 * Automobile class. Stores configuration data to be used in 
 * configuring a car for purchase. 
 * 
 * @version   release5 19 June 2018
 * @author    Gregory Scott Marvin
 */
public class Automobile implements Serializable {
	private String name;
	private String make;
	private String model;
	private float price;
	private ArrayList<OptionSet> opSetList;
	private ArrayList<Option> choice;
	
	public Automobile() {
		name = "";
		make = "";
		model = "";
		price = 0.0f;
		opSetList = new ArrayList<OptionSet>();
		choice = new ArrayList<Option>();
	}
	
	public Automobile(String name) {
		this.name = name;
		String[] str = name.split(": ");
		make = str[0];
		model = str[1];
		price = 0.0f;
		opSetList = new ArrayList<OptionSet>();
		choice = new ArrayList<Option>();
	}
	
	public Automobile(String name, float cost) {
		this.name = name;
		String[] str = name.split(": ");
		make = str[0];
		model = str[1];
		price = cost;
		opSetList = new ArrayList<OptionSet>();
		choice = new ArrayList<Option>();
	}
	
	public Automobile(String name, int size) {
		this.name = name;
		String[] str = name.split(": ");
		make = str[0];
		model = str[1];
		price = 0.0f;
		opSetList = new ArrayList<OptionSet>(size);
		choice = new ArrayList<Option>(size);
		for (int i = 0; i < size; i++) {
			opSetList.add(new OptionSet());
			choice.add(opSetList.get(i).new Option());
		}
	}
	
	public Automobile(float cost) {
		name = "";
		make = "";
		model = "";
		price = cost;
		opSetList = new ArrayList<OptionSet>();
		choice = new ArrayList<Option>();
	}
	
	public Automobile(float cost, int size) {
		name = "";
		make = "";
		model = "";
		price = cost;
		opSetList = new ArrayList<OptionSet>(size);
		choice = new ArrayList<Option>(size);
		for (int i = 0; i < size; i++) {
			opSetList.add(new OptionSet());
			choice.add(opSetList.get(i).new Option());
		}
	}
	
	public Automobile(int size) {
		name = "";
		make = "";
		model = "";
		price = 0.0f;
		opSetList = new ArrayList<OptionSet>(size);
		choice = new ArrayList<Option>(size);
		for (int i = 0; i < size; i++) {
			opSetList.add(new OptionSet());
			choice.add(opSetList.get(i).new Option());
		}
	}
	
	
	public Automobile(String name, float cost, int size) {
		this.name = name;
		String[] str = name.split(": ");
		make = str[0];
		model = str[1];
		price = cost;
		opSetList = new ArrayList<OptionSet>(size);
		choice = new ArrayList<Option>(size);
		for (int i = 0; i < size; i++) {
			opSetList.add(new OptionSet());
			choice.add(opSetList.get(i).new Option());
		}
	}
	
	public Automobile(String make, String model, float cost, int size) {
		
		this.make = make;
		this.model = model;
		price = cost;
		opSetList = new ArrayList<OptionSet>(size);
		choice = new ArrayList<Option>(size);
		for (int i = 0; i < size; i++) {
			opSetList.add(new OptionSet());
			choice.add(opSetList.get(i).new Option());
		}
	}
	
	// getName method for Automobile class
	public String getName() {
		return name;
	}
	
	// getMake method for Automobile
	public String getMake() {
		return make;
	}
	
	//getModel method for Automobile
	public String getModel() {
		return model;
	}
	
	// getPrice method for Automobile class
	public float getPrice() {
		return price;
	}
	
	// getOpSetList
	public ArrayList<OptionSet> getOpSetList() {
		return opSetList;
	}
	
	public ArrayList<Option> getChoiceList() {
		return choice;
	}
	
	// getOptionSet method for Automobile class, returns OptionSet by index
	public OptionSet getOptionSet(int index) {
		return opSetList.get(index);
	}
	
	public int getOpSetLength() {
		return opSetList.size();
	}
	
	// getOptionSetName, returns name of OptionSet by index
	public String getOptionSetName(int index) {
		return opSetList.get(index).getName();
	}
	
	// getOpList
	public ArrayList<Option> getOpList(int index) {
		return opSetList.get(index).getOpArrayList();
	}
	
	// getOptionChoice
	public String getOptionChoice(String opSetName) {
		int index = 0;
		String choiceName = "";
		Iterator <OptionSet> iter = opSetList.iterator();
		while (iter.hasNext()) {
			if (iter.next().getName().equals(opSetName)) {
				choiceName = opSetList.get(index).getOptionChoice().getName();
			}
			index++;
		}
		return choiceName;
	}
	
	// getOptionPrice
	public float getOptionChoicePrice(String opSetName) {
		int index = 0;
		float choicePrice = 0.0f;
		Iterator <OptionSet> iter = opSetList.iterator();
		while (iter.hasNext()) {
			if (iter.next().getName().equals(opSetName)) {
				choicePrice = opSetList.get(index).getOptionChoice().getPrice();
			}
			index++;
		}
		
		return choicePrice;
	}
	
	// getOption. Returns the Option object at the specified indices
	public Option getOption(int opSetIndex, int opIndex) {
		return opSetList.get(opSetIndex).getOption(opIndex);
	}
	
	// getOptionName. Returns the name of an option at the specified indices
	public String getOptionName(int opSetIndex, int opIndex) {
		return opSetList.get(opSetIndex).getOption(opIndex).getName();
	}
	
	// getOptionPrice. Gets the price of an option at the specified indices
	public float getOptionPrice(int opSetIndex, int opIndex) {
		return opSetList.get(opSetIndex).getOption(opIndex).getPrice();
	}
	
	public float getTotalPrice() {
		float total = price;
		Iterator<Option> iter = choice.iterator();
		while (iter.hasNext()) {
			total += iter.next().getPrice();
		}
		
		return total;
	}
	
	// setName method for Automobile. Sets name to particular model
	public void setName(String name) {
		//this.name = name;
		if (name.contains(":")) {
			String[] str = name.split(": ");
			make = str[0];
			model = str[1];
		}
		this.name = name.replace(":", "");
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	// setPrice method for Automobile. Sets price for the particular model
	public void setPrice(float cost) {
		price = cost;
	}
	
	// setOptionSetName, sets name for OptionSet based on index provided
	public void setOptionSetName(int index, String optSetName) {
		opSetList.get(index).setName(optSetName);
	}
	
	// setOptionName, sets name for Option in OptionSet based on indices provided 
	public void setOptionName(int opSetIndex, int opIndex, String optName) {
		opSetList.get(opSetIndex).getOption(opIndex).setName(optName);
	}
	
	// setOptionPrice, sets price for Option in OptionSet based on indices provided
	public void setOptionPrice(int opSetIndex, int opIndex, float cost) {
		opSetList.get(opSetIndex).getOption(opIndex).setPrice(cost);
	}
	
	// setOptionChoice
	public void setOptionChoice(String optSetName, String optName) {
		int index = 0;
		int optIndex = 0;
		boolean optionChoiceSet = false;
		
		Iterator<OptionSet> opSetIter = opSetList.iterator();
		while (opSetIter.hasNext() && !optionChoiceSet) {
			if (opSetIter.next().getName().equals(optSetName)) {
				Iterator<Option> opIter = opSetList.get(index).getOpArrayList().iterator();
				while (opIter.hasNext() && !optionChoiceSet) {
					if (opIter.next().getName().equals(optName)) {
						choice.set(index, opSetList.get(index).getOption(optIndex));
					}
					optIndex++;
				}
			}
			index++;
		}
	}
	
	// createChoiceSet
	public void createChoiceSet(int size) {
		choice = new ArrayList<Option>(size);
		for (int i = 0; i < size; i++) {
			choice.add(opSetList.get(i).new Option());
		}
	}
	
	// createOptionSet. Creates a new OptionSet array of indicated size
	public void createOptionSet(int size) {
		opSetList = new ArrayList<OptionSet>(size);
		for (int i = 0; i < size; i++) {
			opSetList.add(new OptionSet());
		}
		createChoiceSet(size);
	}
	
	// createOption. Creates a new Option array of indicated size at an index in opset
	public void createOption(int index, int size) {
		opSetList.get(index).createOptions(size);
	}
	
	
	// findOptionSet. Search for an OptionSet by its name
	public int findOptionSetIndex(String opSetName) {
		boolean optionSetFound = false;
		int index = 0;
		int foundOptionSetIndex = 1000; // large value to see if loop executes
		
		Iterator<OptionSet> iter = opSetList.iterator();
		while (iter.hasNext() && !optionSetFound) {
			if (iter.next().getName().equals(opSetName)) {
				optionSetFound = true;
				foundOptionSetIndex = index;
			}
			index++;
		}
		
		return foundOptionSetIndex;
	}
	
	public int findOptionIndex(String optName, int opSetIndex) {
		boolean optionFound = false;
		int foundIndex = 1000;
		int index = 0;
		
		Iterator<Option> iter = opSetList.get(opSetIndex).getOpArrayList().iterator();
		while (iter.hasNext() && !optionFound) {
			if (iter.next().getName().equals(optName)) {
				optionFound = true;
				foundIndex = index;
			}
			index++;
		}
		
		return foundIndex;
	}
	
	//deleteOptionSetByIndex
	public void deleteOptionSetByIndex(int index) {
		opSetList.remove(index);
	}
	
	// deleteOptionByIndeces
	public void deleteOptionByIndeces(int opSetIndex, int opIndex) {
		opSetList.get(opSetIndex).deleteOption(opIndex);
	}
	
	// updateOptionSetName
	public void updateOptionSetName(int index, String newName) {
		opSetList.get(index).setName(newName);
	}
	
	//updateOptionName
	public void updateOptionName(int opSetIndex, int opIndex, String newName) {
		System.out.println("Option name being updated...");
		opSetList.get(opSetIndex).updateOpName(opIndex, newName);
	}
	
	// updateOptionPrice
	public void updateOptionPrice(int opSetIndex, int opIndex, float newPrice) {
		opSetList.get(opSetIndex).updateOpPrice(opIndex, newPrice);
	}
	
	public void updateChoice(int index, String newName) {
		System.out.println("Option Choice being updated...");
		choice.get(index).setName(newName);
	}
	
	public void showAutoWithChoices() {
		Iterator<Option> iter = choice.iterator();
		
		System.out.println("Displaying Automobile with options selected:");
		System.out.println("Auto name: " + name);
		System.out.println("Total price: " + String.valueOf(getTotalPrice()));
		while (iter.hasNext()) {
			Option selection = iter.next();
			System.out.print("option: ");
			System.out.println(selection.getName() + 
					" ($" + String.valueOf(selection.getPrice()) + ")");
		}
	}
	
	// printAttributes
	public void printAttributes() {
		
		int opSetIndex = 0;
		
		System.out.println("Displaying Model Information");
		System.out.println("=============================");
		System.out.print("Make: ");
		System.out.println(make);
		System.out.print("Model: ");
		System.out.println(model);
		System.out.print("Base Price: $");
		System.out.printf("%.2f\n", price);
		System.out.println("Option Set:");
		
		Iterator<OptionSet> opSetIter = opSetList.iterator();
		while (opSetIter.hasNext()) {
			Iterator<Option> opIter = opSetIter.next().getOpArrayList().iterator();
			System.out.println("\t" + opSetList.get(opSetIndex).getName() + ": ");
			while (opIter.hasNext()) {
				Option opt = opIter.next();
				System.out.println("\t\t" + "Option: " + opt.getName());
				System.out.printf("\t\tPrice: $%.2f\n",  opt.getPrice());
			}
			opSetIndex++;
		}
	}
}
