package kernel.Interrupt;

import Devices.Keyboard;
import Devices.Timer;
import output.Console;

public class Interrupts {
    private static final int MASTER = 0x20, SLAVE = 0xA0;
    private static boolean IsInterruptFlagActive = false;
    //First free Address 0x07E00
    private static final int IDTStartAdress = 0x07E00;
    private static final int bytesPerEntry = 8;
    private static final int entryCount = 48;

    public static void registerHandlers() {
        int i=0;
        int genericHandlerOffset = MAGIC.mthdOff("Interrupts","genericHandler");
        int genericHandlerwParamOffset = MAGIC.mthdOff("Interrupts","genericHandlerWithParameter");
        int hardwareInterruptOffset = MAGIC.mthdOff("Interrupts","genericHardwareInterruptHandler");
        int timerInterruptOffset = MAGIC.mthdOff("Interrupts","timerInterruptHandler");
        int keyboardInterruptOffset = MAGIC.mthdOff("Interrupts","keyboardInterruptHandler");
        int classRef = MAGIC.cast2Ref(MAGIC.clssDesc("Interrupts"));
        setIDTRegister();
        while(i<=0x07){
            createIDTEntry(i++,MAGIC.rMem32(classRef+genericHandlerOffset)+MAGIC.getCodeOff());
        }
        //Double Fault
        createIDTEntry(i++,MAGIC.rMem32(classRef+genericHandlerwParamOffset)+MAGIC.getCodeOff());
        //reserved
        while(i<=0x0C){
            createIDTEntry(i++,MAGIC.rMem32(classRef+genericHandlerOffset)+MAGIC.getCodeOff());
        }
        //General protection Error and Page fault
        while(i<0x0E){
            createIDTEntry(i++,MAGIC.rMem32(classRef+genericHandlerwParamOffset)+MAGIC.getCodeOff());
        }
        //0x0F bis 0x1F reserved
        while(i<=0x1F){
            createIDTEntry(i++,MAGIC.rMem32(classRef+genericHandlerOffset)+MAGIC.getCodeOff());
        }
        //TODO:IRQ0-15
        //Timer
        createIDTEntry(i++,MAGIC.rMem32(classRef+timerInterruptOffset)+MAGIC.getCodeOff());
        //Keyboard
        createIDTEntry(i++,MAGIC.rMem32(classRef+keyboardInterruptOffset)+MAGIC.getCodeOff());
        //Other devices
        while(i<=0x2F){
            createIDTEntry(i++,MAGIC.rMem32(classRef+hardwareInterruptOffset)+MAGIC.getCodeOff());
        }
    }


    public static void setIDTRegister() {
        //Table-Start and limit
        int tableLimit =bytesPerEntry*entryCount-1;//-1, da Offset für letztes Byte
        long tmp=(((long) IDTStartAdress)<<16)|(long)tableLimit;
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tmp);// lidt [ebp-0x08/tmp] - Phase 3 Seite 2
    }


    public static void setIDTRegisterRM() {
        //RealMode - 1023 limit, BaseAddress 0 -> no shift.
        long tableLimit =1023;
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tableLimit);// lidt [ebp-0x08/tmp] - Phase 3 Seite 2
    }

    //IDT:
    //Werte laut Vorlesung:
    //Offset 0-15 -> klar
    // Segmentselector: "Da muss ne 8 rein"
    //Offset 16-31 -> klar
    // Flags etc laut Vorlesung: 1000111000000000 -> = 0x8E00
    public static void createIDTEntry(int  entryNum, int handler){
        int entryAddr = IDTStartAdress + entryNum*8;
        MAGIC.wMem32(entryAddr, handler&0x0000FFFF|(8<<16));
        MAGIC.wMem32(entryAddr+4, 0x00008E00|handler&0xFFFF0000);
    }

    //STI
    public static void SetInterruptFlag(){
        if(!IsInterruptFlagActive){
            IsInterruptFlagActive = true;
            MAGIC.inline(0xFB);
        }
    }

    //CLI
    public static void ClearInterruptFlag(){
        if(IsInterruptFlagActive){
            IsInterruptFlagActive = false;
            MAGIC.inline(0xFA);
        }
    }

    //TODO: Handler aufsplitten

    //Interrupt Placeholder #1
    @SJC.Interrupt
    private static void genericHandler(){
        Console.directDebugPrint("handled.");
    }

    //Interrupt Placeholder #2
    @SJC.Interrupt
    private static void genericHandlerWithParameter(int param){
        Console.directDebugPrint("handled with parameter");
    }

    //Interrupt Placeholder #3
    @SJC.Interrupt
    public static void genericHardwareInterruptHandler(){
        Console.directDebugPrint("Device handled");
        hardwareEOI(SLAVE);
        hardwareEOI(MASTER);
    }

    @SJC.Interrupt
    public static void timerInterruptHandler(){
        //Console.directDebugPrint("timer");
        Timer.increaseTimer();
        hardwareEOI(MASTER);
    }

    @SJC.Interrupt
    public static void keyboardInterruptHandler(){
        Keyboard.storeByte();
        //Console.directDebugPrint("Keyboardbyte stored");
        hardwareEOI(MASTER);
    }

    //End of Interrupt for every Hardwareinterrupt
    private static void hardwareEOI(int port){
        MAGIC.wIOs8(port,(byte)0x20);
    }

    //Initialize PIC for Hardwareinterrupts
    public static void initPic() {
        programmChip(MASTER, 0x20, 0x04); //init offset and slave config of master
        programmChip(SLAVE, 0x28, 0x02); //init offset and slave config of slave
    }

    private static void programmChip(int port, int offset, int icw3) {
        MAGIC.wIOs8(port++, (byte)0x11); // ICW1
        MAGIC.wIOs8(port, (byte)offset); // ICW2
        MAGIC.wIOs8(port, (byte)icw3); // ICW3
        MAGIC.wIOs8(port, (byte)0x01); // ICW4
    }
}