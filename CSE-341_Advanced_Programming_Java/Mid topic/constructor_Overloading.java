class casio{
    int prize;
    String brand;
    public casio(){
        prize = 0; brand = "Nothing";
    }
    public casio(int prize){
        this.prize = prize;
    }
    public casio(int prize, String name){
        this.prize = prize; this.brand = name;
    }
}
public class constructor_Overloading {
    public static void main(String arg[]){
        casio c = new casio();
        casio d = new casio(10);
        casio e = new casio(10,"dijsktra");
    }   
}

class MathUtil {
    static int add(int a, int b) { return a + b; }
}
public class Main {
    public static void main(String[] args) {
        int sum = MathUtil.add(5, 10); // call without object
        System.out.println(sum); // 15
    }
}