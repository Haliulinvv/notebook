package notebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDataAccessObject {


  /**
   * Получить всех клиентов.
   */

  public List<Client> getAllClients() {
    List<Client> clients = new ArrayList<>();
    String sql = "SELECT * FROM clients ORDER BY id";

    // try-with-resources автоматически закроет Connection, Statement и ResultSet
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Client client = new Client(
                      rs.getInt("id"),
                      rs.getString("name"),
                      rs.getString("phone"),
                      rs.getString("email")
                );
        clients.add(client);
      }
    } catch (SQLException e) {
      System.err.println("Ошибка при получении списка клиентов.");
      e.printStackTrace();
    }
    return clients;
  }

  
  /**
   * Добавить нового клиента.
   */

  public void addClient(Client client) {
    // Используем PreparedStatement для защиты от SQL-инъекций
    String sql = "INSERT INTO clients (name, phone, email) VALUES (?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, client.getName());
      pstmt.setString(2, client.getPhone());
      pstmt.setString(3, client.getEmail());

      pstmt.executeUpdate();
      System.out.println("Клиент добавлен!");

    } catch (SQLException e) {
      System.err.println("Ошибка при добавлении клиента.");
      e.printStackTrace();
    }
  }

  
  /**
   * Обновить данные клиента.
   */

  public void updateClient(Client client) {
    String sql = "UPDATE clients SET name = ?, phone = ?, email = ? WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      
      pstmt.setString(1, client.getName());
      pstmt.setString(2, client.getPhone());
      pstmt.setString(3, client.getEmail());
      pstmt.setInt(4, client.getId());
      
      pstmt.executeUpdate();
      System.out.println("Клиент обновлен!");
      
    } catch (SQLException e) {
      System.err.println("Ошибка при обновлении клиента.");
      e.printStackTrace();
    }
  }

  /**
   * Удалить клиента по ID.
   */
  
  public void deleteClient(int id) {
    String sql = "DELETE FROM clients WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("Клиент удален!");

    } catch (SQLException e) {
      System.err.println("Ошибка при удалении клиента.");
      e.printStackTrace();
    }
  }
  
  /**
   * Поиск клиентов в таблице по name.
   */

  public List<Client> getFindClients(String findName) {
    List<Client> clients = new ArrayList<>();
    String sql = "SELECT * FROM clients WHERE name LIKE '" + findName  + "%'";

    // try-with-resources автоматически закроет Connection, Statement и ResultSet
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Client client = new Client(
                      rs.getInt("id"),
                      rs.getString("name"),
                      rs.getString("phone"),
                      rs.getString("email")
                );
        clients.add(client);
      }
    } catch (SQLException e) {
      System.err.println("Ошибка при получении списка клиентов.");
      e.printStackTrace();
    }
    return clients;
  }
  
  /**
   * Сортировать клиентов по имени.
   */
  
  public List<Client> getSortNameClients(String findName) {
    List<Client> clients = new ArrayList<>();
    String sql = "SELECT * FROM clients WHERE name LIKE '" + findName  + "%' ORDER BY name";

    // try-with-resources автоматически закроет Connection, Statement и ResultSet
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Client client = new Client(
                      rs.getInt("id"),
                      rs.getString("name"),
                      rs.getString("phone"),
                      rs.getString("email")
                );
        clients.add(client);
      }
    } catch (SQLException e) {
      System.err.println("Ошибка при получении списка клиентов.");
      e.printStackTrace();
    }
    return clients;
  }
  
  /**
   * Установка nextval.
   */
  
  public void setNextvalClients(int id) {
    
    String sql = "ALTER SEQUENCE clients_id_seq RESTART WITH " + id;

    // try-with-resources автоматически закроет Connection, Statement и ResultSet
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      //pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("Sequence сброшена. Следующий ID будет: " + id);

    } catch (SQLException e) {
      System.err.println("Ошибка при сбросе sequence: " + e.getMessage());
      e.printStackTrace();
    }

  }
  
  
  /**
   * Проверка дубликатов клиентов по имени и телефону.
   */
  public boolean clientExists(String name, String phone) {
    String sql = "SELECT COUNT(*) FROM clients WHERE name = ? AND phone = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
      pstmt.setString(1, name);
      pstmt.setString(2, phone);
      
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1) > 0;
        }
      }
    } catch (SQLException e) {
      System.err.println("Ошибка при проверке дубликата: " + e.getMessage());
    }
    return false;
  }
  
  
}
