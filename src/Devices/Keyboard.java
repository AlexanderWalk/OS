package Devices;

import DataStructures.ByteRingBuffer;

//TODO: Dynamic Device
public class Keyboard {
    private static ByteRingBuffer inBuffer;
    private static KeyboardEventBuffer outBuffer;
    private static KeyboardLayout keyboardLayout;

    //modifier
    private static boolean shift, control, alt;
    //toggles
    private static boolean capslock, numlock, scrolllock;
    //single pressed modifier
    private static boolean singleShift, singleControl, singleAlt;
    //locking toggles until break
    private static boolean capslock_Locked, numlock_locked, scrolllock_locked;

    static{
        inBuffer = new ByteRingBuffer();
        outBuffer = new KeyboardEventBuffer();
        keyboardLayout = new QwertzLayout();
    }

    public static void setKeyboardLayout(KeyboardLayout layout) {
        keyboardLayout = layout;
    }

    //store Byte from Keyboard Interrupt
    public static void storeByte(){
        byte b = MAGIC.rIOs8(0x60);
        //ignore if code >>
        if((b&0xFF)>=0xE2)
            return;
        inBuffer.writeByte(b);
    }


    public static void processInputBuffer() {
        while(inBuffer.canRead()) {
            int bitmask = 0;
            byte b = inBuffer.readByte();
            bitmask = (bitmask | (b&0xFF));
            // 2 Byte Scancode
            if ((b&0xFF)==0xE0) {
                byte b2 = inBuffer.readByte();
                bitmask = (bitmask << 8) | (b2&0xFF);
            } else
                //3 Byte Scancode
                if ((b&0xFF)==0xE1) {
                byte b2 = inBuffer.readByte();
                byte b3 = inBuffer.readByte();
                bitmask = ((bitmask << 16) | (b2&0xFF) << 8) | (b3&0xFF);
            }
            int key=0;
            //breakkey, if highest bit is set
            boolean breakKey = (bitmask & 0xFF) >= 0x80;
            //get key
            int physKey = bitmask & 0xFFFFFF7F;
            key = keyboardLayout.translatePhysToLogicalKey(physKey, shift, capslock, alt);
            switch (key) {
                //modifier
                case Key.LEFT_SHIFT:
                case Key.RIGHT_SHIFT:
                    if (breakKey) {
                        shift = false;
                        if (singleShift) {
                            writeEvent(key);
                            singleShift = false;
                        }
                    } else {
                        shift = true;
                        singleShift = true;
                    }
                    break;
                case Key.LEFT_CONTROL:
                case Key.RIGHT_CONTROL:
                    if (breakKey) {
                        control = false;
                        if (singleControl) {
                            writeEvent(key);
                            singleControl = false;
                        }
                    } else {
                        control = true;
                        singleControl = true;
                    }
                    break;
                case Key.LEFT_ALT:
                case Key.RIGHT_ALT:
                    if (breakKey) {
                        alt = false;
                        if (singleAlt) {
                            writeEvent(key);
                            singleAlt = false;
                        }
                    } else {
                        alt = true;
                        singleAlt = true;
                    }
                    break;

                //toggles
                case Key.CAPSLOCK:
                    if (!capslock_Locked) {
                        capslock = !capslock;
                        capslock_Locked = true;
                    }
                    if (breakKey) {
                        capslock_Locked = false;
                    }
                    break;
                case Key.SCROLL_LOCK:
                    if(!scrolllock_locked) {
                        scrolllock = !scrolllock;
                        scrolllock_locked = true;
                    }
                    if (breakKey) {
                        scrolllock_locked = false;
                    }
                    break;
                case Key.NUMLOCK:
                    if (!numlock_locked) {
                        numlock = !numlock;
                        numlock_locked = true;
                    }
                    if (breakKey) {
                        numlock_locked = false;
                    }
                    break;

                //keys
                default:
                    if (!breakKey) {
                        writeEvent(key);
                        //no singlepressed toggles because another key has been pressed
                        singleAlt = singleControl = singleShift = false;
                    }
            }
        }
    }

    private static void writeEvent(int key){
        outBuffer.writeEvent(new KeyboardEvent(capslock,alt,shift,control,numlock,scrolllock,key));
    }

    //True, if an event can be read from the buffer
    public static boolean eventAvailable(){
        return outBuffer.canRead();
    }

    public static KeyboardEvent getKeyboardEvent() {
        return outBuffer.readEvent();
    }

    public static void clearBuffers() {
        inBuffer.clearBuffer();
        outBuffer.clearBuffer();
    }
}
