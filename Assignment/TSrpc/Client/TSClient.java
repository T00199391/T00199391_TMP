import java.net.*;
import java.io.*;

public class TSClient
{
	public static void main(String[] argv)
	{
		TSPROG client;
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		
		if(argv.length < 1)
		{	
			System.out.println("Usage: java TSClient host number");
			System.exit(0);
		}		
		try
		{	
			String returnMessage = "";
			String[] returnMessageSplit;
			client = new TSPROG(InetAddress.getByName(argv[0]),true);
			boolean loggedIn = false;
			while(!loggedIn) {
				System.out.println("Welcome to the TS. Please enter your username and password\n");
				System.out.println("Username: ");
				String username = br.readLine();
				System.out.println("Password: ");
				String password = br.readLine();

				returnMessage = client.LogOn_1(username, password);
				returnMessageSplit =  returnMessage.split(";");

				if (returnMessageSplit[0].equals("110")) {
					System.out.println(returnMessageSplit[1]);
					loggedIn = true;
				} 
				else {
					System.out.println(returnMessageSplit[1]);
				}
			}
		}
		catch(Exception e)
		{
		  System.out.println("\nThe Exception is " + e.getMessage());
		}
		System.exit(0);
	 }
}