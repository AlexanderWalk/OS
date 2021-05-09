package sheduler.task.executable;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import output.console.Console;
import sheduler.Scheduler;
import sheduler.task.InputTask;

public class Terminal extends InputTask {

    private Console console;
    private boolean outOfFocus = false;

    public Terminal(){
        console = new Console();
        console.clearConsole();
        console.println("                             Welcome to KasleberkOS                             ");
        console.print("Terminal>");
    }

    @Override
    public void execute() {
        if(this!=Scheduler.getCurrInputTask()){
            outOfFocus=true;
            return;
        }
        if(outOfFocus==true){
            outOfFocus=false;
            console.println();
            console.print("Terminal>");
        }
        while(buffer.canRead()){
            KeyboardEvent event = buffer.readEvent();
            if(!event.alt&&!event.control){
                //TODO besser machen
                if(event.keyCode== Key.ENTER) {
                    console.println();
                    console.print("Terminal>");
                }
                else if(event.keyCode == Key.BACKSPACE || event.keyCode == Key.DELETE)
                    console.delchar();
                else
                    console.print((char)event.keyCode);
            } else {
                if (event.control && event.keyCode == Key.a) {
                    Scheduler.addTask(new Editor());
                }
            }
        }
    }
}
