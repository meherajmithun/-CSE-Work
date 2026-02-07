
public class Point {
    private int x;
    private int y;
    
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Point(){
        this.x = 0;
        this.y = 0;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getX(){
        System.out.print("The value of x is : ");
        return x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getY(){
        System.out.print("The value of y is : ");
        return y;
    }
    // public int distance(Point p2){
    //     return p1.x+this.p2.y;
    // }
    public String toString(){
        return "value of p1 : (x,y) = "+this.x+","+this.y;
    }
    public double distance(Point p2){
        int px = this.x - p2.x;
        int py = this.y - p2.y;
        return (Math.sqrt(px*px+py*py));
    }

}
