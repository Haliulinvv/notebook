package notebook;

import javax.swing.*;

import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Записная книга");
	
    private JTextField nameField = new JTextField(15);
    private JTextField phoneField = new JTextField(15);
    private JTextField emailField = new JTextField(15);
    private JButton addButton = new JButton("Добавить");
    private JButton listButton = new JButton("Показать всех");
    //private JTextArea displayArea = new JTextArea(10, 30);

    //private ClientDAO clientDAO = new ClientDAO();

    public ClientForm() {

        
    	frame.setLayout(null); // отключаем менеджер

        
        // ... настройка интерфейса (размещение полей и кнопок на форме) ...
    	 // Главная форма приложения
        frame.setSize(1000, 500);
        //frame.setResizable(false);
        frame.setLocation(2400,200);
        //frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Добавляем кнопки на панель в нужном порядке
        //Dimension buttonSize = new Dimension(50, 50);
        //addButton.setPreferredSize(buttonSize);
        //listButton.setPreferredSize(buttonSize);
        //frame.setLayout(new FlowLayout());
        

        addButton.setBounds(450, 50, 200, 30); // x, y, ширина, высота
        listButton.setBounds(450, 100, 200, 30); // x, y, ширина, высота
        frame.add(addButton);
        frame.add(listButton);
        
        
        // Добавляем компоненты на фрейм
        nameField.setBounds(100, 50, 300, 30); // x, y, ширина, высота
        phoneField.setBounds(100, 100, 300, 30); // x, y, ширина, высота
        emailField.setBounds(100, 150, 300, 30); // x, y, ширина, высота
        
   
        frame.add(nameField, BorderLayout.NORTH);
        frame.add(phoneField, BorderLayout.NORTH);
        frame.add(emailField, BorderLayout.NORTH);

        
        JLabel labelName = new JLabel("Имя");
        labelName.setBounds(30, 50, 50, 30); // x, y, ширина, высота
        frame.add(labelName);
        
        JLabel labelPhone = new JLabel("Телефон");
        labelPhone.setBounds(30, 100, 70, 30); // x, y, ширина, высота
        frame.add(labelPhone);
        
        JLabel labelMail = new JLabel("E-mail");
        labelMail.setBounds(30, 150, 50, 30); // x, y, ширина, высота
        frame.add(labelMail);
        
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
                JOptionPane.showMessageDialog(ClientForm.this, "Вы ввели имя: " + name  +"\n"
					 	   									     + "Телефон : " + phone +"\n"
					 	   									     + "E-mail : "  + email);
                
 /*               Client newClient = new Client(name, phone, email);
                clientDAO.addClient(newClient); // <-- Вот здесь мы вызываем метод для работы с БД*/

                JOptionPane.showMessageDialog(ClientForm.this, "Клиент добавлен!");
                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");
            }
        });
/*
        listButton.addActionListener(e -> {
            displayArea.setText(""); // Очищаем область
            for (Client client : clientDAO.getAllClients()) {
                displayArea.append(client.getId() + ": " +
                                   client.getName() + ", " +
                                   client.getPhone() + ", " +
                                   client.getEmail() + "\n");
            }
        });*/
        // ... остальной код ...
    }
}