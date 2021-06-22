package paint;

public class Cursor {
    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;
    private int squareSize;
    private int topLeftX;
    private int topLeftY;
    private int color;

    public Cursor(int minX,int maxX,int minY, int maxY){
        this.minX=minX;
        this.maxX=maxX;
        this.minY=minY;
        this.maxY=maxY;
        this.topLeftY=minY;
        this.topLeftX=minX;
    }

    public int getX(){
        return this.topLeftX;
    }

    public int getY(){
        return this.topLeftY;
    }

    public int getColor(){
        return this.color;
    }

    public void setColor(int color){
        this.color=color;
    }

    public int getSquareSize(){
        return this.squareSize;
    }

    public void setSquareSize(int size){
        this.squareSize=size;
    }

    public void moveRight(){
        if(topLeftX+squareSize*2<maxX)
            topLeftX+=squareSize;
        else
            topLeftX=maxX-squareSize;
    }

    public void moveUp(){
        if(topLeftY-squareSize>minY)
            topLeftY-=squareSize;
        else
            topLeftY=minY;
    }

    public void moveDown(){
        if(topLeftY+squareSize*2<maxY) {
            topLeftY += squareSize;
        }
        else
            topLeftY=maxY-squareSize;
    }

    public void moveLeft(){
        if(topLeftX-squareSize>minX)
            topLeftX-=squareSize;
        else
            topLeftX=minX;
    }
}
