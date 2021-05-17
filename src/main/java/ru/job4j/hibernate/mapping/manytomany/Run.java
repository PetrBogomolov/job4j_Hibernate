package ru.job4j.hibernate.mapping.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Run {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        try (Session session = sf.openSession();) {
            session.beginTransaction();
            Book first = new Book("one");
            Book second = new Book("second");
            Book third = new Book("third");
            Author author1 = new Author("author1");
            Author author2 = new Author("author2");
            Author author3 = new Author("author3");
            author1.addBook(first);
            author2.addBook(first);
            author2.addBook(second);
            author3.addBook(first);
            author3.addBook(second);
            author3.addBook(third);
            session.persist(author1);
            session.persist(author2);
            session.persist(author3);
            session.remove(session.get(Author.class, 2));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
