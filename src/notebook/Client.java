package notebook;

public class Client {
  private int id;
  private String name;
  private String phone;
  private String email;
  
  /**
   * Конструктор без id (для новых записей, которые еще не в базе).
   */  
  
  // 
  public Client(String name, String phone, String email) {
    this.name = name;
    this.phone = phone;
    this.email = email;
  }
  
  /**
   * Конструктор с id (для существующих записей из базы).
   */
  
  public Client(int id, String name, String phone, String email) {
    this.id = id;
    this.name = name;
    this.phone = phone;
    this.email = email;
  }
  
  // Геттеры и сеттеры (для доступа к полям)
  public int getId() { 
    return id;
  }
  //public void setId(int id) { this.id = id; }
  
  public String getName() {
    return name;
  }
  //public void setName(String name) { this.name = name; }
  
  public String getPhone() {
    return phone;
  }
  //public void setPhone(String phone) { this.phone = phone; }
  
  public String getEmail() {
    return email; 
  }
  //public void setEmail(String email) { this.email = email; }
}