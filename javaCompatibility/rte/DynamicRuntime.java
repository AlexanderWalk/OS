package rte;

import devices.StaticV24;
import kernel.BIOS;

public class DynamicRuntime {
  private static SEmptyObject firstEmptyObj;
  private static SEmptyObject lastEmptyObj;
  private static final int imageBaseAddr = MAGIC.imageBase+16;
  private static Object firstImageObj;
  private static final int freeMemoryType = 1;
  private static final int memBuffTypeOffset = 16;
  private static final int memBuffLengthOffset = 8;
  private static Object lastRootObject;

  private static void setLastRootObject(){
    firstImageObj = MAGIC.cast2Obj(MAGIC.rMem32(imageBaseAddr));
    Object obj = firstImageObj;
    while(obj._r_next != null) {
      obj = obj._r_next;
    }
    lastRootObject = obj;
  }

  public static void initEmptyObjects(){
    setLastRootObject();
    int continuation = 0;
    do {
      BIOS.memoryMap(continuation);
      //get new continuationIndex from EBX
      continuation = BIOS.regs.EBX;
      int type = MAGIC.rMem32(BIOS.memBuffAddress + memBuffTypeOffset);
      if(type == freeMemoryType){
        //free
        //alloc empty object
        int segmentBaseAddress = (int)MAGIC.rMem64(BIOS.memBuffAddress);
        int segmentLength = (int)MAGIC.rMem64(BIOS.memBuffAddress + memBuffLengthOffset);
        if(segmentBaseAddress> BIOS.BIOSAddressMax){
          int maxAddr = segmentBaseAddress+segmentLength-1;
          //2 Cases: imagebase in same semgment as first empty object
          //  r_next to last initialized object
          //Or imagebase is in another segment
          if(imageBaseAddr >= segmentBaseAddress && imageBaseAddr <= maxAddr){ //imagebase in segment


            int newObjectPointer = MAGIC.cast2Ref(lastRootObject)+lastRootObject._r_scalarSize;
            for(int i=newObjectPointer; i<newObjectPointer+MAGIC.getInstScalarSize("SEmptyObject"); i++){
              MAGIC.wMem8(i,(byte)0);
            }
            //pointer + 12 Bytes - 3 Refs - Type, next, nextEmpty
            int relocBytes = MAGIC.getInstRelocEntries("SEmptyObject")*MAGIC.ptrSize;
            newObjectPointer+=relocBytes;
            Object emptyObject = MAGIC.cast2Obj(newObjectPointer);

            MAGIC.assign(emptyObject._r_relocEntries,MAGIC.getInstRelocEntries("SEmptyObject"));
            MAGIC.assign(lastRootObject._r_next, emptyObject);
            MAGIC.assign(emptyObject._r_type,MAGIC.clssDesc("SEmptyObject"));
            MAGIC.assign(emptyObject._r_scalarSize,maxAddr-newObjectPointer+1);
            if(firstEmptyObj == null){
              firstEmptyObj = (SEmptyObject) emptyObject;
            }else{
              //if there was already an empty object before imagebase
              SEmptyObject temp = firstEmptyObj;
              while(temp.nextEmptyObject!=null){
                temp = temp.nextEmptyObject;
              }
              MAGIC.assign(temp.nextEmptyObject, (SEmptyObject) emptyObject);
              MAGIC.assign(((SEmptyObject)emptyObject).prevEmptyObject, temp);
              MAGIC.assign(temp._r_next, firstImageObj);
            }
            lastEmptyObj=(SEmptyObject)emptyObject;
          }else{ //imagebase not in segment
            //Startaddress of new Object is at the start of the segment
            int newObjectPointer = segmentBaseAddress;
            for(int i=newObjectPointer; i<newObjectPointer+MAGIC.getInstScalarSize("SEmptyObject"); i++){
              MAGIC.wMem8(i,(byte)0);
            }
            int relocBytes = MAGIC.getInstRelocEntries("SEmptyObject")*MAGIC.ptrSize;
            newObjectPointer+=relocBytes;
            Object emptyObject = MAGIC.cast2Obj(newObjectPointer);
            MAGIC.assign(emptyObject._r_relocEntries,MAGIC.getInstRelocEntries("SEmptyObject"));
            MAGIC.assign(emptyObject._r_type,MAGIC.clssDesc("SEmptyObject"));
            MAGIC.assign(emptyObject._r_scalarSize,maxAddr-newObjectPointer+1);
            //If there is an emptyObject already, set this one to next of previous emptyObject
            //Else set it to imagebase object
            if(firstEmptyObj == null){
              firstEmptyObj=(SEmptyObject) emptyObject;
              Object object = firstImageObj;
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
              MAGIC.assign(((SEmptyObject)emptyObject).prevEmptyObject, temp);
              MAGIC.assign(temp._r_next, emptyObject);
            }
            lastEmptyObj=(SEmptyObject)emptyObject;
          }
        }
      } else{
        //reserved
      }
    } while (continuation != 0);
  }
  
  public static Object newInstance(int scalarSize, int relocEntries, SClassDesc type) {
    boolean perfectfit = false;
    SEmptyObject emptyObject = firstEmptyObj;
    scalarSize = ((scalarSize + 3)&~3);
    //TODO: warum war hier +16 in newObjectLength?
    int newObjectlenght = scalarSize + relocEntries*MAGIC.ptrSize;
    while(true){
      if ((emptyObject._r_scalarSize - MAGIC.getInstScalarSize("SEmptyObject")) >= newObjectlenght){
        break;
      }else if((emptyObject._r_scalarSize+emptyObject._r_relocEntries*MAGIC.ptrSize) == newObjectlenght){
        perfectfit=true;
        break;
      }
      if(emptyObject.nextEmptyObject==null){
        MAGIC.inline(0xCC);
      }
      emptyObject = emptyObject.nextEmptyObject;
    }
    //get new Address and set new Scalarsize of EmptyObject
    int newObjectAdress = MAGIC.cast2Ref(emptyObject) + emptyObject._r_scalarSize - scalarSize;
    Object nextObject = emptyObject._r_next;
    Object prevObject = firstImageObj;
    while(prevObject._r_next!=emptyObject){
      prevObject = prevObject._r_next;
    }
    if(!perfectfit){
      MAGIC.assign(emptyObject._r_scalarSize, emptyObject._r_scalarSize-newObjectlenght);
    }else{
      if(emptyObject.prevEmptyObject!=null)
        MAGIC.assign(emptyObject.prevEmptyObject.nextEmptyObject, emptyObject.nextEmptyObject);
      if(emptyObject.nextEmptyObject!=null)
        MAGIC.assign(emptyObject.nextEmptyObject.prevEmptyObject, emptyObject.prevEmptyObject);
    }

    int newObjectStartAddress = newObjectAdress-(relocEntries*MAGIC.ptrSize);
    int newObjectEndAdress = newObjectAdress + scalarSize;
    for(int i=newObjectStartAddress;i< newObjectEndAdress; i++){
      MAGIC.wMem8(i,(byte)0);
    }

    Object newObject = MAGIC.cast2Obj(newObjectAdress);
    MAGIC.assign(newObject._r_next, nextObject);
    MAGIC.assign(newObject._r_relocEntries, relocEntries);
    MAGIC.assign(newObject._r_scalarSize, scalarSize);
    MAGIC.assign(newObject._r_type, type);

    if(!perfectfit)
      MAGIC.assign(emptyObject._r_next, newObject);
    else
      MAGIC.assign(prevObject._r_next, newObject);


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

  public static void garbageCollection(){
    mark();
    sweep();
  }

  private static void mark(){
    Object obj = firstImageObj;
    markRelocs(obj);
    do{
      obj = obj._r_next;
      markRelocs(obj);
    }while(obj!=lastRootObject);
  }

  private static void markRelocs(Object obj){
    if(obj==null)return;
    if(obj.gcMark==1)return;
    MAGIC.assign(obj.gcMark,1);
    int baseAddr = MAGIC.cast2Ref(obj);
    baseAddr -=4;
    for(int i=0;i<obj._r_relocEntries;i++){
      int addr = baseAddr-i*MAGIC.ptrSize;
      Object o = MAGIC.cast2Obj(MAGIC.rMem32(addr));
      if(!(isInstance(o,(SClassDesc) MAGIC.clssDesc("Object"),false)||o==null||
              !isInstance(o,(SClassDesc) MAGIC.clssDesc("SClassDesc"),false)))
        StaticV24.print("Kein Object");
      if(o==obj._r_next||o==obj._r_type)
        continue;
      markRelocs(o);
    }
  }

  private static void sweep(){
    Object obj = firstImageObj;
    Object prevObj = null;
    Object nextObj = obj._r_next;
    SEmptyObject prevEmptyObject = null;
    while(true){
      if(isInstance(obj,(SClassDesc) MAGIC.clssDesc("SEmptyObject"),false)){
        prevEmptyObject=(SEmptyObject) obj;
      } else if(obj.gcMark==0){
          //del Object, create new emptyObject
          //Object doesnt fit
          //TODO: Object is smaller than EmptyObject - fix
          if (obj._r_scalarSize + obj._r_relocEntries * MAGIC.ptrSize < MAGIC.getInstScalarSize("SEmptyObject")
                  + MAGIC.getInstRelocEntries("SEmptyObject") * MAGIC.ptrSize) {
          } else {
            StaticV24.print("Found Object to delete");
              //Object fits
            int objectAddr = MAGIC.cast2Ref(obj);
            int objectMemory = obj._r_relocEntries * MAGIC.ptrSize + obj._r_scalarSize;
            objectAddr -= obj._r_relocEntries * MAGIC.ptrSize;
            int emptyObjectAddr = objectAddr + MAGIC.getInstRelocEntries("SEmptyObject") * MAGIC.ptrSize;
            for (int i = objectAddr; i < emptyObjectAddr + MAGIC.getInstScalarSize("SEmptyObject"); i++) {
              MAGIC.wMem8(i, (byte) 0);
            }
            Object emptyObj = MAGIC.cast2Obj(emptyObjectAddr);
            MAGIC.assign(emptyObj._r_type, MAGIC.clssDesc("SEmptyObject"));
            MAGIC.assign(emptyObj._r_relocEntries, MAGIC.getInstRelocEntries("SEmptyObject"));
            MAGIC.assign(emptyObj._r_scalarSize, objectMemory - MAGIC.getInstRelocEntries("SEmptyObject") * MAGIC.ptrSize);
            if (prevObj != null)
              MAGIC.assign(prevObj._r_next, emptyObj);
            MAGIC.assign(emptyObj._r_next, nextObj);
            //Pointers for empty Objects
            SEmptyObject emptyObject = (SEmptyObject) emptyObj;
            MAGIC.assign(emptyObject.prevEmptyObject, prevEmptyObject);
            if (prevEmptyObject != null) {
              MAGIC.assign(emptyObject.nextEmptyObject, prevEmptyObject.nextEmptyObject);
              MAGIC.assign(prevEmptyObject.nextEmptyObject, emptyObject);
              if(emptyObject.nextEmptyObject!=null)
                MAGIC.assign(emptyObject.nextEmptyObject.prevEmptyObject, emptyObject);
            } else {
              MAGIC.assign(emptyObject.nextEmptyObject, firstEmptyObj);
              MAGIC.assign(firstEmptyObj.prevEmptyObject, emptyObject);
              firstEmptyObj = emptyObject;
            }
            prevEmptyObject = emptyObject;
          }
        }
        else{
        MAGIC.assign(obj.gcMark,0);
      }
      prevObj=obj;
      obj = nextObj;
      if(obj==null){
        return;
      }
      nextObj = obj._r_next;
    }
  }
}

