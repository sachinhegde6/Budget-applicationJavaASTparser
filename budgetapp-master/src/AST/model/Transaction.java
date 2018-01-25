package io.budgetapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 2625666273036891436L;

    private Long id;
    private String name;
    private double amount;
    private String remark;
    private boolean auto;
    private Date transactionOn;
    private Date createdAt;
    private Budget budget;
    private Recurring recurring;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","33");
    }

    @Column(nullable = false, updatable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
		//ASTClass.instrum("Expression Statement","42");
		//ASTClass.instrum("Expression Statement","43");
		//ASTClass.instrum("Expression Statement","44");
		//ASTClass.instrum("Expression Statement","45");
    }

    @Column(nullable = false, updatable = false)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
		//ASTClass.instrum("Expression Statement","51");
		//ASTClass.instrum("Expression Statement","53");
		//ASTClass.instrum("Expression Statement","55");
		//ASTClass.instrum("Expression Statement","57");
    }

    @Column(updatable = false)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
		//ASTClass.instrum("Expression Statement","60");
		//ASTClass.instrum("Expression Statement","63");
		//ASTClass.instrum("Expression Statement","66");
		//ASTClass.instrum("Expression Statement","69");
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
		//ASTClass.instrum("Expression Statement","68");
		//ASTClass.instrum("Expression Statement","72");
		//ASTClass.instrum("Expression Statement","76");
		//ASTClass.instrum("Expression Statement","80");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_on", nullable = false, updatable = false)
    public Date getTransactionOn() {
        return transactionOn;
    }

    public void setTransactionOn(Date transactionOn) {
        this.transactionOn = transactionOn;
		//ASTClass.instrum("Expression Statement","78");
		//ASTClass.instrum("Expression Statement","83");
		//ASTClass.instrum("Expression Statement","88");
		//ASTClass.instrum("Expression Statement","93");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
		//ASTClass.instrum("Expression Statement","88");
		//ASTClass.instrum("Expression Statement","94");
		//ASTClass.instrum("Expression Statement","100");
		//ASTClass.instrum("Expression Statement","106");
    }

    @JoinColumn(updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
		//ASTClass.instrum("Expression Statement","98");
		//ASTClass.instrum("Expression Statement","105");
		//ASTClass.instrum("Expression Statement","112");
		//ASTClass.instrum("Expression Statement","119");
    }

    @JoinColumn(updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Recurring getRecurring() {
        return recurring;
    }

    public void setRecurring(Recurring recurring) {
        this.recurring = recurring;
		//ASTClass.instrum("Expression Statement","108");
		//ASTClass.instrum("Expression Statement","116");
		//ASTClass.instrum("Expression Statement","124");
		//ASTClass.instrum("Expression Statement","132");
    }

    @PrePersist
    void perPersist() {
        if (transactionOn == null) {
            transactionOn = new Date();
			//ASTClass.instrum("Expression Statement","114");
			//ASTClass.instrum("Expression Statement","123");
			//ASTClass.instrum("Expression Statement","132");
			//ASTClass.instrum("Expression Statement","141");
        }
		//ASTClass.instrum("If Statement","113");
		//ASTClass.instrum("If Statement","122");
		//ASTClass.instrum("If Statement","131");
		//ASTClass.instrum("If Statement","140");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
		//ASTClass.instrum("Variable Declaration Statement","153");
		//ASTClass.instrum("Variable Declaration Statement","142");
		//ASTClass.instrum("Variable Declaration Statement","131");
		//ASTClass.instrum("Variable Declaration Statement","120");
        sb.append("id=").append(id);
		//ASTClass.instrum("Expression Statement","157");
		//ASTClass.instrum("Expression Statement","145");
		//ASTClass.instrum("Expression Statement","133");
		//ASTClass.instrum("Expression Statement","121");
        sb.append(", amount=").append(amount);
		//ASTClass.instrum("Expression Statement","161");
		//ASTClass.instrum("Expression Statement","148");
		//ASTClass.instrum("Expression Statement","135");
		//ASTClass.instrum("Expression Statement","122");
        sb.append(", remark='").append(remark).append('\'');
		//ASTClass.instrum("Expression Statement","165");
		//ASTClass.instrum("Expression Statement","151");
		//ASTClass.instrum("Expression Statement","137");
		//ASTClass.instrum("Expression Statement","123");
        sb.append(", transactionOn=").append(transactionOn);
		//ASTClass.instrum("Expression Statement","169");
		//ASTClass.instrum("Expression Statement","154");
		//ASTClass.instrum("Expression Statement","139");
		//ASTClass.instrum("Expression Statement","124");
        sb.append(", createdAt=").append(createdAt);
		//ASTClass.instrum("Expression Statement","173");
		//ASTClass.instrum("Expression Statement","157");
		//ASTClass.instrum("Expression Statement","141");
		//ASTClass.instrum("Expression Statement","125");
        sb.append(", budget=").append(budget);
		//ASTClass.instrum("Expression Statement","177");
		//ASTClass.instrum("Expression Statement","160");
		//ASTClass.instrum("Expression Statement","143");
		//ASTClass.instrum("Expression Statement","126");
        sb.append('}');
		//ASTClass.instrum("Expression Statement","181");
		//ASTClass.instrum("Expression Statement","163");
		//ASTClass.instrum("Expression Statement","145");
		//ASTClass.instrum("Expression Statement","127");
        return sb.toString();
    }
}
