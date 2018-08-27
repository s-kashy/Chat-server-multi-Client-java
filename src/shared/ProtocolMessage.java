package shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProtocolMessage {
	public MessageType type;
	public List<String> params;

	public ProtocolMessage(MessageType type, String... params) {
		this.type = type;
		this.params = Arrays.asList(params);
	}

	public ProtocolMessage(MessageType type, List<String> parms) {
		this.type = type;
		this.params = new ArrayList<>(parms);
	}

	public ProtocolMessage() {
	}

	public ProtocolMessage(String... params) {
		this.params = Arrays.asList(params);
	}

	@Override
	public String toString() {

		return this.type.toString() + " { " + getParmsToString() + " }";

	}

	private String getParmsToString() {
		String s = "";
		for (String s1 : this.params) {
			s += s1 + ", ";
		}
		return s;
	}
}
