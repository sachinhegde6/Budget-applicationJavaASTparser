package io.budgetapp.crypto;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 */
public class Utf8 {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * Get the bytes of the String in UTF-8 encoded form.
     */
    public static byte[] encode(CharSequence string) {
        try {
            ByteBuffer bytes = CHARSET.newEncoder().encode(CharBuffer.wrap(string));
			//ASTClass.instrum("Variable Declaration Statement","21");
			//ASTClass.instrum("Variable Declaration Statement","21");
			//ASTClass.instrum("Variable Declaration Statement","21");
			//ASTClass.instrum("Variable Declaration Statement","21");
            byte[] bytesCopy = new byte[bytes.limit()];
			//ASTClass.instrum("Variable Declaration Statement","25");
			//ASTClass.instrum("Variable Declaration Statement","24");
			//ASTClass.instrum("Variable Declaration Statement","23");
			//ASTClass.instrum("Variable Declaration Statement","22");
            System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());
			//ASTClass.instrum("Expression Statement","23");
			//ASTClass.instrum("Expression Statement","25");
			//ASTClass.instrum("Expression Statement","27");
			//ASTClass.instrum("Expression Statement","29");

            return bytesCopy;
        } catch (CharacterCodingException e) {
            throw new IllegalArgumentException("Encoding failed", e);
        }
    }
}
