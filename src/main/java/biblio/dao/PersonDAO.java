package biblio.dao;

import biblio.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index(){
        return jdbcTemplate.query("select * from person where person_id!=1",
                new BeanPropertyRowMapper<>(Person.class));

    }

    public void save(Person person){

        jdbcTemplate.update("insert into person(name, date) values (?, ?)", person.getName(), person.getDate());

    }

    public Person show(String name){
        return jdbcTemplate.query("select * from person where name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
    public Person show(int person_id){
        return jdbcTemplate.query("select * from person where person_id=?", new Object[]{person_id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void delete(int person_id){
        jdbcTemplate.update("delete from person where person_id=?", person_id);
    }

    public void update(int person_id, Person updatedPerson){

//        Person oldPerson = jdbcTemplate.query("select * from person where person_id=?", new Object[]{person_id},
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
//        if (oldPerson.getDate().equals(updatedPerson.getPerson_id())){
//            correctDate = oldPerson.getDate();
//        }
        jdbcTemplate.update("update person set name=?, date=? where person_id=?", updatedPerson.getName(),
                updatedPerson.getDate(), updatedPerson.getPerson_id());
    }
    public Person showBook(int book_id){
        return jdbcTemplate.query("select * from person join book " +
                        "on person.person_id = book.person_id where book_id=?", new Object[]{book_id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

}
