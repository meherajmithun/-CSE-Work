// Simple Payment Processing System
class Payment {
    protected double amount;
    public Payment(double amount) {
        this.amount = amount;
    }
    public void processPayment() {
        System.out.println("Processing general payment of amount: " + amount);
    }
}

class OnlinePayment extends Payment {
    protected String platform;

    public OnlinePayment(double amount, String platform) {
        super(amount);
        this.platform = platform;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing online payment via " + platform +
                           " of amount: " + amount);
    }
}

class MobileBankingPayment extends OnlinePayment {
    private String mobileNumber;

    public MobileBankingPayment(double amount, String platform, String mobileNumber) {
        super(amount, platform);
        this.mobileNumber = mobileNumber;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing mobile banking payment from " +
                           mobileNumber + " via " + platform +
                           " of amount: " + amount);
    }
}


class PaymentService {

    public void executePayment(Payment p) {
        p.processPayment();   // Runtime Polymorphism
    }
}
/*
🔹 How Runtime Polymorphism Works Here

payment is a Payment reference:

Payment payment = mbp;

It points to a MobileBankingPayment object.

When this is called:

p.processPayment();

Java decides at runtime which method to run.

So it calls:

MobileBankingPayment.processPayment()

This is runtime polymorphism. */

public class Main {

    public static void main(String[] args) {

        MobileBankingPayment mbp = new MobileBankingPayment(1500.50, "bKash", "017XXXXXXXX");

        Payment payment = mbp;

        PaymentService service = new PaymentService();
        service.executePayment(payment);
    }
}
