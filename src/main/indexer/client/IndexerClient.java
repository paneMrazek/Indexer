package main.indexer.client;

import main.indexer.shared.communication.ClientCommunicator;

public class IndexerClient{
	public static void main(String[] args){
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		ClientCommunicator.getInstance().initilize(hostname,port);
		IndexerGUI gui = new IndexerGUI();
		gui.start();
	}
}
