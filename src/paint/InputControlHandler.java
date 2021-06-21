package paint;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import paint.panel.ControlPanel;

public class InputControlHandler {

    private ControlPanel panel;

    public InputControlHandler(ControlPanel panel){
        this.panel=panel;
    }

    public void handleKeyEvent(KeyboardEvent event){
        switch (event.keyCode){
            case Key.LEFT_ARROW:
            case Key.RIGHT_ARROW:
            case Key.UP_ARROW:
            case Key.DOWN_ARROW:
                this.panel.getMode().handleInput(event);
                break;
            case Key.SPACE:
                this.panel.updateMode();
                break;
        }
    }
}
