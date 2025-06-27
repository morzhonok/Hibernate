package jm.task.core.jdbc.dao;
import com.sun.istack.logging.Logger;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class UserDaoJDBCImpl implements UserDao {
private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName().getClass());

    public UserDaoJDBCImpl() {
        this.connection = Util.getConnection();
    }

    @Override
    public void createUsersTable() {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS users (" + " id INT NOT NULL AUTO_INCREMENT," + " name VARCHAR(40)," + "lastName VARCHAR(40)," + "age INT," + "PRIMARY KEY(id));");
            } catch (SQLException e) {
                LOGGER.log(Level.INFO, "При создании таблицы возникла ошибка", e);
                throw new RuntimeException("Ошибка при создании таблицы", e);
        }
    }

    @Override
    public void dropUsersTable() {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS users");
            } catch (SQLException e) {
                LOGGER.log(Level.INFO, "При открытии соединения возникла ошибка", e);
                throw new RuntimeException("При удалении пользователя возникла ошибка", e);
            }
        }

    @Override
    public void saveUser(String name, String lastName, byte age) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, String.valueOf(age));
                preparedStatement.executeUpdate();
                System.out.println("Пользователь" + name + "успешно добавлен");
            } catch (SQLException e) {
                LOGGER.log(Level.INFO, "При сохранении пользователя возникла ошибка", e);
                throw new RuntimeException("При сохранении пользователя возникла ошибка", e);
            }
        }

    @Override
    public void removeUserById(long id) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.log(Level.INFO, "Ошибка при сохранении пользователя", e);
                throw new RuntimeException("Ошибка при сохранении пользователя", e);
            }
        }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT ID, NAME, LASTNAME, AGE FROM users")) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("ID"));
                    user.setName(resultSet.getString("NAME"));
                    user.setLastName(resultSet.getString("LASTNAME"));
                    user.setAge(resultSet.getByte("AGE"));
                    userList.add(user);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.INFO, "Возникла ошибка при извлечении пользователя", e);
                throw new RuntimeException("Возникла ошибка при извлечении пользователя", e);
            }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("TRUNCATE TABLE users");
            } catch (SQLException e) {
                LOGGER.log(Level.INFO, "При очистки таблицы возникла ошибка", e);
                throw new RuntimeException("При очистки таблицы возникла ошибка", e);
            }
        }
    }
