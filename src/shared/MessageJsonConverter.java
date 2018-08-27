package shared;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class MessageJsonConverter implements IMessageJsonConverter {

	public String messageToJson(ProtocolMessage msg) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(msg);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ProtocolMessage jsonToMessage(String msg) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(msg, ProtocolMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
