package io.budgetapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import liquibase.util.MD5Util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Principal, Serializable {

    private static final long serialVersionUID = 3868269731826822792L;
    private Long id;
    private String username;
    private String password;
    private String name;
    private Date createdAt;
    private String currency;

    // not in DB
    private String token;
    // end

    public User() {
    }

    public User(long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","46");
		//ASTClass.instrum("Expression Statement","46");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
		//ASTClass.instrum("Expression Statement","56");
		//ASTClass.instrum("Expression Statement","57");
		//ASTClass.instrum("Expression Statement","58");
		//ASTClass.instrum("Expression Statement","59");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
		//ASTClass.instrum("Expression Statement","64");
		//ASTClass.instrum("Expression Statement","66");
		//ASTClass.instrum("Expression Statement","68");
		//ASTClass.instrum("Expression Statement","70");
    }

    public void setPassword(String password) {
        this.password = password;
		//ASTClass.instrum("Expression Statement","68");
		//ASTClass.instrum("Expression Statement","71");
		//ASTClass.instrum("Expression Statement","74");
		//ASTClass.instrum("Expression Statement","77");
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
		//ASTClass.instrum("Expression Statement","82");
		//ASTClass.instrum("Expression Statement","86");
		//ASTClass.instrum("Expression Statement","90");
		//ASTClass.instrum("Expression Statement","94");
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
		//ASTClass.instrum("Expression Statement","92");
		//ASTClass.instrum("Expression Statement","97");
		//ASTClass.instrum("Expression Statement","102");
		//ASTClass.instrum("Expression Statement","107");
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
		//ASTClass.instrum("Expression Statement","100");
		//ASTClass.instrum("Expression Statement","106");
		//ASTClass.instrum("Expression Statement","112");
		//ASTClass.instrum("Expression Statement","118");
    }

    // not in DB
    @Transient
    @JsonProperty("avatar")
    public String getAvatar() {
        return "https://www.gravatar.com/avatar/" + MD5Util.computeMD5(getUsername());
    }

    @Transient
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
		//ASTClass.instrum("Expression Statement","117");
		//ASTClass.instrum("Expression Statement","124");
		//ASTClass.instrum("Expression Statement","131");
		//ASTClass.instrum("Expression Statement","138");
    }
    // end

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
		//ASTClass.instrum("Variable Declaration Statement","147");
		//ASTClass.instrum("Variable Declaration Statement","139");
		//ASTClass.instrum("Variable Declaration Statement","131");
		//ASTClass.instrum("Variable Declaration Statement","123");
        sb.append("id=").append(id);
		//ASTClass.instrum("Expression Statement","151");
		//ASTClass.instrum("Expression Statement","142");
		//ASTClass.instrum("Expression Statement","133");
		//ASTClass.instrum("Expression Statement","124");
        sb.append(", username='").append(username).append('\'');
		//ASTClass.instrum("Expression Statement","155");
		//ASTClass.instrum("Expression Statement","145");
		//ASTClass.instrum("Expression Statement","135");
		//ASTClass.instrum("Expression Statement","125");
        sb.append('}');
		//ASTClass.instrum("Expression Statement","159");
		//ASTClass.instrum("Expression Statement","148");
		//ASTClass.instrum("Expression Statement","137");
		//ASTClass.instrum("Expression Statement","126");
        return sb.toString();
    }
}
