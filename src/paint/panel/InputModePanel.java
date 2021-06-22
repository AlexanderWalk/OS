package paint.panel;

import binimp.ByteData;
import paint.bitmap.Bitmap;
import paint.modes.ModeViewObserver;

public class InputModePanel extends BitmapPanel {

    private BitmapPanel iconBorder;
    private final Bitmap iconBorderActive;
    private final Bitmap iconBorderInactive;
    private boolean active = false;
    private ModeViewObserver observer;

    public InputModePanel(Bitmap bitmap, PanelBase parent, int xOffset, int yOffset) {
        super(bitmap, parent, xOffset+3, yOffset+3);
        this.iconBorderActive = new Bitmap(ByteData.iconBorderActive);
        this.iconBorderInactive = new Bitmap(ByteData.iconBorder);
        this.iconBorder = new BitmapPanel(iconBorderInactive,this,-3,-3);
    }

    public void register(ModeViewObserver observer){
        this.observer=observer;
    }

    private void updateObserver(){
            this.observer.update();
    }

    @Override
    public void draw() {
        this.iconBorder.draw();
        super.draw();
    }

    public boolean isActive(){
        return this.active;
    }

    public void activate(){
        this.active=true;
        this.updateObserver();
        this.iconBorder=new BitmapPanel(iconBorderActive,this,-3,-3);
        this.draw();
    }

    public void deactivate(){
        this.active=false;
        this.updateObserver();
        this.iconBorder=new BitmapPanel(iconBorderInactive,this,-3,-3);
        this.draw();
    }
}
