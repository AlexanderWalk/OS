package paint.panel;

public abstract class PanelBase {
    protected PanelBase[] children;
    protected int maxChildCount=10;
    protected int currChildCount;
    protected int topLeftX;
    protected int topLeftY;
    protected int height;
    protected int width;
    protected PanelBase parent;
    protected Border border;

    public abstract void draw();

    protected int getAbsoluteX(){
        PanelBase panel = this;
        int x=0;
        while(panel!=null){
            x+=panel.topLeftX;
            panel = panel.parent;
        }
        return x;
    }

    protected int getAbsoluteY(){
        PanelBase panel = this;
        int y=0;
        while(panel!=null){
            y+=panel.topLeftY;
            panel = panel.parent;
        }
        return y;
    }

    protected void addChild(PanelBase childPanel){
        if(this.children == null){
            this.currChildCount = 0;
            this.children = new PanelBase[maxChildCount];
        }
        if(this.maxChildCount == this.currChildCount){
            this.maxChildCount*=2;
            PanelBase[] temp = new PanelBase[maxChildCount];
            for(int i=0;i<currChildCount;i++){
                temp[i]=children[i];
            }
            this.children=temp;
        }
        this.children[currChildCount] = childPanel;
        this.currChildCount++;
    }

    protected void addBorder(int broadpixel){
        this.border = new Border();
        this.border.draw(this.getAbsoluteX(),this.getAbsoluteY(),this.height,this.width,broadpixel);
    }
}
