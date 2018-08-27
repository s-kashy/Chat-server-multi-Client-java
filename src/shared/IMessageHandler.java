package shared;

public interface IMessageHandler {
	void handel(ProtocolMessage msg) throws InterruptedException;
}
