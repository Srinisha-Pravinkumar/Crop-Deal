package com.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.login.model.User;

import feign.Param;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int profileId);

    User findByFullName(String fullName);

    User findByFullNameOrEmailId(String fullName, String emailId);

    User findByEmailId(String emailId);

    User findByMobileNumber(String mobileNumber);

    boolean existsByEmailIdIgnoreCase(String emailId);

    boolean existsByMobileNumber(String mobileNumber);

    List<User> findByRoleIgnoreCase(String role);

    @Query("SELECT u FROM User u " +
    	       "WHERE (:fullName IS NULL OR u.fullName = :fullName) " +
    	       "AND (:email IS NULL OR u.emailId = :email) " +
    	       "AND (:mobileNumber IS NULL OR u.mobileNumber = :mobileNumber)")
    	List<User> searchUsers(@Param("fullName") String fullName,
    	                       @Param("email") String email,
    	                       @Param("mobileNumber") String mobileNumber);


	
}
