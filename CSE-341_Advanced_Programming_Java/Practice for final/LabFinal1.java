import java.util.*;
import java.io.*;

class complaint{
    String Id,name,Text;
    complaint(String i, String n, String T){
        Id = i; name = n; Text = T;
    }
    public String getId(){
        return Id;   
    }
    public String getname(){
        return name;
    }
    public String getText(){
        return Text;
    }
    public String toString(){
        return (Id+" "+name+" "+Text+"\n");
    }
}
class InvalidComplaintException extends Exception{
    InvalidComplaintException(String msg){
        super(msg);
    }
}
public class LabFinal1{
    public static void main(String[] args) throws Exception{
        File f = new File("complaints.txt");
        File f2 = new File("processed_complain.txt");
        FileWriter fw = new FileWriter(f2);
        Scanner sc = new Scanner(f);
        while(sc.hasNext()){
            String s = sc.nextLine();
            String[] str = s.split("\\|");
            String id = str[0];
            String name = str[1];
            String Text = str[2];
            try{
                if(id.length()==0 || name.length()==0 || Text.length()==0){
                    throw new InvalidComplaintException("Invalid data");
                }
                int cnt = 1;
                for(int i=0; i<Text.length(); i++){
                    if(Text.charAt(i)==' '){
                        cnt++;
                    }
                }
                Text = Text.toUpperCase();
                Text = Text.trim();
                System.out.println(Text);
                complaint c = new complaint(id,name,Text);
                fw.write(c.toString());
            }
            catch(InvalidComplaintException e){
                System.out.println(e.getMessage());
            }
            
        }
        fw.close();
        sc.close();
    }
}