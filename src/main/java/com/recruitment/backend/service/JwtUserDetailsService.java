/*
 * Recruitment System 2022
 */
package com.recruitment.backend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.recruitment.backend.repository.UserRepository;

/*
 *tharinda.jayamal@gmail.com
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		com.recruitment.backend.entity.User user = userRepository
				.findByUserName(username);

		if (user != null && user.getUserName().equals(username)
				&& user.getPassword() != null && user.isActive()) {
			return new User(user.getUserName(), user.getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException(
					"User not found with user name: " + username);
		}

	}

}