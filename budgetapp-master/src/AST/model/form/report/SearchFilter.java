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
		//ASTClass.instrum("Expression Statement","24");
		//ASTClass.instrum("Expression Statement","24");
		//ASTClass.instrum("Expression Statement","24");
		//ASTClass.instrum("Expression Statement","24");
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
		//ASTClass.instrum("Expression Statement","32");
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","34");
		//ASTClass.instrum("Expression Statement","35");
    }

    public Date getStartOn() {
        return startOn;
    }

    public void setStartOn(Date startOn) {
        this.startOn = startOn;
		//ASTClass.instrum("Expression Statement","40");
		//ASTClass.instrum("Expression Statement","42");
		//ASTClass.instrum("Expression Statement","44");
		//ASTClass.instrum("Expression Statement","46");
    }

    public Date getEndOn() {
        return endOn;
    }

    public void setEndOn(Date endOn) {
        this.endOn = endOn;
		//ASTClass.instrum("Expression Statement","48");
		//ASTClass.instrum("Expression Statement","51");
		//ASTClass.instrum("Expression Statement","54");
		//ASTClass.instrum("Expression Statement","57");
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
		//ASTClass.instrum("Expression Statement","56");
		//ASTClass.instrum("Expression Statement","60");
		//ASTClass.instrum("Expression Statement","64");
		//ASTClass.instrum("Expression Statement","68");
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
		//ASTClass.instrum("Variable Declaration Statement","84");
		//ASTClass.instrum("Variable Declaration Statement","79");
		//ASTClass.instrum("Variable Declaration Statement","74");
		//ASTClass.instrum("Variable Declaration Statement","69");
        sb.append("maxAmount=").append(maxAmount);
		//ASTClass.instrum("Expression Statement","88");
		//ASTClass.instrum("Expression Statement","82");
		//ASTClass.instrum("Expression Statement","76");
		//ASTClass.instrum("Expression Statement","70");
        sb.append(", minAmount=").append(minAmount);
		//ASTClass.instrum("Expression Statement","92");
		//ASTClass.instrum("Expression Statement","85");
		//ASTClass.instrum("Expression Statement","78");
		//ASTClass.instrum("Expression Statement","71");
        sb.append(", startOn=").append(startOn);
		//ASTClass.instrum("Expression Statement","96");
		//ASTClass.instrum("Expression Statement","88");
		//ASTClass.instrum("Expression Statement","80");
		//ASTClass.instrum("Expression Statement","72");
        sb.append(", endOn=").append(endOn);
		//ASTClass.instrum("Expression Statement","100");
		//ASTClass.instrum("Expression Statement","91");
		//ASTClass.instrum("Expression Statement","82");
		//ASTClass.instrum("Expression Statement","73");
        sb.append(", auto=").append(auto);
		//ASTClass.instrum("Expression Statement","104");
		//ASTClass.instrum("Expression Statement","94");
		//ASTClass.instrum("Expression Statement","84");
		//ASTClass.instrum("Expression Statement","74");
        sb.append('}');
		//ASTClass.instrum("Expression Statement","108");
		//ASTClass.instrum("Expression Statement","97");
		//ASTClass.instrum("Expression Statement","86");
		//ASTClass.instrum("Expression Statement","75");
        return sb.toString();
    }
}
