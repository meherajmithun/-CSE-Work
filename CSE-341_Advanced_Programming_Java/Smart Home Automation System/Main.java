//Smart Home Automation System
class Device {

    protected int deviceId;

    public Device(int deviceId) {
        this.deviceId = deviceId;
    }

    public void operate() {
        System.out.println("Operating generic device");
    }
}

class Appliance extends Device {

    protected double powerConsumption;

    public Appliance(int deviceId, double powerConsumption) {
        super(deviceId);
        this.powerConsumption = powerConsumption;
    }

    // Override method
    @Override
    public void operate() {
        System.out.println("Operating appliance with power consumption: "
                           + powerConsumption + "W");
    }
}

class SmartAppliance extends Appliance {

    private boolean wifiEnabled;

    public SmartAppliance(int deviceId, double powerConsumption, boolean wifiEnabled) {
        super(deviceId, powerConsumption);
        this.wifiEnabled = wifiEnabled;
    }

    @Override
    public void operate() {

        // Call parent class method
        super.operate();

        // Print smart feature info
        System.out.println("Smart features enabled: " + wifiEnabled);
    }
}

class HomeController {

    public void controlDevice(Device d) {
        d.operate();   // Runtime Polymorphism
    }
}

/*
🔹 How This Shows Runtime Polymorphism
 Parent Reference → Child Object
Device device = smart;

device is a Device reference,
but it points to a SmartAppliance object.

2️⃣ Method Call
d.operate();

Java checks at runtime:

“What object is this really?”

It finds → SmartAppliance

So it calls:

SmartAppliance.operate()

Not Device.operate() ❌

3️⃣ Use of super

Inside SmartAppliance:

super.operate();

This calls:

Appliance.operate()

Then prints extra info.
*/

public class Main {

    public static void main(String[] args) {

        SmartAppliance smart =
                new SmartAppliance(101, 850.5, true);

        Device device = smart;

        HomeController controller = new HomeController();
        controller.controlDevice(device);
    }
}
