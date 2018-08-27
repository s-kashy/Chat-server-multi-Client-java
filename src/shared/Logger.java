package shared;

import java.util.List;

public class Logger {
	public static boolean debug;

	public synchronized static void log(String s) {
		if (debug) {
			System.out.println(s);
		}
	}

	public synchronized static void log(List<String> s) {
		String res = "";
		if (debug) {
			for (String s1 : s) {
				res += s1 + ",";
			}
			System.out.println(res);
		}
	}
}
