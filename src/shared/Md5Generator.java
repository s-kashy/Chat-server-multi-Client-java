package shared;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Generator implements IHashGenerator {

	@Override
	public String generate(String s) {
		BigInteger number = null;
		try {
			MessageDigest message = MessageDigest.getInstance("MD5");
			byte[] input = message.digest(s.getBytes());
			number = new BigInteger(1, input);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return number.toString(16);
	}

}
