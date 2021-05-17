package ru.job4j.hibernate.mapping.onetomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        try (Session session = sf.openSession();) {
            session.beginTransaction();
            for (int i = 1; i <= 5; i++) {
                String model = String.format("x%s",i);
                session.save(new ModelAuto(model));
            }
            MarkAuto bmw = new MarkAuto("BMW");
            for (int i = 1; i <= 5; i++) {
                bmw.addModel(session.load(ModelAuto.class, i));
            }
            session.save(bmw);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
