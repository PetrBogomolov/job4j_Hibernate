package ru.job4j.hibernate.hql.selectfetch;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Run {
    public static void main(String[] args) {
        Candidacy result = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            Candidacy candidacy = new Candidacy("candidate");
            DBVacancy db = new DBVacancy("dbase");
            candidacy.setDb(db);

            Vacancy first = new Vacancy("java");
            Vacancy second = new Vacancy("JS");
            db.addVacancy(first);
            db.addVacancy(second);
            session.save(candidacy);
            session.save(db);

            result = (Candidacy) session.createQuery("select distinct c from Candidacy c " +
                                                                    "join fetch c.db d " +
                                                                    "join fetch d.vacancies").uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(result);
    }
}
