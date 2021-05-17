package ru.job4j.hibernate.mapping.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "markauto")
public class MarkAuto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModelAuto> models = new ArrayList<>();

    public MarkAuto() {
    }

    public MarkAuto(String name) {
        this.name = name;
    }

    public MarkAuto(int id, String name, List<ModelAuto> models) {
        this.id = id;
        this.name = name;
        this.models = models;
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

    public List<ModelAuto> getModels() {
        return models;
    }

    public void setModels(List<ModelAuto> models) {
        this.models = models;
    }

    public void addModel(ModelAuto model) {
        models.add(model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarkAuto markAuto = (MarkAuto) o;
        return id == markAuto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
