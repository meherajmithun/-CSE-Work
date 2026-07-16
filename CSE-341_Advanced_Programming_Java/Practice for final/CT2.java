import java.util.*;
import java.io.*;
class Record<T>{
    T data;
    Record(T d){ data = d;}
    public String toString(){
        return (data.toString());
    }
}
class InvalidDataException extends Exception{
    InvalidDataException(String msg){
        super(msg);
    }
}

public class CT2{
    public static void main(String[] args) throws Exception{
        File f = new File("student.txt");
        File f2 = new File("valid_student.txt");
        Scanner sc = new Scanner(f);
        FileWriter fw = new FileWriter(f2);
        while(sc.hasNext()){
            String data = sc.nextLine();
            String[] str = data.split(" ");
            try{
                if(str.length<2){
                    throw new InvalidDataException("Invalid data");
                }
                if(name.length()==0){
                    throw new InvalidDataException("Invalid name");
                }
                if(mark>100 || mark<0){
                    throw new InvalidDataException("Invalid marks");
                }
                String name = str[0];
                int mark = Integer.valueOf(str[1]);
                Record <String> Namee = new Record<>(name);
                // obk<T> name = new ij<>()
                Record <Integer> marks = new Record<>(mark);
                // System.out.println(name+" "+mark);
                fw.write(Namee+" "+marks+"\n");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        fw.close();
        sc.close();
    }
}