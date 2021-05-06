import com.distinct.rpc.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.*;

public class TSServer extends TSPROGServer
{
	public static void main(String[] args)
	{
		try 
		{	
			System.out.println("The server is running!!");
			new TSServer();
 	    } 
    	catch (RPCError e) 
		{	
			System.out.println(e.getMessage()); 
    	} 
	}
 
	public TSServer() throws RPCError 
	{	
		super(); 
	}
 	
	public String LogOn_1(String username, String password)
	{
		List<User> users = User.readUsersFromCSV("UserFiles/Users.csv");
        String returnMessage = "";

        for(User user : users)
        {
			if (username.equals(user.getUserName()) && password.equals(user.getPassword())) {
				System.out.println("User " + username + " has logged in");
                returnMessage = "110;Login Successful! Welcome " + username;
                break;
            }
            else
                returnMessage = "120;Username or Password is incorrect please try again!";
        }
        return returnMessage;
	}
}