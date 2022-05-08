/*
 * Recruitment System 2022
 */
package com.recruitment.backend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.recruitment.backend.entity.CV;
import com.recruitment.backend.entity.Experience;
import com.recruitment.backend.entity.Qualification;
import com.recruitment.backend.entity.Skill;
import com.recruitment.backend.entity.User;
import com.recruitment.backend.model.ChangePassword;
import com.recruitment.backend.repository.CVRepository;
import com.recruitment.backend.repository.ExperienceRepository;
import com.recruitment.backend.repository.QualificationsRepository;
import com.recruitment.backend.repository.SkillRepository;
import com.recruitment.backend.repository.UserRepository;
import com.recruitment.backend.util.RequestModel;

/*
 *tharinda.jayamal@gmail.com
 */
@Service
public class UserService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private QualificationsRepository qualificationsRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private CVRepository cvRepository;

	public User addUpdateUser(User user) {

		user.setUserName(user.getUserName().trim());
		if (user.getId() > 0) {
			User u = userRepository.findById(user.getId()).get();
			user.setPassword(u.getPassword());
			user.setCv(u.getCv());
		}

		if (user.getId() == 0) {
			user.setPassword(hashPassword(user.getPassword()));
			user.setCv(null);
		}

		if (!StringUtils.isEmpty(user.getRole().getName())) {
			user.setRole(roleService.getAllRoleByName(user.getRole().getName()));
		}

		return userRepository.save(user);

	}

	public Page<User> getAllUsers(int page) {

		log.debug("Fetching Users.");
		Page<User> users = userRepository.findAll(PageRequest.of(page, 12));
		List<User> userList = new ArrayList<>();
		for (User u : users) {
			User user = findByUserName(u.getUserName());
			userList.add(user);
		}

		return new PageImpl<User>(userList);
	}

	public User getUserById(Long id) {

		log.debug("Fetching User for :" + id);
		if (userRepository.findById(id).isPresent()) {
			return userRepository.findById(id).get();
		} else {
			return null;
		}
	}

	public void deleteUser(Long id) {

		log.debug("Deleting User : " + id);
		User user = userRepository.findById(id).get();
		user.setRole(null);
		userRepository.save(user);
		userRepository.deleteById(id);
	}

	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}

	public User findByUserName(String userName) {

		User user = userRepository.findByUserName(userName);
		CV cv = new CV();

		if (user != null && user.getCv() != null) {
			cv.setContactNo(user.getCv().getContactNo());
			cv.setCreationDate(user.getCv().getCreationDate());
			cv.setId(user.getCv().getId());
			cv.setJobSector(user.getCv().getJobSector());
			cv.setNoOfGCSEpasses(user.getCv().getNoOfGCSEpasses());
			cv.setAddress(user.getCv().getAddress());

			List<Skill> skillList = skillRepository.findAllByCv_Id(cv.getId());
			Set<Skill> skills = new HashSet<>();
			for (Skill s : skillList) {
				Skill skill = new Skill();
				skill.setId(s.getId());
				skill.setSkillName(s.getSkillName());
				skill.setSkillType(s.getSkillType());
				skills.add(skill);
			}
			cv.setSkills(skills);

			List<Qualification> qualificationList = qualificationsRepository
					.findAllByCv_Id(cv.getId());
			Set<Qualification> qualifications = new HashSet<>();
			for (Qualification q : qualificationList) {
				Qualification qualification = new Qualification();
				qualification.setId(q.getId());
				qualification.setExpiryDate(q.getExpiryDate());
				qualification.setQualificationDate(q.getQualificationDate());
				qualification.setQualificationLevel(q.getQualificationLevel());
				qualification.setQualificationName(q.getQualificationName());
				qualification.setQualificationType(q.getQualificationType());
				qualifications.add(qualification);
			}
			cv.setQualifications(qualifications);

			List<Experience> experienceList = experienceRepository
					.findAllByCv_Id(cv.getId());
			Set<Experience> experiences = new HashSet<>();
			for (Experience e : experienceList) {
				Experience experience = new Experience();
				experience.setId(e.getId());
				experience.setCompanyName(e.getCompanyName());
				experience.setEndDate(e.getEndDate());
				experience.setExperienceType(e.getExperienceType());
				experience.setRole(e.getRole());
				experience.setStartDate(e.getStartDate());
				experiences.add(experience);
			}
			cv.setExperiences(experiences);

			user.setCv(cv);
		}

		return user;
	}

	public boolean checkExistingPassword(ChangePassword changePassword) {

		boolean isValid = false;
		log.debug("Checking passwords.");
		User user = findByUserName(changePassword.getUsername());
		if (user != null) {
			isValid = BCrypt.checkpw(changePassword.getPassword(),
					user.getPassword());
		}
		return isValid;
	}

	public void changePassword(ChangePassword changePassword) {

		log.debug("Changing passwords.");
		User user = findByUserName(changePassword.getUsername());
		if (user != null && user.getPassword() != null) {
			user.setPassword(hashPassword(changePassword.getNewPassword()));
			userRepository.save(user);
		}
	}

	public void inactivateUser(Long id) {

		log.debug("Inactivate account : " + id);

		if (userRepository.existsById(id)) {
			User user = userRepository.findById(id).get();
			user.setIsActive(false);
			userRepository.save(user);
		}
	}

	public Page<User> getAllUsers(RequestModel req, int page) {

		log.debug("Fetching Users.");

		Page<User> users = null;
		if (req != null && !StringUtils.isEmpty(req.getName())) {
			users = userRepository.findByUserName(req.getName(),
					PageRequest.of(page, 12));
		} else {
			users = userRepository.findAll(PageRequest.of(page, 12));
		}

		List<User> userList = new ArrayList<>();
		for (User u : users) {
			User user = findByUserName(u.getUserName());
			userList.add(user);
		}

		return new PageImpl<User>(userList);
	}

	public User findByAccountNumber(String role) {

		log.debug("Fetching Users.");

		User u = null;
		if (role != null && !StringUtils.isEmpty(role)) {
			u = userRepository.findByRole_Name(role);
		}

		return u;
	}

	public Iterable<User> getAllUsers() {

		return userRepository.findAll();
	}

	public List<User> findByQualificationLevel(Integer level) {

		List<Qualification> qualifications = qualificationsRepository
				.findAllByQualificationLevel(level);
		Set<Long> cvIds = new HashSet<>();
		Set<User> users = new HashSet<>();

		for (Qualification q : qualifications) {
			cvIds.add(q.getCv().getId());

			User u = userRepository.findByCv_Id(q.getCv().getId());

			if (u != null) {
				User user = findByUserName(u.getUserName());
				users.add(user);
			}
		}

		return users.stream().collect(Collectors.toList());
	}

	public List<User> findByMinQualificationLevel(Integer level) {

		List<Qualification> qualifications = qualificationsRepository
				.findAllByQualificationLevelGreaterThanEqual(level);
		Set<Long> cvIds = new HashSet<>();
		Set<User> users = new HashSet<>();

		for (Qualification q : qualifications) {
			cvIds.add(q.getCv().getId());

			User u = userRepository.findByCv_Id(q.getCv().getId());

			if (u != null) {
				User user = findByUserName(u.getUserName());
				users.add(user);
			}
		}

		return users.stream().collect(Collectors.toList());
	}

	public List<User> findByMinGCSEPasses(Integer noOfGCSEpasses) {

		List<CV> cvs = cvRepository
				.findAllByNoOfGCSEpassesGreaterThanEqual(noOfGCSEpasses);
		Set<User> users = new HashSet<>();

		for (CV c : cvs) {
			User u = userRepository.findByCv_Id(c.getId());

			if (u != null) {
				User user = findByUserName(u.getUserName());
				users.add(user);
			}
		}

		return users.stream().collect(Collectors.toList());
	}

	public List<User> findByAnySkillParam(String skill) {

		List<Skill> skillsByName = skillRepository
				.findBySkillNameContaining(skill);
		Set<User> users = new HashSet<>();

		if (skillsByName != null && skillsByName.size() > 0) {
			for (Skill s : skillsByName) {
				User u = userRepository.findByCv_Id(s.getCv().getId());

				if (u != null) {
					User user = findByUserName(u.getUserName());
					users.add(user);
				}
			}
		} else {
			List<Skill> skillsByType = skillRepository
					.findBySkillTypeContaining(skill);

			for (Skill s : skillsByType) {
				User u = userRepository.findByCv_Id(s.getCv().getId());

				if (u != null) {
					User user = findByUserName(u.getUserName());
					users.add(user);
				}
			}
		}

		return users.stream().collect(Collectors.toList());
	}

	public List<User> findByAnyQualificationParam(String qualification) {

		List<Qualification> qualificationsByName = qualificationsRepository
				.findByQualificationTypeContaining(qualification);
		Set<User> users = new HashSet<>();

		if (qualificationsByName != null && qualificationsByName.size() > 0) {
			for (Qualification q : qualificationsByName) {
				User u = userRepository.findByCv_Id(q.getCv().getId());

				if (u != null) {
					User user = findByUserName(u.getUserName());
					users.add(user);
				}
			}
		} else {
			List<Qualification> qualificationsByType = qualificationsRepository
					.findByQualificationNameContaining(qualification);

			for (Qualification q : qualificationsByType) {
				User u = userRepository.findByCv_Id(q.getCv().getId());

				if (u != null) {
					User user = findByUserName(u.getUserName());
					users.add(user);
				}
			}
		}

		return users.stream().collect(Collectors.toList());
	}
}