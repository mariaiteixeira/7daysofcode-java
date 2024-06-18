package com.sevendays.sevendays.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sevendays.sevendays.model.Superheroi;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface SuperheroiRepository extends JpaRepository<Superheroi, Long>{
    @Query("SELECT s FROM Superheroi s WHERE LOWER(s.poderes) LIKE LOWER(CONCAT('%', :poderes, '%'))")
    List<Superheroi> findByPoderesContaining(@Param("poderes") String poderes);

    List<Superheroi> findByPoderesContainingIgnoreCase(String poderes);
}