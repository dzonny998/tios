package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Sud;
import org.springframework.stereotype.Repository;

@Repository
public interface SudRepository extends JpaRepository<Sud, Integer> {
	List<Sud> findByNazivContainingIgnoreCase(String naziv);
	
	@Query(value="select * from sud where lower(naziv) like :pocetak%", nativeQuery = true)
	List<Sud> getSudoviByPocetakNaziva(@Param("pocetak") String pocetakNaziva);
}
