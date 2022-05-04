/*
 * Recruitment System 2022
 */
package com.recruitment.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.backend.config.JwtTokenUtil;
import com.recruitment.backend.entity.Role;
import com.recruitment.backend.entity.User;
import com.recruitment.backend.model.JwtRequest;
import com.recruitment.backend.model.JwtResponse;
import com.recruitment.backend.service.UserService;

/*
 *tharinda.jayamal@gmail.com
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<JwtResponse> createAuthenticationToken(
			@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(),
				authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (encoder.matches(authenticationRequest.getPassword(),
				userDetails.getPassword())) {
			final String token = jwtTokenUtil.generateToken(userDetails);
			User user = userService.findByUserName(authenticationRequest
					.getUsername());
			List<Role> roles = new ArrayList<>();

			if (user.getRole() != null) {
				roles.add(user.getRole());
			} else {
				Role role = new Role();
				role.setDescription("SYSTEM");
				role.setId(1001L);
				role.setName("System");
				roles.add(role);
			}

			return ResponseEntity.ok(new JwtResponse(token,
					authenticationRequest.getUsername(), roles,
					"Login Success!"));
		} else {
			return ResponseEntity.ok(new JwtResponse(null,
					authenticationRequest.getUsername(), null,
					"Invalid password!"));
		}

	}

	private void authenticate(String username, String password)
			throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}