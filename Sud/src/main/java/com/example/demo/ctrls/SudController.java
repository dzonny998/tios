package com.example.demo.ctrls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.example.demo.model.Sud;
import com.example.demo.service.SudService;

@CrossOrigin( origins = "*")
@RestController
public class SudController {
	
	@Autowired
	private SudService sudService;

	@GetMapping("/sud")
	public ResponseEntity<?> getAllSudovi(){
		List<Sud> sudovi = sudService.getAllSudovi();
		if(sudovi.isEmpty())
			return new ResponseEntity<>("Sudovi - empty list", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(sudovi, HttpStatus.OK);
	}

	@GetMapping("/sud/{id}")
	public ResponseEntity<?> getSudById(@PathVariable("id")int sudId){
		if(sudService.existsById(sudId)) {
			Optional<Sud> sud = sudService.getSudById(sudId);
			return ResponseEntity.ok(sud);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
		        .body("Sud by id " + sudId + " not found");
	}
	
	@GetMapping("/sud/naziv/{naziv}")
	public ResponseEntity<?> getAllSudoviByNaziv(@PathVariable("naziv")String naziv){
		List<Sud> sudovi = sudService.getAllSudoviByNaziv(naziv);
		if(sudovi.isEmpty())
			return new ResponseEntity<>("Sudovi by naziv - empty list", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(sudovi, HttpStatus.OK);
	}

	@PostMapping("/sud")
	public ResponseEntity<Sud> postSud(@RequestBody Sud sud){
		Sud savedSud = sudService.addSud(sud);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		return new ResponseEntity<>(savedSud, headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/sud/{id}")
	public ResponseEntity<?> putSud(
			@PathVariable("id")int sudId,
			@RequestBody Sud sud){

		Sud updatedSud = sudService.updateSud(sudId, sud);
		if (updatedSud == null) {
			return new ResponseEntity<>("Sud with id " + sudId + " does not exists", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(updatedSud, HttpStatus.OK);
	}
	
	@DeleteMapping("/sud/{id}")
	public ResponseEntity<?> deleteSud(@PathVariable("id")int sudId){
		if(!sudService.existsById(sudId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
			        .body("Sud with id " + sudId + " not found");
		}
		sudService.deleteById(sudId);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		return new ResponseEntity<>("Sud with id " + sudId + " has been deleted", headers, HttpStatus.NO_CONTENT);
	}
}
