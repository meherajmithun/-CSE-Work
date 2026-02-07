class Doctor{
    int doctorId;
    String doctorname,specialization;

    public Doctor(){}

    public Doctor(int id, String name, String special){
        this.doctorId = id;
        this.doctorname = name;
        this.specialization = special;
    }

    public void displayDoctor(){
        System.out.println("Doctor name is : "+doctorname+"\nDoctor id is : "+doctorId+"\nDoctor specialization is : "+specialization);
    }



}
class Patient {
    int patientid;
    String patientname;
    Doctor assignedDoctor;

    public Patient(int id, String name){
        this.patientid = id;
        this.patientname = name;
    }

    public void assignDoctor(Doctor d){
        this.assignedDoctor = d;
    }
    public Doctor generateDoctorInfo(){
        return assignedDoctor;
    }
    public void display_patient_info(){
        System.out.println("Patient name is : "+patientname+"\npatient id is : "+patientid);

    }


}
class Hospital {
    public void admitpatient(Patient p){
        System.out.println("\n Patient Admission Details\n");
        p.display_patient_info();
        
        Doctor d = p.generateDoctorInfo();
        if(d != null){
            System.out.println("\n  Assigned Doctor details ");
            d.displayDoctor();
        }
        else{
            System.out.println("No Doctor assigned");
        }

    }
}
public class Main {
    public static void main(String[] args) {
        Doctor d = new Doctor(201,"Meheraj","Hurt");
        Patient p = new Patient(430, "Hossain");
        p.assignDoctor(d);

        Hospital h = new Hospital();
        h.admitpatient(p);

    }
}
