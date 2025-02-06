package com.visualpathit.account.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * User entity class representing user details.
 * {@author imrant}
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    /** Unique identifier for the user. */
    private Long id;

    /** Username of the user. */
    private String username;

    /** Password of the user. */
    private String password;

    /** Email of the user. */
    private String userEmail;

    /** Confirm password for verification. */
    private String passwordConfirm;

    /** Profile image of the user. */
    private String profileImg;

    /** Path of the profile image. */
    private String profileImgPath;

    private String dateOfBirth;
    private String fatherName;
    private String motherName;
    private String gender;
    private String maritalStatus;
    private String permanentAddress;
    private String tempAddress;
    private String primaryOccupation;
    private String secondaryOccupation;
    private String skills;
    private String phoneNumber;
    private String secondaryPhoneNumber;
    private String nationality;
    private String language;
    private String workingExperience;

    /** Roles associated with the user. */
    private Set<Role> roles;

    /**
     * Gets the ID of the user.
     * 
     * {@link User#id}
     * @return the user's ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * 
     * {@link User#id}
     * @param id the user's ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     * 
     * {@link User#username}
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the user.
     * 
     * {@link User#username}
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * 
     * {@link User#password}
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     * 
     * {@link User#password}
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets the email of the user.
     * 
     * {@link User#userEmail}
     * @return the user's email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the email for the user.
     * 
     * {@link User#userEmail}
     * @param userEmail the email to set
     */
    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Gets the confirmation password for verification.
     * 
     * {@link User#passwordConfirm}
     * @return the confirmation password
     */
    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * Sets the confirmation password for the user.
     * 
     * {@link User#passwordConfirm}
     * @param passwordConfirm the confirmation password to set
     */
    public void setPasswordConfirm(final String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    /**
     * Retrieves the roles associated with the user.
     * 
     * {@link User#roles}
     * @return the set of roles
     */
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the roles for the user.
     * 
     * {@link User#roles}
     * @param roles the set of roles to set
     */
    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getProfileImgPath() {
        return profileImgPath;
    }

    public void setProfileImgPath(String profileImgPath) {
        this.profileImgPath = profileImgPath;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getTempAddress() {
        return tempAddress;
    }

    public void setTempAddress(String tempAddress) {
        this.tempAddress = tempAddress;
    }

    public String getPrimaryOccupation() {
        return primaryOccupation;
    }

    public void setPrimaryOccupation(String primaryOccupation) {
        this.primaryOccupation = primaryOccupation;
    }

    public String getSecondaryOccupation() {
        return secondaryOccupation;
    }

    public void setSecondaryOccupation(String secondaryOccupation) {
        this.secondaryOccupation = secondaryOccupation;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getWorkingExperience() {
        return workingExperience;
    }

    public void setWorkingExperience(String workingExperience) {
        this.workingExperience = workingExperience;
    }
}
