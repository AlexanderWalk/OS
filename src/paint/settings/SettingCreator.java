package paint.settings;

import paint.Cursor;
import paint.panel.selectable.SizeSettingPanel;

public class SettingCreator {

    private SizeSetting sizeSetting;
    private Cursor cursor;

    public SettingCreator(Cursor cursor){
        this.cursor=cursor;
    }

    public void observeSizeSetting(SizeSettingPanel panel){
        if(this.sizeSetting==null){
            this.sizeSetting=new SizeSetting(this.cursor);
        }
        panel.register(this.sizeSetting);
    }

    public void createColorSetting(){
    }
}
