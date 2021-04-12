import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SSLSocket extends Thread{
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    SSLSocket(Socket socket) throws IOException{
        try {
            this.socket = socket;
            setStreams();
        }catch (IOException e) {
            System.out.println("Unable to create connection");
        }// end try-catch
    }// end constructor

    private void setStreams( ) throws IOException{
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e) {
            System.out.println("Unable to create connection");
        }// end try-catch
    }//end setStream

    public void sendMessage(String message) throws IOException{
        printWriter.println(message);
    } // end sendMessage

    public String receiveMessage( ) throws IOException{
        try {
            return bufferedReader.readLine( );
        }catch (IOException e) {
            System.out.println("Unable to receive message");
        }// end try-catch
        return "";
    } //end receiveMessage

    public void close( ) throws IOException{
        try {
            socket.close( );
        }catch (IOException e) {
            System.out.println("Error occurred when closing connection");
        }// end try-catch
    }//end close

    //This method will reverse the message sent between the client and the server
    public String encryptMessage(String message) {
        String returnMessage = "";
        char[] c = message.toCharArray();
        for (int i = c.length - 1; i >= 0; i--) {
            char t = c[i];
            returnMessage += t;
        }
        return returnMessage;
    }//end encrypt
}//end class
