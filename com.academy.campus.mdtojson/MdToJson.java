import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MdToJson {
    public static void main(String[] args) {
        File file = new File(args[0]);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
            }
        } catch (IOException fnfEx) {
            fnfEx.printStackTrace();
        }
    }
}
