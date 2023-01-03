package edu.kh.network.run;

import edu.kh.network.model.service.TDPServer;

public class ServerRun {
	
	public static void main(String[] args) {
		
		TDPServer tcp = new TDPServer();
		
		tcp.serverStart();
		
	}

}
