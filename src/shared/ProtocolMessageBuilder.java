package shared;

import java.util.List;

public class ProtocolMessageBuilder {
	public ProtocolMessage build(MessageType type, String... params) {
		return new ProtocolMessage(type, params);
	}

	public ProtocolMessage build(String jsonStr) {
		return new ProtocolMessage();
	}

	public ProtocolMessage build(MessageType type, List<String> parms) {
		return new ProtocolMessage(type, parms);
	}
}
