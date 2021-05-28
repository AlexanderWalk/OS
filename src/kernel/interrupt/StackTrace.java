package kernel.interrupt;

import output.console.DebugConsole;

public class StackTrace {
    private static final int stackStart=0x9BFFC;
    private static final int interreuptEIPOffset = 9*4;
    private static final int EIPOffset = 4;

    static void printStacktrace(int EBP){
        //old eip in interrupt Stackframe is 9 addresses away -> 9*4=36 Bytes
        int EIP = MAGIC.rMem32(EBP+interreuptEIPOffset);
        //iterate through EBP until first stackframe reached
        do {
            DebugConsole.debugPrint("EBP: ");
            DebugConsole.debugPrintHex(EBP);
            DebugConsole.debugPrint(" | EIP: ");
            DebugConsole.debugPrintlnHex(EIP);
            //EBP points to base of previous Stackframe
            EBP= MAGIC.rMem32(EBP);
            //old eip in normal Method is 1 address away -> 1*4 Bytes
            EIP= MAGIC.rMem32(EBP+EIPOffset);
            //TODO oldEBP < EBP
        } while(EBP<=stackStart && EBP>0 );
    }
}
