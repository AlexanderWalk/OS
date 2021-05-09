package kernel;

import devices.keyboard.Key;
import devices.keyboard.Keyboard;
import devices.keyboard.KeyboardEvent;
import devices.PCI.BaseClassCode;
import devices.PCI.PCI;
import devices.PCI.PCIDevice;
import devices.Timer;
import kernel.interrupt.Interrupts;
import output.console.Console;
import output.console.DebugConsole;
import output.colors.StaticColors;
import rte.DynamicRuntime;

public class Kernel {

    private static Console console;

    static{
        console = new Console();
    }

    public static void main() {
        //while(true);
        initKernel();
        testFunctions();
    }

    private static void initKernel(){
        DynamicRuntime.initEmptyObjects();
        //for Static Blocks
        MAGIC.doStaticInit();
        Interrupts.initInterrupts();
        console.clearConsole();
        console.println("                             Welcome to KasleberkOS                             ");
    }

    private static void testFunctions(){
        //consoleCheck();
        //debugConsoleCheck();
        //checkMultipleObjects();
        //checkGraphicMode();
        //breakpointCheck();
        //getMemoryMap();
        enterTextinputMode();
        //getPCIDevices();
    }

    private static void getPCIDevices(){
        PCIDevice[] devices = PCI.getDevices();
        console.println("PCI Devices");
        for(int i = 0; i<devices.length; i++){
            console.print("Device #");
            console.println(i);
            console.print(" Bus: ");
            console.printHex(devices[i].bus);
            console.print(" | Device: ");
            console.printHex(devices[i].device);
            console.print(" | Function: ");
            console.printHex(devices[i].function);
            console.print(" Baseclasscode: ");
            console.print(BaseClassCode.codeToString(devices[i].baseclasscode));
            console.print(" | Subclasscode: ");
            console.printHex(devices[i].subclasscode);
            console.print(" | VendorID: ");
            console.printHex(devices[i].vendorID);
            console.print(" | DeviceID ");
            console.printlnHex(devices[i].deviceID);
        }
    }

    private static void getMemoryMap(){
        int continuation = 0;
        do {
            MemoryMapEntry map = BIOS.getMemoryMap(continuation);
            //get new continuationIndex from EBX
            continuation = BIOS.regs.EBX;
            //Printing Adress and length
            console.printHex(map.baseAddress);
            console.print(" - ");
            console.printHex(map.baseAddress + map.length -1);
            console.print(" ");
            //type determines if Memory is free or already reserved
            if(map.type == 1){
                console.println("free");
            } else{
                console.println("reserved");
            }
        } while (continuation != 0);
    }

    private static void enterTextinputMode(){
        boolean textinput = true;
        console.println("Texteingabe: ESC zum Beenden druecken");
        while(textinput){
            Keyboard.processInputBuffer();
            KeyboardEvent keyboardEvent;
            if(Keyboard.eventAvailable()){
                keyboardEvent = Keyboard.getKeyboardEvent();
                if(keyboardEvent.keyCode == Key.ESCAPE)
                    textinput=false;
                if(keyboardEvent.keyCode<=127)
                    console.print((char)keyboardEvent.keyCode);
                if(keyboardEvent.keyCode == Key.BACKSPACE)
                    console.delchar();
            }
        }
    }

    private static void checkGraphicMode(){
        //Enter Graphicmode, draw and exit after 5 Seconds
        BIOS.enterGraphicMode();
        for(int i=0;i<32000;i++){
            MAGIC.wMem8(0xA0000+i,(byte)0x4B);
        }
        Timer.sleep(5);
        BIOS.exitGraphicMode();
        console.clearConsole();
    }

    private static void breakpointCheck(){
        MAGIC.inline(0xCC);
    }

    private static void consoleCheck(){
        //inputs to test:
        console.print("char: ");
        console.println('c');
        console.print("string: ");
        console.println("String");
        console.print("int: ");
        console.println(273);
        console.print("negative int: ");
        console.println(-273);
        console.print("int: ");
        console.println(1234567890);
        console.print("null: ");
        console.println(0);
        console.print("short: ");
        console.println((short)12345);
        console.print("long: ");
        console.println((long)1234567890987654321L);
        console.print("Hex byte: ");
        console.printlnHex((byte)55);
        console.print("Hex byte2: ");
        console.printlnHex((byte)0x55);
        console.print("Hex short: ");
        console.printlnHex((short)0x12AF);
        console.print("Hex int: ");
        console.printlnHex(0x1234ABCD);
        console.print("Hex long: ");
        console.printlnHex((long)0x123456789ABCDEF0L);
        console.print("Hex long2: ");
        console.printlnHex((long)-1L);
        console.setCursor(1,0);
        console.println();
    }

    private static void debugConsoleCheck(){
        DebugConsole.clearConsoleDebug();
        DebugConsole.debugPrint("char: ");
        DebugConsole.debugPrintln('c');
        DebugConsole.debugPrint("String: ");
        DebugConsole.debugPrintln("string");
        DebugConsole.debugPrint("Hex: ");
        DebugConsole.debugPrintlnHex(0x1234ABCD);
        DebugConsole.debugPrint("Hex2: ");
        DebugConsole.debugPrintlnHex(0xFDEC0123);
    }

    private static void checkMultipleObjects(){
        console.println("Standardconsole");
        Console c1 = new Console();
        c1.setColor(StaticColors.font_cyan, StaticColors.back_black);
        c1.print("zweites object - ");
        console.print("erstes object");
    }
}