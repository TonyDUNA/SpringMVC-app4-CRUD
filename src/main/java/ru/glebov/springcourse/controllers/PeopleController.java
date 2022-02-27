package ru.glebov.springcourse.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.glebov.springcourse.dao.PersonDAO;
import ru.glebov.springcourse.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people") // все адреса в контроллере начинаются со /people
public class PeopleController {

    // внедряем объект DAO в контроллер
    private final PersonDAO personDAO; // заводим поле и через конструктор Spring внедряет зависимость в контроллер

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @GetMapping()// попадаем сюда просто по /people - веб форма со списком всех людей
    public String index(Model model) {
        // получим всех людей из DAO и передадим на отображение в представление
        model.addAttribute("people",personDAO.index()); // обратимся к DAO вызовем метод index
        return "people/index"; // вернем список
    }

    @GetMapping("/{id}") // веб форма оображения конкретного чловека
    public String show(@PathVariable("id") int id, Model model) { // извлечем id из урла и используем внутри метода
        // получим одного чела из DAO и передадим на отображение в представление
        model.addAttribute("person",personDAO.show(id));// подключен person, лежит то что пришло из DAO по id
        return "people/show";
    }

    @GetMapping("/new") // веб-форма для содания нового человека
    public String newPerson(Model model) { // внедряем модель, для передачи объекта в форму таймлиф
        model.addAttribute("person", new Person()); // без значения полей, соотв добавим в класс Person пустой конструктор

        return "people/new"; // название таймлиф шаблона
    }

    @PostMapping() // адрес не передаем, по /people должны попасть в этот метод
    // получить данные из формы, создать нов чел, положить в него данные из формы, добавить в БД
        public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) { // для
        // создания нов. объекта класса Person+положить в него данные из формы
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person); // добавим человек в БД
        return "redirect:/people";// вернем страницу через редирект
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        // ищем человека в БД и меняем его значения на полученные из формы
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

}
