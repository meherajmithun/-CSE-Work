import java.io.*;
import java.util.*;

public class FileUtils {

    public static void writeLine(String file, String data) {
        try {
            new File("data").mkdirs();
            FileWriter fw = new FileWriter(file, true);
            fw.write(data + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAll(String file) {
        List<String> list = new ArrayList<>();

        try {
            File f = new File(file);
            if (!f.exists()) f.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                list.add(line);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}