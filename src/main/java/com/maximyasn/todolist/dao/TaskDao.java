package com.maximyasn.todolist.dao;

import com.maximyasn.todolist.domain.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TaskDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public TaskDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Task task) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(task);
    }

    public List<Task> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession
                .createQuery("select t from Task t", Task.class)
                .getResultList();
    }

    public List<Task> findAllByPage(Integer offset, Integer limit) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("select t from Task t", Task.class)
                .setFirstResult(offset)
                .setMaxResults(limit).getResultList();
    }

    public int getAllCount() {
        Session currentSession = sessionFactory.getCurrentSession();
        Long singleResult = currentSession
                .createQuery("select count(*) from Task", Long.class)
                .getSingleResult();
        return Math.toIntExact(singleResult);
    }

    public Task findOne(Integer id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Task.class, id);
    }

    @Transactional
    public void update(Task task) {
        sessionFactory.getCurrentSession().merge(task);
    }

    @Transactional
    public void delete(Integer id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Task task = currentSession.get(Task.class, id);
        currentSession.remove(task);
    }


}
