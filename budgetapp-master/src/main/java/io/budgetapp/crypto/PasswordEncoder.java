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
		ASTClass.instrum("Expression Statement","21");
        this.secret = Utf8.encode(secret);
		ASTClass.instrum("Expression Statement","22");
        this.saltGenerator = new SaltGenerator();
		ASTClass.instrum("Expression Statement","23");
    }

    public String encode(CharSequence rawPassword) {
        return encode(rawPassword, saltGenerator.generateKey());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        byte[] digested = decode(encodedPassword);
		ASTClass.instrum("Variable Declaration Statement","31");
        byte[] salt = subArray(digested, 0, saltGenerator.getKeyLength());
		ASTClass.instrum("Variable Declaration Statement","32");
        return matches(digested, digest(rawPassword, salt));
    }

    /**
     * Constant time comparison to prevent against timing attacks.
     */
    private boolean matches(byte[] expected, byte[] actual) {
        if (expected.length != actual.length) {
            return false;
        }
		ASTClass.instrum("If Statement","40");

        int result = 0;
		ASTClass.instrum("Variable Declaration Statement","44");
        for (int i = 0; i < expected.length; i++) {
            result |= expected[i] ^ actual[i];
			ASTClass.instrum("Expression Statement","46");
        }
		ASTClass.instrum("For Statement","45");
        return result == 0;
    }

    private String encode(CharSequence rawPassword, byte[] salt) {
        byte[] digest = digest(rawPassword, salt);
		ASTClass.instrum("Variable Declaration Statement","52");
        return new String(Hex.encode(digest));
    }

    private byte[] decode(CharSequence encodedPassword) {
        return Hex.decode(encodedPassword);
    }


    private byte[] digest(CharSequence rawPassword, byte[] salt) {
        byte[] digest = digester.digest(concatenate(salt, secret, Utf8.encode(rawPassword)));
		ASTClass.instrum("Variable Declaration Statement","62");
        return concatenate(salt, digest);
    }

    /**
     * Combine the individual byte arrays into one array.
     */
    public static byte[] concatenate(byte[]... arrays) {
        int length = 0;
		ASTClass.instrum("Variable Declaration Statement","70");
        for (byte[] array : arrays) {
            length += array.length;
			ASTClass.instrum("Expression Statement","72");
        }
        byte[] newArray = new byte[length];
		ASTClass.instrum("Variable Declaration Statement","74");
        int destPos = 0;
		ASTClass.instrum("Variable Declaration Statement","75");
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, newArray, destPos, array.length);
			ASTClass.instrum("Expression Statement","77");
            destPos += array.length;
			ASTClass.instrum("Expression Statement","78");
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
		ASTClass.instrum("Variable Declaration Statement","90");
        byte[] subarray = new byte[length];
		ASTClass.instrum("Variable Declaration Statement","91");
        System.arraycopy(array, beginIndex, subarray, 0, length);
		ASTClass.instrum("Expression Statement","92");
        return subarray;
    }
}
