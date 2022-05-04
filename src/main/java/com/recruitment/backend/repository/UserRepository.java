/*
 * Recruitment System 2022
 */
package com.recruitment.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.backend.entity.User;

/*
 *tharinda.jayamal@gmail.com
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUserName(String userName);

	User findTopByOrderByIdDesc();

	Page<User> findByUserName(String name, Pageable pageable);

	User findByRole_Name(String name);
}