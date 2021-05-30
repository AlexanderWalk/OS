package kernel;

import devices.StaticV24;
import rte.SClassDesc;
import rte.SMthdBlock;
import rte.SPackage;

public class SymbolInformation {

    public static String[] getFullMethodInfo(int address){
        int count = 2, packCount=1;
        SMthdBlock methodBlock = findMethod(address);
        if(methodBlock==null){
            String[] noInfo = new String[1];
            noInfo[0]="No methodinfo found";
            return noInfo;
        }

        //Find all outer packages
        SPackage outestPack = methodBlock.owner.pack;
        while(true){
            if(outestPack.outer!=null){
                outestPack=outestPack.outer;
                packCount++;
            }else{
                //ignore outest package "<null>"?
                packCount--;
                break;
            }
        }
        count+=packCount;

        //temporary save packages
        SPackage pack = methodBlock.owner.pack;
        SPackage[] temp = new SPackage[packCount];
        for(int i=packCount-1; i>=0;i--){
            temp[i]=pack;
            pack=pack.outer;
        }

        String[] methodInfo = new String[count];
        methodInfo[count-1]=getMethodName(methodBlock);
        methodInfo[count-2]=getClassName(methodBlock);
        for(int j=0;j<temp.length;j++){
            methodInfo[j]=getPackageName(temp[j]);
        }
        return methodInfo;
    }

    public static String getMethodName(SMthdBlock methodBlock){
        if(methodBlock==null){
            return "NoMethodFound";
        }
        return methodBlock.namePar;
    }

    public static String getClassName(SMthdBlock methodBlock){
        if(methodBlock==null){
            return "";
        }
        return methodBlock.owner.name;
    }

    public static String getPackageName(SMthdBlock methodBlock) {
        if (methodBlock == null) {
            return "";
        }
        return methodBlock.owner.pack.name;
    }

    private static String getPackageName(SPackage pack) {
        if (pack == null) {
            return "";
        }
        return pack.name;
    }

    public static SMthdBlock findMethod(int address){
        return searchPackages(address,SPackage.root);
    }

    private static SMthdBlock searchPackages(int address, SPackage pack){
        //sanity check - should never be true
        if(pack==null){
            return null;
        }
        //visit Subpackages first
        if(pack.subPacks!=null){
            SMthdBlock method = searchPackages(address,pack.subPacks);
                if(method!=null){
                    return method;
                }
        }
        //Search Classes in curr Package
        SMthdBlock method = searchClasses(address,pack.units);
        if(method!=null){
            return method;
        }
        //Search Classes in next Package
        if(pack.nextPack != null) {
            return searchPackages(address, pack.nextPack);
        }
        //No Method found
        return null;
    }

    private static SMthdBlock searchClasses(int address, SClassDesc classDesc){
        while(classDesc!=null){
            //Skip if Method cant be in current Class
            if(classDesc.nextUnit!=null){
                if(classDesc.nextUnit.mthds!=null){
                    int addrOfNextUnit = MAGIC.cast2Ref(classDesc.nextUnit.mthds);
                    if(addrOfNextUnit<=address){
                        classDesc=classDesc.nextUnit;
                        continue;
                    }
                }
            }
            //Search for Method
            if(classDesc.mthds!=null){
                SMthdBlock method = searchMethods(address,classDesc.mthds);
                if(method!=null){
                    return method;
                }
            }
            classDesc=classDesc.nextUnit;
        }
        return null;
    }

   private static SMthdBlock searchMethods(int address, SMthdBlock method){
        while(method!=null){
            int methodAddress = MAGIC.cast2Ref(method);
            if(address >= methodAddress && address <= (methodAddress+method._r_scalarSize)){
                return method;
            }
            method=method.nextMthd;
        }
        return null;
    }
}
