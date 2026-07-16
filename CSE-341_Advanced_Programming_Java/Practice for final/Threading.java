class A implements Runnable{
    public void run(){
        for(int i=0; i<10; i++){
            System.out.print("hi ");
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
class B implements Runnable{
    public void run(){
        for(int i=0; i<10; i++){
            System.out.println("Hello");
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }  
    }
}

public class Threading{
    public static void main(String[] args){
        A obj1 = new A();
        B obj2 = new B();
        Thread t1 = new Thread(obj1);
        Thread t2 = new Thread(obj2);
        t1.start(); t2.start();
         System.out.println(Thread.currentThread().getName());
        // System.out.println(t1.getPriority());
        // System.out.println(t2.getPriority());
        // t2.setPriority(10);
        // System.out.println(t2.getPriority());
        System.out.println(t2.getName());
        t2.setName("Baler thread");
        System.out.println(t2.getName());
        // System.out.println(t1.getId());
    }
}