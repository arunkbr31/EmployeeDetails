package com.arunkumar.springbootwebioc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arunkumar.springbootwebioc.entity.User;
import com.arunkumar.springbootwebioc.repository.UserRepository;
import com.arunkumar.springbootwebioc.service.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userrepository;

	@Override
	public User saveEmployee(@Valid User user) {
		if (userrepository.existsByEmployeeId(user.getEmployeeId())) {
			throw new IllegalArgumentException("Employee Id Must Be Unique");
		}
		return null;
	}

	@Override
	public List<User> getAllEmployees() {
		return userrepository.findAll();

	}

}
