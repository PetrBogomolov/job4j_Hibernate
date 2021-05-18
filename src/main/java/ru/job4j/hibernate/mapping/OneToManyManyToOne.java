package ru.job4j.hibernate.mapping;

import javax.persistence.*;
import java.util.List;

public class OneToManyManyToOne {

    @Entity
    @Table(name = "cars")
    class Car {
        private int id;
        private String name;
        @ManyToOne
        @JoinColumn(name = "engine_id")
        private Engine engine;
    }

    @Entity
    @Table(name = "engines")
    class Engine {
        private int id;
        private String name;
        @OneToMany(mappedBy = "engine")
        private List<Car> cars;
    }
}
