package biblio.models;

import javax.persistence.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @NotEmpty(message = "Name must be not empty")
    @Size(min = 1, max = 100, message = "Name must be valid")
    private String name;


    @NotEmpty(message = "Date must be not empty")
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private String date;

    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;


    public Person(String name, String date) {
        this.name = name;
        this.date = date;

    }

    public Person(){

    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
