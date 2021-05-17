package scheduler.task.executable;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import scheduler.Scheduler;
import scheduler.task.ConsoleTask;

public class Editor extends ConsoleTask {


    public Editor(){
        this.console.println("Einfacher Editor:");
    }

    @Override
    protected void handleCommand(KeyboardEvent event) {
        if(event.control&&event.keyCode==Key.c){
            Scheduler.delTask(this);
        }
    }
}

