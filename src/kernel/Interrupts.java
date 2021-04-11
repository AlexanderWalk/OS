package kernel;

import Output.Console;

public class Interrupts {
    private static final int MASTER = 0x20, SLAVE = 0xA0;
    private static boolean IsInterruptFlagActive = false;
    //Stack frei ab 0x07E00
    private static final int IDTStartAdress = 0x07E00;
    private static final int bytesPerEntry = 8;
    private static final int entryCount = 48;

    public static void registerHandlers() {
        int i=0;
        int genericHandlerOffset = MAGIC.mthdOff("Interrupts","genericHandler");
        int genericHandlerwParamOffset = MAGIC.mthdOff("Interrupts","genericHandlerWithParameter");
        int hardwareInterruptOffset = MAGIC.mthdOff("Interrupts","genericHardwareInterruptHandler");
        int classRef = MAGIC.cast2Ref(MAGIC.clssDesc("Interrupts"));
        setIDTRegister();
        while(i<=0x07){
            createIDTEntry(i,MAGIC.rMem32(classRef+genericHandlerOffset)+MAGIC.getCodeOff());
            i++;
        }
        //Double Fault
        createIDTEntry(i,MAGIC.rMem32(classRef+genericHandlerwParamOffset)+MAGIC.getCodeOff());
        i++;
        //reserviert
        while(i<=0x0C){
            createIDTEntry(i,MAGIC.rMem32(classRef+genericHandlerOffset)+MAGIC.getCodeOff());
            i++;
        }
        //General protection Error und Page fault
        while(i<0x0E){
            createIDTEntry(i,MAGIC.rMem32(classRef+genericHandlerwParamOffset)+MAGIC.getCodeOff());
            i++;
        }
        //0x0F bis 0x1F reserviert
        while(i<=0x1F){
            createIDTEntry(i,MAGIC.rMem32(classRef+genericHandlerOffset)+MAGIC.getCodeOff());
            i++;
        }
        //TODO:IRQ0-15
        while(i<=0x2F){
            createIDTEntry(i,MAGIC.rMem32(classRef+hardwareInterruptOffset)+MAGIC.getCodeOff());
            i++;
        }
    }

    public static void setIDTRegister() {
        //Table-Anfang und limit
        int tableLimit =bytesPerEntry*entryCount-1;
        long tmp=(((long) IDTStartAdress)<<16)|(long)tableLimit;
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tmp);// lidt [ebp-0x08/tmp] - Phase 3 Seite 2
    }
    public static void setIDTRegisterRM() {
        //RealMode - 1023
        long tableLimit =1023;
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tableLimit);// lidt [ebp-0x08/tmp] - Phase 3 Seite 2
    }

    //IDT:
    //Werte laut Vorlesung:
    //Offset 0-15 -> klar
    // Segmentselector: "Da muss ne 8 rein"
    //Offset 16-31 -> klar
    // Flags etc laut Vorlesung: 1000111000000000 -> = 0x8E00
    public static void createIDTEntry(int  entryNum, int handler){
        int entryAddr = IDTStartAdress + entryNum*8;
        MAGIC.wMem32(entryAddr, handler&0x0000FFFF|(8<<16));
        MAGIC.wMem32(entryAddr+4, 0x00008E00|handler&0xFFFF0000);
    }

    //STI
    static void SetInterruptFlag(){
        if(!IsInterruptFlagActive){
            IsInterruptFlagActive = true;
            MAGIC.inline(0xFB);
        }
    }

    //CLI
    static void ClearInterruptFlag(){
        if(IsInterruptFlagActive){
            IsInterruptFlagActive = false;
            MAGIC.inline(0xFA);
        }
    }

    @SJC.Interrupt
    private static void genericHandler(){
        //TODO
        Console.directDebugPrint("handled.");
    }

    @SJC.Interrupt
    private static void genericHandlerWithParameter(int param){
        //TODO
        Console.directDebugPrint("handled with parameter");
    }

    //Todo: In eigene Klasse - Interrupt für Timer und Tastatur erstellen

    @SJC.Interrupt
    public static void genericHardwareInterruptHandler(){
        Console.directDebugPrint("Device handled");
    }

    private static void hardwareEOI(int port){
        MAGIC.wIOs8(port,(byte)0x20);
    }

    public static void initPic() {
        programmChip(MASTER, 0x20, 0x04); //init offset and slave config of master
        programmChip(SLAVE, 0x28, 0x02); //init offset and slave config of slave
    }

    private static void programmChip(int port, int offset, int icw3) {
        MAGIC.wIOs8(port++, (byte)0x11); // ICW1
        MAGIC.wIOs8(port, (byte)offset); // ICW2
        MAGIC.wIOs8(port, (byte)icw3); // ICW3
        MAGIC.wIOs8(port, (byte)0x01); // ICW4
    }
}