package jm.task.core.jdbc.dao;

import com.sun.istack.logging.Logger;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.logging.Level;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = getSessionFactory();
    private Transaction transaction = null;
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName().getClass());

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users " + "(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(40) NOT NULL, lastName VARCHAR(40) NOT NULL, " + "age INT NOT NULL)").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.INFO, "Hibernate Exception");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.INFO, "Hibernate Exception");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();

        } catch (HibernateException e) {
            logger.log(Level.INFO, "Hibernate Exception");
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.INFO, "Hibernate Exception");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
            query.from(User.class);
            users = session.createQuery(query).getResultList();
            transaction.commit();

        } catch (HibernateException e) {
            logger.log(Level.INFO, "Hibernate Exception");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users;").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.INFO, "Hibernate Exception");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
