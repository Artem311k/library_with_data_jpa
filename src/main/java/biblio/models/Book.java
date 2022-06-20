package biblio.models;


import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {

    private int book_id;
    private int person_id = 1; // <--default person_id is 1 - Admin. Books on Admin are free.--> //
    @NotEmpty(message = "Name must be not empty")
    private String title = "Untitled";
    @NotEmpty(message = "Author must be not empty")
    private String author = "No Author";

    @Range(min=1, max=2022, message = "Year must be valid")
    private int year = 1900;

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

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
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
}
