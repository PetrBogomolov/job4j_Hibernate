package ru.job4j.hibernate.mapping;

import javax.persistence.*;
import java.util.List;

public class OneToOne {

    @Entity
    @Table(name = "cars")
    class Car {
        @Id
        private int id;
        private String name;
        @OneToOne(fetch = FetchType.LAZY)
        @MapsId
        private Driver driver;
    }

    @Entity
    @Table(name = "drivers")
    class Driver {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String name;
    }
}
