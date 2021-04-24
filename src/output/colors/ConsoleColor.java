package output.colors;

public class ConsoleColor{
    protected int color;

    public ConsoleColor(){
        this.setColor(StaticColors.defaultFont,StaticColors.defaultBack);
    }

    public int getColor(){
        return color;
    }

    public void setColor(int font, int back){
        this.color=font|back;
    }
}
