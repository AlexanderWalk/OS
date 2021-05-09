package devices.PCI;

public class PCI {
    private static final int ECD = 0x80<<24, type = 0x0;
    private static final int addrToWrite = 0x0CF8, addrToRead = 0x0CFC;
    private static PCIDevice[] devices;
    private static int deviceArraySize = 8, deviceCount = 0;

    public static void searchForDevices(){
        devices = new PCIDevice[8];
        deviceCount = 0;
        for(int busNumber = 0; busNumber<256; busNumber++){
            for(int deviceNumber=0; deviceNumber<32; deviceNumber++){
                addDevice(busNumber, deviceNumber, 0);
                if(isSinglefuncDevice(busNumber, deviceNumber)){
                    continue;
                }else{
                    for(int functionNumber=1; functionNumber<8; functionNumber++){
                        addDevice(busNumber, deviceNumber, functionNumber);
                    }
                }
            }
        }
        correctArray();
    }

    public static PCIDevice[] getDevices(){
        if(devices==null){
            searchForDevices();
        }
        return devices;
    }

    private static void expandArray(){
        int oldArraySize = deviceArraySize;
        deviceArraySize *= 2;
        replaceArray(oldArraySize, deviceArraySize);
    }

    private static void correctArray(){
        replaceArray(deviceArraySize, deviceCount);
    }

    private static void replaceArray(int oldSize, int newSize){
        PCIDevice[] temp = new PCIDevice[newSize];
        if(oldSize>newSize)
            oldSize=newSize;
        for(int i = 0; i<oldSize; i++){
            temp[i] = devices[i];
        }
        devices=temp;
    }

    private static int getAddress(int busNumber, int deviceNumber, int functionNumber, int registerNumber){
        return ECD | (busNumber&0xFF)<<16 | (deviceNumber&0x1F)<<11 |
                (functionNumber&0x07)<<8 | (registerNumber&0x3F)<<2 | (type&0x03);
    }

    private static boolean isSinglefuncDevice(int busNumber, int deviceNumber){
        writeAddress(getAddress(busNumber, deviceNumber, 0, 3));
        int register3 = readData();
        //If highest Bit in Header in Reg 3 is not set -> Singlefunction
        return (register3 & 0x00800000) == 0;
    }

    private static void addDevice(int busNumber, int deviceNumber, int functionNumber){
        //Search at current Address
        writeAddress(getAddress(busNumber, deviceNumber, functionNumber, 0));
        int register0 = readData();
        //No device if vendorID and deviceID are 0 or -1
        if(register0 == 0 || register0 == -1){
            return;
        }
        //second reg with status and operation not needed
        writeAddress(getAddress(busNumber, deviceNumber, functionNumber, 2));
        int register2 = readData();
        //fourth reg not needed
        int deviceID = (register0&0xFFFF0000)>>16;
        int vendorID = register0&0x0000FFFF;
        int baseclassCode = (register2&0xFF000000)>>24;
        int subclassCode = (register2&0x00FF0000)>>16;
        PCIDevice device = new PCIDevice(busNumber, deviceNumber, functionNumber,
                                         baseclassCode, subclassCode, vendorID, deviceID);
        if(deviceCount == deviceArraySize){
            expandArray();
        }
        devices[deviceCount] = device;
        deviceCount++;
    }

    private static void writeAddress(int address){
        MAGIC.wIOs32(addrToWrite,address);
    }

    private static int readData(){
        return MAGIC.rIOs32(addrToRead);
    }
}
