class Authors {
  String Authorname;
  int age;
  Address Adrs;  
  String papername;
  int no_of_paper;
  public Authors(String Authorname,int age,Address Adrs,String papername,int no_of_paper){
    this.Authorname = Authorname;
    this.age = age;
    this.Adrs= Adrs;
    this.papername = papername;
    this.no_of_paper = no_of_paper;
    
  }
  public String toString(){
    return Authorname +" "+age+" "+Adrs+" "+papername+" "+no_of_paper;
  }
}
class Address {

    String houseno;
    int roadno;
    String area;

  public Address(String houseno,int roadno,String area) {
    this.houseno = houseno;
    this.roadno = roadno;
    this.area = area;
  } 
  public String toString(){
    return houseno +" "+roadno+" "+area;
  }
}
public class practiceSet3 {
public static void main(String[] args) {
    Authors[] authr = new Authors[5];
    authr[0] = new Authors("alice", 44, new Address("12/A",2,"DHANMONDI"), "AI", 3);
    authr[1] = new Authors("bob", 41, new Address("1/c",3,"gulshan"), "Algorithm", 2);
    authr[2] = new Authors("geni", 65, new Address("2/d",5,"sanfrasisco"), "quantam physics", 3);
    authr[3] = new Authors("jons", 33, new Address("9/f",1,"bonani"), "AI ML", 1);
    authr[4] = new Authors("don", 50, new Address("7/g",4,"uttara"), "Data Science", 4);
   System.out.println("Authors with more than one paper:");
   for(Authors a: authr){
    if(a.no_of_paper>1){
        System.out.println(a.toString());
    }
   }
}

}