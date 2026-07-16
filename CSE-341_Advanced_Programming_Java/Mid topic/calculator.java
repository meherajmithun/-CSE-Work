public class calculator {
    public int sum(int a, int b) {
        return a + b;
    }
}
class cal extends calculator {
    public int sum(int a, int b) {
        return a + b + 10;
    }
}
class Run {
    public static void main(String arg[]) {
        cal obj = new cal();
        int res = obj.sum(3, 1);
        System.out.println(res);
        calculator obj2 = new calculator();
        System.out.println(obj2.sum(1,34));
    }
}