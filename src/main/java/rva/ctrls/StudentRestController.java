package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Grupa;
import rva.jpa.Student;
import rva.repository.GrupaRepository;
import rva.repository.StudentRepository;

@CrossOrigin
@RestController
@Api(tags = {"Student CRUD operacije"})
public class StudentRestController {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private GrupaRepository grupaRepository;
	
	@GetMapping("student")
	@ApiOperation(value = "Vraca kolekciju svih studenata iz baze podataka")
	public Collection<Student> getStudente(){
		return studentRepository.findAll();
	}
	
	@GetMapping("student/{id}")
	@ApiOperation(value = "Vraća jednog studenta na osnovu ID-a iz baze podataka")
	public Student getStudent(@PathVariable("id") Integer id){
		return studentRepository.getOne(id);
	} 
	
	
	@GetMapping("studentIme/{ime}")
	@ApiOperation(value = "Vraća kolekciju studenata na osnovu imena iz baze podataka")
	public Collection<Student> getStudentIme(@PathVariable("ime") String ime){
		return studentRepository.findByImeContainingIgnoreCase(ime);
	}
	
	@GetMapping("studentPrezime/{prz}")
	@ApiOperation(value = "Vraća kolekciju studenata na osnovu prezimena iz baze podataka")
	public Collection<Student> getStudentPrezime(@PathVariable("prz") String prz){
		return studentRepository.findByPrezimeContainingIgnoreCase(prz);
	}
	
	@GetMapping("studentByGrupaID/{id}")
	@ApiOperation(value = "Vraća kolekciju studenata na osnovu grupe kojoj pripadaju iz baze podataka")
	public Collection<Student> getStudentPoGrupi(@PathVariable("id") Integer id){
		Grupa g=grupaRepository.getOne(id);
		return studentRepository.findByGrupa(g);
	}
	
	@PostMapping("student")
	@ApiOperation(value = "Upisuje novog korisnika u bazu podataka")
	public ResponseEntity<Student> insertStudent(@RequestBody Student student){
		if(!studentRepository.existsById(student.getId())) {
			studentRepository.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
		return new ResponseEntity<Student>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("student")
	@ApiOperation(value = "Modifikuje postojeceg studenta u bazi podataka")
	public ResponseEntity<Student> upDateStudent(@RequestBody Student student){
		if(!studentRepository.existsById(student.getId())) 
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		studentRepository.save(student);
		return new ResponseEntity<Student>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Brise studenta iz baze podataka na osnovu ID-a")
	@DeleteMapping("student/{id}")
	public ResponseEntity<Student> deleteStudent(@PathVariable("id")  Integer id){
		if(!studentRepository.existsById(id)) {
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		}		
		studentRepository.deleteById(id);
		return new ResponseEntity<Student>(HttpStatus.OK);
	}
	
}
