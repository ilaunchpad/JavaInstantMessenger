package seis635.project.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	private static Socket socket;
	
	public static ArrayList<Socket> sockets = new ArrayList<Socket>();
	public static ArrayList<String> users = new ArrayList<String>();

	public static void main(String[] args) {
		try {

			int port = 8002;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port "
					+ port);

			// Server is running always. This is done using this while(true)
			// loop
			while (true) {
				// Reading the message from the client
				socket = serverSocket.accept();
				
				sockets.add(socket);
				
				ServerThread CHAT = new ServerThread(socket);
				Thread t = new Thread(CHAT);
				t.start();
				
//				InputStream is = socket.getInputStream();
//				InputStreamReader isr = new InputStreamReader(is);
//				BufferedReader br = new BufferedReader(isr);
//				String message = br.readLine();
//			    System.out.println("Sever get message from client"+ message);
//			   
//
//				// Sending the response back to the client.
//				OutputStream os = socket.getOutputStream();
//				OutputStreamWriter osw = new OutputStreamWriter(os);
//				BufferedWriter bw = new BufferedWriter(osw);
//				bw.write(message+"\n");
//	System.out.println("Server write message from client"+ message);
//	
//				bw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
			}
		}
	}
}