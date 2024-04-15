package com.arunkumar.springbootwebioc.service;

import java.util.List;

import com.arunkumar.springbootwebioc.entity.User;

import jakarta.validation.Valid;

public interface UserService {

	User saveEmployee(@Valid User user);

	List<User> getAllEmployees();

	


}
