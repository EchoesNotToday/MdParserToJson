import jdk.nashorn.internal.parser.JSONParser;
import models.State;

import java.io.*;
import java.util.Dictionary;

public class MdToJson {
    public static void main(String[] args) {
        System.out.println("Convertion du fichier " + args[0]);
        File file = new File(args[0]);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
