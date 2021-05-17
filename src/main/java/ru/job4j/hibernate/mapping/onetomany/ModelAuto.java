package ru.job4j.hibernate.mapping.onetomany;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "modelsauto")
public class ModelAuto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public ModelAuto() {
    }

    public ModelAuto(String name) {
        this.name = name;
    }

    public ModelAuto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelAuto modelAuto = (ModelAuto) o;
        return id == modelAuto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
