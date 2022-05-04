/*
 * Recruitment System 2022
 */
package com.recruitment.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.recruitment.backend.entity.Role;
import com.recruitment.backend.repository.RoleRepository;

/*
 *tharinda.jayamal@gmail.com
 */
@Service
public class RoleService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleRepository roleRepository;

	public Role addUpdateRole(Role role) {

		return roleRepository.save(role);
	}

	public Page<Role> getAllRoles(int page) {

		log.debug("Fetching roles.");
		return roleRepository.findAll(PageRequest.of(page, 12));
	}

	public Role getRoleById(Long id) {

		log.debug("Fetching role for :" + id);
		if (roleRepository.findById(id).isPresent()) {
			return roleRepository.findById(id).get();
		} else {
			return null;
		}
	}

	public void deleteRole(Long id) {

		log.debug("Deleting role : " + id);
		roleRepository.deleteById(id);
	}

	public List<Role> getAllRoles() {

		log.debug("Fetching all roles.");

		Iterable<Role> roles = roleRepository.findAll();

		return StreamSupport.stream(roles.spliterator(), false).collect(
				Collectors.toList());
	}

	public com.recruitment.backend.entity.Role getAllRoleByName(String name) {

		log.debug("Fetching department by name " + name);
		return roleRepository.findByName(name);
	}

}
