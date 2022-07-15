package biblio.models;


import javax.persistence.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner; // <--default person_id is 1 - Admin. Books on Admin are free.--> //

    @Column(name = "title")
    @NotEmpty(message = "Name must be not empty")
    private String title = "Untitled";

    @Column(name = "author")
    @NotEmpty(message = "Author must be not empty")
    private String author = "No Author";

    @Column(name = "year")
    @Range(min=1, max=2022, message = "Year must be valid")
    private int year = 1900;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(){

    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getFormatedTakenAtDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(getTakenAt());
    }
}
