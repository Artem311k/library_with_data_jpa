package biblio.controllers;

import biblio.dao.BookDAO;
import biblio.dao.PersonDAO;
import biblio.models.Book;
import biblio.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    //TODO BookValidator validation if needed

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{book_id}")
    public String show(@PathVariable("book_id") int book_id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.show(book_id));
        model.addAttribute("people", personDAO.index());
        model.addAttribute("current_person", personDAO.showBook(book_id));
        return "books/show";
    }

    @GetMapping("/new")
    public String save(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }


    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(@PathVariable("book_id") int book_id, Model model){
        model.addAttribute("book", bookDAO.show(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDAO.update(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id") int book_id){
        bookDAO.delete(book_id);
        return "redirect:/books";

    }

    @PatchMapping("/{book_id}/set")
    public String setBook(@ModelAttribute("person") Person person, @PathVariable("book_id") int book_id){
        bookDAO.setBook(person.getPerson_id(), book_id);
        return "redirect:/books/" + book_id;
    }

    @PatchMapping("/{book_id}/clear")
    public String clearBook(@PathVariable("book_id") int book_id){
        bookDAO.clearBook(book_id);
        return "redirect:/books/" + book_id;
    }
}
