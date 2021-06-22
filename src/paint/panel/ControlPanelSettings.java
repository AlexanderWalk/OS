package paint.panel;

import paint.panel.selectable.SettingPanel;

public class ControlPanelSettings {
    private int selectedSetting;
    private int currSettingCount;
    private int maxSettingCount=2;
    private SettingPanel[] settings;

    public void updateMode(){
        if(selectedSetting == currSettingCount-1){
            this.settings[this.selectedSetting].deactivateBorder();
            this.selectedSetting=0;
            this.settings[this.selectedSetting].activateBorder();
        }else{
            this.settings[this.selectedSetting].deactivateBorder();
            this.selectedSetting++;
            this.settings[this.selectedSetting].activateBorder();
        }
    }

    public void enterSetting(){
        this.settings[this.selectedSetting].enterSetting();
    }

    void addMode(SettingPanel panel){
        if(this.settings == null){
            this.currSettingCount = 0;
            settings = new SettingPanel[maxSettingCount];
        }
        if(this.maxSettingCount == this.currSettingCount){
            this.expandArray();
        }
        this.settings[currSettingCount] = panel;
        this.currSettingCount++;
    }

    private void expandArray(){
        this.maxSettingCount *=2;
        SettingPanel[] temp = new SettingPanel[maxSettingCount];
        for(int i = 0; i< currSettingCount; i++){
            temp[i]= settings[i];
        }
        this.settings=temp;
    }

    void init(){
        this.selectedSetting=0;
        this.settings[this.selectedSetting].activateBorder();
        this.settings[this.selectedSetting].enterSetting();
    }
}
