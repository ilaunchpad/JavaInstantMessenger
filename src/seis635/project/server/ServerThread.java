package seis635.project.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread implements Runnable{
	Socket socket;
	Scanner scanner; 
	PrintWriter pWriter;
	String message;
	
	public ServerThread(Socket sock)
	{
		this.socket = sock;
	}
	
	public void checkConnection() throws IOException
	{
		if(!socket.isConnected())
		{
			for(int i = 1; i<= Server.sockets.size(); i++)
			{
				if(Server.sockets.get(i) == socket)
				{
					Server.sockets.remove(i);
				}
			}
			
			for(int i = 1; i<= Server.sockets.size(); i++)
			{
				
				Socket tmp = (Socket) Server.sockets.get(i-1);
				PrintWriter tmpWriter = new PrintWriter(tmp.getOutputStream());
				tmpWriter.println(tmp.getLocalAddress().getHostName() + " is Disconnected");
				tmpWriter.flush();
				
				System.out.println(tmp.getLocalAddress().getHostName() + " is Disconnected");
			}
		}
	}

	@Override
	public void run() {
		try
		{			
			try
			{
				scanner =new Scanner(socket.getInputStream());
				pWriter =new PrintWriter(socket.getOutputStream());
				
				while (true) {
					checkConnection();

					if (!scanner.hasNext()) {
						return;
					}

					message = scanner.nextLine();

					if (message.contains("#?!")) {
						String tmp = message.substring(3);
						Server.users.add(tmp);
						message = "";
					}
					else
						System.out.println("From Client : " + message);

					for (int i = 1; i <= Server.sockets.size(); i++) {
						Socket tmp = (Socket) Server.sockets.get(i - 1);
						PrintWriter tmpWriter = new PrintWriter(
								tmp.getOutputStream());
						tmpWriter.print("#?!");
						tmpWriter.println(Server.users.toString());
						if (!message.equals("")) {
							tmpWriter.println(message);
							
							System.out.println("Sent to :"
									+ tmp.getLocalAddress().getHostName());
						}
						tmpWriter.flush();
					}
				}

			}
			catch(Exception Ex){}
			finally{
				socket.close();
			}
		}
		catch(Exception Ex){}
		finally{}		
	}
	
}
