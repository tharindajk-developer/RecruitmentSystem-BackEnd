/*
 * Recruitment System 2022
 */
package com.recruitment.backend.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/*
 *tharinda.jayamal@gmail.com
 */
@Entity
@Table(name = "cv")
public class CV {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String jobSector;
	private String contactNo;
	private Date creationDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	@OneToMany(mappedBy = "cv")
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Skill> skills;
	@OneToMany(mappedBy = "cv")
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Qualification> qualifications;
	@OneToMany(mappedBy = "cv")
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Experience> experiences;
	private Integer noOfGCSEpasses;

	/*
	 * @OneToOne(mappedBy = "cv") private User user;
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobSector() {
		return jobSector;
	}

	public void setJobSector(String jobSector) {
		this.jobSector = jobSector;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(Set<Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public Set<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(Set<Experience> experiences) {
		this.experiences = experiences;
	}

	public Integer getNoOfGCSEpasses() {
		return noOfGCSEpasses;
	}

	public void setNoOfGCSEpasses(Integer noOfGCSEpasses) {
		this.noOfGCSEpasses = noOfGCSEpasses;
	}

	/*
	 * public User getUser() { return user; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 */

}
