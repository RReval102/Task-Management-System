package com.example.taskmanagement.dao;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Data access object for {@link Task} entities.  Provides basic
 * CRUD operations using Hibernate sessions.  Each method opens a
 * new session and closes it automatically via try‑with‑resources.
 */
public class TaskDAO {

    public Task save(Task task) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(task);
            tx.commit();
            return task;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public Task getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Task.class, id);
        }
    }

    public List<Task> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Task", Task.class).list();
        }
    }

    public void delete(Task task) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(task);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}