import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;

public class TMPServer {
   public static void main(String[] args) throws IOException {
      try {
         System.setProperty("javax.net.ssl.keyStore", "t00199391.store");
         System.setProperty("javax.net.ssl.keyStorePassword", "password");
         ServerSocket serverSocket = ((SSLServerSocketFactory) SSLServerSocketFactory.getDefault()).createServerSocket(4444);
         System.out.println("Connection to server successful");
         while (true) {
            new TMPServerThread(new SSLSocket(serverSocket.accept())).start();
         }//end while
      }catch(IOException e){
         System.out.println("Error occurred when trying to create a server connection");
      }//end try-catch
   }//end main
}
