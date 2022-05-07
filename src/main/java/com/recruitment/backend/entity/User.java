/*
 * Recruitment System 2022
 */
package com.recruitment.backend.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*
 *tharinda.jayamal@gmail.com
 */
@Entity
@Table(name = "user")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private boolean isActive;
	private boolean isHired;
	private Long hiredBy;
	private Date hiredDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cv_id", referencedColumnName = "id")
	private CV cv;

	/*@OneToOne(mappedBy = "user")
	private AppliedJobs appliedJobs;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public CV getCv() {
		return cv;
	}

	public void setCv(CV cv) {
		this.cv = cv;
	}

	public boolean isHired() {
		return isHired;
	}

	public void setHired(boolean isHired) {
		this.isHired = isHired;
	}

	public Long getHiredBy() {
		return hiredBy;
	}

	public void setHiredBy(Long hiredBy) {
		this.hiredBy = hiredBy;
	}

	public Date getHiredDate() {
		return hiredDate;
	}

	public void setHiredDate(Date hiredDate) {
		this.hiredDate = hiredDate;
	}
/*
	public AppliedJobs getAppliedJobs() {
		return appliedJobs;
	}

	public void setAppliedJobs(AppliedJobs appliedJobs) {
		this.appliedJobs = appliedJobs;
	}
*/
}
