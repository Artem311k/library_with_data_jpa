package biblio.repositories;

import biblio.models.Book;
import biblio.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByTitleContainingIgnoreCase(String title);

    @Modifying
    @Query(value = "update book set person_id = ?1 where book_id = ?2", nativeQuery = true)
    void setBookToPerson(int person_id, int book_id);


    @Query(value = "select * from book where person_id=?1", nativeQuery = true)
    List<Book> showPersonBooks(int person_id);

    @Modifying
    @Query(value = "update book set person_id=1 where book_id=?1", nativeQuery = true)
    void clearBook(int book_id);

}
