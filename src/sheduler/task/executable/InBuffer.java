package sheduler.task.executable;

import devices.keyboard.Keyboard;
import devices.keyboard.KeyboardEventBuffer;
import sheduler.Scheduler;
import sheduler.task.Task;

public class InBuffer extends Task {

    private KeyboardEventBuffer buffer;

    public InBuffer(){
        buffer = new KeyboardEventBuffer(32);
    }

    @Override
    public void execute() {
        Keyboard.processInputBuffer();
        while(Keyboard.eventAvailable()){
            buffer.writeEvent(Keyboard.getKeyboardEvent());
        }
        Scheduler.getCurrInputTask().setBuffer(buffer);
    }
}
