class ATM{
    Double balance;
}
class deposit extends Thread{

}
class withdrow extends Thread{
    
}
public class ThreadTask2{
    public static void main(String[] args){
        deposit t1 = new deposit();
        withdrow t2 = new withdrow();
        t1.start();
    }
}