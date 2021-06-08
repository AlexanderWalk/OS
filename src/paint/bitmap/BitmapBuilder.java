package paint.bitmap;

import devices.StaticV24;
import output.vesa.VESAGraphics;
import output.vesa.VESAMode;

public class BitmapBuilder {

    private final VESAGraphics graphics;
    private final Bitmap bitmap;
    private VESAMode mode;


    public BitmapBuilder(VESAGraphics graphics, Bitmap bitmap){
        this.graphics=graphics;
        this.bitmap=bitmap;
        this.modeSelector();
    }

    public void drawBitmap(){
        this.graphics.setMode(mode);
        int width = this.bitmap.getInfoHeader().getBitmapWidth();
        int height = this.bitmap.getInfoHeader().getBitmapHeight();
        int colorDepth = this.bitmap.getInfoHeader().getColorDepth();
        int dataOffset = this.bitmap.getDataOffset();
        switch(colorDepth){
            case 24:
                this.drawBitmap24ColDepth(dataOffset,height,width);
                return;
            case 32:
            case 16:
            case 15:
            case 8:
            case 4:
            case 1:
            default:
                StaticV24.println("BitmapBuilder: No matching Method for Colordepth found");
                return;
        }
    }

    private void drawBitmap24ColDepth(int dataOffset, int height, int width){
        if(this.bitmap.getInfoHeader().IsBottomUp()){
            int pos = dataOffset;
            for(int j = height-1;j>=0;j--){
                for(int i = 0; i < width;i++){
                    int color=0;
                    color+=this.bitmap.bitmap[pos++];
                    color+=this.bitmap.bitmap[pos++]<<8;
                    color+=this.bitmap.bitmap[pos++]<<16;
                    this.graphics.setPixel(i,j,color);
                }
            }
        }else{
            int pos = dataOffset;
            for(int j = 0;j<height;j++){
                for(int i = 0; i < width;i++){
                    int color=0;
                    color+=this.bitmap.bitmap[pos++];
                    color+=this.bitmap.bitmap[pos++]<<8;
                    color+=this.bitmap.bitmap[pos++]<<16;
                    this.graphics.setPixel(i,j,color);
                }
            }
        }
    }

    private void modeSelector(){
        //Find mode with smallest possible resolution
        BitmapInfoHeader header = bitmap.getInfoHeader();
        int height = header.getBitmapHeight();
        int width = header.getBitmapWidth();
        int colorDepth = header.getColorDepth();
        this.mode = graphics.modes;
        VESAMode bestMode = null;
        while(mode!=null){
            if(mode.colDepth!=colorDepth){
                mode=mode.nextMode;
                continue;
            }
            if(bestMode==null){
                if(mode.xRes>width&&mode.yRes>height){
                    bestMode=this.mode;
                    mode=mode.nextMode;
                    continue;
                }
            }else {
                if (mode.xRes>width&&mode.yRes>height&&mode.xRes<bestMode.xRes&&mode.yRes<bestMode.yRes)
                    bestMode=mode;
            }
            mode=mode.nextMode;
        }
        this.mode=bestMode;
    }
}
