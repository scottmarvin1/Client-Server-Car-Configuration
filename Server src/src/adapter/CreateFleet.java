package adapter;

import model.Automobile;

public interface CreateFleet {
	
	public void createFleet(int fleetSize);
	
	public void addToFleet(String name, Automobile auto);
	
	public int getSize();
	
	public void printFleet();

}
