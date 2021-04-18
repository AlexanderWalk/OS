package kernel;

import Devices.Keyboard;
import Devices.KeyboardEvent;
import Devices.Timer;
import kernel.Interrupt.Interrupts;
import output.Console;
import output.colors.StaticColors;

public class Kernel {
    public static void main() {
        //while(true);

        //For static Blocks
        MAGIC.doStaticInit();
        //Interrupt setup
        Interrupts.registerHandlers();
        Interrupts.initPic();
        //Activate Interrupts - Dangerous
        Interrupts.SetInterruptFlag();
        Console console = new Console();
        console.clearConsole();
        //console.print("TESTsaa");
        //MAGIC.inline(0xCC);
        while(true){
            Keyboard.processInputBuffer();
            KeyboardEvent keyboardEvent;
            if(Keyboard.eventAvailable()){
                keyboardEvent = Keyboard.getKeyboardEvent();
                console.print((char)keyboardEvent.keyCode);
            }
        }
        //console.print('ß');
        //console.setCursor(20,11);
        //while(true);
        //consoleCheck(console);
        //interruptCheck();
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

    private static void consoleCheck(Console console){
        //console.setCursor(10,10);
        console.print('A');
        console.println();
        console.setColor(StaticColors.font_cyan,StaticColors.defaultBack);
        console.print("AAAAAAAAAAAAAAAAAAAAAA");
        Console console1 = new Console();
        console1.println();
        console1.setColor(StaticColors.font_green, StaticColors.defaultBack);
        console1.print("Anderes Objekt, gleicher Videospeicher");
        console1.println();
        console.print("Farbe von erster Konsole immernoch auf Cyan?");
        console1.println();
        console.setColor(StaticColors.font_lightmagenta,StaticColors.defaultBack);
        int z = 0;
        int x = 273;
        long y = 500000000;
        int minusx = -273;
        byte hex1= (byte) 0xA1;
        byte hex2= -2;
        short hex3= (short) 0xF1B3;
        int hex4= 0x1A2B3C4D;
        long hex5= 0x1A2B3C4D1AAABCFDL;
        long hex6=-1L;
        console1.println(x);
        console1.println(y);
        console.println(z);
        console.println(minusx);
        console.printlnHex(hex1);
        console.printlnHex(hex2);
        console.printlnHex(hex3);
        console.printlnHex(hex4);
        console.printlnHex(hex5);
        console1.printlnHex(hex6);
        /*for(int i=0;i<11;i++){
            console.println();
        }*/
        //Overflow Check
        //console.print("Das hier sollte in der ersten Zeile stehen, sofern nichts hinzugefuegt wurde --");
    }
}