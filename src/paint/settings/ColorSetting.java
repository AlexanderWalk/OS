package paint.settings;

import paint.Cursor;

public class ColorSetting extends ColorViewObserver {

    private final Cursor cursor;

    public ColorSetting(Cursor cursor){
        this.cursor=cursor;
    }

    @Override
    public void update(int color) {
        this.cursor.setColor(color);
    }
}
