package scheduler.task.executable;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import paint.PaintTask;
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
        if(event.control){
            switch(event.keyCode){
                case Key.a:
                    Scheduler.addTask(new Editor());
                    break;
                case Key.b:
                    Scheduler.addTask(new ForceBreakPoint());
                    break;
                case Key.p:
                    ForcePageFault testPageFault = new ForcePageFault();
                    if(event.alt)
                        testPageFault.testLastPage();
                    else
                        testPageFault.testFirstPage();
                    Scheduler.addTask(testPageFault);
                    break;
                case Key.q:
                    Scheduler.addTask(new PaintTask());
                    break;
            }
        }
    }
}
