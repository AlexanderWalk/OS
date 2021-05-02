package rte;

import kernel.BIOS;
import output.Console.DebugConsole;

public class DynamicRuntime {
  private static SEmptyObject firstEmptyObj;

  public static void initEmptyObjects(){
    int continuation = 0;
    do {
      BIOS.memoryMap(continuation);
      //get new continuationIndex from EBX
      continuation = BIOS.regs.EBX;
      int type = MAGIC.rMem32(BIOS.memBuffAddress + 16);
      if(type == 1){
        //free
        //alloc empty object
        int segmentBaseAddress = (int)MAGIC.rMem64(BIOS.memBuffAddress);
        int segmentLength = (int)MAGIC.rMem64(BIOS.memBuffAddress + 8);
        if(segmentBaseAddress> BIOS.BIOSAddressMax){
          int maxAddr = segmentBaseAddress+segmentLength-1;
          //2 Cases: imagebase in same semgment as first empty object
          //  r_next to last initialized object
          //Or imagebase is in another segment
          int imageBaseAddr = MAGIC.imageBase+16;
          if(imageBaseAddr >= segmentBaseAddress && imageBaseAddr <= maxAddr){ //imagebase in segment
            Object obj = MAGIC.cast2Obj(MAGIC.imageBase+16);
            while(obj._r_next != null) {
              obj = obj._r_next;
            }
            int newObjectPointer = MAGIC.cast2Ref(obj)+obj._r_scalarSize;
            for(int i=newObjectPointer; i<newObjectPointer+20; i++){
              MAGIC.wMem8(i,(byte)0);
            }
            //pointer + 12 Bytes - 3 Refs - Type, next, nextEmpty
            newObjectPointer+=12;
            Object emptyObject = MAGIC.cast2Obj(newObjectPointer);

            MAGIC.assign(emptyObject._r_relocEntries,3);
            MAGIC.assign(obj._r_next, emptyObject);
            MAGIC.assign(emptyObject._r_type,MAGIC.clssDesc("SEmptyObject"));
            MAGIC.assign(emptyObject._r_scalarSize,maxAddr-newObjectPointer+1);
            if(firstEmptyObj == null){
              firstEmptyObj = (SEmptyObject) emptyObject;
            }else{
              SEmptyObject temp = firstEmptyObj;
              while(temp.nextEmptyObject!=null){
                temp = temp.nextEmptyObject;
              }
              MAGIC.assign(temp.nextEmptyObject, (SEmptyObject) emptyObject);
              MAGIC.assign(temp._r_next, MAGIC.cast2Obj(imageBaseAddr));
            }

          }else{ //imagebase not in segment
            //Startaddress of new Object is at the start of the segment
            int newObjectPointer = segmentBaseAddress;
            for(int i=newObjectPointer; i<newObjectPointer+20; i++){
              MAGIC.wMem8(i,(byte)0);
            }
            newObjectPointer+=12;
            Object emptyObject = MAGIC.cast2Obj(newObjectPointer);
            MAGIC.assign(emptyObject._r_relocEntries,3);
            MAGIC.assign(emptyObject._r_type,MAGIC.clssDesc("SEmptyObject"));
            MAGIC.assign(emptyObject._r_scalarSize,maxAddr-newObjectPointer+1);
            //If there is an emptyObject already, set this one to next of previous emptyObject
            //Else set it to imagebase object
            if(firstEmptyObj == null){
              firstEmptyObj=(SEmptyObject) emptyObject;
              Object object = MAGIC.cast2Obj(imageBaseAddr);
              while(object._r_next != null){
                object = object._r_next;
              }
              MAGIC.assign(object._r_next,emptyObject);
            }else{
              SEmptyObject temp = firstEmptyObj;
              while(firstEmptyObj.nextEmptyObject != null){
                temp = temp.nextEmptyObject;
              }
              MAGIC.assign(temp.nextEmptyObject, (SEmptyObject) emptyObject);
              MAGIC.assign(temp._r_next, emptyObject);
            }
          }
        }
      } else{
        //reserved
      }
    } while (continuation != 0);
  }
  
  public static Object newInstance(int scalarSize, int relocEntries, SClassDesc type) {


    SEmptyObject emptyObject = firstEmptyObj;
    scalarSize = ((scalarSize + 3)&~3);
    int newObjectlenght = scalarSize + relocEntries*4 + 16;
    while(true){
      if ((emptyObject._r_scalarSize - 8) > newObjectlenght){
        break;
      }
      if(emptyObject.nextEmptyObject==null){
        MAGIC.inline(0xCC);
      }
      emptyObject = emptyObject.nextEmptyObject;
    }
    //get new Address and set new Scalarsize of EmptyObject
    int newObjectAdress = MAGIC.cast2Ref(emptyObject) + emptyObject._r_scalarSize - scalarSize;
    //DebugConsole.debugPrintln(emptyObject._r_scalarSize);
    //DebugConsole.debugPrint(scalarSize);
    MAGIC.assign(emptyObject._r_scalarSize, emptyObject._r_scalarSize-newObjectlenght);

    int newObjectStartAddress = newObjectAdress-(relocEntries*4);
    int newObjectEndAdress = newObjectStartAddress + scalarSize + relocEntries*4 +4;
    for(int i=newObjectStartAddress;i< newObjectEndAdress; i++){
      MAGIC.wMem8(i,(byte)0);
    }

    Object newObject = MAGIC.cast2Obj(newObjectAdress);
    MAGIC.assign(newObject._r_next, emptyObject._r_next);
    MAGIC.assign(emptyObject._r_next, newObject);
    MAGIC.assign(newObject._r_relocEntries, relocEntries);
    MAGIC.assign(newObject._r_scalarSize, scalarSize);
    MAGIC.assign(newObject._r_type, type);
    /*
    //Pointer auf erstes Objekt im Speicher
    int firstObjectPtr = MAGIC.imageBase+16;
    Object object = MAGIC.cast2Obj(firstObjectPtr);
    //Zum letzten Objekt springen
    while(object._r_next != null) {
      object = object._r_next;
    }
    //Referenz auf Objekt, Anzahl Skalare aufrechnen und Allign auf 4 Bytes
    int newObjectPointer = MAGIC.cast2Ref(object)+object._r_scalarSize;
    //Allign mit &~ wie in Vorlesung
    newObjectPointer = (newObjectPointer + 3)&~3;
    //Von Java geforderte Null-Initialisierung
    //relocEntries*4, da Anzahl Pointer à 4 Bytes
    int newObjectEndAdress = newObjectPointer + scalarSize + relocEntries*4 +4;
    for(int i = newObjectPointer; i<newObjectEndAdress;i++) {
      MAGIC.wMem8(i, (byte)0);
    }
    //Platz für die relocEntries machen
    newObjectPointer+=relocEntries*4;
    Object newObject = MAGIC.cast2Obj(newObjectPointer);
    MAGIC.assign(object._r_next, newObject);
    MAGIC.assign(newObject._r_relocEntries, relocEntries);
    MAGIC.assign(newObject._r_scalarSize, scalarSize);
    MAGIC.assign(newObject._r_type, type);*/
    return newObject;
  }
  
  public static SArray newArray(int length, int arrDim, int entrySize, int stdType,
      SClassDesc unitType) { //unitType is not for sure of type SClassDesc
    int scS, rlE;
    SArray me;
    
    if (stdType==0 && unitType._r_type!=MAGIC.clssDesc("SClassDesc"))
      MAGIC.inline(0xCC); //check type of unitType, we don't support interface arrays
    scS=MAGIC.getInstScalarSize("SArray");
    rlE=MAGIC.getInstRelocEntries("SArray");
    if (arrDim>1 || entrySize<0) rlE+=length;
    else scS+=length*entrySize;
    me=(SArray)newInstance(scS, rlE, (SClassDesc) MAGIC.clssDesc("SArray"));
    MAGIC.assign(me.length, length);
    MAGIC.assign(me._r_dim, arrDim);
    MAGIC.assign(me._r_stdType, stdType);
    MAGIC.assign(me._r_unitType, unitType);
    return me;
  }
  
  public static void newMultArray(SArray[] parent, int curLevel, int destLevel,
      int length, int arrDim, int entrySize, int stdType, SClassDesc clssType) {
    int i;
    
    if (curLevel+1<destLevel) { //step down one level
      curLevel++;
      for (i=0; i<parent.length; i++) {
        newMultArray((SArray[])((Object)parent[i]), curLevel, destLevel,
            length, arrDim, entrySize, stdType, clssType);
      }
    }
    else { //create the new entries
      destLevel=arrDim-curLevel;
      for (i=0; i<parent.length; i++) {
        parent[i]=newArray(length, destLevel, entrySize, stdType, clssType);
      }
    }
  }
  
  public static boolean isInstance(Object o, SClassDesc dest, boolean asCast) {
    SClassDesc check;
    
    if (o==null) {
      if (asCast) return true; //null matches all
      return false; //null is not an instance
    }
    check=o._r_type;
    while (check!=null) {
      if (check==dest) return true;
      check=check.parent;
    }
    if (asCast) MAGIC.inline(0xCC);
    return false;
  }
  
  public static SIntfMap isImplementation(Object o, SIntfDesc dest, boolean asCast) {
    SIntfMap check;
    
    if (o==null) return null;
    check=o._r_type.implementations;
    while (check!=null) {
      if (check.owner==dest) return check;
      check=check.next;
    }
    if (asCast) MAGIC.inline(0xCC);
    return null;
  }
  
  public static boolean isArray(SArray o, int stdType, SClassDesc clssType, int arrDim, boolean asCast) {
    SClassDesc clss;
    
    //in fact o is of type "Object", _r_type has to be checked below - but this check is faster than "instanceof" and conversion
    if (o==null) {
      if (asCast) return true; //null matches all
      return false; //null is not an instance
    }
    if (o._r_type!=MAGIC.clssDesc("SArray")) { //will never match independently of arrDim
      if (asCast) MAGIC.inline(0xCC);
      return false;
    }
    if (clssType==MAGIC.clssDesc("SArray")) { //special test for arrays
      if (o._r_unitType==MAGIC.clssDesc("SArray")) arrDim--; //an array of SArrays, make next test to ">=" instead of ">"
      if (o._r_dim>arrDim) return true; //at least one level has to be left to have an object of type SArray
      if (asCast) MAGIC.inline(0xCC);
      return false;
    }
    //no specials, check arrDim and check for standard type
    if (o._r_stdType!=stdType || o._r_dim<arrDim) { //check standard types and array dimension
      if (asCast) MAGIC.inline(0xCC);
      return false;
    }
    if (stdType!=0) {
      if (o._r_dim==arrDim) return true; //array of standard-type matching
      if (asCast) MAGIC.inline(0xCC);
      return false;
    }
    //array of objects, make deep-check for class type (PicOS does not support interface arrays)
    if (o._r_unitType._r_type!=MAGIC.clssDesc("SClassDesc")) MAGIC.inline(0xCC);
    clss=o._r_unitType;
    while (clss!=null) {
      if (clss==clssType) return true;
      clss=clss.parent;
    }
    if (asCast) MAGIC.inline(0xCC);
    return false;
  }
  
  public static void checkArrayStore(SArray dest, SArray newEntry) {
    if (dest._r_dim>1) isArray(newEntry, dest._r_stdType, dest._r_unitType, dest._r_dim-1, true);
    else if (dest._r_unitType==null) MAGIC.inline(0xCC);
    else isInstance(newEntry, dest._r_unitType, true);
  }
}
