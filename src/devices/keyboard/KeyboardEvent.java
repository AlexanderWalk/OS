package devices.keyboard;

public class KeyboardEvent {
    //Modifier
    public final boolean alt, shift, control;
    //Toggles
    public final boolean capslock, numlock, scrollock;
    public final int keyCode;

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
