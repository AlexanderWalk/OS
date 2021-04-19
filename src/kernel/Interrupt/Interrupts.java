package kernel.Interrupt;

import Devices.Keyboard.Keyboard;
import Devices.Timer;
import output.Console.DebugConsole;

public class Interrupts {
    private static boolean IsInterruptFlagActive = false;
    //First free Address 0x07E00
    private static final int IDTStartAdress = 0x8000;
    private static final int bytesPerEntry = 8;
    private static final int entryCount = 48;

    public static void initInterrupts(){
        //Interrupt setup
        Interrupts.registerHandlers();
        Interrupts.initPic();
        //Activate Interrupts - Dangerous
        Interrupts.SetInterruptFlag();
    }

    private static void registerHandlers() {
        setIDTRegister();
        int classRef = MAGIC.cast2Ref(MAGIC.clssDesc("InterruptHandler"));
        createIDTEntry(0x00,MAGIC.rMem32(classRef+InterruptHandler.divideByZeroOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x01,MAGIC.rMem32(classRef+InterruptHandler.debugExceptionOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x02,MAGIC.rMem32(classRef+InterruptHandler.NMIOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x03,MAGIC.rMem32(classRef+InterruptHandler.breakpointOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x04,MAGIC.rMem32(classRef+InterruptHandler.overflowOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x05,MAGIC.rMem32(classRef+InterruptHandler.indexOutOfRangeOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x06,MAGIC.rMem32(classRef+InterruptHandler.invalidOpcodeOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x07,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x08,MAGIC.rMem32(classRef+InterruptHandler.doubleFaultOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x09,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x0A,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x0B,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x0C,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x0D,MAGIC.rMem32(classRef+InterruptHandler.generalProtectionErrorOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x0E,MAGIC.rMem32(classRef+InterruptHandler.pageFaultOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x0F,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x10,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x11,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x12,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x13,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x14,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x15,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x16,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x17,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x18,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x19,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x1A,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x1B,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x1C,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x1D,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x1E,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x1F,MAGIC.rMem32(classRef+InterruptHandler.reservedOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x20,MAGIC.rMem32(classRef+InterruptHandler.timerInterruptOffset())
                +MAGIC.getCodeOff());
        createIDTEntry(0x21,MAGIC.rMem32(classRef+InterruptHandler.keyboardInterruptOffset())
                +MAGIC.getCodeOff());
        int i=0x22;
        //Not yet used Devices
        while(i<=0x2F){
            createIDTEntry(i++,MAGIC.rMem32(classRef+InterruptHandler.genericHardwareInterruptOffset())
                    +MAGIC.getCodeOff());
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
    private static void createIDTEntry(int  entryNum, int handler){
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

    //Initialize PIC for Hardwareinterrupts
    public static void initPic() {
        programmChip(InterruptHandler.hardwareInterruptMaster, 0x20, 0x04); //init offset and slave config of master
        programmChip(InterruptHandler.hardwareInterruptSlave, 0x28, 0x02); //init offset and slave config of slave
    }

    private static void programmChip(int port, int offset, int icw3) {
        MAGIC.wIOs8(port++, (byte)0x11); // ICW1
        MAGIC.wIOs8(port, (byte)offset); // ICW2
        MAGIC.wIOs8(port, (byte)icw3); // ICW3
        MAGIC.wIOs8(port, (byte)0x01); // ICW4
    }
}