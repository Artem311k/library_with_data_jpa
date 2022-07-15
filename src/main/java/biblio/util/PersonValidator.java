package biblio.util;


import biblio.models.Person;
import biblio.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {


    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;

            if(peopleRepository.findByName(person.getName()).isPresent()) {
                if(peopleRepository.findByName(person.getName()).get().getPerson_id() != person.getPerson_id()){
                    errors.rejectValue("name", "", "This name is already exists");
                }

            }


    }
}
