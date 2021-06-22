package paint.settings;

import paint.Cursor;

public class SizeSetting extends SizeViewObserver{

    private final Cursor cursor;

    public SizeSetting(Cursor cursor){
        this.cursor=cursor;
    }

    @Override
    public void update(int size) {
        this.cursor.setSquareSize(size);
    }
}
