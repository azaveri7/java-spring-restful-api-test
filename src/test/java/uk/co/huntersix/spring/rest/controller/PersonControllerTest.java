package uk.co.huntersix.spring.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
    	Person person = new Person("Mary", "Smith");
        when(personDataService.findPerson(any(), any())).thenReturn(person);
        this.mockMvc.perform(get("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Mary"))
            .andExpect(jsonPath("lastName").value("Smith"));
    }
    
    @Test
    public void shouldReturnPersonFromServiceByLastName() throws Exception {
    	Person person1 = new Person("Mary", "Smith");
    	List<Person> personList = new ArrayList<>();
    	personList.add(person1);
        when(personDataService.findPersonsByLastName(any())).thenReturn(personList);
        this.mockMvc.perform(get("/person/smith"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].firstName").value("Mary"))
            .andExpect(jsonPath("$[0].lastName").value("Smith"));
    }
    
    @Test
    public void shouldSavePersonInService() throws Exception {
    	Person person = new Person("Anand", "Zaveri");
    	when(personDataService.savePerson(any())).thenReturn(person);
    	this.mockMvc.perform(post("/person/save")
    			.content(new ObjectMapper().writeValueAsString(person))
    			.contentType(MediaType.APPLICATION_JSON))
    		.andDo(print())
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Anand"))
            .andExpect(jsonPath("lastName").value("Zaveri"));
    }
}