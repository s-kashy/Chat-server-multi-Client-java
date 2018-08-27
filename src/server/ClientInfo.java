package server;

public class ClientInfo {
	private String username;
	private String passwordHash;

	public ClientInfo(String username, String passwordhash) {
		this.username = username;
		this.passwordHash = passwordhash;
	}

	public ClientInfo() {
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getUsername() {
		return username;
	}
}
