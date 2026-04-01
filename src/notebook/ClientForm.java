package notebook;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientForm extends JFrame {

  private static final long serialVersionUID = 1L;
  private JFrame frame = new JFrame("Записная книга");

  private JTextField nameField = new JTextField(15);
  private JTextField phoneField = new JTextField(15);
  private JTextField emailField = new JTextField(15);
  private JTextField idField = new JTextField(5);
  private JButton addButton = new JButton("Добавить");
  private JButton listButton = new JButton("Показать всех");
  private JButton deleteButton = new JButton("Удалить");
  private JButton updateButton = new JButton("Обновить");
  
  // Загружаем иконку
  ImageIcon originalIcon = new ImageIcon(getClass().getResource("/find.png"));
  // Получаем изображение
  Image img = originalIcon.getImage();
  // Масштабируем (можно использовать MediaTracker для ожидания, но обычно работает)
  Image scaledImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
  private JButton findButton = new JButton("Найти", new ImageIcon(scaledImg));
  
  private JButton sortNameButton = new JButton("Сортировать");
  private JTextArea displayArea = new JTextArea(10, 30);
  private JScrollPane scrollPane = new JScrollPane(displayArea);
  private ClientDataAccessObject clientDataAccessObject = new ClientDataAccessObject();


  /**
   * Создаёт новый экземпляр формы ClientForm. Инициализирует компоненты интерфейса и подключается к
   * базе данных.
   *
   * @author
   *
   */

  public ClientForm() {

    // Иконка окна
    ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
    if (icon.getImage() != null) {
      frame.setIconImage(icon.getImage());
    } else {
      System.err.println("Иконка не найдена");
    }
    
    frame.setLayout(null); // отключаем менеджер

    // ... настройка интерфейса (размещение полей и кнопок на форме) ...
    // Главная форма приложения
    frame.setSize(1000, 550);
    //frame.setResizable(false);
    frame.setLocation(300, 100);
    //frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  
    addButton.setBounds(450, 50, 200, 30); // x, y, ширина, высота
    listButton.setBounds(450, 100, 200, 30);
    deleteButton.setBounds(450, 470, 200, 30);
    updateButton.setBounds(450, 150, 200, 30);
    findButton.setBounds(750, 50, 200, 30);
    sortNameButton.setBounds(750, 100, 200, 30);
    frame.add(addButton);
    frame.add(listButton);
    frame.add(deleteButton);
    frame.add(updateButton);
    frame.add(findButton);
    frame.add(sortNameButton);
    
    // Добавляем компоненты на фрейм
    nameField.setBounds(100, 50, 300, 30); // x, y, ширина, высота
    phoneField.setBounds(100, 100, 300, 30);
    emailField.setBounds(100, 150, 300, 30);
    idField.setBounds(100, 470, 300, 30);

    frame.add(nameField, BorderLayout.NORTH);
    frame.add(phoneField, BorderLayout.NORTH);
    frame.add(emailField, BorderLayout.NORTH);
    frame.add(idField, BorderLayout.NORTH);

    JLabel labelName = new JLabel("Имя");
    labelName.setBounds(30, 50, 50, 30); // x, y, ширина, высота
    frame.add(labelName);

    JLabel labelPhone = new JLabel("Телефон");
    labelPhone.setBounds(30, 100, 70, 30); // x, y, ширина, высота
    frame.add(labelPhone);

    JLabel labelMail = new JLabel("E-mail");
    labelMail.setBounds(30, 150, 50, 30); // x, y, ширина, высота
    frame.add(labelMail);

    JLabel labelId = new JLabel("ID");
    labelId.setBounds(30, 470, 50, 30); // x, y, ширина, высота
    frame.add(labelId);

    // Добавляем на фрейм
    //displayArea.setBounds(20, 200, 300, 250); // x, y, ширина, высота
    //frame.add(displayArea);
    displayArea.setEditable(false);
    scrollPane.setBounds(20, 200, 950, 250); // x, y, ширина, высота
    frame.add(scrollPane);

    // Обработка события Добавления записи в таблицу
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty()) {
          JOptionPane.showMessageDialog(ClientForm.this, "Имя не может быть пустым!");
          return;
        }
        JOptionPane.showMessageDialog(ClientForm.this, "Вы ввели имя: " + name + "\n"
            + "Телефон : " + phone + "\n"
            + "E-mail : " + email);

        Client newClient = new Client(name, phone, email);
        clientDataAccessObject.addClient(newClient); // Вот здесь мы вызываем метод для работы с БД

        JOptionPane.showMessageDialog(ClientForm.this, "Клиент добавлен!");
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        updateClientList();
      }
    });

    // Обработка события Вывести все записи из таблицы в многострочное текстовое поле
    listButton.addActionListener(e -> {
      displayArea.setText(""); // Очищаем область
      for (Client client : clientDataAccessObject.getAllClients()) {
        displayArea.append("ID: " + client.getId()
            + " Имя: " + client.getName()
            + " Телефон: " + client.getPhone()
            + " e-mail: " + client.getEmail() + "\n");

      }
    });

    // Обработка события Удаления записи из таблицы
    deleteButton.addActionListener(e -> {
      String idText = idField.getText().trim();
      if (idText.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Введите ID записи для удаления.");
        return;
      }
      try {
        int id = Integer.parseInt(idText);
        // Вызываем метод удаления
        clientDataAccessObject.deleteClient(id);
        // Обновляем список клиентов (вызываем ту же логику, что и при нажатии "Показать всех")
        updateClientList(); // метод для обновления displayArea
        idField.setText(""); // очищаем поле
        JOptionPane.showMessageDialog(frame, "Запись удалена.");
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "ID должен быть целым числом.");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "Ошибка при удалении: " + ex.getMessage());
        ex.printStackTrace();
      }
    });

    // Обработка события Обновления записи в таблице
    updateButton.addActionListener(e -> {
      String idText = idField.getText();
      String name = nameField.getText();
      String phone = phoneField.getText();
      String email = emailField.getText();
      try {
        int id = Integer.parseInt(idText);
        Client updateClient = new Client(id, name, phone, email);
        clientDataAccessObject.updateClient(updateClient);
        updateClientList();
        JOptionPane.showMessageDialog(frame, "Запись обновлена.");
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "ID должен быть целым числом.");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "Ошибка при обновлении: " + ex.getMessage());
        ex.printStackTrace();
      }
    });

    // Обработка события Добавления записи в таблицу
    findButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        if (name.isEmpty()) {
          JOptionPane.showMessageDialog(ClientForm.this, "Имя не может быть пустым!");
          return;
        }
        displayArea.setText(""); // Очищаем область
        for (Client client : clientDataAccessObject.getFindClients(name)) {
          displayArea.append("ID: " + client.getId()
              + " Имя: " + client.getName()
              + " Телефон: " + client.getPhone()
              + " e-mail: " + client.getEmail() + "\n");

        }
      }
      
    });
    
    
    // Обработка события Сортировать записи таблици по имени
    sortNameButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        if (name.isEmpty()) {
          JOptionPane.showMessageDialog(ClientForm.this, "Имя не может быть пустым!");
          return;
        }
        displayArea.setText(""); // Очищаем область
        for (Client client : clientDataAccessObject.getSortNameClients(name)) {
          displayArea.append(" Имя: " + client.getName()
                           + "    Телефон: " + client.getPhone() + "\n");

        }
      }
      
    });
    
    
  }


  private void updateClientList() {
    displayArea.setText("");
    for (Client client : clientDataAccessObject.getAllClients()) {
      displayArea.append("ID: " + client.getId()
          + " Имя: " + client.getName()
          + " Телефон: " + client.getPhone()
          + " e-mail: " + client.getEmail() + "\n");

    }
  }
}
