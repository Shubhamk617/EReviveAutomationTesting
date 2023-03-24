package utils;

import org.apache.log4j.Logger;

public class LoggerFile {
	private static boolean root = false;

	public static Logger getLogger(Class cls) {
		if (root) {
			return Logger.getLogger(cls);
		}	root = true;
		return Logger.getLogger(cls);
	}
}
