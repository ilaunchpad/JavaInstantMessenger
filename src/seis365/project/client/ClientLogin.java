package seis365.project.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientLogin {

	private JFrame frame;
	private JTextField textField_name;
	private JTextField textField_IP;
	

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientLogin window = new ClientLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientLogin() {

		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblEnterName = new JLabel("Enter Name");
		lblEnterName.setBounds(48, 63, 81, 14);
		frame.getContentPane().add(lblEnterName);

		textField_name = new JTextField();
		textField_name.setBounds(121, 60, 86, 20);
		frame.getContentPane().add(textField_name);
		textField_name.setColumns(10);

		JLabel lblEnterIp = new JLabel("Enter IP");
		lblEnterIp.setBounds(48, 94, 46, 14);
		frame.getContentPane().add(lblEnterIp);

		textField_IP = new JTextField();
		textField_IP.setBounds(121, 91, 86, 20);
		frame.getContentPane().add(textField_IP);
		textField_IP.setColumns(10);

		JButton btnLogin = new JButton("LogIn");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Client Client = new Client(textField_name.getText(),
						textField_IP.getText());

			}
		});
		btnLogin.setBounds(121, 150, 89, 23);
		frame.getContentPane().add(btnLogin);

		frame.setVisible(true);
	}
}
