package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
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
import rva.repository.GrupaRepository;

@CrossOrigin
@RestController
@Api(tags = {"Grupa CRUD operacije"})
public class GrupaRestController {

	@Autowired
	private GrupaRepository grupaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("grupa")
	@ApiOperation(value = "Vraca kolekciju svih grupa iz baze podataka")
	public Collection<Grupa> getGrupe(){
		return grupaRepository.findAll();
	}
	
	@GetMapping("grupa/{id}")
	@ApiOperation(value = "Vraća jednu grupu na osnovu ID-a iz baze podataka")
	public Grupa getGrupa(@PathVariable("id") Integer id) {
		return grupaRepository.getOne(id);
	}
	
	@GetMapping("grupaOznaka/{oznaka}")
	@ApiOperation(value = "Vraća kolekciju studenata na osnovu oznake iz baze podataka")
	public Collection<Grupa> getProjekatNaziv(@PathVariable("oznaka") String oznaka){
		return grupaRepository.findByOznakaContainingIgnoreCase(oznaka);
	}
	
	@PostMapping("grupa")
	@ApiOperation(value = "Upisuje novu grupu u bazu podataka")
	public ResponseEntity<Grupa> insertGrupa(@RequestBody Grupa grupa){
		if(!grupaRepository.existsById(grupa.getId())) {
			grupaRepository.save(grupa);
			return new ResponseEntity<Grupa>(HttpStatus.OK);
		}
		return new ResponseEntity<Grupa>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("grupa")
	@ApiOperation(value = "Modifikuje postojecu grupu u bazi podataka")
	public ResponseEntity<Grupa> updateGrupa(@RequestBody Grupa grupa){
		if(!grupaRepository.existsById(grupa.getId())) 
			return new ResponseEntity<Grupa>(HttpStatus.NO_CONTENT);
		grupaRepository.save(grupa);
		return new ResponseEntity<Grupa>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("grupa/{id}")
	@ApiOperation(value = "Brise grupu iz baze podataka na osnovu ID-a")
	public ResponseEntity<Grupa> deleteGrupa(@PathVariable Integer id){
		if(!grupaRepository.existsById(id)) {
			return new ResponseEntity<Grupa>(HttpStatus.NO_CONTENT);
		}
		jdbcTemplate.execute(" DELETE FROM student WHERE grupa=" + id);	
		grupaRepository.deleteById(id); 
		return new ResponseEntity<Grupa>(HttpStatus.OK);
	}
	
}
