package scheduler.task;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import output.console.Console;


public abstract class ConsoleTask extends InputTask{

    protected Console console;
    protected String prompt = "";

    public ConsoleTask(){
        console = new Console();
        console.clearConsole();
    }

    @Override
    public void execute(){
        if(!this.isFocused()||this.buffer==null){
            return;
        }else {
            if (this.wasOutOfFcous()) {
                this.console.println();
                this.console.print(prompt);
            }
        }
        while(buffer.canRead()&&!this.hasTerminated()){
            KeyboardEvent event = buffer.readEvent();
            if(event.alt||event.control){
                this.handleCommand(event);
            } else {
                this.handleInput(event);
            }
        }
    }

    protected abstract void handleCommand(KeyboardEvent event);

    protected void handleInput(KeyboardEvent event){
        switch(event.keyCode){
            case Key.BACKSPACE:
            case Key.DELETE:
                console.delchar();
                break;
            case Key.ENTER:
                console.println();
                console.print(prompt);
                break;
            case Key.TAB:
                console.tab();
                break;
            case Key.UP_ARROW:
                break;
            case Key.DOWN_ARROW:
                break;
            case Key.LEFT_ARROW:
                console.decreaseCursor();
                break;
            case Key.RIGHT_ARROW:
                console.increaseCursor();
                break;
            case Key.PG_UP:
                break;
            case Key.PG_DOWN:
                break;
            default:
                if(event.keyCode > 0x00 && event.keyCode <=0xFF)
                    console.print((char)event.keyCode);
        }
    }
}
