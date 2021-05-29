package kernel.interrupt;

import output.console.DebugConsole;

public class Bluescreen {

    static void createBluescreen(String callerName){
        DebugConsole.clearConsoleDebug();
        DebugConsole.debugPrintln("Oh no! A Bluescreen. Anyway");
        DebugConsole.debugPrint("Reason: ");
        DebugConsole.debugPrint(callerName);
    }

    static void printStackTrace(int ebp){
        DebugConsole.debugPrintln();
        DebugConsole.debugPrintln("Stacktrace:");
        StackTrace.printStacktrace(ebp);
    }

    static void pageFaultError(int CR2, int errorCode){
        DebugConsole.debugPrintln();
        DebugConsole.debugPrintln("Page fault information:");
        DebugConsole.debugPrint("ErrorCode: ");
        DebugConsole.debugPrintlnHex(errorCode);
        DebugConsole.debugPrint("Pageaddress: ");
        DebugConsole.debugPrintlnHex(CR2);
        DebugConsole.debugPrint("    ");
        if((errorCode&0x1)>0) {
            DebugConsole.debugPrint("Page-protecion violation, ");
        }else{
            DebugConsole.debugPrint("Page not present, ");
        }
        if((errorCode&0x2)>0) {
            DebugConsole.debugPrint("Write-access violation");
        }else{
            DebugConsole.debugPrint("Read-access violation");
        }
    }
}
