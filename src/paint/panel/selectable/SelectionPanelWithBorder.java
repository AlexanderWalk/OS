package paint.panel.selectable;

import paint.panel.BitmapPanel;
import paint.panel.PanelBase;

public abstract class SelectionPanelWithBorder extends PanelBase {

    protected BitmapPanel currIconBorder;
    protected BitmapPanel iconBorderActive;
    protected BitmapPanel iconBorderInactive;
    protected boolean active = false;

    public SelectionPanelWithBorder(PanelBase parent, int xOffset, int yOffset) {
        super(parent, xOffset, yOffset);
    }

    public void activateBorder(){
        this.active=true;
        this.currIconBorder = iconBorderActive;
        this.draw();
    }

    public void deactivateBorder(){
        this.active=false;
        this.currIconBorder = iconBorderInactive;
        this.draw();
    }

    public boolean isActive(){
        return this.active;
    }
}
