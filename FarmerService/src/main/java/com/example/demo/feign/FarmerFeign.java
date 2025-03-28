package com.example.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.UserDto;


@FeignClient("AdminService")
public interface FarmerFeign {
	@PostMapping("admin/addUser")
	public String addUser(@RequestBody UserDto userModel);

	@DeleteMapping("admin/deleteUser/{id}")
	public String deleteUser(@PathVariable Integer id);

	@PutMapping("admin/updateUser/{id}")
	public String updateUser(@RequestBody UserDto updatedUserModel, @PathVariable Integer id);

	@GetMapping("admin/getAllUsers")
	public List<UserDto> getUsers();

	@GetMapping("admin/getUser/{id}")
	public UserDto getUserById(@PathVariable Integer id);

}
