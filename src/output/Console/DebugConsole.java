package output.Console;

import output.VideoMem.VideoMemory;
import output.colors.StaticColors;

public class DebugConsole {
    private static final int defaultBack=StaticColors.back_blue;
    private static final int defaultFont=StaticColors.font_white;

    public static void clearConsoleDebug(){
        VideoMemory.clearMemory(defaultBack|defaultFont);
    }

    public static void debugPrint(String s) {
        if(s!=null)
            for (int i=0; i<s.length(); i++)
                debugPrint(s.charAt(i));
    }

    public static void debugPrint(char c) {
        VideoMemory.writeChar(c, defaultBack|defaultFont);
    }

    public static void debugPrintln(){
        VideoMemory.newLine(defaultBack|defaultFont);
    }

    public static void debugPrintln(char c){
        debugPrint(c);
        debugPrintln();
    }

    public static void debugPrintln(String s){
        debugPrint(s);
        debugPrintln();
    }
}
