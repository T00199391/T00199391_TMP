import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class User {
    private String userName;
    private String password;

    public User(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }// end constructor

    public String getUserName() {
        return userName;
    }//end getUserName

    public String getPassword() {
        return password;
    }//end getPassword

    //https://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html
    private static User createUser(String[] metadata) {
        String userName = metadata[0];
        String password = metadata[1];

        return new User(userName, password);
    }//end createUser

    //https://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html
    public static List<User> readUsersFromCSV(String fileName) {
        List<User> users = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            String line = br.readLine();

            while (line != null) {

                String[] attributes = line.split(",");

                User user = createUser(attributes);

                users.add(user);

                line = br.readLine();
            }
        } catch (IOException ioe) {
            System.out.println("Error occurred when trying to read in users");
        }// end try-catch

        return users;
    }//end readUsersFromCSV
}//end User