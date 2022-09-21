package site.metacoding.red.util;

public class MyUtils {
	public static int 문자길이를바이트로리턴(String s1) {
		int size = 0;
		for (int i = 0; i < s1.length(); i++) {
			int num = s1.charAt(i);
			if (num > 122) {
				size = size + 3;
			} else {
				size = size + 1;
			}
		}
		return size;
	}
}