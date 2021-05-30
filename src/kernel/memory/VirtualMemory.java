package kernel.memory;

import devices.StaticV24;

public class VirtualMemory {
    private static final int pageCountPerTable = 1024;
    private static final int pageTableCount = 1024;
    private static final int entrySize = 4;
    private static final int pagedirStartAddr = 0x9000;//4k Alligned
    private static Object pageTable;

    private static void setCR3(int addr) {
        MAGIC.inline(0x8B, 0x45);
        MAGIC.inlineOffset(1, addr); //mov eax,[ebp+8]
        MAGIC.inline(0x0F, 0x22, 0xD8); //mov cr3,eax
    }

    public static void enableVirtualMemory() {
        writePageDirectoryAndTable();
        setCR3(pagedirStartAddr);
        MAGIC.inline(0x0F, 0x20, 0xC0); //mov eax,cr0
        MAGIC.inline(0x0D, 0x00, 0x00, 0x01, 0x80); //or eax,0x80010000
        MAGIC.inline(0x0F, 0x22, 0xC0); //mov cr0,eax
    }

    public static int getCR2() {
        int cr2=0;
        MAGIC.inline(0x0F, 0x20, 0xD0); //mov e/rax,cr2
        MAGIC.inline(0x89, 0x45);
        MAGIC.inlineOffset(1, cr2); //mov [ebp-4],eax
        return cr2;
    }

    private static void writePageDirectoryAndTable() {
        //find last image Object
        Object obj = MAGIC.cast2Obj(MAGIC.rMem32(MAGIC.imageBase+16));
        while(obj._r_next!=null){
            obj=obj._r_next;
        }

        //allign Address to 4k
        int firstFreeAddr = MAGIC.cast2Ref(obj)+obj._r_scalarSize;
        int pageTableObjectAddr = (firstFreeAddr+0xFFF)&~0xFFF;
        pageTableObjectAddr-=MAGIC.getInstScalarSize("Object");
        if(pageTableObjectAddr - MAGIC.getInstRelocEntries("Object")*4 <=firstFreeAddr){
            pageTableObjectAddr+=0x1000;
        }
        int pageTableScalarSize = MAGIC.getInstScalarSize("Object")+pageTableCount * pageCountPerTable * entrySize;

        for(int i=pageTableObjectAddr-MAGIC.getInstRelocEntries("Object"); i<pageTableObjectAddr+pageTableScalarSize; i++){
            MAGIC.wMem8(i,(byte)0);
        }
        pageTable = MAGIC.cast2Obj(pageTableObjectAddr);
        MAGIC.assign(obj._r_next,pageTable);
        MAGIC.assign(pageTable._r_scalarSize,pageTableScalarSize);
        MAGIC.assign(pageTable._r_relocEntries,MAGIC.getInstRelocEntries("Object"));
        MAGIC.assign(pageTable._r_type,MAGIC.clssDesc("Object"));

        int pageTableAddr = pageTableObjectAddr+MAGIC.getInstScalarSize("Object");
        for(int i=0;i<pageTableCount;i++){
            //set write and present for every PageTable
            MAGIC.wMem32(pagedirStartAddr + i * entrySize,((i<<12)+pageTableAddr)|0x3);
        }

        //First and Last page not present and not writeable
        MAGIC.wMem32(pageTableObjectAddr, 0);
        MAGIC.wMem32(pageTableObjectAddr+(pageTableCount*pageCountPerTable-1)*entrySize, 0);
        //present and writeable for every other page
        for (int i = 1; i < pageTableCount*pageCountPerTable-1; i++) {
            MAGIC.wMem32(i*entrySize+pageTableAddr, (i<<12)|0x3);
        }
    }
}
