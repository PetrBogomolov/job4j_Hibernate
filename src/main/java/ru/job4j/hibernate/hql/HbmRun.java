package ru.job4j.hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;


import java.util.List;

public class HbmRun {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public void addCandidate(Candidate candidate) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(candidate);
            session.getTransaction();
        }
    }

    public List<Candidate> selectAllCandidates() {
        List candadates;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            candadates = session.createQuery("from Candidate").list();
            session.getTransaction().commit();
        }
        return candadates;
    }

    public Candidate selectById(int id) {
        Candidate candidate;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            candidate = session.get(Candidate.class, id);
            session.getTransaction().commit();
        }
        return candidate;
    }

    public Candidate selectByName(String name) {
        Candidate candidate;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Candidate c where c.name = :name");
            query.setParameter("name", name);
            candidate = (Candidate) query.uniqueResult();
            session.getTransaction().commit();
        }
        return candidate;
    }

    public void updateCandidate(int id, Candidate candidate) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            candidate.setId(id);
            session.createQuery("update Candidate c set " +
                    "c.name = :newName, " +
                    "c.experience = :newExperience," +
                    "c.salary = :newSalary" +
                    " where c.id = :id")
                    .setParameter("newName", candidate.getName())
                    .setParameter("newExperience", candidate.getExperience())
                    .setParameter("newSalary", candidate.getSalary())
                    .setParameter("id", candidate.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void deleteById(int id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from Candidate where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    public static void main(String[] args) {
        HbmRun db = new HbmRun();
        db.addCandidate(new Candidate(1, "name", "two years", 100000));
        db.addCandidate(new Candidate(1, "name2", "three years", 150000));
        db.addCandidate(new Candidate(1, "name3", "four years", 200000));
        db.selectAllCandidates().forEach(System.out::println);
        System.out.println(db.selectById(1));
        System.out.println(db.selectByName("name"));
        db.updateCandidate(2, new Candidate("name", "two years", 100000));
        db.deleteById(1);
    }
}
