package shared;

public interface IMessageJsonConverter {
	String messageToJson (ProtocolMessage msg);
	ProtocolMessage jsonToMessage(String msg);
	

}
