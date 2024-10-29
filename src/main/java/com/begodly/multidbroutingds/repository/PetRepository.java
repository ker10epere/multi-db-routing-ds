package com.begodly.multidbroutingds.repository;

import com.begodly.multidbroutingds.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query(value = "SELECT * FROM pet WHERE name = :name LIMIT 1", nativeQuery = true)
    Pet takeByName(String name);

    @Query(value = "SELECT * FROM pet", nativeQuery = true)
    List<Pet> takeAll();
}
