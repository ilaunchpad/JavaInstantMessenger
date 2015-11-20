package seis365.project.client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import seis635.project.server.Server;

public class ClientThread implements Runnable {

	Socket socket;
	Scanner scanner;
	PrintWriter pWriter;
	
	public ClientThread(Socket sock)
	{
		this.socket = sock;
	}
	
	public void send(String message)
	{
		pWriter.print(Client.username + ": "+ message+"\n");
		pWriter.flush();
		Client.textField_send.setText("");
	}
	public void Receive()
	{
		if(scanner.hasNext())
		{
			String message = scanner.nextLine();
			
			if(message.contains("#?!"))
			{
				String tmp1 = message.substring(3);
				tmp1 = tmp1.replace("[","");
				tmp1 = tmp1.replace("]","");
				
				String[] users = tmp1.split(", ");	
				
				Client.UserList.setListData(users);
			}
			else
			{
				Client.textArea_show.append(message +"\n");
			}
		}
	}
	
	@Override
	public void run() {
		try
		{
			try
			{
				scanner = new Scanner(socket.getInputStream());
				pWriter = new PrintWriter(socket.getOutputStream());
				pWriter.flush();
				//
				while(true)
				{
					Receive();
				}
				
			}
			finally
			{
				socket.close();
			}
		}
		catch(Exception ex)
		{
			
		}
		
	}

}
