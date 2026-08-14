class people{
    String name;
    people(String s) {name = s;}
}
class MyThread extends Thread{
    people p ;
    MyThread(people p){
        this.p = p;
    }
    public void run(){
        System.out.println("Thread name : "+getName());
        System.out.println("Thread Priority : "+getPriority());
        int cnt = 0;
        for(int i=0; i<p.name.length(); i++){
            if(p.name.charAt(i)=='a' || p.name.charAt(i)=='e'|| p.name.charAt(i)=='i'|| p.name.charAt(i)=='o'|| p.name.charAt(i)=='u') cnt++;
        }
        System.out.println("Total number of vowels : "+cnt);
        try{
            Thread.sleep(5000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        for(int i=0; i<p.name.length(); i++){
            if(p.name.charAt(i)=='a' || p.name.charAt(i)=='e'|| p.name.charAt(i)=='i'|| p.name.charAt(i)=='o'|| p.name.charAt(i)=='u'){
                System.out.print(p.name.charAt(i)+" ");
                try{
                    Thread.sleep(2000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

public class ThreadTask1{
    public static void main(String[] args){
        people p = new people("Meheraj");
        MyThread m = new MyThread(p);
        m.start();
    }
}