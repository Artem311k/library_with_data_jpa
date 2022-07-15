package biblio.services;


import biblio.models.Book;
import biblio.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(Sort.by("year"));
        else
            return bookRepository.findAll();
    }

    public List<Book> findAllByTitleContainingIgnoreCase(String title) {
        return bookRepository.findAllByTitleContainingIgnoreCase(title);
    }

    public Book show(int book_id) {
        return bookRepository.findById(book_id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
        bookRepository.setBookToPerson(1, book.getBook_id());
    }

    @Transactional
    public void update(int book_id, Book updatedBook) {
        Book bookToBeUpdated = bookRepository.findById(book_id).get();
        updatedBook.setBook_id(book_id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        updatedBook.setTakenAt(bookToBeUpdated.getTakenAt());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int book_id) {
        bookRepository.deleteById(book_id);
    }

    @Transactional
    public void setBookToPerson(int person_id, int book_id) {
        bookRepository.setBookToPerson(person_id, book_id);
        bookRepository.findById(book_id).ifPresent(book -> book.setTakenAt(new Date()));
    }


    @Transactional
    public void clearBook(int book_id) {
        bookRepository.clearBook(book_id);
        bookRepository.findById(book_id).ifPresent(book -> book.setTakenAt(null));
    }


}

