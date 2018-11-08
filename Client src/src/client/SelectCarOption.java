package client;

import java.io.*;
import model.Automobile;

/**
 * SelectCarOptionsClass. Receives an Automobile for designing.
 * Allows user to select desired options and shows total for 
 * their newly designed autmobile.
 * 
 * @version   release5 19 June 2018
 * @author    Gregory Scott Marvin
 */
public class SelectCarOption {
	private Automobile auto;
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	public SelectCarOption(Automobile auto) {
		this.auto = auto;
		displayWelcomeInstructions();
	}
	
	//
	public void makeSelection() {
		String selection = "";
		for (int i = 0; i < auto.getOpSetLength(); i++) {
			System.out.println("Please enter the option name for: " + auto.getOptionSetName(i));
			System.out.print("option: ");
			try {
				selection = input.readLine();
			} catch (IOException e) {
				System.out.println("Error, could not read input from user.");
			}
			auto.setOptionChoice(auto.getOptionSetName(i), selection);
		}
	}
	
	public void showChoices() {
		auto.showAutoWithChoices();
	}
	
	public void displayWelcomeInstructions() {
		System.out.println("=== Welcome to the configuration module! ===");
		System.out.println("Please use the above Automobile attributes");
		System.out.println("listing for the possible options you may");
		System.out.println("choose from a certain options set. Your");
		System.out.println("designed Automobile will be displayed after");
		System.out.println("all options are selected and your total");
		System.out.println("price will be shown as well.");
	}
}
