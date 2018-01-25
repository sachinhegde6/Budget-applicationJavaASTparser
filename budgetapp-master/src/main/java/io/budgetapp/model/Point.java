package io.budgetapp.model;

import java.io.Serializable;

/**
 *
 */
public class Point implements Serializable {

    private static final long serialVersionUID = -4254482540288351126L;
    private String label;
    private long key;
    private double value;
    private PointType pointType;

    public Point(String label, long key, double value, PointType pointType) {
        this.label = label;
		ASTClass.instrum("Expression Statement","17");
        this.key = key;
		ASTClass.instrum("Expression Statement","18");
        this.value = value;
		ASTClass.instrum("Expression Statement","19");
        this.pointType = pointType;
		ASTClass.instrum("Expression Statement","20");
    }

    public String getLabel() {
        return label;
    }

    public long getKey() {
        return key;
    }

    public double getValue() {
        return value;
    }

    public PointType getPointType() {
        return pointType;
    }
}
