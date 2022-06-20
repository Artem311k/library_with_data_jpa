package biblio.util;

import biblio.dao.BookDAO;
import biblio.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookValidator implements Validator {


    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;


    }
}
