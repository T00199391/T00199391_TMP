import java.net.*; // network classes

public class echoclient
{
	public static void main(String[] argv)
	{
		ECHOPROG client; // client is an instance of echo class (i.e. the stub)
		if(argv.length <2)
		{	System.out.println("Usage: java echoclient host number");
			System.exit(0);
		}		
		try
		// call echo method in the stub
		{	client = new ECHOPROG(InetAddress.getByName(argv[0]),true);			
			int result = client.echo_1(Integer.parseInt(argv[1]));
			System.out.println("\nThe Echo Number is " + result);
		}
		catch(Exception e)
		{
		  System.out.println("\nThe Exception is " + e.getMessage());

		}
		System.exit(0);
	 }
}