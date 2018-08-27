package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import shared.ChatSocket;
import shared.ClientLisener;
import shared.IDisplayMessage;
import shared.IMessageHandler;
import shared.Logger;
import shared.MessageType;
import shared.ProtocolMessage;
import shared.ProtocolMessageBuilder;

public class ClientCommThread implements Runnable, IMessageHandler {

	private ChatSocket socket;
	private ClientLisener listener;
	private IDisplayMessage messageDisplayer;
	private ConnectedClientsManager clientsManager;
	private IUsersDbManager dbManager;
	private ProtocolMessageBuilder messageBuilder;
	private ClientInfo info;

	public ClientCommThread(Socket sock, ConnectedClientsManager clientsManager, IUsersDbManager dbManager,
			IDisplayMessage messageDisplayer, ProtocolMessageBuilder messageBuilder) throws IOException {
		this.socket = new ChatSocket(sock);
		this.listener = new ClientLisener(this.socket, this);
		this.messageDisplayer = messageDisplayer;
		this.clientsManager = clientsManager;
		this.messageBuilder = messageBuilder;
		this.dbManager = dbManager;
		this.info = null;
	}

	public synchronized void sendMessage(ProtocolMessage msg) {
		this.socket.send(msg);
	}

	public ClientInfo getInfo() {
		return info;
	}

	@Override
	public void handel(ProtocolMessage msg) throws InterruptedException {

		Logger.log("Handling msg " + msg.toString());

		switch (msg.type) {

		case LOGIN:

			String userName = msg.params.get(0);
			String passwordHash = msg.params.get(1);
			if (!this.dbManager.isExist(userName)) {
				this.sendMessage(messageBuilder.build(MessageType.USER_DOES_NOT_EXIST));
			} else if (!this.dbManager.checkCredentials(userName, passwordHash)) {
				this.sendMessage(messageBuilder.build(MessageType.BAD_CREDENTIALS));
			} else if (this.clientsManager.isExist(userName)) {
				this.sendMessage(messageBuilder.build(MessageType.USER_ALREADY_LOGIN));
			} else {
				this.sendMessage(messageBuilder.build(MessageType.LOGIN_SUCCESS));
				this.info = new ClientInfo(userName, passwordHash);
				this.clientsManager.addClient(this);
				this.clientsManager.sendAll(this, new ProtocolMessage(MessageType.NEW_USER_JOIN, userName));
			}

			break;
		case REGISTER:

			String userName1 = msg.params.get(0);
			String passwordHash1 = msg.params.get(1);
			if (!this.dbManager.isExist(userName1)) {
				this.sendMessage(messageBuilder.build(MessageType.REGISTER_SUCCESS));
				this.dbManager.addUser(userName1, passwordHash1);

			} else {
				this.sendMessage(messageBuilder.build(MessageType.USER_ALREADY_EXIST));
			}

			break;
		case PRIVATE_MESSAGE:
			this.clientsManager.sendPrivateMessage(this.getInfo().getUsername(), msg);
			break;
		case CONNECTED_USERS:
			ArrayList<String> arr = (ArrayList<String>) this.clientsManager.getConnectedUsers();
			Logger.log(arr);
			this.sendMessage(messageBuilder.build(MessageType.CONNECTED_USERS, arr));
			break;
		case DISCONNECT:
			this.clientsManager.removeClient(this);
			this.listener.exit();
			break;
		default:
			this.messageDisplayer.show("Got no know command from Client");
			break;
		}

	}

	@Override
	public void run() {
		this.listener.start();
	}
}
