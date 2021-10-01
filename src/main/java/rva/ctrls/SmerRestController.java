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
import rva.jpa.Smer;
import rva.repository.SmerRepository;

@CrossOrigin
@RestController
@Api(tags = {"Smer CRUD operacije"})
public class SmerRestController {

	@Autowired
	private SmerRepository smerRepository;
	
	@GetMapping("smer")
	@ApiOperation(value = "Vraca kolekciju svih smerova iz baze podataka")
	public Collection<Smer> getSmerovi(){
		return smerRepository.findAll();
	}
	
	@GetMapping("smer/{id}")
	@ApiOperation(value = "Vraća jedan smer na osnovu ID-a iz baze podataka")
	public Smer getSmer(@PathVariable("id") Integer id){
		return smerRepository.getOne(id);
	}
	
	@GetMapping("smerNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju smerova na osnovu naziva iz baze podataka")
	public Collection<Smer> getSmerNaziv(@PathVariable("naziv") String naziv){
		return smerRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("smer")
	@ApiOperation(value = "Upisuje novi smer u bazu podataka")
	public ResponseEntity<Smer> insertArtikl(@RequestBody Smer smer){
		if(!smerRepository.existsById(smer.getId())) {
			smerRepository.save(smer);
			return new ResponseEntity<Smer>(HttpStatus.OK);  
		}
		return new ResponseEntity<Smer>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("smer")
	@ApiOperation(value = "Modifikuje postojeci smer u bazi podataka")
	public ResponseEntity<Smer> updateSmer(@RequestBody Smer smer){
		if(!smerRepository.existsById(smer.getId())) {
			return new ResponseEntity<Smer>(HttpStatus.CONFLICT);
		}
		smerRepository.save(smer);
		return new ResponseEntity<Smer>(HttpStatus.OK);
	}
	
	@DeleteMapping("smer/{id}")
	@ApiOperation(value = "Brise smer iz baze podataka na osnovu ID-a")
	public ResponseEntity<Smer> deleteSmer(@PathVariable Integer id){
		if(!smerRepository.existsById(id))
		{
			return new ResponseEntity<Smer>(HttpStatus.NO_CONTENT);
		}
		smerRepository.deleteById(id);
		return new ResponseEntity<Smer>(HttpStatus.OK);
	}
	
}
