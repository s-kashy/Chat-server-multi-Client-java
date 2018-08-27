package main;

import java.io.IOException;

import client.Client;
import server.Server;
import shared.Logger;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		if (args.length != 4) {
			usage();
			return;
		}

		Logger.debug = Boolean.parseBoolean(args[3]);

		switch (args[0]) {
		case "server":
			Server server = new Server(args[1], Integer.parseInt(args[2]));
			server.run();
			break;
		case "client":
			Client client = new Client(args[1], Integer.parseInt(args[2]));
			client.run();
			break;
		default:
			usage();
			break;

		}
	}

	private static void usage() {
		System.err.println("Usage: main.jar [server|client] IP PORT [DEBUG:false|true]");
	}
}
