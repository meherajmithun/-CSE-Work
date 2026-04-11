//Employee Management System
class Employee {

    protected int empId;
    protected double basicSalary;

    public Employee(int empId, double basicSalary) {
        this.empId = empId;
        this.basicSalary = basicSalary;
    }

    public double calculateSalary() {
        return basicSalary;
    }
}


class Manager extends Employee {

    protected double managementAllowance;

    public Manager(int empId, double basicSalary, double managementAllowance) {
        super(empId, basicSalary);
        this.managementAllowance = managementAllowance;
    }

    // Override method
    @Override
    public double calculateSalary() {
        return basicSalary + managementAllowance;
    }
}


class SeniorManager extends Manager {

    private double performanceBonus;

    public SeniorManager(int empId, double basicSalary,
                         double managementAllowance, double performanceBonus) {
        super(empId, basicSalary, managementAllowance);
        this.performanceBonus = performanceBonus;
    }

    // Override method
    @Override
    public double calculateSalary() {
        return basicSalary + managementAllowance + performanceBonus;
    }
}

class PayrollService {

    public void generatePayroll(Employee e) {

        double totalSalary = e.calculateSalary(); // Polymorphism

        System.out.println("Employee ID: " + e.empId);
        System.out.println("Total Salary: " + totalSalary);
    }
}
/*
🔹 How Runtime Polymorphism Works Here
1️⃣ Parent Reference → Child Object
Employee emp = sm;

emp → Employee reference

sm → SeniorManager object

2️⃣ Method Call
e.calculateSalary();

Java checks the actual object at runtime.

Since the object is SeniorManager, it calls:

SeniorManager.calculateSalary()

Not Employee.calculateSalary() 
*/

public class Main {

    public static void main(String[] args) {

        SeniorManager sm = new SeniorManager(1001, 50000, 10000, 8000);

        Employee emp = sm;

        PayrollService payroll = new PayrollService();
        payroll.generatePayroll(emp);
    }
}
