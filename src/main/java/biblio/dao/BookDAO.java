package biblio.dao;

import biblio.models.Book;
import biblio.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<Book> index(){
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int book_id){
        return jdbcTemplate.query("select * from book where book_id=?", new Object[]{book_id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book){
        jdbcTemplate.update("insert into book(person_id, title, author, year) values (?, ?, ? , ?)",
                book.getPerson_id(), book.getTitle(), book.getAuthor(), book.getYear());
    }


    public void update(Book updatedBook){
        jdbcTemplate.update("update book set person_id=?, title=?, author=?, year=? where book_id=?",
                updatedBook.getPerson_id(), updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(),
                updatedBook.getBook_id());
    }

    public void delete(int book_id){
        jdbcTemplate.update("delete from book where book_id=?", book_id);
    }

    public void setBook(int person_id, int book_id){
        jdbcTemplate.update("update book set person_id=? where book_id=?", person_id, book_id);
    }

    public List<Book> showPersonBooks(int person_id){
        return jdbcTemplate.query("select * from book where person_id=?", new Object[]{person_id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void clearBook(int book_id){
        jdbcTemplate.update("update book set person_id=1 where book_id=?", book_id);
    }
}
