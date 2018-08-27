package shared;

public class ClientLisener extends Thread {

	private ChatSocket socket;
	private IMessageHandler handler;
	private boolean exit;

	public ClientLisener(ChatSocket socket, IMessageHandler handler) {
		this.handler = handler;
		this.socket = socket;
		this.exit = false;
	}

	@Override
	public void run() {
		System.out.println("starting to run");
		while (!exit) {
			try {
				this.handler.handel(this.socket.recieve());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void exit() {
		this.exit = true;
	}
}
