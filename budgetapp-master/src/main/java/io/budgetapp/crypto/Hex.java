package io.budgetapp.crypto;

/**
 *
 */
public class Hex {

    private static final char[] HEX = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static char[] encode(byte[] bytes) {
        final int nBytes = bytes.length;
		ASTClass.instrum("Variable Declaration Statement","13");
        char[] result = new char[2*nBytes];
		ASTClass.instrum("Variable Declaration Statement","14");

        int j = 0;
		ASTClass.instrum("Variable Declaration Statement","16");
        for (byte b : bytes) {
            // Char for top 4 bits
            result[j++] = HEX[(0xF0 & b) >>> 4];
			ASTClass.instrum("Expression Statement","19");
            // Bottom 4
            result[j++] = HEX[(0x0F & b)];
			ASTClass.instrum("Expression Statement","21");
        }

        return result;
    }

    public static byte[] decode(CharSequence s) {
        int nChars = s.length();
		ASTClass.instrum("Variable Declaration Statement","28");

        if (nChars % 2 != 0) {
            throw new IllegalArgumentException("Hex-encoded string must have an even number of characters");
        }
		ASTClass.instrum("If Statement","30");

        byte[] result = new byte[nChars / 2];
		ASTClass.instrum("Variable Declaration Statement","34");

        for (int i = 0; i < nChars; i += 2) {
            int msb = Character.digit(s.charAt(i), 16);
			ASTClass.instrum("Variable Declaration Statement","37");
            int lsb = Character.digit(s.charAt(i+1), 16);
			ASTClass.instrum("Variable Declaration Statement","38");

            if (msb < 0 || lsb < 0) {
                throw new IllegalArgumentException("Non-hex character in input: " + s);
            }
			ASTClass.instrum("If Statement","40");
            result[i / 2] = (byte) ((msb << 4) | lsb);
			ASTClass.instrum("Expression Statement","43");
        }
		ASTClass.instrum("For Statement","36");
        return result;
    }

}
