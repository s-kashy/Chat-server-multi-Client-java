package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class UserDbManager implements IUsersDbManager {
	private List<ClientInfo> DbUserRegiser;
	private File file;
	private ObjectMapper mapper;

	public UserDbManager() {

		mapper = new ObjectMapper();
		file = new File("DBUserRegeister");
		setDbManneger();
	}

	@Override
	public synchronized void addUser(String username, String passwordHash) {

		DbUserRegiser.add(new ClientInfo(username, passwordHash));
		try {
			mapper.writeValue(file, DbUserRegiser);
		} catch (IOException e) {

		}
	}

	@Override
	public synchronized boolean isExist(String username) {

		for (ClientInfo client : DbUserRegiser) {
			if (client.getUsername().equals(username)) {
				return true;
			}

		}
		return false;

	}

	@Override
	public boolean checkCredentials(String username, String passwordHash) {
		for (ClientInfo client : DbUserRegiser) {
			if (client.getUsername().equals(username) && client.getPasswordHash().equals(passwordHash)) {
				return true;
			}

		}
		return false;

	}

	public void setDbManneger() {

		if (this.file.exists()) {
			byte[] JsonInByte;
			try {
				JsonInByte = Files.readAllBytes(file.toPath());
				this.DbUserRegiser = mapper.readValue(JsonInByte, new TypeReference<List<ClientInfo>>() {
				});

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.DbUserRegiser = new ArrayList<>();
		}

	}
}
