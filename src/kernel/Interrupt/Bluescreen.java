package kernel.Interrupt;

import output.Console.DebugConsole;

public class Bluescreen {

    static void createBluescreen(String callerName, int ebp){
        DebugConsole.clearConsoleDebug();
        DebugConsole.debugPrintln("Oh no! A Bluescreen. Anyway");
        DebugConsole.debugPrint("Reason: ");
        DebugConsole.debugPrintln(callerName);
        DebugConsole.debugPrintln("Stacktrace:");
        StackTrace.printStacktrace(ebp);
    }
}
