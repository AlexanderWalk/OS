package paint.panel;

import binimp.ByteData;
import paint.bitmap.Bitmap;
import paint.modes.ModeCreator;
import paint.panel.selectable.InputModePanel;
import paint.panel.selectable.SizeSettingPanel;
import paint.settings.SettingCreator;

public class ControlPanel extends PanelBase{

    public static final int panelHeight=300;
    public static final int panelWidth=100;
    private final ModeCreator modeCreator;
    private final SettingCreator settingCreator;
    public final ControlPanelModes modes;
    public final ControlPanelSettings settings;

    public ControlPanel(ModeCreator factory, SettingCreator creator){
        super(null,0,0);
        this.height=ControlPanel.panelHeight;
        this.width=ControlPanel.panelWidth;
        this.modeCreator=factory;
        this.settingCreator=creator;
        this.modes= new ControlPanelModes();
        this.settings= new ControlPanelSettings();
    }

    @Override
    public void draw() {
        this.createControlPanel();
        for(int i=0;i<this.currChildCount;i++){
            this.children[i].draw();
        }
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public void setStartPos(int topLeftX, int topLeftY){
        this.topLeftX=topLeftX;
        this.topLeftY=topLeftY;
    }

    private void createControlPanel(){
        this.addBorder(3);
        this.createModeSection();
        this.createSettingsSection();
    }

    private void createModeSection(){
        PanelBase spaceButton = new BitmapPanel(this,10,10,new Bitmap(ByteData.spaceButton));
        this.addChild(spaceButton);
        InputModePanel mouseIcon = new InputModePanel(new Bitmap(ByteData.mouseIcon),this,10,50);
        this.modeCreator.observeCursorMode(mouseIcon);
        this.modes.addMode(mouseIcon);
        this.addChild(mouseIcon);
        InputModePanel pencilIcon = new InputModePanel(new Bitmap(ByteData.pencilIcon),this,60,50);
        this.modeCreator.observeDrawMode(pencilIcon);
        this.modes.addMode(pencilIcon);
        this.addChild(pencilIcon);
        this.setInitialMode();
    }

    private void createSettingsSection(){
        PanelBase tabButton = new BitmapPanel(this,10,90,new Bitmap(ByteData.tabButton));
        this.addChild(tabButton);
        PanelBase enterButton = new BitmapPanel(this,10,130,new Bitmap(ByteData.enterButton));
        this.addChild(enterButton);
        SizeSettingPanel cursor1 = new SizeSettingPanel(this,10,170,1);
        this.settingCreator.observeSizeSetting(cursor1);
        this.addChild(cursor1);
        this.settings.addMode(cursor1);
        SizeSettingPanel cursor6 = new SizeSettingPanel(this,40,170,6);
        this.settingCreator.observeSizeSetting(cursor6);
        this.addChild(cursor6);
        this.settings.addMode(cursor6);
        SizeSettingPanel cursor10 = new SizeSettingPanel(this,70,170,10);
        this.settingCreator.observeSizeSetting(cursor10);
        this.addChild(cursor10);
        this.settings.addMode(cursor10);
        SizeSettingPanel cursor16 = new SizeSettingPanel(this,10,200,16);
        this.settingCreator.observeSizeSetting(cursor16);
        this.addChild(cursor16);
        this.settings.addMode(cursor16);
        this.setInitialSettings();
    }

    private void setInitialMode(){
        this.modes.init();
    }

    private void setInitialSettings(){
        this.settings.init();
    }
}
