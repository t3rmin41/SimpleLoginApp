package com.table.booking.system.android.app;

import java.util.ArrayList;
import java.util.List;

public class User {

  private Long id;
  private String email, firstName, lastName, password, type;
  private List<String> roles, rolesAsStrings = new ArrayList<String>();
  private boolean enabled;
  
  public User() {
  }
  public User(String email, String firstName, String lastName) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }
  public User(Long id, String email, String firstName, String lastName) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getId() {
    return id;
  }
  public User setId(Long id) {
    this.id = id;
    return this;
  }

  public String getEmail() {
    return email;
  }
  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }
  public User setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }
  public User setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPassword() {
    return password;
  }
  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getType() {
    return type;
  }
  public User setType(String type) {
    this.type = type;
    return this;
  }

  public List<String> getRoles() {
    return roles;
  }
  public User setRoles(List<String> roles) {
    this.roles = roles;
    return this;
  }

  public List<String> getRolesAsStrings() {
    return rolesAsStrings;
  }
  public User setRolesAsStrings(List<String> rolesAsStrings) {
    this.rolesAsStrings = rolesAsStrings;
    return this;
  }

  public boolean isEnabled() {
    return enabled;
  }
  public User setEnabled(boolean enabled) {
    this.enabled = enabled;
    return this;
  }

}
