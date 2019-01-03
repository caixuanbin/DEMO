package com.xbcai.myweb.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="User.findByTheUsersName",query = " from JpaUser u where u.username=?1")
public class JpaUser extends AbstractPersistable<Long> {
    @Column(unique = true)
    private String username;
    private String firstname;
    private String lastname;

    public JpaUser() {
    }

    public JpaUser(Long id) {
        this.setId(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
