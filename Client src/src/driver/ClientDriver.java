package driver;

import client.*;

public class ClientDriver {
	public static void main(String[] args) {
		CarModelOptionsIO client = new CarModelOptionsIO("localhost", 4444);
		client.start();
	}
}
