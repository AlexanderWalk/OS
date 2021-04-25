package Devices.PCI;

public class BaseClassCode {
    public static String codeToString(int code){
        switch(code){
            case 0x01:
                return "Massenspeicher";
            case 0x02:
                return "Netzwerk-Controller";
            case 0x03:
                return "Display-Controller";
            case 0x04:
                return "Multimedia-Gerät";
            case 0x05:
                return "Memory-Controller";
            case 0x06:
                return "Bridge";
            case 0x07:
                return "Communication-Controller";
            case 0x08:
                return "System-Peripherie";
            case 0x09:
                return "Eingabe-Gerät";
            case 0x0A:
                return "Docking-Station";
            case 0x0B:
                return "Processor-Einheit";
            case 0x0C:
                return "Serieller Bus";
            case 0x0D:
                return "Drahtloses Kommunikations-Gerät";
            case 0x0E:
                return "Intelligente Controller";
            case 0x0F:
                return "Satelliten-Kommunikation";
            default:
                return "Altes Geraet";
        }
    }
}
