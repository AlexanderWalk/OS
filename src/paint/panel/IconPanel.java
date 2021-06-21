package paint.panel;

import binimp.ByteData;
import paint.bitmap.Bitmap;
import paint.modes.ModeBase;

public class IconPanel extends BitmapPanel {

    private BitmapPanel iconBorder;
    private final Bitmap iconBorderActive;
    private final Bitmap iconBorderInactive;
    private boolean active = false;
    private final ModeBase mode;

    public IconPanel(Bitmap bitmap, PanelBase parent, int xOffset, int yOffset, ModeBase mode) {
        super(bitmap, parent, xOffset+3, yOffset+3);
        this.iconBorderActive = new Bitmap(ByteData.iconBorderActive);
        this.iconBorderInactive = new Bitmap(ByteData.iconBorder);
        this.iconBorder = new BitmapPanel(iconBorderInactive,this,-3,-3);
        this.mode=mode;
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
        this.mode.activate();
        this.iconBorder=new BitmapPanel(iconBorderActive,this,-3,-3);
        this.draw();
    }

    public void deactivate(){
        this.active=false;
        this.iconBorder=new BitmapPanel(iconBorderInactive,this,-3,-3);
        this.draw();
    }

    public ModeBase getMode(){
        return this.mode;
    }

}
