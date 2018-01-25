package io.budgetapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Entity
@Table(name = "recurrings")
public class Recurring implements Serializable {

    private static final long serialVersionUID = -2889004877850258404L;

    private Long id;
    private double amount;
    private RecurringType recurringType;
    private Date lastRunAt;
    private Date createdAt;
    private BudgetType budgetType;
    private String remark;
    private List<Transaction> transactions;

    // not in DB
    private String name;
    // end

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","51");
		//ASTClass.instrum("Expression Statement","51");
		//ASTClass.instrum("Expression Statement","51");
		//ASTClass.instrum("Expression Statement","51");
    }

    @Column(updatable = false)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
		//ASTClass.instrum("Expression Statement","60");
		//ASTClass.instrum("Expression Statement","61");
		//ASTClass.instrum("Expression Statement","62");
		//ASTClass.instrum("Expression Statement","63");
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", updatable = false)
    public RecurringType getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(RecurringType recurringType) {
        this.recurringType = recurringType;
		//ASTClass.instrum("Expression Statement","70");
		//ASTClass.instrum("Expression Statement","72");
		//ASTClass.instrum("Expression Statement","74");
		//ASTClass.instrum("Expression Statement","76");
    }

    @Transient
    public String getRecurringTypeDisplay() {
        return recurringType.getDisplay();
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_run_at", nullable = false)
    public Date getLastRunAt() {
        return lastRunAt;
    }

    public void setLastRunAt(Date lastRunAt) {
        this.lastRunAt = lastRunAt;
		//ASTClass.instrum("Expression Statement","85");
		//ASTClass.instrum("Expression Statement","88");
		//ASTClass.instrum("Expression Statement","91");
		//ASTClass.instrum("Expression Statement","94");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
		//ASTClass.instrum("Expression Statement","95");
		//ASTClass.instrum("Expression Statement","99");
		//ASTClass.instrum("Expression Statement","103");
		//ASTClass.instrum("Expression Statement","107");
    }

    @JoinColumn(name = "budget_type_id", updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public BudgetType getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
		//ASTClass.instrum("Expression Statement","105");
		//ASTClass.instrum("Expression Statement","110");
		//ASTClass.instrum("Expression Statement","115");
		//ASTClass.instrum("Expression Statement","120");
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
		//ASTClass.instrum("Expression Statement","113");
		//ASTClass.instrum("Expression Statement","119");
		//ASTClass.instrum("Expression Statement","125");
		//ASTClass.instrum("Expression Statement","131");
    }

    @JoinColumn(name = "transaction_id", updatable = false)
    @OneToMany(fetch = FetchType.LAZY)
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
		//ASTClass.instrum("Expression Statement","123");
		//ASTClass.instrum("Expression Statement","130");
		//ASTClass.instrum("Expression Statement","137");
		//ASTClass.instrum("Expression Statement","144");
    }

    @Transient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
		//ASTClass.instrum("Expression Statement","132");
		//ASTClass.instrum("Expression Statement","140");
		//ASTClass.instrum("Expression Statement","148");
		//ASTClass.instrum("Expression Statement","156");
    }

    @Override
    public String toString() {
        return "Recurring{" +
                "createdAt=" + createdAt +
                ", recurringType=" + recurringType +
                ", amount=" + amount +
                ", id=" + id +
                '}';
    }
}
