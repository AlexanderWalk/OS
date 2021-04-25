package Devices.PCI;

public class PCI {
    private static final int ECD = 0x80<<24, type = 0x0;
    private static final int addrToWrite = 0x0CF8, addrToRead = 0x0CFC;
    private static int busNumber = 0, deviceNumber = 0, functionNumber = 0, register = 0;
    private static PCIDevice[] devices;
    private static int deviceArraySize = 8, deviceCount = 0;

    public static void searchForDevices(){
        reset();
        devices = new PCIDevice[deviceArraySize];
        for(busNumber = 0; busNumber<255; busNumber++){
            for(deviceNumber=0; deviceNumber<32; deviceNumber++){
                addDevice();
                if(isSinglefuncDevice()){
                    continue;
                }
                for(functionNumber=0; functionNumber<8; functionNumber++){
                    addDevice();
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

    private static void reset(){
        devices=null;
        busNumber = deviceNumber = functionNumber = register = 0;
        deviceArraySize = 8; deviceCount = 0;
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

    private static int getAddress(){
        return ECD | (busNumber&0xFF)<<16 | (deviceNumber&0x1F)<<11 |
                functionNumber&0x07<<8 | register&0x3F<<2 | type&0x03;
    }

    private static boolean isSinglefuncDevice(){
        register += 3;
        MAGIC.wIOs32(addrToWrite,getAddress());
        int thirdReg = MAGIC.rIOs32(addrToRead);
        //If highest Bit in Header in Reg 3 is not set -> Singlefunction
        return (thirdReg & 0x00800000) == 0;
    }

    private static void addDevice(){
        //Search at current Address
        MAGIC.wIOs32(addrToWrite,getAddress());
        int firstReg = MAGIC.rIOs32(addrToRead);
        //No device if vendorID and deviceID are 0 or -1
        if(firstReg == 0 || firstReg == -1){
            return;
        }
        //second reg with status and operation not needed
        register += 0x02;
        MAGIC.wIOs32(addrToWrite,getAddress());
        int thirdReg = MAGIC.rIOs32(addrToRead);
        //fourth reg not needed
        int deviceID = (firstReg&0xFFFF0000)>>16;
        int vendorID = firstReg&0x0000FFFF;
        int baseclassCode = (thirdReg&0xFF000000)>>24;
        int subclassCode = (thirdReg&0x00FF0000)>>16;
        PCIDevice device = new PCIDevice(busNumber, deviceNumber, functionNumber,
                                         baseclassCode, subclassCode, vendorID, deviceID);
        if(deviceCount == deviceArraySize){
            expandArray();
        }
        devices[deviceCount] = device;
        deviceCount++;
        register = 0;
    }
}
