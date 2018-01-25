package io.budgetapp.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class Digester {

    private final MessageDigest messageDigest;

    private final int iterations;

    /**
     * Create a new Digester.
     * @param algorithm the digest algorithm; for example, "SHA-1" or "SHA-256".
     * @param iterations the number of times to apply the digest algorithm to the input
     */
    public Digester(String algorithm, int iterations) {
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
			ASTClass.instrum("Expression Statement","22");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No such hashing algorithm", e);
        }

        this.iterations = iterations;
		ASTClass.instrum("Expression Statement","27");
    }

    public byte[] digest(byte[] value) {
        synchronized (messageDigest) {
            for (int i = 0; i < iterations; i++) {
                value = messageDigest.digest(value);
				ASTClass.instrum("Expression Statement","33");
            }
			ASTClass.instrum("For Statement","32");
            return value;
        }
    }
}
