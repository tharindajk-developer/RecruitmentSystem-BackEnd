/*
 * Recruitment System 2022
 */
package com.recruitment.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.recruitment.backend.entity.User;
import com.recruitment.backend.model.ChangePassword;
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

	public User addUpdateUser(User user) {

		String password = null;
		user.setUserName(user.getUserName().trim());
		if (!StringUtils.isEmpty(user.getId())) {
			password = userRepository.findById(user.getId()).get()
					.getPassword();
			user.setPassword(password);
		}

		if (StringUtils.isEmpty(user.getId())) {
			user.setPassword(hashPassword(user.getPassword()));
		}

		if (!StringUtils.isEmpty(user.getRole().getName())) {
			user.setRole(roleService.getAllRoleByName(user.getRole().getName()));
		}

		return userRepository.save(user);

	}

	public Page<User> getAllUsers(int page) {

		log.debug("Fetching Users.");
		return userRepository.findAll(PageRequest.of(page, 12));
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
		userRepository.deleteById(id);
	}

	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}

	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
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

		if (req != null && !StringUtils.isEmpty(req.getName())) {
			return userRepository.findByUserName(req.getName(),
					PageRequest.of(page, 12));
		} else {
			return userRepository.findAll(PageRequest.of(page, 12));
		}
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
}