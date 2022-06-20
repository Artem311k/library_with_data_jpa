package biblio.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {
    private int person_id;

    @NotEmpty(message = "Name must be not empty")
    @Size(min = 1, max = 100, message = "Name must be valid")
    private String name;


    @NotEmpty(message = "Date must be not empty")
    private String date = "00.00.0000";


    public Person(int person_id, String name, String date) {
        this.person_id = person_id;
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
}
