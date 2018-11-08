package adapter;

import model.Automobile;
import java.util.ArrayList;
import java.util.Properties;

public interface AutoServer {
	
	public void buildWithProperties(Properties properties);
	
	public ArrayList<String> displayModels();
	
	public Automobile sendAuto(int index);

}
