package server;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import shared.ProtocolMessage;

public class ConnectedClientsManager {

	private LinkedBlockingQueue<ClientCommThread> clients;

	public ConnectedClientsManager() {

		this.clients = new LinkedBlockingQueue<>();

	}

	public synchronized boolean isExist(String username) {
		for (ClientCommThread client : clients)
			if (client.getInfo().getUsername().equals(username))
				return true;
		return false;

	}

	public synchronized void addClient(ClientCommThread client) throws InterruptedException {
		clients.put(client);

	}

	public synchronized void sendPrivateMessage(String fromUsername, ProtocolMessage msg) {
		String usernameToSend = msg.params.get(0);
		ClientCommThread clientToSend = this.clients.stream()
				.filter(c -> c.getInfo().getUsername().equals(usernameToSend)).findFirst().get();
		msg.params.set(0, fromUsername);
		clientToSend.sendMessage(msg);

		// find the user in the q to send .set the parm of msg the usermessagefrom
	}

	public synchronized List<String> getConnectedUsers() {
		return this.clients.stream().map(x -> x.getInfo().getUsername()).collect(Collectors.toList());

	}

	public synchronized void removeClient(ClientCommThread client) {
		clients.remove(client);
	}

	public synchronized void sendAll(ClientCommThread client, ProtocolMessage msg) {
		for (ClientCommThread cl : clients) {
			if (!cl.equals(client))
				cl.sendMessage(msg);
		}

	}
}
