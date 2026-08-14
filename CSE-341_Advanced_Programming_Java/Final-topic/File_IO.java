import java.io.*;
// import java.io.IOException;
import java.util.Scanner;

public class File_IO{
    public static void main(String[] args) throws Exception{
        File f = new File("sample.txt");
        FileWriter fw = new FileWriter(f);
        fw.write("Meheraj\n");
        fw.write("Meheraj CSE 342\n");
        fw.write("Hussain\n");
        fw.close();
        Scanner sc = new Scanner(f);
        while(sc.hasNext()){
            // String s = sc.next();
            String s = sc.nextLine();
            System.out.println(s);
        }
        sc.close();
    }
}