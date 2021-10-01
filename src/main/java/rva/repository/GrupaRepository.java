package rva.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Grupa;

public interface GrupaRepository extends JpaRepository<Grupa,Integer>{
	
	Collection<Grupa> findByOznakaContainingIgnoreCase(String oznaka);
}
