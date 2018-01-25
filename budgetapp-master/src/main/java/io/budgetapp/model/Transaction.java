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
		ASTClass.instrum("Expression Statement","33");
    }

    @Column(nullable = false, updatable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
		ASTClass.instrum("Expression Statement","42");
    }

    @Column(nullable = false, updatable = false)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
		ASTClass.instrum("Expression Statement","51");
    }

    @Column(updatable = false)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
		ASTClass.instrum("Expression Statement","60");
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
		ASTClass.instrum("Expression Statement","68");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_on", nullable = false, updatable = false)
    public Date getTransactionOn() {
        return transactionOn;
    }

    public void setTransactionOn(Date transactionOn) {
        this.transactionOn = transactionOn;
		ASTClass.instrum("Expression Statement","78");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
		ASTClass.instrum("Expression Statement","88");
    }

    @JoinColumn(updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
		ASTClass.instrum("Expression Statement","98");
    }

    @JoinColumn(updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Recurring getRecurring() {
        return recurring;
    }

    public void setRecurring(Recurring recurring) {
        this.recurring = recurring;
		ASTClass.instrum("Expression Statement","108");
    }

    @PrePersist
    void perPersist() {
        if (transactionOn == null) {
            transactionOn = new Date();
			ASTClass.instrum("Expression Statement","114");
        }
		ASTClass.instrum("If Statement","113");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
		ASTClass.instrum("Variable Declaration Statement","120");
        sb.append("id=").append(id);
		ASTClass.instrum("Expression Statement","121");
        sb.append(", amount=").append(amount);
		ASTClass.instrum("Expression Statement","122");
        sb.append(", remark='").append(remark).append('\'');
		ASTClass.instrum("Expression Statement","123");
        sb.append(", transactionOn=").append(transactionOn);
		ASTClass.instrum("Expression Statement","124");
        sb.append(", createdAt=").append(createdAt);
		ASTClass.instrum("Expression Statement","125");
        sb.append(", budget=").append(budget);
		ASTClass.instrum("Expression Statement","126");
        sb.append('}');
		ASTClass.instrum("Expression Statement","127");
        return sb.toString();
    }
}
