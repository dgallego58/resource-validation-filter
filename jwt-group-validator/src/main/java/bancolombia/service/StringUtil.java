package bancolombia.service;

public class StringUtil {

    private StringUtil() {
        //util
    }

    public static String toHex(String string) {

        char[] charArray = string.toCharArray();
        StringBuilder hexConcat = new StringBuilder();
        for (int i = 0; i < charArray.length; i = i + 2) {
            String segment = String.valueOf(charArray[i]) + charArray[i + 1];
            hexConcat.append(Integer.parseInt(segment, 16));
        }
        return hexConcat.toString().toUpperCase();
    }

    public static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte singleByte : bytes) {
            sb.append(String.format("%02x", singleByte));
        }
        return sb.toString().toUpperCase();
    }

}
