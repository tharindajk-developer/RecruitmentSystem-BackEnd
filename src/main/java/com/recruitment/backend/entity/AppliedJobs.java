/*
 * Recruitment System 2022
 */
package com.recruitment.backend.entity;

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
@Table(name = "appliedjobs")
public class AppliedJobs {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "jobseeker_id", referencedColumnName = "id")
	private User user;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "jobapplied_id", referencedColumnName = "id")
	private Job job;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}
