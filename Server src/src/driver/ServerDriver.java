package driver;

import server.*;

public class ServerDriver {
	public static void main(String[] args) {
		BuildCarModelOptions server = new BuildCarModelOptions(4444);
		server.serverSetUp();
		server.connectStreams();
	}
}
