import java.util.Scanner;

class MyException extends Exception{
    MyException(String msg){
        super(msg);
    }
}
public class customException{
    void CheckSum(int a,int b, int c) throws MyException{
        if((a+b)<c || (b+c)<a || (a+c)<b){
            throw new MyException("Invalid Triangle");
        }
        else{
            System.out.println("Valid Triangle");
        }
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        customException v = new customException();
        try{
            v.CheckSum(a,b,c);
        }
        catch(MyException e){
            System.out.println(e);
        }
    }
}