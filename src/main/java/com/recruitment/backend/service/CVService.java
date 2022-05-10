/*
 * Recruitment System 2022
 */

package com.recruitment.backend.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recruitment.backend.entity.CV;
import com.recruitment.backend.entity.Experience;
import com.recruitment.backend.entity.Qualification;
import com.recruitment.backend.entity.Skill;
import com.recruitment.backend.entity.User;
import com.recruitment.backend.enums.RolesEnum;
import com.recruitment.backend.repository.CVRepository;
import com.recruitment.backend.repository.ExperienceRepository;
import com.recruitment.backend.repository.QualificationsRepository;
import com.recruitment.backend.repository.SkillRepository;
import com.recruitment.backend.repository.UserRepository;

/*
 *tharinda.jayamal@gmail.com
 */
@Service
public class CVService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${cv.template}")
	private String cvTemplate;

	@Autowired
	private CVRepository cvRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private QualificationsRepository qualificationsRepository;

	@Autowired
	private UserService userService;

	public String addUpdateCV(CV cv, Long userId) {

		User user = null;
		String status = "Failed to store CV";
		if (userRepository.existsById(userId)) {
			user = userRepository.findById(userId).get();
		}

		if (user != null
				&& user.getRole() != null
				&& user.getRole().getName()
						.equalsIgnoreCase(RolesEnum.JOBSEEKER.toString())) {
			user.setCv(cv);

			cvRepository.save(cv);

			Set<Experience> experiences = new HashSet<>();
			for (Experience e : cv.getExperiences()) {
				e.setCv(cv);
				experiences.add(e);
			}
			experienceRepository.saveAll(experiences);
			// cv.setExperiences(experiences);

			Set<Skill> skills = new HashSet<>();
			for (Skill s : cv.getSkills()) {
				s.setCv(cv);
				skills.add(s);
			}
			skillRepository.saveAll(skills);
			// cv.setSkills(skills);

			Set<Qualification> qualifications = new HashSet<>();
			for (Qualification q : cv.getQualifications()) {
				q.setCv(cv);
				qualifications.add(q);
			}
			// cv.setQualifications(qualifications);
			qualificationsRepository.saveAll(qualifications);

			status = "Successfully stored the CV ";
		}
		return status;

	}

	public CV getCVByUserId(Long userId) {

		log.debug("Fetching CV for user:" + userId);
		if (userRepository.findById(userId).isPresent()) {

			User u = userRepository.findById(userId).get();
			return userService.findByUserName(u.getUserName()).getCv();
		} else {
			return null;
		}
	}

	public void deleteCV(Long userId) {

		log.debug("Deleting CV by user Id: " + userId);

		User user = null;
		if (userRepository.existsById(userId)) {
			user = userRepository.findById(userId).get();

			if (user.getCv() != null && user.getCv().getId() != null) {
				user.setCv(null);
				userRepository.save(user);
			}
		}
	}

}
