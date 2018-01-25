package io.budgetapp.model.form.report;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class SearchFilter implements Serializable {

    private static final long serialVersionUID = 2152292419706145722L;

    private Double maxAmount;
    private Double minAmount;
    private Date startOn;
    private Date endOn;
    private Boolean auto;

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
		ASTClass.instrum("Expression Statement","24");
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
		ASTClass.instrum("Expression Statement","32");
    }

    public Date getStartOn() {
        return startOn;
    }

    public void setStartOn(Date startOn) {
        this.startOn = startOn;
		ASTClass.instrum("Expression Statement","40");
    }

    public Date getEndOn() {
        return endOn;
    }

    public void setEndOn(Date endOn) {
        this.endOn = endOn;
		ASTClass.instrum("Expression Statement","48");
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
		ASTClass.instrum("Expression Statement","56");
    }

    public boolean isDateRange() {
        return getStartOn() != null && getEndOn() != null;
    }

    public boolean isAmountRange() {
        return getMaxAmount() != null && getMinAmount() != null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchFilter{");
		ASTClass.instrum("Variable Declaration Statement","69");
        sb.append("maxAmount=").append(maxAmount);
		ASTClass.instrum("Expression Statement","70");
        sb.append(", minAmount=").append(minAmount);
		ASTClass.instrum("Expression Statement","71");
        sb.append(", startOn=").append(startOn);
		ASTClass.instrum("Expression Statement","72");
        sb.append(", endOn=").append(endOn);
		ASTClass.instrum("Expression Statement","73");
        sb.append(", auto=").append(auto);
		ASTClass.instrum("Expression Statement","74");
        sb.append('}');
		ASTClass.instrum("Expression Statement","75");
        return sb.toString();
    }
}
