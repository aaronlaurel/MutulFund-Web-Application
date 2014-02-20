package edu.cmu.mgmt.utils;

import org.apache.commons.lang3.StringUtils;

public class ValidateUtil {

	private static final int MAXNUMBER = 100000000;

	public static boolean isValid(String str) {
		return StringUtils.isNotEmpty(str);
	}

	// Check whether str valid double number and not larger then the int_max
	public static String isNumberValid(String str, int decimal) {
		double num = 0;
		try {
			num = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			e.getStackTrace();
			return "The number you input should be a valid number";
		}
		if (str.contains(".")) {
			int index = str.indexOf('.');
			if (str.length() - index - 1 > decimal) {
				return "The number you input should have maximum " + decimal
						+ " decimal places";
			}
		}
		if (num > MAXNUMBER || num <= 0) {
			return "The number you input should be larger than 0 and equal to or less than 100,000,000";
		}
		return null;
	}

	// Check whether str valid id
	public static boolean isIDValid(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			e.getStackTrace();
			return false;
		}
		return true;
	}

	public static boolean isValidInput(String str) {
		if (str.matches(".*[<>\"].*")) {
			return false;
		}
		return true;
	}

	public static boolean isValidLength(String str, int min, int max) {
		if (str.length() >= min && str.length() <= max) {
			return true;
		} else {
			return false;
		}
	}

	public static String getRandom() {
		StringBuffer sb = new StringBuffer();
		for (int x = 0; x < 5; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		for (int x = 0; x < 3; x++) {
			sb.append((int) (Math.random() * 10));
		}
		return sb.toString();
	}
}
