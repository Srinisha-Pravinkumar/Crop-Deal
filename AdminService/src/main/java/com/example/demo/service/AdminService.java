package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CropDto;
import com.example.demo.exception.EmptyTableException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.feign.CropFeign;
import com.example.demo.model.User;

import com.example.demo.repo.UserRepository;

import feign.FeignException;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	CropFeign cropFeign;

//	public String addUser(User userModel) {
//		if(!userRepository.existsByFullName(userModel.getFullName())) {
//			userRepository.save(userModel);
//			return "User Added Succesfully !!!";
//		}else {
//			throw new UserAlreadyExistException("Use some Diffrent username");
//		}
//	}

	public List<User> getUsers() {
		if(userRepository.findAll().isEmpty()) {
			throw new EmptyTableException("Please add a user to continue");
		}else {
			return userRepository.findAll();
		}
		
	}

	public User getUserById(Integer id) {
		if (userRepository.existsById(id)) {
			return userRepository.findById(id).get();
		} else {
			throw new NoSuchElementException(id.toString());
		}

	}

	public String deleteUser(Integer id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return "Delete Succesfully";
		} else {
			throw new NoSuchElementException(id.toString());
		}
	}

	public String updateUser(User updatedUserModel, Integer id) {
		if (!userRepository.existsById(id)) {
			throw new NoSuchElementException(id.toString());
		} else if (id != updatedUserModel.getId()) {
			throw new NoSuchElementException(id.toString()+" The provided ID (" + id + ")"
					+ " does not match the ID in the request body."
					+ " Please check your input.");
		} else {
			userRepository.save(updatedUserModel);
			return "Updated Succesfully";
		}
	}

	public List<CropDto> getAllCrops() {
		try {
			return cropFeign.getCrops();
		} catch (FeignException feignException) {
			throw feignException;
		}
	}

}
