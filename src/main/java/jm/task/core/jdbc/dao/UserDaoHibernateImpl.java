package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try(final Session session = Util.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            session.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id BIGSERIAL PRIMARY KEY, " +
                            "name VARCHAR(50), " +
                            "lastName VARCHAR(50), " +
                            "age SMALLINT)"
            ).executeUpdate();
            tx.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try(final Session session = Util.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            tx.commit();
        }
    }

    @Override
    public void saveUser(final String name, final String lastName, final byte age) {
        try(final Session session = Util.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            final User user = new User(null, name, lastName, age);
            session.save(user);
            tx.commit();
        }
    }

    @Override
    public void removeUserById(final long id) {
        try(final Session session = Util.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            final User user = session.get(User.class, id);
            if(user != null) {
                session.delete(user);
            }
            tx.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(final Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try(final Session session = Util.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            tx.commit();
        }

    }
}
