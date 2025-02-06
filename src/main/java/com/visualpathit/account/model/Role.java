package com.visualpathit.account.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Role entity class for defining roles in the application.
 * {@author imrant}
 */
@Entity
@Table(name = "role")
public class Role {

    /** The unique identifier for the role. */
    private Long id;
    
    /** The name of the role. */
    private String name;
    
    /** The set of users associated with the role. */
    private Set<User> users;

    /**
     * Gets the ID of the role.
     * 
     * {@link Role#id}
     * @return the role ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the role.
     * 
     * {@link Role#id}
     * @param id the role ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the role.
     * 
     * {@link Role#name}
     * @return the role name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     * 
     * {@link Role#name}
     * @param name the role name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Retrieves the users associated with the role.
     * 
     * {@link Role#users}
     * @return a set of users associated with the role
     */
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles", cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets the users associated with the role.
     * 
     * {@link Role#users}
     * @param users the users to be associated with the role
     */
    public final void setUsers(Set<User> users) {
        this.users = users;
    }
}
