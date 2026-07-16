import java.util.*;
import java.io.*;

class carData{
    private String no,model;
    private double price;
    private int year;
    carData(){}
    carData(String n, String m, double p){
        no=n; model=m; price=p;
    }
    public void setNo(String s){
        no=s;
    }
    public void setModel(String m){
        model = m;
    }
    public void setPrice(double p){
        price=p;
    }
    public void setYear(int y){
        year = y;
    }
    public String getNo(){
        return no;
    }
    public String getModel(){
        return model;
    }
    public double getPrice(){
        return price;
    }
    public int getYear(){
        return year;
    }
    public String toString(){
        return (no+" "+model+" "+price+" "+year+"\n");
    }
}
class InValidYearException extends Exception{
    InValidYearException(String msg){
        super(msg);
    }
}
class practiceSet2{
    public static void main(String[] args) throws Exception{
        carData c = new carData();

        ArrayList<carData>arr = new ArrayList<>();

        //Already give in the question
        File f = new File("record2.txt");

        File f2 = new File("updateData2.txt");
        FileWriter fw = new FileWriter(f2);
        fw.write("NO Model price Year\n");

        Scanner sc = new Scanner(f);
        if (sc.hasNextLine()) sc.nextLine();

        while(sc.hasNext()){
            String no = sc.next();
            String model = sc.next();
            double price = sc.nextDouble();
            int year = sc.nextInt();
            if(no.length()==4){ // Question 1 er ans
                fw.write(no+" "+model+" "+price+" "+" "+year+"\n");
                carData st = new carData(no,model,price);
                arr.add(st);
            }
        }
        fw.close();
        sc = new Scanner(f2);
        if(sc.hasNextLine()) sc.nextLine();
        while(sc.hasNext()){
            String no = sc.next();
            String model = sc.next();
            double price = sc.nextDouble();
            int year = sc.nextInt();
            int start = 0, end = model.length()-1;
            if(model.charAt(start)=='C' && model.charAt(end)=='A'){ //Question 2 er ans
                System.out.println(no+", "+", "+model+", "+", "+price+", "+year);
            }

        }
        sc.close();
        // for(carData s : arr) System.out.println(s);
        for(carData s : arr){ // Question 3 er ans
            try{
                if(s.getYear()<1800){
                    throw new InValidYearException("The car was built before year 1800");
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}