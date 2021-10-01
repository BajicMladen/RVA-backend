package rva.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Grupa;
import rva.jpa.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{
	
	Collection<Student> findByImeContainingIgnoreCase(String ime);
	
	Collection<Student> findByPrezimeContainingIgnoreCase(String prezime);
	
	Collection<Student> findByGrupa(Grupa g);
}
