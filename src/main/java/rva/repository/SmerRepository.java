package rva.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Smer;

public interface SmerRepository extends JpaRepository<Smer,Integer>{
	
	Collection<Smer> findByNazivContainingIgnoreCase(String naziv);
}
