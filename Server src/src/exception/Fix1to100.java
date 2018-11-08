package exception;

import java.util.Scanner;

/**
 * Fix1To100 class. Contains the fix methods for errors 1 through 100. 
 * 
 * @version   release4 29 May 2018
 * @author    Gregory Scott Marvin
 */
public class Fix1to100 {
	
	public String fix1() {
		System.out.println("Error reading in model name from file, please enter a model name.\n");
		
		Scanner input = new Scanner(System.in);
		String newModelName = input.nextLine();
		input.close();
		
		return newModelName;
	}
	
	public float fix2() {
		System.out.println("Error reading in model price from file, please enter a model price.\n");
		
		Scanner input = new Scanner(System.in);
		float newModelPrice = input.nextFloat();
		input.close();
		
		return newModelPrice;
	}
	
	

}
