package biblio.controllers;



import biblio.models.Person;
import biblio.services.PeopleService;
import biblio.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PersonValidator personValidator;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("peopleWhoNotAdmin", peopleService.findAllByNotId(1));
        return "people/index";
    }

    @GetMapping("/{person_id}")
    public String show(@PathVariable("person_id") int person_id, Model model){
        model.addAttribute("person", peopleService.show(person_id));
        model.addAttribute("listbooks", peopleService.getBooksByPersonId(person_id));
        return "people/show";

    }

    @GetMapping("/new")
    public String save(Model model){
        model.addAttribute("person", new Person());

        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{person_id}")
    public String delete(@PathVariable("person_id") int person_id){

        peopleService.delete(person_id);
        return "redirect:/people";
    }

    @GetMapping("/{person_id}/edit")
    public String edit(@PathVariable("person_id") int person_id, Model model){
        model.addAttribute("person", peopleService.show(person_id));

        return "people/edit";
    }

    @PatchMapping("/{person_id}")
    public String update(@ModelAttribute("person") @Valid Person person, @PathVariable("person_id") int person_id,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.update(person_id, person);
        return "redirect:/people";
    }
}
