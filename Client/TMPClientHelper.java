import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.Socket;

public class TMPClientHelper {

    public Socket socket;
    SSLSocket sslSocket;
    String username = "";

    TMPClientHelper() throws IOException{
        try {
            System.setProperty("javax.net.ssl.trustStore", "t00199391.store");
            socket = ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket("localhost", 4444);
            sslSocket = new SSLSocket(socket);
        }catch (IOException e){
            System.out.println("Unable to create SSL connection");
        }// end try-catch
    } // end constructor

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String LogOn(String username, String password) throws IOException{
        try {
            String returnMessage = "";
            String message = "Login;100;" + sslSocket.encryptMessage(username) + ";" + sslSocket.encryptMessage(password);

            sslSocket.sendMessage(message);
            returnMessage = sslSocket.receiveMessage();

            setUsername(username);

            return returnMessage;
        }catch (IOException e){
            System.out.println("Error occurred when logging in");
        }// end try-catch

        return "";
    }//end Login

    public String Upload(String clientMessage) throws IOException{
        try {
            String returnMessage = "";
            String message = "Upload;200;" + sslSocket.encryptMessage(clientMessage);

            sslSocket.sendMessage(message);
            returnMessage = sslSocket.receiveMessage();

            return returnMessage;
        }catch (IOException e){
            System.out.println("Error occurred when uploading message");
        }// end try-catch

        return "";
    }//end Upload

    public String Download() throws IOException{
        try {
            String returnMessage = "";
            String message = "Download;300";

            sslSocket.sendMessage(message);
            returnMessage = sslSocket.receiveMessage();

            return returnMessage;
        }catch (IOException e){
            System.out.println("Error occurred when downloading messages");
        }// end try-catch

        return "";
    }//end Download

    public String LogOff() throws IOException{
        try {
            String returnMessage = "";
            String message = "Logoff;400";

            sslSocket.sendMessage(message);
            returnMessage = sslSocket.receiveMessage();

            return returnMessage;
        }catch(IOException e){
            System.out.println("Error occurred when logging off");
        }// end try-catch

        return "";
    }//end LogOff

    public void done( ) throws IOException{
        try {
            sslSocket.sendMessage(getUsername() +   " Logged Off");
            sslSocket.close();
        }catch (IOException e){
            System.out.println("Error occurred when closing connection");
        }// end try-catch
    } // end done
} //end class
