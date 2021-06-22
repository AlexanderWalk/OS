package paint;

import binimp.ByteData;
import devices.keyboard.KeyboardEvent;
import output.vesa.VESAGraphics;
import output.vesa.VESAMode;
import output.vesa.VESAModeSelector;
import paint.bitmap.Bitmap;
import paint.modes.ModeCreator;
import paint.modes.ModeHandler;
import paint.panel.ControlPanel;
import scheduler.task.InputTask;

public class PaintTask extends InputTask {

    public static VESAGraphics graphics;
    private ControlPanel controlPanel;
    private final Bitmap bitmap;
    private Screen screen;
    private Cursor cursor;
    private InputControlHandler inputHandler;
    private ModeHandler modeHandler;
    private boolean initialized = false;

    static{
        graphics = VESAGraphics.detectDevice();
    }

    public PaintTask(){
        this.bitmap= new Bitmap(ByteData.gandalf);
    }

    @Override
    public void execute() {
        if(!this.isFocused()||this.buffer==null){
            return;
        }
        if(!initialized){
            this.initPaint();
            return;
        }
        this.handleInput();
    }

    private void initPaint(){
        this.initVesaMode();
        this.initCursor();
        this.initScreen();
        this.initModeHandler();
        this.initControlPanel();
        this.initInputHandler();
        this.initialized=true;
    }

    private void initVesaMode(){
        VESAModeSelector.customModeSelector(this.bitmap.getInfoHeader(),
                ControlPanel.panelWidth,0,0,ControlPanel.panelHeight);
    }

    private void initControlPanel(){
        this.controlPanel = new ControlPanel(new ModeCreator(this.cursor,this.bitmap.getBitmapData(),this.modeHandler));
        VESAMode mode = graphics.curMode;
        int topLeftX = mode.xRes - this.controlPanel.getWidth();
        this.controlPanel.setStartPos(topLeftX,0);
        this.controlPanel.draw();
    }

    private void initCursor(){
        this.cursor = new Cursor(0,this.bitmap.getInfoHeader().getBitmapWidth(),
                0,this.bitmap.getInfoHeader().getBitmapHeight());
    }

    private void initScreen(){
        this.screen = new Screen(this.bitmap,this.cursor);
    }

    private void initInputHandler(){
        this.inputHandler = new InputControlHandler(this.modeHandler,this.controlPanel);
    }

    private void initModeHandler(){
        this.modeHandler = new ModeHandler();
    }

   private void handleInput(){
        while(this.buffer.canRead()&&!this.hasTerminated()){
            KeyboardEvent event = this.buffer.readEvent();
                this.inputHandler.handleKeyEvent(event);
        }
    }
}
