package uk.co.huntersix.spring.rest.referencedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import uk.co.huntersix.spring.rest.model.Person;

@Service
public class PersonDataService {
	public static List<Person> PERSON_DATA = new ArrayList<>();

	static {

		PERSON_DATA.add(new Person("Mary", "Smith"));
		PERSON_DATA.add(new Person("Brian", "Archer"));
		PERSON_DATA.add(new Person("Collin", "Brown"));
	}

	public Person findPerson(String lastName, String firstName) throws Exception {
		Person person = PERSON_DATA.stream()
				.filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
				.findFirst().orElse(null);
		if (Objects.nonNull(person))
			return person;
		else
			throw new Exception();
	}

	public List<Person> getPersons() {
		return PERSON_DATA;
	}

	public List<Person> findPersonsByLastName(String lastName) throws Exception {
		List<Person> personList = PERSON_DATA.stream().filter(p -> p.getLastName().equalsIgnoreCase(lastName))
				.collect(Collectors.toList());
		if (Objects.nonNull(personList) && !personList.isEmpty()) {
			return personList;
		} else {
			throw new Exception();
		}
	}

	public Person savePerson(Person person) throws Exception {
		if (Objects.nonNull(person)) {
			Optional<Person> ifPersonPresent = PERSON_DATA.stream()
					.filter(p -> p.getFirstName().equalsIgnoreCase(person.getFirstName())
							&& p.getLastName().equalsIgnoreCase(person.getLastName()))
					.findAny();
			if (!ifPersonPresent.isPresent()) {
				PERSON_DATA.add(person);
			} else {
				throw new Exception();
			}
		}
		return person;
	}
}
