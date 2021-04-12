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

class TMPServerThread extends Thread {
    String[] messageSplit;
    SSLSocket socket;
    String username;

    TMPServerThread(SSLSocket socket) {
        this.socket = socket;
    }// end constructor

    public void run( ) {
        boolean done = false;
        String message;

        try {
            while (!done) {
                message = socket.receiveMessage();

                System.out.println("message received: " + message);
                messageSplit = message.split(";");

                if (messageSplit[0].equals("Login")) {
                    socket.sendMessage(LogOn(messageSplit));
                }
                else if(messageSplit[0].equals("Upload")) {
                    socket.sendMessage(Upload(messageSplit));
                }
                else if(messageSplit[0].equals("Download")) {
                    socket.sendMessage(Download(messageSplit));
                }
                else if(messageSplit[0].equals("Logoff")) {
                    socket.sendMessage(LogOff());
                }
                else {
                    socket.sendMessage("Error! Failed to execute server functions");
                    done = true;
                }
            } //end while
        }catch (Exception ex) {
            System.out.println("Exception caught in thread: " + ex);
        } // end try-catch
    } //end run

    public String LogOn(String[] msg)
    {
        List<User> users = User.readUsersFromCSV("UserFiles/Users.csv");
        String returnMessage = "";

        for(User user : users)
        {
            if (msg[2].equals(user.getUserName()) && msg[3].equals(user.getPassword())) {
                username = socket.encryptMessage(msg[2]);
                returnMessage = "110;Login Successful! Welcome " + username;
                break;
            }
            else
                returnMessage = "120;Username or Password is incorrect please try again!";
        }
        return returnMessage;
    }//end LogOn

    public String Upload(String[] msg)
    {
        if(msg.length == 3) {
            writeUserMessage("UserFiles/" + username + ".csv",msg[2]);
            return "210;Upload Successful";
        }
        else{
            return "220;Message was empty. Upload Failed!";
        }
    }//end Upload

    public String Download(String[] msg)
    {
        List<String> clientMessages = readUserMessages("UserFiles/" + username + ".csv");
        if(clientMessages.size() > 0) {
            String messages = "All Messages: ,";

            for (String word : clientMessages) {
                messages += socket.encryptMessage(word) + ",";
            }
            return "310;" + messages;
        }
        else{
            return "320;There are no messages available for download.";
        }
    }//end Download

    public String LogOff()
    {
        return "410;Log Off Successful";
    }//end LogOff

    private List<String> readUserMessages(String fileName) {
        List<String> messages = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            String line = br.readLine();

            while (line != null) {
                messages.add(line);
                line = br.readLine();
            }

        } catch (IOException ioe) {
            System.out.println("Error occurred when trying to read in messages for user");
        }//end try-catch

        return messages;
    }//end readUserMessages

    //https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
    public void writeUserMessage(String fileName, String message)
    {
        try(FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            if(!readUserMessages(fileName).isEmpty())
                bw.newLine();
            bw.write(message);
        } catch (IOException e) {
            System.out.println("Error occurred when trying to write message for user");
        }//end try-catch
    }//end writeUserMessage
} //end class 
