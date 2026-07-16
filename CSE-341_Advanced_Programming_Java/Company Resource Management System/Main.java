//Company Resource Management System
class Staff {

    protected int staffId;
    protected double basicPay;
    protected static int totalStaff = 0; 

    public Staff(int staffId, double basicPay) {
        this.staffId = staffId;
        this.basicPay = basicPay;
        totalStaff++; 
    }
    public double calculatePay() {
        return basicPay;
    }
}

class Supervisor extends Staff {

    protected double supervisionAllowance;

    public Supervisor(int staffId, double basicPay, double supervisionAllowance) {
        super(staffId, basicPay);
        this.supervisionAllowance = supervisionAllowance;
    }
    @Override
    public double calculatePay() {
        return basicPay + supervisionAllowance;
    }
}

class Manager extends Supervisor {

    private double managementBonus;

    public Manager(int staffId, double basicPay, double supervisionAllowance, double managementBonus) {
        super(staffId, basicPay, supervisionAllowance);
        this.managementBonus = managementBonus;
    }

    @Override
    public double calculatePay() {
        return basicPay + supervisionAllowance + managementBonus;
    }
}

class PayrollSystem {

    public void generatePaySlip(Staff s) {
        System.out.println("Staff ID: " + s.staffId);
        System.out.println("Total Pay: " + s.calculatePay());
        System.out.println("----------------------------");
    }
}
/*
🔹 How Runtime Polymorphism Works

Staff references point to child objects:

Staff staff1 = sup;  // Supervisor
Staff staff2 = man;  // Manager

When calculatePay() is called in:

s.calculatePay();

Java checks the actual object at runtime:

staff1 → Supervisor → calls Supervisor.calculatePay()

staff2 → Manager → calls Manager.calculatePay()

🔹 Static Variable Usage

totalStaff counts all objects of Staff and its subclasses.

Every time a Staff, Supervisor, or Manager object is created, totalStaff++ runs.

This is why totalStaff = 2 at the end.

This example demonstrates:

Inheritance → Supervisor & Manager extend Staff

Method overriding → calculatePay()

Runtime polymorphism → Staff reference calling overridden method

Static variables → totalStaff counting all created staff
*/
public class Main {

    public static void main(String[] args) {

        Supervisor sup = new Supervisor(101, 50000, 5000);
        Manager man = new Manager(102, 70000, 8000, 10000);

        Staff staff1 = sup;
        Staff staff2 = man;

        PayrollSystem payroll = new PayrollSystem();
        payroll.generatePaySlip(staff1);
        payroll.generatePaySlip(staff2);

        System.out.println("Total Staff Created: " + Staff.totalStaff);
    }
}
