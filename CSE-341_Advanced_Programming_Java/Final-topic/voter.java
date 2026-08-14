import java.util.Scanner;

class MyException extends Exception{
    MyException(String msg){
        super(msg);
    }
}
public class voter{
    void CheckAge(int age) throws MyException{
        if(age<18){
            throw new MyException("The person is not eligible to give vote");
        }
        else{
            System.out.println("The person is eligible to give vote");
        }
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int age = sc.nextInt();
        voter v = new voter();
        try{
            v.CheckAge(age);
        }
        catch(MyException e){
            System.out.println(e);
        }
    }
}