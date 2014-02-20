package edu.cmu.mgmt.utils;

import java.security.MessageDigest;

public class PasswordUtils {

	public static String md5(String src) {
		if (src == null || src.isEmpty()) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		try {
			char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'A', 'B', 'C', 'D', 'E', 'F' };
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] data = md.digest(src.getBytes());
			for (byte b : data) {
				buffer.append(chars[(b >> 4 & 0x0F)]);
				buffer.append(chars[(b & 0x0F)]);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.err.println(md5("123456"));
	}
}
