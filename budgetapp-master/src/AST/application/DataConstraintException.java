package io.budgetapp.application;

/**
 *
 */
public class DataConstraintException extends RuntimeException {

    private static final long serialVersionUID = 4029133736296771145L;

    private final String path;

    public DataConstraintException(String path, String message) {
        super(message);
        this.path = path;
		//ASTClass.instrum("Expression Statement","14");
		//ASTClass.instrum("Expression Statement","14");
		//ASTClass.instrum("Expression Statement","14");
		//ASTClass.instrum("Expression Statement","14");
    }

    public String getPath() {
        return path;
    }
}
