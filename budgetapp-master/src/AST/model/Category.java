package io.budgetapp.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "categories")
public class Category implements Serializable {

    private static final long serialVersionUID = -8472850956290096457L;

    private Long id;
    private String name;
    private CategoryType type;
    private Date createdAt;
    private User user;

    public Category() {
    }

    public Category(long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","29");
		//ASTClass.instrum("Expression Statement","29");
		//ASTClass.instrum("Expression Statement","29");
		//ASTClass.instrum("Expression Statement","29");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","39");
		//ASTClass.instrum("Expression Statement","40");
		//ASTClass.instrum("Expression Statement","41");
		//ASTClass.instrum("Expression Statement","42");
    }

    @NotEmpty(message = "{validation.name.required}")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
		//ASTClass.instrum("Expression Statement","48");
		//ASTClass.instrum("Expression Statement","50");
		//ASTClass.instrum("Expression Statement","52");
		//ASTClass.instrum("Expression Statement","54");
    }

    @NotNull(message = "{validation.categoryType.required}")
    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
		//ASTClass.instrum("Expression Statement","59");
		//ASTClass.instrum("Expression Statement","62");
		//ASTClass.instrum("Expression Statement","65");
		//ASTClass.instrum("Expression Statement","68");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
		//ASTClass.instrum("Expression Statement","69");
		//ASTClass.instrum("Expression Statement","73");
		//ASTClass.instrum("Expression Statement","77");
		//ASTClass.instrum("Expression Statement","81");
    }

    @JoinColumn(updatable = false)
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
		//ASTClass.instrum("Expression Statement","79");
		//ASTClass.instrum("Expression Statement","84");
		//ASTClass.instrum("Expression Statement","89");
		//ASTClass.instrum("Expression Statement","94");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
		//ASTClass.instrum("If Statement","102");
		//ASTClass.instrum("If Statement","96");
		//ASTClass.instrum("If Statement","90");
		//ASTClass.instrum("If Statement","84");
        if (o == null || getClass() != o.getClass()) return false;
		//ASTClass.instrum("If Statement","85");
		//ASTClass.instrum("If Statement","92");
		//ASTClass.instrum("If Statement","99");
		//ASTClass.instrum("If Statement","106");

        Category category = (Category) o;
		//ASTClass.instrum("Variable Declaration Statement","87");
		//ASTClass.instrum("Variable Declaration Statement","95");
		//ASTClass.instrum("Variable Declaration Statement","103");
		//ASTClass.instrum("Variable Declaration Statement","111");

        return id != null ? id.equals(category.id) : category.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
