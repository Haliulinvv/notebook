package notebook;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

  ImageIcon originalIcon = new ImageIcon(getClass().getResource("/find.png")); // Загружаем иконку
  Image img = originalIcon.getImage(); // Получаем изображение
  //Масштабируем (можно использовать MediaTracker для ожидания, но обычно работает)
  Image scaledImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
  private JButton findButton = new JButton("Найти", new ImageIcon(scaledImg));
  
  private JButton sortNameButton = new JButton("Сортировать");
  
  ImageIcon exportIcon = new ImageIcon(getClass().getResource("/export.png"));
  Image exportImg = exportIcon.getImage();
  Image exportScaledImg = exportImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
  private JButton exportButton = new JButton(new ImageIcon(exportScaledImg));
  
  ImageIcon importIcon = new ImageIcon(getClass().getResource("/import.png"));
  Image importImg = importIcon.getImage();
  Image importScaledImg = importImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
  private JButton importButton = new JButton(new ImageIcon(importScaledImg));
  
  private JButton setNextvalDbButton = new JButton("Set nextval DB");
  
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
    exportButton.setBounds(750, 470, 30, 30);
    importButton.setBounds(790, 470, 30, 30);
    setNextvalDbButton.setBounds(750, 150, 200, 30);
    frame.add(addButton);
    frame.add(listButton);
    frame.add(deleteButton);
    frame.add(updateButton);
    frame.add(findButton);
    frame.add(sortNameButton);
    frame.add(exportButton);
    frame.add(importButton);
    frame.add(setNextvalDbButton);
    
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
        JOptionPane.showMessageDialog(frame, "Вы ввели имя: " + name + "\n"
            + "Телефон : " + phone + "\n"
            + "E-mail : " + email);

        Client newClient = new Client(name, phone, email);
        clientDataAccessObject.addClient(newClient); // Вот здесь мы вызываем метод для работы с БД

        JOptionPane.showMessageDialog(frame, "Клиент добавлен!");
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
    
    // Обработка события Экспорта таблици в CSV файл
    exportButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Используем UTF-8 с BOM для корректного открытия в Excel
        try (PrintWriter writer = new PrintWriter(
            new OutputStreamWriter(new FileOutputStream("clients.csv"),  
            StandardCharsets.UTF_8))) {
          // Добавляем BOM (Byte Order Mark) для корректного распознавания UTF-8 в Excel
          writer.print('\uFEFF');
          writer.println("ID;Имя;Телефон;Email");
          for (Client client : clientDataAccessObject.getAllClients()) {
              writer.printf("%d;%s;%s;%s%n", 
                  client.getId(), client.getName(), client.getPhone(), client.getEmail());
          }
          JOptionPane.showMessageDialog(frame, "Экспорт завершён!");
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
      
    }); 
    
    // Обработка события Импорта в таблицу из CSV файл
    importButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // 1. Выбор файла
            
        FileDialog fileDialog = new FileDialog(frame, "Выберите CSV файл", FileDialog.LOAD);
        fileDialog.setFile("*.csv; *.txt; *.xlsx");
        fileDialog.setVisible(true);


        // 2. Получаем выбранный файл
        String fileName = fileDialog.getFile();
        String directory = fileDialog.getDirectory();
        
        if (fileName == null) {
            return; // пользователь отменил выбор
        }
        
        File selectedFile = new File(directory, fileName);
        
        // 3. Проверяем существование файла
        if (!selectedFile.exists()) {
            JOptionPane.showMessageDialog(frame, 
                "Файл не найден!", 
                "Ошибка", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 4. Выбираем разделитель
        char separator = chooseSeparator();
        
        // 5. Импортируем данные
        importDataFromFile(selectedFile, separator);
        
      }
      
    });
    
    // Обработка события установки nextval
    setNextvalDbButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String idText = idField.getText();
        try {
          int id = Integer.parseInt(idText);
          clientDataAccessObject.setNextvalClients(id);
          JOptionPane.showMessageDialog(frame, "Установка nextval выполнена!");
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(frame, "ID должен быть целым числом.");
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(frame, "Ошибка при удалении: " + ex.getMessage());
          ex.printStackTrace();
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
  
  
  /**
   * Диалог выбора разделителя CSV.
   */
  private char chooseSeparator() {
    String[] options = {"Точка с запятой (;)", "Запятая (,)", "Табуляция (Tab)"};
    int choice = JOptionPane.showOptionDialog(frame,
          "Выберите разделитель полей в CSV файле:",
          "Разделитель",
          JOptionPane.DEFAULT_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null, options, options[0]);
    switch (choice) {
      case 0: return ';';
      case 1: return ',';
      case 2: return '\t';
      default: return ';';
    }
  }
  
  /**
   * Импорт данных из файла.
   */
  private void importDataFromFile(File file, char separator) {
    int importedCount = 0;
    int duplicateCount = 0;
    int errorCount = 0;
    List<String> errors = new ArrayList<>();
    
    try {
      // Читаем файл в UTF-8
      List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), 
                                              StandardCharsets.UTF_8);
      
      // Обработка BOM (Byte Order Mark) для UTF-8 файлов из Excel
      if (!lines.isEmpty() && lines.get(0).startsWith("\uFEFF")) {
        lines.set(0, lines.get(0).substring(1));
      }
          
      for (int i = 0; i < lines.size(); i++) {
        String line = lines.get(i).trim();
        
        // Пропускаем пустые строки
        if (line.isEmpty()) {
          continue;
        }
              
        // Парсим строку CSV с учётом кавычек
        String[] fields = parseCsvLine(line, separator);
        
        // Проверяем количество полей (минимум имя и телефон)
        if (fields.length < 2) {
          errorCount++;
          errors.add("Строка " + (i + 1) + ": недостаточно полей (найдено " + fields.length + ")");
          continue;
        }
              
        // Пропускаем заголовок, если он есть
        if (i == 0 && isHeaderRow(fields)) {
          continue;
        }
        
        // Извлекаем данные
        String name = fields[1].trim();
        String phone = fields.length > 1 ? fields[2].trim() : "";
        String email = fields.length > 2 ? fields[3].trim() : "";
              
        // Валидация имени
        if (name.isEmpty()) {
          errorCount++;
          errors.add("Строка " + (i + 1) + ": имя не может быть пустым");
          continue;
        }
              
        // Проверка на дубликат
        if (clientDataAccessObject.clientExists(name, phone)) {
          duplicateCount++;
          continue;
        }
              
        // Добавляем клиента
        Client client = new Client(name, phone, email);
        clientDataAccessObject.addClient(client);
        importedCount++;
      }
          
      // Показываем результат
      showImportResult(importedCount, duplicateCount, errorCount, errors);
      
      // Обновляем список клиентов
      updateClientList();
          
    } catch (IOException e) {
      JOptionPane.showMessageDialog(frame,
          "Ошибка при чтении файла:\n" + e.getMessage(),
          "Ошибка импорта",
          JOptionPane.ERROR_MESSAGE);
      e.printStackTrace();
    }
  }
  
  
  /**
   * Парсит одну строку CSV с учётом кавычек
   * Например: "Иванов, Иван";"+7(999)123-45-67";"ivan@mail.ru".
   */
  private String[] parseCsvLine(String line, char separator) {
    List<String> result = new ArrayList<>();
    boolean inQuotes = false;
    StringBuilder currentField = new StringBuilder();
    
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
        
      if (c == '"') {
        // Обработка экранированных кавычек ("" внутри кавычек)
        if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
          currentField.append('"');
          i++; // пропускаем вторую кавычку
        } else {
          inQuotes = !inQuotes;
        }
      } else if (!inQuotes && c == separator) {
        // Конец поля
        result.add(currentField.toString());
        currentField = new StringBuilder();
      } else {
        currentField.append(c);
      }
    }
    result.add(currentField.toString());
      
    return result.toArray(new String[0]);
  }
  
  
  /**
   * Проверяет, является ли строка заголовком.
   */
  private boolean isHeaderRow(String[] fields) {
    String firstField = fields[0].toLowerCase();
    String secondField = fields[1].toLowerCase();
    
    return firstField.equals("id")
           ||  firstField.equals("№")
           ||  firstField.equals("имя")
           ||  secondField.equals("имя")
           ||  secondField.equals("name")
           ||  secondField.equals("телефон")
           ||  secondField.equals("phone");
  }
  
  /**
  * Показывает диалог с результатами импорта.
  */
  private void showImportResult(int imported,
                              int duplicates,
                              int errors,
                              List<String> errorMessages) {
    String message = String.format(
         "Импорт завершён!\n\n"
         + "✅ Добавлено: %d\n"
         + "⚠️ Пропущено (дубликаты): %d\n"
         + "❌ Ошибок: %d",
         imported, duplicates, errors);
     
    // Если есть ошибки, показываем первые 10
    if (!errorMessages.isEmpty()) {
      StringBuilder sb = new StringBuilder(message);
      sb.append("\n\nПервые ошибки:\n");
      for (int i = 0; i < Math.min(errorMessages.size(), 10); i++) {
        sb.append("• ").append(errorMessages.get(i)).append("\n");
      }
      if (errorMessages.size() > 10) {
        sb.append("... и ещё ").append(errorMessages.size() - 10).append(" ошибок");
      }
      message = sb.toString();
    }
     
    JOptionPane.showMessageDialog(frame, message, "Результат импорта",
                                   JOptionPane.INFORMATION_MESSAGE);
  }
  
}
