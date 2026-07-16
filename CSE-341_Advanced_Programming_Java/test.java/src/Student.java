public class Student {
    private String name;
    private int id,age,sec;

    public void setter(String s, int i, int a, int sec){
        name = s;
        id = i;
        age = a;
        this.sec = sec;
    }
    public void getter(){
        System.out.println("Student name is : "+name+"\nStudent id is : "+id+"\nStudent age is : "+age+"\nStudent age is :"+age+"\nStudent Section is : "+sec);
    }

}