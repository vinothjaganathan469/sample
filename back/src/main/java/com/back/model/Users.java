package com.back.model;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="V_users")
@Component
public class Users { // it will not map this user class with table user
	@Id
private String username;
	@Column	
private String firstname;
private String lastname;
private String email;
private String password;
private String role;
private boolean enabled;
@Column(name="on_line") private boolean online;


public boolean isOnline() {
	return online;
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


public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}


public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}

public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}

public void setOnline(boolean b){
}
}


