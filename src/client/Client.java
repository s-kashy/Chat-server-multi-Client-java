package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import shared.ChatSocket;
import shared.ClientLisener;
import shared.Confirm;
import shared.DisplayMessage;
import shared.IConfirm;
import shared.IDisplayMessage;
import shared.IHashGenerator;
import shared.IMessageHandler;
import shared.Md5Generator;
import shared.MessageType;
import shared.ProtocolMessage;
import shared.ProtocolMessageBuilder;

public class Client implements IMessageHandler {
	private InetSocketAddress addr;
	private ChatSocket socket;
	private ProtocolMessageBuilder messageBuilder;
	private ClientLisener clientLisner;
	private IDisplayMessage messageDisplay;
	private IConfirm confirm;
	private IHashGenerator hashGenerator;
	private final int needToregister = 4;
	private final int needtologOurMsg = 3;

	public Client(String ip, int port) {
		this.addr = new InetSocketAddress(ip, port);
		this.socket = null;
		this.messageBuilder = new ProtocolMessageBuilder();
		this.messageDisplay = new DisplayMessage();
		this.confirm = new Confirm();
		this.hashGenerator = new Md5Generator();

	}

	public boolean connect() {

		try {
			Socket s = new Socket();
			s.connect(this.addr);
			socket = new ChatSocket(s);
			return true;
		} catch (IOException e) {
			socket = null;
			return false;
		}
	}

	private String concatenateUsers(List<String> users) {
		String res = "";

		for (int i = 0; i < users.size(); i++) {
			res += users.get(i);
			if (i < users.size() - 1)
				res += ", ";
		}
		return res;

	}

	public void run() {

		if (!connect()) {
			System.err.println("[ERROR] failed to connect to server");
			return;
		}
		this.clientLisner = new ClientLisener(this.socket, this);
		this.clientLisner.start();
		Scanner input = new Scanner(System.in);
		boolean exit = false;
		String username = null;
		String password = null;
		String[] parts = null;

		do {
			this.messageDisplay.displayMenu();
			String cpassword = "";

			String choice = input.nextLine().trim();

			parts = choice.split("\\s+");

			switch (parts[0]) {
			case "register":
				if (this.confirm.amountEnteredByUser(parts, needToregister)) {
					if (parts.length == 4) {
						username = parts[1];
						password = parts[2];
						cpassword = parts[3];

						if (this.confirm.confirm(password, cpassword)) {

							socket.send(messageBuilder.build(MessageType.REGISTER, username,
									hashGenerator.generate(password)));
						}
					}

				} else {

					this.messageDisplay.show("NOT GOOD INFO");

				}

				break;
			case "login":

				if (parts.length == 3) {
					username = parts[1];
					password = parts[2];

					socket.send(messageBuilder.build(MessageType.LOGIN, username, hashGenerator.generate(password)));
				} else {
					this.messageDisplay.show("NOT GOOD INFO");

				}
				break;
			case "message":
				if (parts.length >= 3) {
					username = parts[1];
					String message = choice.substring(parts[0].length() + parts[1].length() + 2);

					socket.send(messageBuilder.build(MessageType.PRIVATE_MESSAGE, username, message));
				} else {
					this.messageDisplay.show("NOT GOOD INFO");

				}

				break;
			case "connected-users":
				socket.send(messageBuilder.build(MessageType.CONNECTED_USERS));
				break;
			case "exit":
				exit = true;
				socket.send(messageBuilder.build(MessageType.DISCONNECT));
				break;

			default:
				System.err.println("[ERROR] unknown command");
				break;
			}
		} while (!exit);

	}

	@Override
	public void handel(ProtocolMessage msg) {
		// switch on the MessageType and perform the right action
		/// msg from the server

		switch (msg.type) {

		case PRIVATE_MESSAGE:
			this.messageDisplay.show("[ " + msg.params.get(0) + " ] " + msg.params.get(1));
			break;
		case CONNECTED_USERS:
			this.messageDisplay.show(this.concatenateUsers(msg.params));
			break;
		case REGISTER_SUCCESS:
		case LOGIN_SUCCESS:
		case BAD_CREDENTIALS:
		case USER_ALREADY_EXIST:
		case USER_DOES_NOT_EXIST:
		case USER_IS_NOT_CONNECTED:
		case USER_ALREADY_LOGIN:
		case NEW_USER_JOIN:

			this.messageDisplay.show(msg.type.toString());
			break;
		case DISCONNECT:
			this.messageDisplay.show(MessageType.DISCONNECT.toString());
			clientLisner.exit();
			break;
		default:
			this.messageDisplay.show("Got unkown command from Server=" + msg.type.toString());
			break;
		}

	}

}
