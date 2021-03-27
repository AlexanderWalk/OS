package java.lang;
import rte.SClassDesc;
public class Object {
  public final SClassDesc _r_type=null;
  public final Object _r_next=null;
  public final int _r_relocEntries=0, _r_scalarSize=0;
}
package java.lang;
public class String {
  private char[] value;
  private int count;
  @SJC.Inline
  public int length() {
    return count;
  }
  @SJC.Inline
  public char charAt(int i) {
    return value[i];
  }
}
package rte;
public class SArray {
  public final int length=0, _r_dim=0, _r_stdType=0;
  public final Object _r_unitType=null;
}
package rte;
public class SClassDesc {
  public SClassDesc parent;
  public SIntfMap implementations;
}
package rte;
public class SIntfDesc {
}
package rte;
public class SIntfMap {
  public SIntfDesc owner;
  public SIntfMap next;
}
package rte;
public class SMthdBlock {
}
package rte;
public class DynamicRuntime {
  public static Object newInstance(int scalarSize, int relocEntries, SClassDesc type) {
    //Pointer auf erstes Objekt im Speicher
    int firstObjectPtr = MAGIC.imageBase+16;
    Object object = MAGIC.cast2Obj(firstObjectPtr);
    //Zum letzten Objekt springen
    while(object._r_next != null) {
      object = object._r_next;
    }
    //Referenz auf Objekt, Anzahl Skalare aufrechnen und Allign auf 4 Bytes
    int newObjectPointer = Magic.cast2Ref(object);
    newObjectPointer +=object._r_scalarSize;
    newObjectPointer = (newObjectPointer + 3)&~3;
    if(objPtr % 4 != 0){
      objPtr += 4 - (objPtr % 4);
    }
    //Von Java geforderte Null-Initialisierung
    //relocEntries*4, da Anzahl Pointer à 4 Bytes
    int newObjectEndAdress = newObjectPointer + (scalarSize + relocEntries*4)+4;
    for(int i = newObjectPointer; i<newObjectEndAdress;i++) {
      MAGIC.wMem32(i, 0);
    }
    //Platz für die relocEntries machen
    newObjectPointer+=relocEntries*4;
    objPtr += rlE*4;//offset object pointer to make space for the relocs
    Object newObject = MAGIC.cast2Obj(newObjectPointer);//we now have the correct address for the new object in objPtr
    MAGIC.assign(ob._r_next, newObject);
    MAGIC.assign(newObject._r_relocEntries, relocEntries);
    MAGIC.assign(newObject._r_scalarSize, scalarSize);
    MAGIC.assign(newObject._r_type, type);
    return newObject;
  }
  public static SArray newArray(int length, int arrDim, int entrySize,
      int stdType, Object unitType) { while(true); }
  public static void newMultArray(SArray[] parent, int curLevel,
      int destLevel, int length, int arrDim, int entrySize, int stdType,
      Object unitType) { while(true); }
  public static boolean isInstance(Object o, SClassDesc dest,
      boolean asCast) { while(true); }
  public static SIntfMap isImplementation(Object o, SIntfDesc dest,
      boolean asCast) { while(true); }
  public static boolean isArray(SArray o, int stdType,
      Object unitType, int arrDim, boolean asCast) { while(true); }
  public static void checkArrayStore(Object dest,
      SArray newEntry) { while(true); }
}
