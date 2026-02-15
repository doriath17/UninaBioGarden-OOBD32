package uninabiogarden.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import uninabiogarden.exceptions.DatabaseException;

public class Database {
  private static final String dbUrl = "jdbc:postgresql://localhost:5432/uninabiogarden";
  private static final String dbUser = "ubg_user";
  private static final String dbPassword = "ubg_password";

  private static final Database instance = new Database();
  private Connection connection;

  private Database() {
  }

  public static Database getInstance() {
    return instance;
  }

  public Connection getConnection() throws DatabaseException {
    try {
      if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      throw new DatabaseException();
    }
    return connection;
  }
}