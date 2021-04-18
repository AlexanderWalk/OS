package Devices.PCI;

public class PCI {
    private final int ECD = 0x80<<24, type = 0x0;
    private int busNumber = 0, deviceNumber = 0, functionNumber = 0, register = 0;

    private int getAddress(){
        return ECD | busNumber<<16 | deviceNumber<<11 | functionNumber<<8 | register<<2 | type;
    }

    public static void getPCIDevices(){

    }

}
