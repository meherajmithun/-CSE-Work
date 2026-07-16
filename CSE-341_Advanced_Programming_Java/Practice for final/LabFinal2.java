import java.util.*;
import java.io.*;

class Book<T>{
    T bookId;
    String name,writter;
    int copy;
    Book(T id, String n,int c, String w){
        bookId = id; name = n; writter = w; copy = c;
    }
    public T getId(){
        return bookId;
    }
    public String getname(){
        return name;
    }
    public String getWritter(){
        return writter;
    }
    public int getCopy(){
        return copy;
    }
    public String toString(){
        return (bookId+" "+name+" "+copy+" "+writter+"\n");
    }
}

public class LabFinal2{
    public static void main(String[] args) throws Exception{

        ArrayList<Book> arr = new ArrayList<>();

        File f = new File("books.txt");
        Scanner sc = new Scanner(f);
        while(sc.hasNext()){
            String s = sc.nextLine();
            String[] str = s.split(",");
            int id = Integer.parseInt(str[0]);
            String name = str[1];
            int copy = Integer.parseInt(str[2]);
            String writter = str[3];
            Book b = new Book(id,name,copy,writter);
            arr.add(b);
            // System.out.println(id+" "+name+" "+copy+" "+writter);
        }
        sc.close();
        String name="";
        int mx=0;
        for(int i=0; i<arr.size(); i++){
            if(mx<arr.get(i).getCopy()){
                mx = arr.get(i).getCopy();
                name = arr.get(i).getname();
            }
        }
        // System.out.println(name+" "+mx);

        for(int i=0; i<arr.size(); i++){
            if(arr.get(i).getCopy()==0){
                System.out.println(arr.get(i).getId());
                break;
            }
        }
        for(int i=0; i<arr.size(); i++){
            String namee = arr.get(i).getWritter();
            // System.out.println(namee);
            for(int j=0; j<namee.length(); j++){
                if(namee.charAt(j)=='A' || namee.charAt(j)=='a'){
                    System.out.println(arr.get(i).toString());
                    break;
                }
            }
        }

    }
}