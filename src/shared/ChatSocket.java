package shared;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ChatSocket {
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private IMessageJsonConverter converter;

	public ChatSocket(Socket socket) throws IOException {
		this.converter = new MessageJsonConverter();
		this.socket = socket;
		in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
		out = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
	}

	public void send(ProtocolMessage msg) {
		/*
		 * 1. transfer ProtocolMessage to JSON format (String) 2. send the data to the
		 * server
		 */

		try {
			out.writeUTF(this.converter.messageToJson(msg));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ProtocolMessage recieve() {
		try {
			String input = in.readUTF();
			return this.converter.jsonToMessage(input);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
