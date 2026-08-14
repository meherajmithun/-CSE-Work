// import java.io.*;
// import java.util.Scanner;

// class student{
//     private String name;
//     private int id,sec;
//     private double cg;
//     student(String s, int i, int sc, double c){
//         name = s; id = i; sec = sc; cg = c;
//     }
//     public String getName(){
//         return name;
//     }
//     public int getId(){
//         return id;
//     }
//     public int getSec(){
//         return sec;
//     }
//     public double getCg(){
//         return cg;
//     }
//     public String toString(){
//         return ("Name : "+name+", Id : "+id+", Section : "+sec+", CGPA : "+cg+"\n");
//     }
// }
// public class FileStudent{
//     public static void main(String[] args) throws Exception{
//         student s1 = new student("Meheraj",430,03,3.66);
//         student s2 = new student("Naimul",433,03,3.93);
//         student s3 = new student("Hussain",439,03,3.99);
//         File f = new File("student.txt");
//         FileWriter fw = new FileWriter(f);
//         fw.write(s1.toString());
//         fw.write(s2.toString());
//         fw.write(s3.toString());
//         fw.close();
//         Scanner sc = new Scanner(f);
//         while(sc.hasNext()){
//             String s = sc.nextLine();
//             System.out.println(s);
//         }
//         sc.close();
//     }
// }

// import java.io.*;
// import java.util.ArrayList;
// import java.util.Scanner;

// class student{
//     private String name;
//     private int id,sec;
//     private double cg;
//     student(String s, int i, int sc, double c){
//         name = s; id = i; sec = sc; cg = c;
//     }
//     public String getName(){
//         return name;
//     }
//     public int getId(){
//         return id;
//     }
//     public int getSec(){
//         return sec;
//     }
//     public double getCg(){
//         return cg;
//     }
//     public String toString(){
//         return (name+" "+id+" "+sec+" "+cg+"\n");
//     }
// }
// public class FileStudent{
//     public static void main(String[] args) throws Exception{
//         ArrayList<student>arr = new ArrayList<>();
//         student s1 = new student("Meheraj",430,03,3.66);
//         student s2 = new student("Naimul",433,03,3.93);
//         student s3 = new student("Hussain",439,03,3.99);
//         File f = new File("student.txt");
//         FileWriter fw = new FileWriter(f);
//         fw.write(s1.toString());
//         fw.write(s2.toString());
//         fw.write(s3.toString());
//         fw.close();
//         Scanner sc = new Scanner(f);
//         while(sc.hasNext()){
//             String s = sc.next();
//             int id = sc.nextInt();
//             int sec = sc.nextInt();
//             double cg = sc.nextDouble();
//             System.out.println(s+" "+id+" "+sec+" "+cg);
//         }
//         sc.close();
//     }
// }


///Remove the student if cg is lower than 3.9
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class student{
    private String name;
    private int id,sec;
    private double cg;
    student(String s, int i, int sc, double c){
        name = s; id = i; sec = sc; cg = c;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public int getSec(){
        return sec;
    }
    public double getCg(){
        return cg;
    }
    public String toString(){
        return (name+" "+id+" "+sec+" "+cg+"\n");
    }
}
public class FileStudent{
    public static void main(String[] args) throws Exception{
        ArrayList<student>arr = new ArrayList<>();
        // student s1 = new student("Meheraj",430,03,3.66);
        // student s2 = new student("Naimul",433,03,3.93);
        // student s3 = new student("Hussain",439,03,3.99);
        File f = new File("student.txt");
        // fw.write(s1.toString());
        // fw.write(s2.toString());
        // fw.write(s3.toString());
        // fw.close();
        Scanner sc = new Scanner(f);
        while(sc.hasNext()){
            String s = sc.next();
            int id = sc.nextInt();
            int sec = sc.nextInt();
            double cg = sc.nextDouble();
            student st = new student(s,id,sec,cg);
            arr.add(st);
        }
        sc.close();
        // for(int i=0; i<arr.size(); i++){
        //     System.out.print(arr.get(i));
        // }
        FileWriter fw = new FileWriter(f);
        for(int i=0; i<arr.size(); i++){
            if(arr.get(i).getCg()<3.9){
                fw.write(arr.get(i).toString());
            }
        }
        fw.close();
    }
}
