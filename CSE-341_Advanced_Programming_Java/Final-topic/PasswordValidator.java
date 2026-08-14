import java.util.*;

class MyException extends Exception{
    MyException(String s){
        super(s);
    }
}
class password{
    void isValid(String s) throws MyException{
        for(int i=0; i<s.length(); i++){
            // int a = s.charAt(i)-'0';
            // System.out.println(a);
            if(s.charAt(i)>'t'){
                throw new MyException("password is not valid");
            }
        }
        System.out.println("Password is valid\n");
    }
}
class passwordValidator{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String pass = sc.next();
        password p = new password();
        
        try{
            p.isValid(pass);
        }
        catch(MyException e){
            System.out.println(e);
        }
    }
}