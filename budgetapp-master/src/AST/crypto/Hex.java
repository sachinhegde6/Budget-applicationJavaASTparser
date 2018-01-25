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
		//ASTClass.instrum("Variable Declaration Statement","13");
		//ASTClass.instrum("Variable Declaration Statement","13");
		//ASTClass.instrum("Variable Declaration Statement","13");
		//ASTClass.instrum("Variable Declaration Statement","13");
        char[] result = new char[2*nBytes];
		//ASTClass.instrum("Variable Declaration Statement","14");
		//ASTClass.instrum("Variable Declaration Statement","15");
		//ASTClass.instrum("Variable Declaration Statement","16");
		//ASTClass.instrum("Variable Declaration Statement","17");

        int j = 0;
		//ASTClass.instrum("Variable Declaration Statement","22");
		//ASTClass.instrum("Variable Declaration Statement","20");
		//ASTClass.instrum("Variable Declaration Statement","18");
		//ASTClass.instrum("Variable Declaration Statement","16");
        for (byte b : bytes) {
            // Char for top 4 bits
            result[j++] = HEX[(0xF0 & b) >>> 4];
			//ASTClass.instrum("Expression Statement","28");
			//ASTClass.instrum("Expression Statement","25");
			//ASTClass.instrum("Expression Statement","22");
			//ASTClass.instrum("Expression Statement","19");
            // Bottom 4
            result[j++] = HEX[(0x0F & b)];
			//ASTClass.instrum("Expression Statement","21");
			//ASTClass.instrum("Expression Statement","25");
			//ASTClass.instrum("Expression Statement","29");
			//ASTClass.instrum("Expression Statement","33");
        }

        return result;
    }

    public static byte[] decode(CharSequence s) {
        int nChars = s.length();
		//ASTClass.instrum("Variable Declaration Statement","28");
		//ASTClass.instrum("Variable Declaration Statement","33");
		//ASTClass.instrum("Variable Declaration Statement","38");
		//ASTClass.instrum("Variable Declaration Statement","43");

        if (nChars % 2 != 0) {
            throw new IllegalArgumentException("Hex-encoded string must have an even number of characters");
        }
		//ASTClass.instrum("If Statement","30");
		//ASTClass.instrum("If Statement","36");
		//ASTClass.instrum("If Statement","42");
		//ASTClass.instrum("If Statement","48");

        byte[] result = new byte[nChars / 2];
		//ASTClass.instrum("Variable Declaration Statement","34");
		//ASTClass.instrum("Variable Declaration Statement","41");
		//ASTClass.instrum("Variable Declaration Statement","48");
		//ASTClass.instrum("Variable Declaration Statement","55");

        for (int i = 0; i < nChars; i += 2) {
            int msb = Character.digit(s.charAt(i), 16);
			//ASTClass.instrum("Variable Declaration Statement","61");
			//ASTClass.instrum("Variable Declaration Statement","53");
			//ASTClass.instrum("Variable Declaration Statement","45");
			//ASTClass.instrum("Variable Declaration Statement","37");
            int lsb = Character.digit(s.charAt(i+1), 16);
			//ASTClass.instrum("Variable Declaration Statement","38");
			//ASTClass.instrum("Variable Declaration Statement","47");
			//ASTClass.instrum("Variable Declaration Statement","56");
			//ASTClass.instrum("Variable Declaration Statement","65");

            if (msb < 0 || lsb < 0) {
                throw new IllegalArgumentException("Non-hex character in input: " + s);
            }
			//ASTClass.instrum("If Statement","70");
			//ASTClass.instrum("If Statement","60");
			//ASTClass.instrum("If Statement","50");
			//ASTClass.instrum("If Statement","40");
            result[i / 2] = (byte) ((msb << 4) | lsb);
			//ASTClass.instrum("Expression Statement","43");
			//ASTClass.instrum("Expression Statement","54");
			//ASTClass.instrum("Expression Statement","65");
			//ASTClass.instrum("Expression Statement","76");
        }
		//ASTClass.instrum("For Statement","60");
		//ASTClass.instrum("For Statement","52");
		//ASTClass.instrum("For Statement","44");
		//ASTClass.instrum("For Statement","36");
        return result;
    }

}
