package kernel;

import Colors.ColorController;
import Colors.StaticColors;
import Output.Console;
import Colors.ColorBase;
import Colors.ColorController;

public class Kernel {

    public static void main() {
        Console console = new Console();

        console.clearConsole();
        //console.setColor(console.colorPicker.font_grey,console.colorPicker.back_black);
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
        int z = 0;
        int x = 273;
        long y = 500000000;
        int minusx = -273;
        byte hex1= (byte) 0xA1;
        byte hex2= -2;
        short hex3= (short) 0xF1B3;
        int hex4= 0x1A2B3C4D;
        long hex5= 0x1A2B3C4F;
        long hex6=-1;
        //byte hex3=-1;
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
        //console.setColor(c.font_green,c.back_black);
        //console.print("TEST");
    }



    //Newline Test



}