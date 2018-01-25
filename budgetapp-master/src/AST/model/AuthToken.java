package io.budgetapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "auth_tokens")
public class AuthToken implements Serializable {

    private static final long serialVersionUID = 2483816645438788013L;

    private Long id;
    private String token;
    private Date createdAt;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","36");
		//ASTClass.instrum("Expression Statement","36");
		//ASTClass.instrum("Expression Statement","36");
		//ASTClass.instrum("Expression Statement","36");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
		//ASTClass.instrum("Expression Statement","44");
		//ASTClass.instrum("Expression Statement","45");
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","47");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
		//ASTClass.instrum("Expression Statement","54");
		//ASTClass.instrum("Expression Statement","56");
		//ASTClass.instrum("Expression Statement","58");
		//ASTClass.instrum("Expression Statement","60");
    }

    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
		//ASTClass.instrum("Expression Statement","user,",this.user=user","63");
		//ASTClass.instrum("Expression Statement","user,",this.user=user","66");
		//ASTClass.instrum("Expression Statement","user,",this.user=user","69");
		//ASTClass.instrum("Expression Statement","user,",this.user=user","72");
    }
}
