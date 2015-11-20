package seis365.project.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;

public class Client {

	private static ClientThread client;
	private static Socket socket;
	static String username; // unique identifier
	String IP;
	Socket clientSocket;
	private JFrame frame;
	static JTextField textField_send;
	static JTextArea textArea_show;
	static JList UserList;

	/**
	 * Create the application.
	 */
	public Client(String name, String IP) {

		this.username = name;
		this.IP = IP;
		initialize();
	}

	public String sendandGetMessage(String message) {
		try {
			

            OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.write(message+"\n");
			bw.flush();
			System.out.println("client write message "+message);
			//message = GetMessage();
			
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			message = br.readLine();
			
			System.out.println("client get message "+message);
			textArea_show.append("Samir" + " : " + message +"\n");
			textField_send.setText("");

		} catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
		return message;
	}

	public String GetMessage() {
		String message = "";
		try {

			
			 String host = "localhost";
			
            int port = 8002;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
           
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			message = br.readLine();

		} catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
		return message;

	}
	
	public static void connect()
	{
		try{
			String host = "localhost";
	        int port = 8002;
	        InetAddress address = null;
			address = InetAddress.getByName(host);
			socket = new Socket(address, port);
			
			client = new ClientThread(socket);
			
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println("#?!"+username);
			writer.flush();
			
			Thread t = new Thread(client);
			t.start();
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 617, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textArea_show = new JTextArea();

		textArea_show.setBounds(10, 11, 433, 339);
		textArea_show.setLineWrap(true);

		frame.getContentPane().add(textArea_show);

		JButton btnNewButton = new JButton("Send");

		btnNewButton.setBounds(478, 384, 89, 48);
		frame.getContentPane().add(btnNewButton);

		JLabel lblName = new JLabel("User");
		lblName.setText(this.username);
		lblName.setBounds(463, 16, 46, 14);
		frame.getContentPane().add(lblName);

		JLabel lblGroup = new JLabel("group");
		lblGroup.setBounds(463, 56, 46, 14);
		frame.getContentPane().add(lblGroup);

		textField_send = new JTextField();
		textField_send.setBounds(33, 361, 220, 64);
		frame.getContentPane().add(textField_send);
		textField_send.setColumns(10);
		
		UserList = new JList();
		UserList.setBounds(453, 89, 112, 262);
		frame.getContentPane().add(UserList);

		frame.setVisible(true);
		
		connect();
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String message  = textField_send.getText();
				if (!message.equals(""))
				{
					client.send(message);
					textField_send.requestFocus();
				}
			}
		});
	}
}
