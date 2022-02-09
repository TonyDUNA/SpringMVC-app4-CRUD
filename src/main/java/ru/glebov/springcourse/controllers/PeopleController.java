package ru.glebov.springcourse.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.glebov.springcourse.dao.PersonDAO;

@Controller
@RequestMapping("/people") // все адреса в контроллере начинаются со /people
public class PeopleController {

    // внедряем объект DAO в контроллер
    private final PersonDAO personDAO; // заводим поле и через конструктор Spring внедряет зависимость в контроллер

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @GetMapping()// попадаем сюда просто по /people
    public String index(Model model) {
        // получим всех людей из DAO и передадим на отображение в представление
        model.addAttribute("people",personDAO.index()); // обратимся к DAO вызовем метод index
        return "people/index"; // вернем список
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { // извлечем id из урла и используем внутри метода
        // получим одного чела из DAO и передадим на отображение в представление
        model.addAttribute("person",personDAO.show(id));// подключен person, лежит то что пришло из DAO по id
        return "people/show";
    }
}
