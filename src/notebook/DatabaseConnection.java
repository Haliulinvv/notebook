package notebook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  // Параметры подключения (измени на свои!)
  private static final String URL = "jdbc:postgresql://localhost:5432/myappdb";
  private static final String USER = "postgres";
  private static final String PASSWORD = "CoDeSys711";
  
  
  /**
   * Метод для получения соединения с базой данных.
   */

  public static Connection getConnection() throws SQLException {
    Connection conn = null;
    try {
      // Регистрируем драйвер (в новых версиях Java это необязательно, но полезно)
      Class.forName("org.postgresql.Driver");
      // Устанавливаем соединение
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (ClassNotFoundException e) {
      System.err.println("PostgreSQL JDBC Driver не найден.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("Ошибка подключения к базе данных.");
      throw e; // Пробрасываем исключение дальше
    }
    return conn;
  }
}