package sheduler.task.executable;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import output.console.Console;
import sheduler.Scheduler;
import sheduler.task.InputTask;

public class Editor extends InputTask {

    Console console;

    public Editor(){
        console = new Console();
        console.println();
        console.println("Einfacher Editor:");
    }

    @Override
    public void execute() {
        if(this!= Scheduler.getCurrInputTask() || !buffer.canRead()){
            return;
        }
        while(buffer.canRead()){
            KeyboardEvent event = buffer.readEvent();
            if(!event.alt&&!event.control){
                //TODO besser machen
                if(event.keyCode== Key.ENTER)
                    console.println();
                else if(event.keyCode == Key.BACKSPACE || event.keyCode == Key.DELETE)
                    console.delchar();
                else
                    console.print((char)event.keyCode);
            } else
            if(event.control&&event.keyCode==Key.c){
                this.terminated=true;
                break;
            }
        }
    }
}

