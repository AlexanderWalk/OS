package kernel;

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

        /*
        //TODO: Auslagern
        boolean textinput = true;
        console.println("Texteingabe: 0 zum Beenden druecken");
        while(textinput){
            Keyboard.processInputBuffer();
            KeyboardEvent keyboardEvent;
            if(Keyboard.eventAvailable()){
                keyboardEvent = Keyboard.getKeyboardEvent();
                if((char)keyboardEvent.keyCode == '0')
                    textinput=false;
                console.print((char)keyboardEvent.keyCode);
            }
        }

        //PHASE 4C
        int i = 0;
        do {
            MemoryMapEntry map = BIOS.getMemoryMap(i);
            //get new continuationIndex from EBX
            i = BIOS.regs.EBX;
            //if type is 1 - memory not free
            console.printHex(map.baseAddress);
            console.print(" ");
            console.printHex(map.length);
            console.print(" ");
            if(map.type == 1){
                console.println("free");
            } else{
                console.println("reserved");
            }
        } while (i != 0);*/
    }

    private static void initKernel(){
        //for Static Blocks
        MAGIC.doStaticInit();
        Interrupts.initInterrupts();
        console.clearConsole();
        console.println("                             Welcome to KasleberkOS                             ");
    }

    private static void testFunctions(){
        consoleCheck();
        checkMultipleObjects();
    }



    private static void interruptCheck(){
        Console c = new Console();
        c.println("Interrupttests:");
        MAGIC.inline(0xCC);
        BIOS.regs.EAX=0x0013;
        BIOS.rint(0x10);
        for(int i=0;i<32000;i++){
            MAGIC.wMem8(0xA0000+i,(byte)0x4B);
        }
        Timer.sleep(5);
        BIOS.regs.EAX=0x0003;
        BIOS.rint(0x10);
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