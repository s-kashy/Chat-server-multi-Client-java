package server;

public interface IUsersDbManager {
	void addUser(String username, String passwordHash);

	boolean isExist(String username);

	boolean checkCredentials(String username, String passwordHash);

}
