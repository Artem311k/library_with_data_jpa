package biblio.controllers;


import biblio.models.Book;
import biblio.models.Person;
import biblio.services.BookService;
import biblio.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    //TODO BookValidator validation if needed
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

        if (page == null || booksPerPage == null)
            model.addAttribute("books", bookService.findAll(sortByYear));
        else
            model.addAttribute("books", bookService.findWithPagination(page, booksPerPage, sortByYear));

        return "books/index";
    }

    @GetMapping("/{book_id}")
    public String show(@PathVariable("book_id") int book_id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookService.show(book_id));
        model.addAttribute("people", peopleService.index());
        model.addAttribute("current_person", peopleService.showBook(book_id));
        model.addAttribute("peopleNotHavingBook", peopleService.findAllByNotId(peopleService.showBook(book_id).getPerson_id()));
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
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(@PathVariable("book_id") int book_id, Model model){
        model.addAttribute("book", bookService.show(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("book_id") int book_id,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        bookService.update(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id") int book_id){
        bookService.delete(book_id);
        return "redirect:/books";

    }

    @PatchMapping("/{book_id}/set")
    public String setBook(@ModelAttribute("person") Person person, @PathVariable("book_id") int book_id){
        bookService.setBookToPerson(person.getPerson_id(), book_id);
        return "redirect:/books/" + book_id;
    }

    @PatchMapping("/{book_id}/clear")
    public String clearBook(@PathVariable("book_id") int book_id){
        bookService.clearBook(book_id);
        return "redirect:/books/" + book_id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("title") String title) {
        model.addAttribute("books", bookService.findAllByTitleContainingIgnoreCase(title));
        return "books/search";

    }
}
