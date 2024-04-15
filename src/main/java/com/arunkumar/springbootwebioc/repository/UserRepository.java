package com.arunkumar.springbootwebioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arunkumar.springbootwebioc.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmployeeId(String employeeId);

	

}
