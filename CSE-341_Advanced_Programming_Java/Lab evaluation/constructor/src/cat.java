public class cat {
    private String name;
    private int age;

    public cat(){
        this.name = "Unknown";
        this.age = 0;
    }
    public void setName(String s){
        this.name = s;
    }
    public String getName(){
        return "Name is : "+name;
    }
    public void setAge(int s){
        this.age = s;
    }
    public String getAge(){
        return "Age is : "+age;
    }
}
