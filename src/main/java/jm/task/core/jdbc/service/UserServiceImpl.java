package jm.task.core.jdbc.service;

import com.sun.istack.logging.Logger;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;
import java.util.logging.Level;

public class UserServiceImpl implements UserService {
    public final UserDao userDao;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName().getClass());

    public UserServiceImpl() {
        this.userDao = new UserDaoJDBCImpl();
    }

    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
        logger.log(Level.INFO, "Таблица создана");
    }
    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
        logger.log(Level.INFO, "Таблица удалена");
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        logger.log(Level.INFO, "Пользователь с именем - " + name + " добавлен в базу данных");
    }
    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
        logger.log(Level.INFO, "Пользователь удален");
    }
    @Override
    public List<User> getAllUsers() {
        logger.log(Level.INFO, userDao.getAllUsers().toString());
        return userDao.getAllUsers();
    }
    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
        logger.log(Level.INFO, "Таблица очищена");

    }
}