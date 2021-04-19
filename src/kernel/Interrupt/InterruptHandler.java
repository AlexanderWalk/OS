package kernel.Interrupt;

import Devices.Keyboard.Keyboard;
import Devices.Timer;
import output.Console.DebugConsole;

public class InterruptHandler {
    static final int hardwareInterruptMaster = 0x20;
    static final int hardwareInterruptSlave = 0xA0;

    @SJC.Interrupt
    private static void divideByZero(){
        DebugConsole.directDebugPrint("DivideByZero");
        while(true);
    }
    static int divideByZeroOffset() {
        return MAGIC.mthdOff("InterruptHandler", "divideByZero");
    }

    @SJC.Interrupt
    private static void debugException(){
        DebugConsole.directDebugPrint("debugException");
        while(true);
    }
    static int debugExceptionOffset() {
        return MAGIC.mthdOff("InterruptHandler", "debugException");
    }

    @SJC.Interrupt
    private static void NMI(){
        DebugConsole.directDebugPrint("NMI");
        while(true);
    }
    static int NMIOffset() {
        return MAGIC.mthdOff("InterruptHandler", "NMI");
    }

    @SJC.Interrupt
    private static void breakpoint(){
        DebugConsole.directDebugPrint("breakpoint");
        while(true);
    }
    static int breakpointOffset() {
        return MAGIC.mthdOff("InterruptHandler", "breakpoint");
    }

    @SJC.Interrupt
    private static void overflow(){
        DebugConsole.directDebugPrint("overflow");
        while(true);
    }
    static int overflowOffset() {
        return MAGIC.mthdOff("InterruptHandler", "overflow");
    }

    @SJC.Interrupt
    private static void indexOutOfRange(){
        DebugConsole.directDebugPrint("indexOutOfRange");
        while(true);
    }
    static int indexOutOfRangeOffset() {
        return MAGIC.mthdOff("InterruptHandler", "indexOutOfRange");
    }

    @SJC.Interrupt
    private static void invalidOpcode(){
        DebugConsole.directDebugPrint("invalidOpcode");
        while(true);
    }
    static int invalidOpcodeOffset() {
        return MAGIC.mthdOff("InterruptHandler", "invalidOpcode");
    }

    //reserved: 0x07, 0x09-0x0C, 0x0F-0x1F
    @SJC.Interrupt
    private static void reserved(){
        DebugConsole.directDebugPrint("reserved");
        while(true);
    }
    static int reservedOffset() {
        return MAGIC.mthdOff("InterruptHandler", "reserved");
    }

    @SJC.Interrupt
    private static void doubleFault(){
        DebugConsole.directDebugPrint("doubleFault");
        while(true);
    }
    static int doubleFaultOffset() {
        return MAGIC.mthdOff("InterruptHandler", "doubleFault");
    }

    @SJC.Interrupt
    private static void generalProtectionError(){
        DebugConsole.directDebugPrint("generalProtectionError");
        while(true);
    }
    static int generalProtectionErrorOffset() {
        return MAGIC.mthdOff("InterruptHandler", "generalProtectionError");
    }

    @SJC.Interrupt
    private static void pageFault(){
        DebugConsole.directDebugPrint("pageFault");
        while(true);
    }
    static int pageFaultOffset() {
        return MAGIC.mthdOff("InterruptHandler", "pageFault");
    }

    //Unknown Device
    @SJC.Interrupt
    public static void genericHardwareInterrupt(){
        DebugConsole.directDebugPrint("unknown Device");
        while(true);
        //hardwareEOI(hardwareInterruptSlave);
        //hardwareEOI(hardwareInterruptMaster);
    }
    static int genericHardwareInterruptOffset() {
        return MAGIC.mthdOff("InterruptHandler", "genericHardwareInterrupt");
    }

    @SJC.Interrupt
    public static void timerInterrupt(){
        Timer.increaseTimer();
        hardwareEOI(hardwareInterruptMaster);
    }
    static int timerInterruptOffset() {
        return MAGIC.mthdOff("InterruptHandler", "timerInterrupt");
    }

    @SJC.Interrupt
    public static void keyboardInterrupt(){
        Keyboard.storeByte();
        hardwareEOI(hardwareInterruptMaster);
    }
    static int keyboardInterruptOffset() {
        return MAGIC.mthdOff("InterruptHandler", "keyboardInterrupt");
    }


    //End of Interrupt for every Hardwareinterrupt
    private static void hardwareEOI(int port){
        MAGIC.wIOs8(port,(byte)0x20);
    }
}
