package biblio.services;


import biblio.models.Book;
import biblio.models.Person;
import biblio.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleService) {
        this.peopleRepository = peopleService;
    }

    public List<Person> index() {
        return peopleRepository.findAll();
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    public Person show(int person_id) {
        return peopleRepository.findById(person_id).orElse(null);
    }

    @Transactional
    public void delete(int person_id) {
        peopleRepository.deleteById(person_id);
    }

    @Transactional
    public void update(int person_id, Person updatedPerson) {
        updatedPerson.setPerson_id(person_id);
        peopleRepository.save(updatedPerson);
    }

    public Person showBook(int book_id) {
        return peopleRepository.showBook(book_id).orElse(null);
    }


    public List<Book> getBooksByPersonId(int person_id) {
        Optional<Person> person = peopleRepository.findById(person_id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBookList());
            person.get().getBookList().forEach(book -> {
                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (diffInMillies > 864000000) {
                    book.setExpired(true);
                }
            });
            return person.get().getBookList();

        } else {
            return Collections.emptyList();
        }
    }

    public List<Person> findAllByNotId(int person_id) {
        return peopleRepository.findAllByNotId(person_id);
    }
}
