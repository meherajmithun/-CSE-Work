class ATM{
    Double balance;
    int cnt;
    public void increment() {cnt++;}
}
class deposit extends Thread{
    ATM a = new ATM();
    public void run(){
        for(int i=0; i<1000; i++){
            a.increment();
        }   
    }
}
class withdrow extends Thread{
    ATM a = new ATM();
    public void run(){
        for(int i=0; i<1000; i++){
            a.increment();
        }
        System.out.println(a.cnt);
    }
}
public class ThreadTask2{
    public static void main(String[] args){
        deposit t1 = new deposit();
        withdrow t2 = new withdrow();
        t1.start(); t2.start();
    }
}