public class RunPoint {
    public static void main(String s[]){
        Point p1 = new Point();
        p1.setX(10);
        System.out.println(p1.getX());
        p1.setY(5);
        System.out.println(p1.getY());
        Point p2 = new Point(20,30);
        System.out.println(p1.toString());
        System.out.println(p2.toString());

        System.out.println(p1.distance(p2));

    }
}
