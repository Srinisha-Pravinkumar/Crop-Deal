package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CropDto;
import com.example.demo.model.User;

import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private AdminService adminService;

//	@PostMapping("addUser")
//	public String addUser(@Valid @RequestBody User userModel) {
//		return adminService.addUser(userModel);
//	}

	@DeleteMapping("deleteUser/{id}")
	public String deleteUser(@PathVariable Integer id) {
		return adminService.deleteUser(id);
	}

	@PutMapping("updateUser/{id}")
	public String updateUser(@RequestBody User updatedUserModel, @PathVariable Integer id) {
		return adminService.updateUser(updatedUserModel, id);
	}

	@GetMapping("getAllUsers")
	public List<User> getUsers() {
		return adminService.getUsers();
	}

	@GetMapping("getUser/{id}")
	public User getUserById(@PathVariable Integer id) {
		return adminService.getUserById(id);
	}

	@GetMapping("getAllCrops")
	public List<CropDto> getAllCrops() {
		return adminService.getAllCrops();
	}

}
