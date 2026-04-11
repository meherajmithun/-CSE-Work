import java.util.Scanner;

class MyException extends Exception{
    MyException(String msg){
        super(msg);
    }
}

public class PasswordValidator{
    
    void IsValid(String s) throws MyException{
        int r=0,f=0;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)-'0'>=0 && s.charAt(i)-'0'<10){
                f=1;
            }
        }
        if((s.length()>7) && (f==1)){
            System.out.println("Password is valid");
        }
        else{
            throw new MyException("Password is not valid");
        }
    }


    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        String password = sc.next();
        System.out.println(password);

        PasswordValidator p = new PasswordValidator();

        try{
            p.IsValid(password);
        }
        catch(MyException e){
            System.out.println(e);
        }


    }
}