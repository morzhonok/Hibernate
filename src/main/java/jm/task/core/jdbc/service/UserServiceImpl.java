package jm.task.core.jdbc.service;

import com.sun.istack.logging.Logger;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;
import java.util.logging.Level;

public class UserServiceImpl implements UserService {
    public UserDao userDaoHibernate = new UserDaoHibernateImpl();
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName().getClass());


    public void createUsersTable() {
        userDaoHibernate.createUsersTable();
        logger.log(Level.INFO, "Таблица создана");
    }

    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
        logger.log(Level.INFO, "Таблица удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoHibernate.saveUser(name, lastName, age);
        logger.log(Level.INFO, "Пользователь с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
        logger.log(Level.INFO, "Пользователь удален");
    }

    public List<User> getAllUsers() {
        logger.log(Level.INFO, userDaoHibernate.getAllUsers().toString());
        return userDaoHibernate.getAllUsers();
    }

    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
        logger.log(Level.INFO, "Таблица очищена");

    }
}