package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import shared.DisplayMessage;
import shared.IDisplayMessage;
import shared.ProtocolMessageBuilder;

public class Server {

	private InetSocketAddress addr;
	public ConnectedClientsManager clientsManager;
	public IUsersDbManager dbManager;
	public IDisplayMessage dispaly;
	private ProtocolMessageBuilder builder;

	public Server(String ip, int port) {
		this.addr = new InetSocketAddress(ip, port);

		this.clientsManager = new ConnectedClientsManager();
		this.dispaly = new DisplayMessage();
		this.dbManager = new UserDbManager();
		this.dispaly = new DisplayMessage();
		this.builder = new ProtocolMessageBuilder();
	}

	public void run() throws IOException, InterruptedException {
		try (ServerSocket serversocket = new ServerSocket()) {
			serversocket.bind(this.addr);
			boolean stop = false;
			while (!stop) {
				Socket socket = serversocket.accept();
				this.dispaly.show("Got new connection");
				ClientCommThread client = new ClientCommThread(socket, clientsManager, dbManager, dispaly, builder);
				client.run();

			}
		}
	}

}
