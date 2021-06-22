package paint;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import paint.modes.ModeHandler;
import paint.panel.ControlPanel;

public class InputControlHandler {

    private ModeHandler modeHandler;
    private ControlPanel panel;

    public InputControlHandler(ModeHandler handler, ControlPanel panel){
        this.modeHandler=handler;
        this.panel=panel;
    }

    public void handleKeyEvent(KeyboardEvent event){
        switch (event.keyCode){
            case Key.LEFT_ARROW:
            case Key.RIGHT_ARROW:
            case Key.UP_ARROW:
            case Key.DOWN_ARROW:
                this.modeHandler.getCurrMode().handleInput(event);
                break;
            case Key.SPACE:
                this.panel.updateMode();
                break;
        }
    }
}
