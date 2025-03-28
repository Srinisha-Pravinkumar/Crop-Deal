package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.CropModel;

import java.util.List;
import java.util.Optional;

public interface CropRepository extends JpaRepository<CropModel, Integer> {

	@Query("SELECT c FROM CropModel c WHERE c.Id = :Id")
	List<CropModel> findAllbyId(Integer Id);

	@Modifying
	@Transactional
	@Query("DELETE FROM CropModel c WHERE c.Id = :Id AND c.cropId = :cropId")
	void deleteByIdAndCropId(@Param("Id") Integer Id, @Param("cropId") Integer cropId);



	

}
