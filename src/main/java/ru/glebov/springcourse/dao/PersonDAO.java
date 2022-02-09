package ru.glebov.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.glebov.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component // для внедрения бина в контроллер
public class PersonDAO {
    private static int PEOPLE_COUNT; // для динамического id
    private List<Person> people; // список - БД людей

    // для создания людей - конструктор или блок инициализации
    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Alex"));
        people.add(new Person(++PEOPLE_COUNT, "Alim"));
        people.add(new Person(++PEOPLE_COUNT, "Nora"));
        people.add(new Person(++PEOPLE_COUNT, "Alla"));
        people.add(new Person(++PEOPLE_COUNT, "Dora"));
    }

    // два метода:
    public List<Person> index() { // возвращает список людей
        return people;
    }

    public Person show(int id) {
        // можно использовать цикл for - пройтись по списку, если id совпадает - вернуть значение, если нет null
        // находим человека, возвращаем id, если нет возвращаем null через лямбда выражения java8
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
}
