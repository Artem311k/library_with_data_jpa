package biblio.repositories;

import biblio.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);

    @Query(value = "select * from person where person_id != ?1", nativeQuery = true)
    List<Person> findAllByNotId(int person_id);

    @Query(value = "select * from person join book on person.person_id = book.person_id where book_id=?1",
            nativeQuery = true)
    Optional<Person> showBook(int book_id);

}
