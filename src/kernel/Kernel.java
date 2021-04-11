package kernel;

import Output.Colors.StaticColors;
import Output.Console;
import BIOS;

public class Kernel {
    private static final int GDTBASE = 0x10000;
    public static void main() {
        //while(true);
        Console c = new Console();
        c.clearConsole();
        c.println("Interrupttests:");
        Interrupts.registerHandlers();
        //Interrupts.ClearInterruptFlag();
        //Interrupts.SetInterruptFlag();
        MAGIC.inline(0xCC);
        BIOS.regs.EAX=0x0013;
        BIOS.rint(0x10);
        BIOS.regs.EAX=0x0003;
        BIOS.rint(0x10);
        while(true);
        //consoleCheck();
    }

    private static void writeGDT() {
        MAGIC.wMem32(GDTBASE, 0x00000000);
        MAGIC.wMem32(GDTBASE+4, 0x00000000);
        MAGIC.wMem32(GDTBASE+8, 0x0000FFFF);
        MAGIC.wMem32(GDTBASE+12, 0x00CF9A00);
        MAGIC.wMem32(GDTBASE+16, 0x0000FFFF);
        MAGIC.wMem32(GDTBASE+20, 0x00CF9200);
        MAGIC.wMem32(GDTBASE+24, 0x0000FFFF);
        MAGIC.wMem32(GDTBASE+28, 0x008F9A06);
    }

    private static void lGDTR(int count) {
        //LGDTR
        long offs=0, tmp;
        MAGIC.ignore(offs);
        tmp = (((long)GDTBASE)<<16) | (((long)4<<count)-1);
        MAGIC.ignore(tmp);
        MAGIC.inline(0x0F, 0x01, 0x5D, 0xF0); // lidt [e/rbp-0x10]}
    }

    private static void consoleCheck(){
        Console console = new Console();
        console.clearConsole();
        console.setCursor(0,0);
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
        for(int i=0;i<11;i++){
            console.println();
        }
        //Overflow Check
        console.print("Das hier sollte in der ersten Zeile stehen, sofern nichts hinzugefuegt wurde --");
    }
}