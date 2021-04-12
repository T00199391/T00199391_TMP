import java.io.*;

public class TMPClient {
   public static void main(String[] args) {
      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
      try {
         String returnMessage = "";
         String[] returnMessageSplit;
         TMPClientHelper helper = new TMPClientHelper();
         boolean loggedIn = false;

         //While their is no logged in user this code will continue to run until a user sucessfully logs in.
         while(!loggedIn) {
            System.out.println("Welcome to the TMP. Please enter your username and password\n");
            System.out.println("Username: ");
            String username = br.readLine();
            System.out.println("Password: ");
            String password = br.readLine();

            returnMessage = helper.LogOn(username, password);
            returnMessageSplit =  returnMessage.split(";");

            if (returnMessageSplit[0].equals("110")) {
               System.out.println(returnMessageSplit[1]);
               loggedIn = true;
            } else {
               System.out.println(returnMessageSplit[1]);
            }
         }//end while

         //While a user is logged in, they can pick between three options: Upload a message, download all messages from the server for the logged in user and log off.
         while (loggedIn) {
            System.out.println("\nPlease pick an option \n1)Upload message \n2)Download messages \n3)Log off");
            String option = br.readLine();

            if(option.equals("1"))
            {
               System.out.println("\nPlease enter your message:");
               String msg = br.readLine();

               returnMessage = helper.Upload(msg);
               returnMessageSplit =  returnMessage.split(";");

               if(returnMessageSplit[0].equals("210"))
                  System.out.println(returnMessageSplit[1]);
               else
                  System.out.println(returnMessageSplit[1]);
            }
            else if(option.equals("2"))
            {
               System.out.println();
               returnMessage = helper.Download();
               returnMessageSplit =  returnMessage.split(";");

               if(returnMessageSplit[0].equals("310")) {
                  String[] fullMessage = returnMessageSplit[1].split(",");

                  for (String msg : fullMessage) {
                     System.out.println(msg);
                  }
               }
               else
                  System.out.println(returnMessageSplit[1]);
            }
            else if(option.equals("3"))
            {
               System.out.println();
               returnMessage = helper.LogOff();
               returnMessageSplit =  returnMessage.split(";");

               if(returnMessageSplit[0].equals("410"))
               {
                  System.out.println(returnMessageSplit[1] + " Thank you for using the system!");
                  loggedIn = false;
                  helper.done();
               }
            }
            else
            {
               System.out.println("Option entered was not recognised please try again!");
            }
         } // end while
      } // end try
      catch (Exception ex) {
         System.out.println("Error occurred at client level");
      } //end catch
   } //end main
} // end class