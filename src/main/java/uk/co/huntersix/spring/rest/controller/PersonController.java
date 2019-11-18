package uk.co.huntersix.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/person/{lastName}/{firstName}")
    public ResponseEntity<?> person(@PathVariable(value="lastName") String lastName,
                         @PathVariable(value="firstName") String firstName) {
    	try {
			return new ResponseEntity<>(personDataService.findPerson(lastName, firstName),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    
    @GetMapping("/person/{lastName}")
    public ResponseEntity<?> findPersonsByLastName(@PathVariable(value="lastName") String lastName) {
        try {
			return new ResponseEntity<>(personDataService.findPersonsByLastName(lastName),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    
    @GetMapping("/persons")
    public ResponseEntity<?> getPersons() {
        return new ResponseEntity<>(personDataService.getPersons(), HttpStatus.OK);
    }
    
    @PostMapping("/person/save")
    public ResponseEntity<?> savePerson(@RequestBody Person person) {
    	try {
			return new ResponseEntity<>(personDataService.savePerson(person),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(person, HttpStatus.CONFLICT);
		}
    }
    
}