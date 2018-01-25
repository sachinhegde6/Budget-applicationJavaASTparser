package io.budgetapp.crypto;


/**
 *
 */
public class PasswordEncoder {

    private static final int DEFAULT_ITERATIONS = 1024;
    private static final String DEFAULT_ALGORITHM = "SHA-256";

    private final SaltGenerator saltGenerator;
    private final Digester digester;
    private final byte[] secret;

    public PasswordEncoder() {
        this("");
    }

    public PasswordEncoder(String secret) {
        this.digester = new Digester(DEFAULT_ALGORITHM, DEFAULT_ITERATIONS);
		//ASTClass.instrum("Expression Statement","21");
		//ASTClass.instrum("Expression Statement","21");
		//ASTClass.instrum("Expression Statement","21");
		//ASTClass.instrum("Expression Statement","21");
        this.secret = Utf8.encode(secret);
		//ASTClass.instrum("Expression Statement","25");
		//ASTClass.instrum("Expression Statement","24");
		//ASTClass.instrum("Expression Statement","23");
		//ASTClass.instrum("Expression Statement","22");
        this.saltGenerator = new SaltGenerator();
		//ASTClass.instrum("Expression Statement","23");
		//ASTClass.instrum("Expression Statement","25");
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","29");
    }

    public String encode(CharSequence rawPassword) {
        return encode(rawPassword, saltGenerator.generateKey());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        byte[] digested = decode(encodedPassword);
		//ASTClass.instrum("Variable Declaration Statement","40");
		//ASTClass.instrum("Variable Declaration Statement","37");
		//ASTClass.instrum("Variable Declaration Statement","34");
		//ASTClass.instrum("Variable Declaration Statement","31");
        byte[] salt = subArray(digested, 0, saltGenerator.getKeyLength());
		//ASTClass.instrum("Variable Declaration Statement","44");
		//ASTClass.instrum("Variable Declaration Statement","40");
		//ASTClass.instrum("Variable Declaration Statement","36");
		//ASTClass.instrum("Variable Declaration Statement","32");
        return matches(digested, digest(rawPassword, salt));
    }

    /**
     * Constant time comparison to prevent against timing attacks.
     */
    private boolean matches(byte[] expected, byte[] actual) {
        if (expected.length != actual.length) {
            return false;
        }
		//ASTClass.instrum("If Statement","40");
		//ASTClass.instrum("If Statement","45");
		//ASTClass.instrum("If Statement","50");
		//ASTClass.instrum("If Statement","55");

        int result = 0;
		//ASTClass.instrum("Variable Declaration Statement","62");
		//ASTClass.instrum("Variable Declaration Statement","56");
		//ASTClass.instrum("Variable Declaration Statement","50");
		//ASTClass.instrum("Variable Declaration Statement","44");
        for (int i = 0; i < expected.length; i++) {
            result |= expected[i] ^ actual[i];
			//ASTClass.instrum("Expression Statement","46");
			//ASTClass.instrum("Expression Statement","53");
			//ASTClass.instrum("Expression Statement","60");
			//ASTClass.instrum("Expression Statement","67");
        }
		//ASTClass.instrum("For Statement","66");
		//ASTClass.instrum("For Statement","59");
		//ASTClass.instrum("For Statement","52");
		//ASTClass.instrum("For Statement","45");
        return result == 0;
    }

    private String encode(CharSequence rawPassword, byte[] salt) {
        byte[] digest = digest(rawPassword, salt);
		//ASTClass.instrum("Variable Declaration Statement","79");
		//ASTClass.instrum("Variable Declaration Statement","70");
		//ASTClass.instrum("Variable Declaration Statement","61");
		//ASTClass.instrum("Variable Declaration Statement","52");
        return new String(Hex.encode(digest));
    }

    private byte[] decode(CharSequence encodedPassword) {
        return Hex.decode(encodedPassword);
    }


    private byte[] digest(CharSequence rawPassword, byte[] salt) {
        byte[] digest = digester.digest(concatenate(salt, secret, Utf8.encode(rawPassword)));
		//ASTClass.instrum("Variable Declaration Statement","92");
		//ASTClass.instrum("Variable Declaration Statement","82");
		//ASTClass.instrum("Variable Declaration Statement","72");
		//ASTClass.instrum("Variable Declaration Statement","62");
        return concatenate(salt, digest);
    }

    /**
     * Combine the individual byte arrays into one array.
     */
    public static byte[] concatenate(byte[]... arrays) {
        int length = 0;
		//ASTClass.instrum("Variable Declaration Statement","103");
		//ASTClass.instrum("Variable Declaration Statement","92");
		//ASTClass.instrum("Variable Declaration Statement","81");
		//ASTClass.instrum("Variable Declaration Statement","70");
        for (byte[] array : arrays) {
            length += array.length;
			//ASTClass.instrum("Expression Statement","72");
			//ASTClass.instrum("Expression Statement","84");
			//ASTClass.instrum("Expression Statement","96");
			//ASTClass.instrum("Expression Statement","108");
        }
        byte[] newArray = new byte[length];
		//ASTClass.instrum("Variable Declaration Statement","113");
		//ASTClass.instrum("Variable Declaration Statement","100");
		//ASTClass.instrum("Variable Declaration Statement","87");
		//ASTClass.instrum("Variable Declaration Statement","74");
        int destPos = 0;
		//ASTClass.instrum("Variable Declaration Statement","117");
		//ASTClass.instrum("Variable Declaration Statement","103");
		//ASTClass.instrum("Variable Declaration Statement","89");
		//ASTClass.instrum("Variable Declaration Statement","75");
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, newArray, destPos, array.length);
			//ASTClass.instrum("Expression Statement","122");
			//ASTClass.instrum("Expression Statement","107");
			//ASTClass.instrum("Expression Statement","92");
			//ASTClass.instrum("Expression Statement","77");
            destPos += array.length;
			//ASTClass.instrum("Expression Statement","78");
			//ASTClass.instrum("Expression Statement","94");
			//ASTClass.instrum("Expression Statement","110");
			//ASTClass.instrum("Expression Statement","126");
        }
        return newArray;
    }

    /**
     * Extract a sub array of bytes out of the byte array.
     * @param array the byte array to extract from
     * @param beginIndex the beginning index of the sub array, inclusive
     * @param endIndex the ending index of the sub array, exclusive
     */
    public static byte[] subArray(byte[] array, int beginIndex, int endIndex) {
        int length = endIndex - beginIndex;
		//ASTClass.instrum("Variable Declaration Statement","141");
		//ASTClass.instrum("Variable Declaration Statement","124");
		//ASTClass.instrum("Variable Declaration Statement","107");
		//ASTClass.instrum("Variable Declaration Statement","90");
        byte[] subarray = new byte[length];
		//ASTClass.instrum("Variable Declaration Statement","145");
		//ASTClass.instrum("Variable Declaration Statement","127");
		//ASTClass.instrum("Variable Declaration Statement","109");
		//ASTClass.instrum("Variable Declaration Statement","91");
        System.arraycopy(array, beginIndex, subarray, 0, length);
		//ASTClass.instrum("Expression Statement","149");
		//ASTClass.instrum("Expression Statement","130");
		//ASTClass.instrum("Expression Statement","111");
		//ASTClass.instrum("Expression Statement","92");
        return subarray;
    }
}
