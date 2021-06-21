package paint.panel;

import paint.PaintTask;

public class Border {
    private final int whiteColor = 0xFFFFFF;

    public void draw(int topLeftX, int topLeftY, int height, int width, int broadPixel){
        //TODO mit grpaphics.drawLine austauschen
        if(broadPixel<=0)
            broadPixel=1;
        int j;
        //draw top
        for(j=topLeftY;j<topLeftY+broadPixel;j++){
            for(int i=topLeftX;i<topLeftX+width;i++){
                PaintTask.graphics.setPixel(i,j,whiteColor);
            }
        }
        //draw sides
        for(j=j;j<height-broadPixel;j++){
            for(int i=topLeftX;i<topLeftX+broadPixel;i++){
                PaintTask.graphics.setPixel(i,j,whiteColor);
            }
            for(int i=topLeftX+width-broadPixel;i<topLeftX+width;i++){
                PaintTask.graphics.setPixel(i,j,whiteColor);
            }
        }
        //draw bottom
        for(j=j;j<topLeftY+height;j++){
            for(int i=topLeftX;i<topLeftX+width;i++){
                PaintTask.graphics.setPixel(i,j,whiteColor);
            }
        }
    }
}
