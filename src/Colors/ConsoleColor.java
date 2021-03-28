package Colors;

public class ConsoleColor{
    protected int color;

    public ConsoleColor(){
        this.setColor(StaticColors.defaultFont,StaticColors.defaultBack);
        //ColorController c = new ColorController();
        //this.setColor(c.font_green, c.defaultBackColor);
    }

    public int getColor(){
        return color;
    }

    public void setColor(FontColor font, BackColor back){
        this.color=font.color|back.color;
    }

    public void setColor(int font, int back){
        this.color=font|back;
    }
}
