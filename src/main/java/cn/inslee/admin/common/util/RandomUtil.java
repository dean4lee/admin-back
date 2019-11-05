package cn.inslee.admin.common.util;

/**
 * @author dean.lee
 */
public class RandomUtil {
	private static String[] randomChar = {
			"a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x",
			"y", "z",
			"A", "B", "C", "D", "E", "F",
			"G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X",
			"Y", "Z",
			"0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9"
	};
	private static String[] randomNum = {
			"0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9"
	};

	public static String getRandomChar(int len) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < len; i++) {
			result.append(randomChar[(int) (Math.random() * randomChar.length)]);
		}
		return result.toString();
	}

	public static String getRandomNum(int len) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < len; i++) {
			result.append(randomNum[(int) (Math.random() * randomNum.length)]);
		}
		return result.toString();
	}

}
