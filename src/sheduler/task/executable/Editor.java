package sheduler.task.executable;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import sheduler.task.ConsoleTask;

public class Editor extends ConsoleTask {


    public Editor(){
        this.console.println("Einfacher Editor:");
    }

    @Override
    protected void handleCommand(KeyboardEvent event) {
        if(event.control&&event.keyCode==Key.c){
            this.setTerminationStatus(true);
        }
    }
}

