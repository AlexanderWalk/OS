package Devices;

public class KeyboardEvent {
    //Modifier
    final boolean alt, shift, control;
    //Toggles
    final boolean capslock, numlock, scrollock;
    final int keyCode;

    public KeyboardEvent(boolean capslock, boolean alt, boolean shift, boolean control, boolean numlock,
                         boolean scrollock, int keyCode) {
        this.capslock = capslock;
        this.alt = alt;
        this.shift = shift;
        this.control = control;
        this.numlock = numlock;
        this.scrollock = scrollock;
        this.keyCode = keyCode;
    }
}
