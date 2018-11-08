/* 
 * Gregory Scott Marvin
 * 
 * CIS 35B Lab 1
 * 
 */

package model;

import java.io.Serializable;
import java.util.*;

/**
 * OptionSet class. Stores data pertaining to a number of 
 * option categories and option values for configuring a car 
 * to be purchased. 
 * 
 * @version   release5 19 June 2018
 * @author    Gregory Scott Marvin
 */
public class OptionSet implements Serializable {
	private String name;
	private ArrayList<Option> opList;
	private Option choice;
	
	public OptionSet() {
		name = "";
		opList = new ArrayList<Option>();
		choice = null;
	}
	
	public OptionSet(String optSetName) {
		name = optSetName;
		opList = new ArrayList<Option>();
		choice = null;
	}
	
	public OptionSet(int size) {
		name = "";
		opList = new ArrayList<Option>(size);
		for (int i = 0; i < size; i++) {
			opList.add(new Option());
		}
		choice = null;
	}
	
	public OptionSet(String set, int size) {
		name = set;
		opList = new ArrayList<Option>(size);
		for (int i = 0; i < size; i++) {
			opList.add(new Option());
		}
		choice = null;
	}
	
	// getName. Returns the name of the OptionSet 
	protected String getName() {
		return name;
	}
	
	// getOption. Returns the Option object at the specified index
	protected Option getOption(int index) {
		return opList.get(index);
	}
	
	// getOptionLength. Returns the length, or number of elements, in the Option array
	protected int getOptionLength() {
		return opList.size();
	}
	
	// getOpArrayList
	protected ArrayList<Option> getOpArrayList() {
		return opList;
	}
	
	
	// getOptionChoice
	protected Option getOptionChoice() {
		return choice;
	}
	
	// setName. Sets the name of the OptionSet
	protected void setName(String optSetName) {
		name = optSetName;
	}
	
	// setOptionChoice
	protected void setOptionChoice(String optName) {
		int index = 0;
		Iterator<Option> iter = opList.iterator();
		while (iter.hasNext()) {
			if (iter.next().getName().equals(optName)) {
				choice = opList.get(index);
			}
			index++;
		}
	}
	
	// deleteOption
	protected void deleteOption(int index) {
		opList.remove(index);
	}
	
	protected void updateOpName(int index, String newName) {
		opList.get(index).setName(newName);
	}
	
	protected void updateOpPrice(int index, float newPrice) {
		opList.get(index).setPrice(newPrice);
	}
	
	// createOptions. Creates a new Option array of indicated size
	protected void createOptions(int size) {
		opList = new ArrayList<Option>(size);
		for (int i = 0; i < size; i++) {
			opList.add(new Option());
		}
	}
	
	/**
	 * Option class. Stores data pertaining to the available
	 * options in each OptionSet category. Data stored is the 
	 * name of the option and the price for the option. 
	 * 
	 * @version   release5 19 June 2018
	 * @author    Gregory Scott Marvin
	 */
	protected class Option implements Serializable {
		private String name;
		private float price;
		
		public Option() {
			name = "";
			price = 0.0f;
		}
		
		public Option(String option) {
			name = option;
			price = 0.0f;
		}
		
		public Option(float cost) {
			name = "";
			price = cost;
		}
		
		public Option(String option, float cost) {
			name = option;
			price = cost;
		}
		
		// getName. Returns the name of the Option object
		protected String getName() {
			return name;
		}
		
		// getPrice. Returns the price of the Option object
		protected float getPrice() {
			return price;
		}
		
		// setName. Sets the name of the Option object
		protected void setName(String option) {
			name = option;
		}
		
		// setPrice. Sets the name of the Option Object
		protected void setPrice(float cost) {
			price = cost;
		}
	}
}
