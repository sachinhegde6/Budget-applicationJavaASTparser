package io.budgetapp.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Data implements Serializable {
    private static final long serialVersionUID = 18900214111166232L;

    private String x;
    private List<Double> y;

    public Data(String x, List<Double> y) {
        this.x = x;
		//ASTClass.instrum("Expression Statement","17");
		//ASTClass.instrum("Expression Statement","17");
		//ASTClass.instrum("Expression Statement","17");
		//ASTClass.instrum("Expression Statement","17");
        this.y = Collections.unmodifiableList(y);
		//ASTClass.instrum("Expression Statement","18");
		//ASTClass.instrum("Expression Statement","19");
		//ASTClass.instrum("Expression Statement","20");
		//ASTClass.instrum("Expression Statement","21");
    }

    public String getX() {
        return x;
    }

    public List<Double> getY() {
        return y;
    }
}
