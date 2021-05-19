package ru.job4j.hibernate.mapping.exception;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class Run {
    public static void main(String[] args) {
        List<Mark> marks = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Mark toyota = new Mark("toyota");
            session.save(toyota);
            session.save(new Model("camry", session.load(Mark.class, 1)));
            session.save(new Model("cruiser200", session.load(Mark.class, 1)));
            marks = session.createQuery("select distinct m from Mark m join fetch m.models").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Mark mark : marks) {
            for (Model model : mark.getModels()) {
                System.out.println(model);
            }
        }
    }
}
