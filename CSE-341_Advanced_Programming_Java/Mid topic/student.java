// import java.util.Scanner;

public class student {

}

class Driver{ //Complete
    int driverId;
    String name;
    double rating;
    static int totalDriver;
    public Driver(int i, String n, double r){
        driverId = i; name = n; rating = r;
        totalDriver++;
    }
    public double getRatings(){
        return rating;
    }
    public void displayDriver(){
        System.out.println("Driver name is : "+name);
        System.out.println("Driver id is : "+driverId);
        System.out.println("Driver rating is : "+rating);
    }
    public static void show(){
        System.out.println("Total driver is : "+totalDriver);
    }
}

class Car{ //Complete
    String carNumber,model;
    Driver driver;
    public Car(String cN, String m, Driver d){
        carNumber = cN; model = m;
        driver = new Driver(d.driverId,d.name, d.rating);
    }
    public Driver getDriver(){
        return driver;
    }
    public void getAssignedDriverDetails(){
        driver.displayDriver();
    }
}

class Trip {
    int tripId;
    double distance;
    Car car;
    public Trip(int tripId, double distance){
        this.tripId = tripId;
        this.distance = distance;
    }
    public void startTrip(Car c){
        if(c.getDriver().getRatings()>=4.0){
            this.car = c;
        }
        else {System.out.println("Noooo");}
    }
    public Driver allocateDriver(){
        if(car != null && distance>5.0){
            return car.getDriver();
        }
        else return null;
    }

}
class RideService{
    Car[] availableCars;
    public RideService(Car[] availableCars) {
        this.availableCars = availableCars;
    }

    public void processTrip(Trip t){
        if(t.allocateDriver()!=null){
            Driver d = t.car.getDriver();
            d.displayDriver();
        }
        else{
            System.out.println("Trip can't start");
        }
    }
}


class run {
    public static void main(String[] args) {
        Driver d1 = new Driver(430, "Meheraj",5.66);
        Driver d2 = new Driver(433, "Sajib",2.1);
        Driver.show();
        Car c1 = new Car("Dijsktra","Toyota",d1);
        Car c2 = new Car("Warshall","Honda",d2);
        Car cars[] = {c1,c2};

        Trip t = new Trip(1,10.0);
        t.startTrip(c1);
        RideService r = new RideService(cars);
        r.processTrip(t);
    }
}
