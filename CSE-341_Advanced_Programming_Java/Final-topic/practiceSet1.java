import java.util.*;
import java.io.*;

class cars{
    private String no,model;
    private double price;
    cars(){
        no=" ";model=" ";price=0;
    }
    cars(String n, String m, double p){
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
    public String getNo(){
        return no;
    }
    public String getModel(){
        return model;
    }
    public double getPrice(){
        return price;
    }
    public String toString(){
        return (no+" "+model+" "+price);
    }
    public double taxes(double p){
        return (p*0.05);
    }
}
class CarNOException extends Exception{
    CarNOException(String msg){
        super(msg);
    }
}
class practiceSet1{
    public static void main(String[] args) throws Exception{
        cars c = new cars();

        ArrayList<cars>arr = new ArrayList<>();

        //Already give in the question
        File f = new File("record.txt");
        //create new txt file write to add the taxes data.(no, model , price, taxes)

        File f2 = new File("updateData.txt");
        //to add new file column
        FileWriter fw = new FileWriter(f2);
        fw.write("NO\tModel\tprice\ttaxes\n");

        Scanner sc = new Scanner(f);
        while(sc.hasNext()){
            String no = sc.next();
            String model = sc.next();
            double price = sc.nextDouble();
            double tax = c.taxes(price);
            //add model no,model,price, taxes in the new file.(This is the solution of question -1)
            fw.write(no+" "+model+" "+price+" "+tax+"\n");
            cars st = new cars(no,model,price);
            arr.add(st); // add object in the arraylist
        }
        fw.close();
        // for(cars s : arr) System.out.println(s.toString());//print arrayList
        sc = new Scanner(f2);
        if (sc.hasNextLine()) sc.nextLine(); // skip header because the header is NO Model Price Taxes
        while(sc.hasNext()){
            String no = sc.next();
            String model = sc.next();
            double price = sc.nextDouble();
            String tax = sc.next();
            // System.out.println(no+", "+", "+model+", "+", "+price+", "+tax);
            if(no.length()>1){
                System.out.println(no+", "+", "+model+", "+", "+price+", "+tax);
            }

        }
        sc.close();
        for(cars s : arr){
            String no = s.getNo();
            try{
                int cnt = 0;
                for(int i=0; i<no.length(); i++){
                    if(no.charAt(i)>='a' && no.charAt(i)<='z'){
                        cnt++;
                    }
                }
                if(cnt<3 || cnt>5){
                    throw new CarNOException("car no size is less than 3 or more than 5");
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}