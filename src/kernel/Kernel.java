package kernel;

public class Kernel {
    //80x25 Auflösung -> 2000 Zeichen, 2 Byte/Zeichen, da Ascii + Farbe
    private static final int vidMemRowCount=0x50;
    private static final int vidMemRowByteCount=vidMemRowCount*2;
    private static final int vidMemColumnCount= 0x19;
    private static final int vidMemStartAddress =0xB8000;
    private static final int vidMemEndAddress = vidMemStartAddress +0xFA0;
    private static int currentColor = Colors.defaultColor;
    private static int vidMemCurr = vidMemStartAddress;

    public static void main() {
        clear();
        print("Hallo Welt");
        printNL();
        print("Hallo Welt, aber in der zweiten Zeile");
        printNL();
        setColor(Colors.font_green,Colors.back_black, false);
        print("Hallo Welt, aber in rot(fast)");
        printNL();
        setColor(1337,69420,true);
        print("Hallo Welt, aber mit ungueltiger Farbdefinition");
        while(true);
    }

    private static void setColor(int font, int back, boolean blinking){
        currentColor = font|back;
        if(currentColor < 0x00 || currentColor > 0xFF){
            currentColor = Colors.defaultColor;
        }
        if(blinking)
            currentColor = currentColor | Colors.back_blinking;
    }

    public static void print(String str) {
        for (int i=0; i<str.length(); i++)
            print(str.charAt(i),currentColor);
    }

    public static void print(char c, int color) {
        MAGIC.wMem8(vidMemCurr++, (byte)c);
        MAGIC.wMem8(vidMemCurr++, (byte)color);
    }

    //Newline Test
    public static void printNL(){
        //relativen Platz in der Zeile holen.
        int temp = (vidMemCurr-vidMemStartAddress)%vidMemRowByteCount;
        //restlichen Bytes zum auffüllen der Zeile bestimmen.
        int temp2 = vidMemRowByteCount-temp;
        vidMemCurr += temp2;
    }

    public static void clear(){
        vidMemCurr = vidMemStartAddress;
        int i = vidMemStartAddress;
        while(i < vidMemEndAddress){
            MAGIC.wMem8(i++,(byte)0x20);
            MAGIC.wMem8(i++,(byte)currentColor);
        }
    }
}