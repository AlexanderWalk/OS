package paint.bitmap;

import devices.StaticV24;

public class BitmapData {
    private int[][] data;

    public BitmapData(Bitmap bitmap){
        this.checkColorDepth(bitmap);
    }

    public void checkColorDepth(Bitmap bitmap) {
        int colorDepth = bitmap.getInfoHeader().getColorDepth();
        switch (colorDepth) {
            case 24:
                this.setData24BitDepth(bitmap);
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

    private void setData24BitDepth(Bitmap bitmap){
        int offset = bitmap.getFileHeader().getDataOffset();
        int height = bitmap.getInfoHeader().getBitmapHeight();
        int width = bitmap.getInfoHeader().getBitmapWidth();
        this.data = new int[width][height];
        if(bitmap.getInfoHeader().IsBottomUp()){
            for(int j = height-1;j>=0;j--){
                for(int i = 0; i < width;i++){
                    int color=0;
                    color+=bitmap.rawData[offset++];
                    color+=bitmap.rawData[offset++]<<8;
                    color+=bitmap.rawData[offset++]<<16;
                    data[i][j]=color;
                }
                //TODO: Why has the Iconborder 60 Byte too much?! 2 per row
                if(width==30)
                    offset+=2;
            }
        }else{
            for(int j = 0;j<height;j++){
                for(int i = 0; i < width;i++){
                    int color=0;
                    color+=bitmap.rawData[offset++];
                    color+=bitmap.rawData[offset++]<<8;
                    color+=bitmap.rawData[offset++]<<16;
                    data[i][j]=color;
                }
            }
        }
    }

    public int[][] getData(){
        return this.data;
    }
}
