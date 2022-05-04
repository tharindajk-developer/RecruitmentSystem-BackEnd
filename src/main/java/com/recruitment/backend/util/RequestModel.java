/*
 * Recruitment System 2022
 */
package com.recruitment.backend.util;

/*
 *tharinda.jayamal@gmail.com
 */
public class RequestModel {

	private String name;
	private String receiverEmail;
	private String message;
	private String subject;
	private Long recruiterUserId;
	private Long jobSeekerUserId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getRecruiterUserId() {
		return recruiterUserId;
	}

	public void setRecruiterUserId(Long recruiterUserId) {
		this.recruiterUserId = recruiterUserId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getJobSeekerUserId() {
		return jobSeekerUserId;
	}

	public void setJobSeekerUserId(Long jobSeekerUserId) {
		this.jobSeekerUserId = jobSeekerUserId;
	}

}
