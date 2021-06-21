package output.vesa;

import paint.PaintTask;
import paint.bitmap.BitmapInfoHeader;

public class VESAModeSelector {

    public static void customModeSelector(BitmapInfoHeader header, int addX, int addY, int minX, int minY){
        int height = header.getBitmapHeight() + addY;
        if(height<minY)
            height=minY;
        int width = header.getBitmapWidth() + addX;
        if(width<minX)
            height=minX;
        int colorDepth = header.getColorDepth();
        modeSelector(height,width,colorDepth);
    }

    public static void standardModeSelector(BitmapInfoHeader header){
        int height = header.getBitmapHeight();
        int width = header.getBitmapWidth();
        int colorDepth = header.getColorDepth();
        modeSelector(height,width,colorDepth);
    }

    private static void modeSelector(int height, int width, int colorDepth){
        //Find mode with smallest possible resolution
        VESAMode mode = PaintTask.graphics.modes;
        VESAMode bestMode = null;
        while(mode!=null){
            if(mode.colDepth!=colorDepth){
                mode=mode.nextMode;
                continue;
            }
            if(bestMode==null){
                if(mode.xRes>width&&mode.yRes>height){
                    bestMode=mode;
                    mode=mode.nextMode;
                    continue;
                }
            }else {
                if (mode.xRes>width&&mode.yRes>height&&mode.xRes<bestMode.xRes&&mode.yRes<bestMode.yRes)
                    bestMode=mode;
            }
            mode=mode.nextMode;
        }
        mode=bestMode;
        PaintTask.graphics.setMode(mode);
    }
}
