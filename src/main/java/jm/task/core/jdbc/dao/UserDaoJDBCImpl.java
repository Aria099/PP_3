package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private static final Connection conn = Util.getConnection();

    @Override
    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                " name VARCHAR(20), " +
                " lastName VARCHAR(20), " +
                " age TINYINT)";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Database has been created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS user";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Database has been dropped!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);

                preparedStatement.executeUpdate();

                System.out.printf("User с именем — " + name + " добавлен в базу данных\n");

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        String sql = "DELETE FROM user WHERE id = ?";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);

                preparedStatement.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT id, name, lastName, age FROM user";

        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {

        String sql = "TRUNCATE TABLE user";

        try (Statement statement = conn.createStatement()) {
            statement.execute(sql);
            System.out.println("Database has been cleaned!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
