package devices.PCI;

public class PCIDevice {
    public final int bus, device, function, baseclasscode, subclasscode, vendorID, deviceID;

    public PCIDevice(int bus, int device, int function, int baseclasscode, int subclasscode, int vendorID, int deviceID){
        this.bus = bus;
        this.device = device;
        this.function = function;
        this.baseclasscode = baseclasscode;
        this.subclasscode = subclasscode;
        this.vendorID = vendorID;
        this.deviceID = deviceID;
    }
}
