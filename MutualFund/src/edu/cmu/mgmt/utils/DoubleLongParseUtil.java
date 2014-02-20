package edu.cmu.mgmt.utils;

public class DoubleLongParseUtil {
    // Must call ValidateUtil.is isNumberValid first
    public static long parseAmount(String str) {
        double d = Double.parseDouble(str);
        return (long) (d * 100);
    }

    // Must call ValidateUtil.is isNumberValid first
    public static long parseShares(String str) {
        double d = Double.parseDouble(str);
        return (long) (d * 1000);
    }
}
