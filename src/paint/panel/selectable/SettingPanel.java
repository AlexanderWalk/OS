package paint.panel.selectable;

import paint.panel.PanelBase;

public abstract class SettingPanel extends SelectionPanelWithBorder{

    protected SettingPanel(PanelBase parent, int xOffset, int yOffset) {
        super(parent, xOffset, yOffset);
    }

    public abstract void enterSetting();
}
