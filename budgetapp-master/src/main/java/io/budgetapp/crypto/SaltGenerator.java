package io.budgetapp.crypto;

import java.security.SecureRandom;

/**
 *
 */
public class SaltGenerator {

    private static final int DEFAULT_KEY_LENGTH = 8;

    private final SecureRandom random;

    public SaltGenerator() {
        this.random = new SecureRandom();
		ASTClass.instrum("Expression Statement","15");
    }

    public byte[] generateKey() {
        byte[] bytes = new byte[DEFAULT_KEY_LENGTH];
		ASTClass.instrum("Variable Declaration Statement","19");
        random.nextBytes(bytes);
		ASTClass.instrum("Expression Statement","20");
        return bytes;
    }


    public int getKeyLength() {
        return DEFAULT_KEY_LENGTH;
    }
}
