import com.distinct.rpc.*; // RPC libraries

public class MyServer extends ECHOPROGServer
{
	public static void main(String[] args)
	{
		try 
		{	
			new MyServer(); // creates an instance of the Server stub
 	    } 
    	catch (RPCError e) 
		{	
			System.out.println(e.getMessage()); 
    	} 
	}
 
	public MyServer() throws RPCError 
	{	
		super(); 
	}
 	// override the echo_1 method from the stub – where it does nothing
	public int echo_1(int a)
	{
		return(--a);
	}
}