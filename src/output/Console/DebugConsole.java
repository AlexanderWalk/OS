package output.Console;

import output.VideoMem.VideoMemory;
import output.colors.StaticColors;

public class DebugConsole {

    public static void clearConsoleDebug(){
        VideoMemory.clearMemory();
    }

    public static void directDebugPrint(String string) {
        if(string!=null)
            for (int i=0; i<string.length(); i++)
                directDebugPrint(string.charAt(i));
    }

    public static void directDebugPrint(char c) {
        VideoMemory.writeChar(c, StaticColors.back_black|StaticColors.font_grey);
    }
}
