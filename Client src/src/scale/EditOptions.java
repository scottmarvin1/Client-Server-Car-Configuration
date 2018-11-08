package scale;

import adapter.*;
import model.Automobile;

import java.util.*;

/**
 * EditOptions class. Allows for the updating of an OptionSet value of an Automobile
 * contained in the LinkedHashMap myFleet from ProxyAutoMobile. Two major methods
 * of the EditOptions class is updateOption and updateOptionNoSync, which are the 
 * synchronized and no-synchronized implementations respectively.
 * 
 * @version   release4 29 May 2018
 * @author    Gregory Scott Marvin
 */
public class EditOptions extends ProxyAutomobile implements Runnable {
	
	private int opNum;
	private int threadno;
	private String[] input;
	private Thread t;
	private boolean running;
	

	public EditOptions(int opNum, int threadno, LinkedHashMap<String, Automobile> fleet) {
		this.opNum = opNum;
		this.threadno = threadno;
		t = new Thread(this);
	}

	public void run() {
		while (running) {
			switch (opNum) {
			case 0:
				System.out.println("Starting thread " + threadno + " changeOption with sync");
				break;
			case 1:
				System.out.println("Start thread " + threadno + " ChangeOption no sync");
				break;
			}
			ops();
		}
		
		System.out.println("Stopping thread " + threadno);
	}
	
	public void start(String[] input) {
		this.input = input;
		t.start();
		running = true;
	}
	
	public void stop() {
		running = false;
	}

	public void ops() {
		switch (opNum) {
		case 0:
			changeOption();
			break;
		case 1:
			changeOptionNoSync();
			break;
		}
	}
	/*
	public void changeOption() {
		System.out.println("Entering changeOption method with synchronization...");
		Automobile auto = null;
		boolean found = false;
		int opSetIndex;
		int opIndex;
		
		synchronized (myFleet) {
			Collection<Automobile> autoList = myFleet.values();
			Iterator<Automobile> iter = autoList.iterator();
			
			while(iter.hasNext() && !found) {
				auto = iter.next();
				if (auto.getName().equals(input[2])) {
					opSetIndex = auto.findOptionSetIndex(input[3]);
					opIndex = auto.findOptionIndex(input[4], opSetIndex);
					if (opIndex != 1000) {
						auto.updateOptionName(opSetIndex, opIndex, input[5]);
						myFleet.put(auto.getName(), auto);
						found = true;
					}
					else {
						System.out.println("Error, could not find OptionSet based on passed argument");
					}
				}
			}
			System.out.println("Printing after potential race condition...");
			printOneAuto(auto.getName());
			stop();
			System.out.println("finished with the updating for: " + String.valueOf(threadno));
	
		}// end sync
	}*/
	
	public void changeOption() {
		System.out.println("Entering changeOption method with synchronization...");
		Automobile auto = myFleet.get(input[2]);
		int opSetIndex;
		int opIndex;
		
		synchronized (auto) {
			opSetIndex = auto.findOptionSetIndex(input[3]);
			opIndex = auto.findOptionIndex(input[4], opSetIndex);
			if (opIndex != 1000) {
				auto.updateOptionName(opSetIndex, opIndex, input[5]);
				myFleet.put(auto.getName(), auto);
				//found = true;
			}
			System.out.println("Printing after potential race condition...");
			printOneAuto(auto.getName());
			stop();
			System.out.println("finished with the updating for: " + String.valueOf(threadno));
		} // end sync
	}
	
	public void changeOptionNoSync() {
		System.out.println("Entering changeOption method without synchronization...");
		Automobile auto = null;
		boolean found = false;
		int opSetIndex;
		int opIndex;
		Collection<Automobile> autoList = myFleet.values();
		Iterator<Automobile> iter = autoList.iterator();
		
		while(iter.hasNext() && !found) {
			auto = iter.next();
			if (auto.getName().equals(input[2])) {
				opSetIndex = auto.findOptionSetIndex(input[3]);
				opIndex = auto.findOptionIndex(input[4], opSetIndex);
				if (opIndex != 1000) {
					auto.updateOptionName(opSetIndex, opIndex, input[5]);
					myFleet.put(auto.getName(), auto);
					found = true;
				}
				else {
					System.out.println("Error, could not find OptionSet based on passed argument");
				}
			}
		}
		System.out.println("Printing after potential race condition...");
		printOneAuto(auto.getName());
		stop();
		System.out.println("finished with the updating for: " + String.valueOf(threadno));
		
	}
	
	

}
