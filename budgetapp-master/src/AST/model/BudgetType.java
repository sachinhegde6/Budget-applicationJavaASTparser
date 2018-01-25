package io.budgetapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "budget_types")
public class BudgetType implements Serializable {

    private static final long serialVersionUID = -7580231307267509312L;

    private Long id;
    private Date createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","26");
		//ASTClass.instrum("Expression Statement","26");
		//ASTClass.instrum("Expression Statement","26");
		//ASTClass.instrum("Expression Statement","26");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
		//ASTClass.instrum("Expression Statement","36");
		//ASTClass.instrum("Expression Statement","37");
		//ASTClass.instrum("Expression Statement","38");
		//ASTClass.instrum("Expression Statement","39");
    }

    @Override
    public String toString() {
        return "BudgetType{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                '}';
    }
}
