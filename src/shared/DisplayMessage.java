package shared;

public class DisplayMessage implements IDisplayMessage {

	@Override
	public synchronized void show(String msg) {
		System.out.println(msg);

	}

	@Override
	public synchronized void displayMenu() {
		System.out.println("-==============Menu==============-");
		System.out.println("register <username> <password> <cpassword>");
		System.out.println("login <username> <password>");
		System.out.println("message <username> <message>");
		System.out.println("connected-users");
		System.out.println("exit");
		System.out.println("-================================-");
	}
}
