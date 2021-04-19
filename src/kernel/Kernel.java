package kernel;

import Devices.Keyboard.Key;
import Devices.Keyboard.Keyboard;
import Devices.Keyboard.KeyboardEvent;
import Devices.Timer;
import kernel.Interrupt.Interrupts;
import output.Console.Console;
import output.colors.StaticColors;

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
        //for Static Blocks
        MAGIC.doStaticInit();
        Interrupts.initInterrupts();
        console.clearConsole();
        console.println("                             Welcome to KasleberkOS                             ");
    }

    private static void testFunctions(){
        //consoleCheck();
        //checkMultipleObjects();
        //interruptCheck();
        //getMemoryMap();
        //enterTextinputMode();
    }

    private static void getMemoryMap(){
        int continuation = 0;
        do {
            MemoryMapEntry map = BIOS.getMemoryMap(continuation);
            //get new continuationIndex from EBX
            continuation = BIOS.regs.EBX;
            //Printing Adress and length
            console.printHex(map.baseAddress);
            console.print(" ");
            console.printHex(map.length);
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
                console.print((char)keyboardEvent.keyCode);
            }
        }
    }

    private static void interruptCheck(){
        console.println("Interrupttests:");
        //Enter Graphicmode, draw and exit after 5 Seconds
        BIOS.enterGraphicMode();
        for(int i=0;i<32000;i++){
            MAGIC.wMem8(0xA0000+i,(byte)0x4B);
        }
        Timer.sleep(5);
        BIOS.exitGraphicMode();
        console.clearConsole();
        //Breakpoint
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

    private static void checkMultipleObjects(){
        console.println("Standardconsole");
        Console c1 = new Console();
        c1.setColor(StaticColors.font_cyan, StaticColors.back_black);
        c1.print("zweites object - ");
        console.print("erstes object");
    }
}