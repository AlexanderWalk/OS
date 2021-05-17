package scheduler.task.executable;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import scheduler.Scheduler;
import scheduler.task.ConsoleTask;

public class Terminal extends ConsoleTask {

    public Terminal(){
        this.console.println("                             Welcome to KasleberkOS                             ");
        this.prompt=">";
        this.console.print(prompt);
    }

    @Override
    protected void handleCommand(KeyboardEvent event) {
        if (event.control && event.keyCode == Key.a) {
            Scheduler.addTask(new Editor());
        }
    }
}
