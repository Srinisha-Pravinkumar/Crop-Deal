package com.examole.demo.service;

import com.example.demo.dto.CropDto;
import com.example.demo.exception.EmptyTableException;
import com.example.demo.feign.CropFeign;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.AdminService;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CropFeign cropFeign;

    @InjectMocks
    private AdminService adminService;

    private User user;
    private CropDto crop;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a User object
        user = new User();
        user.setId(1);
        user.setFullName("John Doe");
        user.setEmailId("john.doe@example.com");
        user.setPassword("password123");
        user.setMobileNumber("1234567890");
        user.setGender("Male");
        user.setRole("ADMIN");
        user.setAddress("123 Main Street, City, Country");

        // Setup a CropDto object
        crop = new CropDto();
        crop.setCropId(101);
        crop.setCropName("Wheat");
        crop.setCropQuantity("100 kg");
        crop.setCropPrice(150.0);
        crop.setCropDescription("High-quality wheat");
    }

//    @Test
//    void testGetUsers_Success() {
//        // Mock the repository to return a list of users
//        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
//
//        // Call the method
//        List<User> users = adminService.getUsers();
//
//        // Assertions
//        assertEquals(1, users.size());
//        assertEquals("John Doe", users.get(0).getFullName());
//
//        verify(userRepository, times(1)).findAll();
//    }

    @Test
    void testGetUsers_EmptyTable() {
        // Mock the repository to return an empty list
        when(userRepository.findAll()).thenReturn(List.of());

        // Assert exception is thrown
        EmptyTableException exception = assertThrows(EmptyTableException.class, () -> adminService.getUsers());
        assertEquals("Please add a user to continue", exception.getMessage());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() {
        // Mock repository to return user when ID exists
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Call the method
        User fetchedUser = adminService.getUserById(1);

        // Assertions
        assertNotNull(fetchedUser);
        assertEquals("John Doe", fetchedUser.getFullName());

        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testGetUserById_NotFound() {
        // Mock repository to return false for existsById
        when(userRepository.existsById(1)).thenReturn(false);

        // Assert exception is thrown
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> adminService.getUserById(1));

        // Check that the exception message contains the expected ID
        assertTrue(exception.getMessage().contains("1"), "Exception message should contain the ID");

        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, never()).findById(1);
    }


    @Test
    void testDeleteUser_Success() {
        // Mock repository to return true for existsById
        when(userRepository.existsById(1)).thenReturn(true);

        // Call the method
        String result = adminService.deleteUser(1);

        // Assertions
        assertEquals("Delete Succesfully", result);

        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUser_NotFound() {
        // Mock repository to return false for existsById
        when(userRepository.existsById(1)).thenReturn(false);

        // Assert exception is thrown
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> adminService.deleteUser(1));
        assertEquals("1", exception.getMessage());

        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void testUpdateUser_Success() {
        // Mock repository to return true for existsById
        when(userRepository.existsById(1)).thenReturn(true);

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setFullName("Jane Doe");

        // Mock save operation
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // Call the method
        String result = adminService.updateUser(updatedUser, 1);

        // Assertions
        assertEquals("Updated Succesfully", result);

        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, times(1)).save(updatedUser);
    }

//    @Test
//    void testUpdateUser_IdMismatch() {
//        User updatedUser = new User();
//        updatedUser.setId(2); // ID mismatch
//
//        // Assert exception is thrown
//        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> adminService.updateUser(updatedUser, 1));
//        assertTrue(exception.getMessage().contains("does not match the ID in the request body"));
//
//        verify(userRepository, never()).save(any());
//    }

    @Test
    void testGetAllCrops_Success() {
        // Mock Feign client to return crop data
        when(cropFeign.getCrops()).thenReturn(Arrays.asList(crop));

        // Call the method
        List<CropDto> crops = adminService.getAllCrops();

        // Assertions
        assertEquals(1, crops.size());
        assertEquals("Wheat", crops.get(0).getCropName());

        verify(cropFeign, times(1)).getCrops();
    }

    @Test
    void testGetAllCrops_FeignException() {
        // Mock Feign client to throw exception
        when(cropFeign.getCrops()).thenThrow(FeignException.class);

        // Assert exception is thrown
        assertThrows(FeignException.class, () -> adminService.getAllCrops());

        verify(cropFeign, times(1)).getCrops();
    }
}
