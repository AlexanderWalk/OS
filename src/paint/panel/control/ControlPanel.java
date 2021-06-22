package paint.panel.control;

import binimp.ByteData;
import paint.bitmap.Bitmap;
import paint.colors.RGB;
import paint.modes.ModeCreator;
import paint.panel.BitmapPanel;
import paint.panel.PanelBase;
import paint.panel.selectable.ColorSettingPanel;
import paint.panel.selectable.InputModePanel;
import paint.panel.selectable.SettingPanel;
import paint.panel.selectable.SizeSettingPanel;
import paint.settings.SettingCreator;

public class ControlPanel extends PanelBase {

    public static final int panelHeight=320;
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
        this.initMode(mouseIcon);
        InputModePanel pencilIcon = new InputModePanel(new Bitmap(ByteData.pencilIcon),this,60,50);
        this.modeCreator.observeDrawMode(pencilIcon);
        this.initMode(pencilIcon);
        this.setInitialMode();
    }

    private void initMode(InputModePanel panel){
        this.modes.addMode(panel);
        this.addChild(panel);
    }

    private void createSettingsSection(){
        PanelBase tabButton = new BitmapPanel(this,10,90,new Bitmap(ByteData.tabButton));
        this.addChild(tabButton);
        PanelBase enterButton = new BitmapPanel(this,10,130,new Bitmap(ByteData.enterButton));
        this.addChild(enterButton);
        this.createSizeSettings();
        this.setInitialSettings();
        this.createColorSettings();
    }

    private void createSizeSettings(){
        SizeSettingPanel cursor1 = new SizeSettingPanel(this,10,170,1);
        this.initSizeSettingPanel(cursor1);
        SizeSettingPanel cursor6 = new SizeSettingPanel(this,40,170,6);
        this.initSizeSettingPanel(cursor6);
        SizeSettingPanel cursor10 = new SizeSettingPanel(this,70,170,10);
        this.initSizeSettingPanel(cursor10);
        SizeSettingPanel cursor16 = new SizeSettingPanel(this,10,200,16);
        this.initSizeSettingPanel(cursor16);
    }

    private void createColorSettings(){
        ColorSettingPanel black = new ColorSettingPanel(this,10,230, RGB.black);
        this.initColorSettingPanel(black);
        ColorSettingPanel white = new ColorSettingPanel(this,40,230, RGB.white);
        this.initColorSettingPanel(white);
        ColorSettingPanel grey = new ColorSettingPanel(this,70,230, RGB.grey);
        this.initColorSettingPanel(grey);
        ColorSettingPanel red = new ColorSettingPanel(this,10,260, RGB.red);
        this.initColorSettingPanel(red);
        ColorSettingPanel orange = new ColorSettingPanel(this,40,260, RGB.orange);
        this.initColorSettingPanel(orange);
        ColorSettingPanel yellow = new ColorSettingPanel(this,70,260, RGB.yellow);
        this.initColorSettingPanel(yellow);
        ColorSettingPanel green = new ColorSettingPanel(this,10,290, RGB.green);
        this.initColorSettingPanel(green);
        ColorSettingPanel blue = new ColorSettingPanel(this,40,290, RGB.blue);
        this.initColorSettingPanel(blue);
        ColorSettingPanel purple = new ColorSettingPanel(this,70,290, RGB.purple);
        this.initColorSettingPanel(purple);
        white.enterSetting();
    }

    private void initSizeSettingPanel(SizeSettingPanel panel){
        this.settingCreator.observeSizeSetting(panel);
        this.initSetting(panel);
    }

    private void initColorSettingPanel(ColorSettingPanel panel){
        this.settingCreator.observeColorSetting(panel);
        this.initSetting(panel);
    }

    private void initSetting(SettingPanel panel){
        this.addChild(panel);
        this.settings.addMode(panel);
    }

    private void setInitialMode(){
        this.modes.init();
    }

    private void setInitialSettings(){
        this.settings.init();
    }
}
