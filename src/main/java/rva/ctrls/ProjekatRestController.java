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
import rva.jpa.Projekat;
import rva.repository.ProjekatRepository;

@CrossOrigin
@RestController
@Api(tags = {"Projekat CRUD operacije"})
public class ProjekatRestController {

	@Autowired
	private ProjekatRepository projekatRepository;
	
	@GetMapping("projekat")
	@ApiOperation(value = "Vraca kolekciju svih projekata iz baze podataka")
	public Collection<Projekat> getProjekti(){
		return projekatRepository.findAll();
	}
	
	@GetMapping("projekat/{id}")
	@ApiOperation(value = "Vraća jedan projekat na osnovu ID-a iz baze podataka")
	public Projekat getProjekat(@PathVariable("id") Integer id){
		return projekatRepository.getOne(id);
	}
	
	@GetMapping("projekatNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju projekata na osnovu naziva iz baze podataka")
	public Collection<Projekat> getProjekatNaziv(@PathVariable("naziv") String naziv){
		return projekatRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("projekat")
	@ApiOperation(value = "Upisuje novi projekat u bazu podataka")
	public ResponseEntity<Projekat> insertProjekat(@RequestBody Projekat projekat){
		if(!projekatRepository.existsById(projekat.getId())) {
			projekatRepository.save(projekat);
			return new ResponseEntity<Projekat>(HttpStatus.OK);
		}
		return new ResponseEntity<Projekat>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("projekat")
	@ApiOperation(value = "Modifikuje postojecu grupu u bazi podataka")
	public ResponseEntity<Projekat> updateProjekat(@RequestBody Projekat projekat){
		if(!projekatRepository.existsById(projekat.getId())) 
			return new ResponseEntity<Projekat>(HttpStatus.CONFLICT);
		projekatRepository.save(projekat);
		return new ResponseEntity<Projekat>(HttpStatus.OK);
	}
	
	@DeleteMapping("projekat/{id}")
	@ApiOperation(value = "Brise projekat iz baze podataka na osnovu ID-a")
	public ResponseEntity<Projekat> deleteProjekat(@PathVariable Integer id){
		if(!projekatRepository.existsById(id))
			return new ResponseEntity<Projekat>(HttpStatus.NO_CONTENT);
		projekatRepository.deleteById(id);
		return new ResponseEntity<Projekat>(HttpStatus.OK);
	}
	
}
