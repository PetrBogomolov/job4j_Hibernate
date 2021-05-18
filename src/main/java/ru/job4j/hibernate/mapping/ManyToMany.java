package ru.job4j.hibernate.mapping;

import javax.persistence.*;
import java.util.List;

public class ManyToMany {

    @Entity
    @Table(name = "cars")
    class Car {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String name;
        @ManyToMany(mappedBy = "cars")
        private List<Driver> drivers;
    }

    @Entity
    @Table(name = "drivers")
    class Driver {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String name;
        @ManyToMany
        private List<Car> cars;
    }
}
