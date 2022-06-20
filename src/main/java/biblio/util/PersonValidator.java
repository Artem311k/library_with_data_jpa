package biblio.util;

import biblio.dao.PersonDAO;
import biblio.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;

        if (personDAO.show(person.getName()) != null) {
            Person returnedPerson = personDAO.show(person.getName());
            if(returnedPerson.getPerson_id() != person.getPerson_id()) {
                errors.rejectValue("name", "", "This name is already exists");
            }
        }



    }
}
