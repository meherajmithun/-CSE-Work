public class RunCat {
    public static void main(String ar[]){
        cat c1 = new cat();
        System.out.println(c1.getName());
        System.out.println(c1.getAge());
        
        c1.setAge(20);
        c1.setName("Meheraj");
        
        System.out.println(c1.getName());
        System.out.println(c1.getAge());

    }


}
