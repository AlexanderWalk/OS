package paint.modes;

import devices.keyboard.KeyboardEvent;

public abstract class ModeBase {
    public abstract void handleInput(KeyboardEvent event);
    public abstract void activate();
}
