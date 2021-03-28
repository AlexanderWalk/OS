package Output.Colors;

public class ColorController {

    //Zum Austesten für dynamic Runtime: Massenhaft Objekte initialisieren
    private final int light_version = 0x08;
    public final FontColor font_black = new FontColor(0x00);
    public final FontColor font_darkgrey = new FontColor(0x00 | light_version);
    public final FontColor font_blue = new FontColor(0x01);
    public final FontColor font_lightblue = new FontColor(0x01 | light_version);
    public final FontColor font_green = new FontColor(0x02);
    public final FontColor font_lightgreen = new FontColor(0x02 | light_version);
    public final FontColor font_cyan = new FontColor(0x03);
    public final FontColor font_lightcyan = new FontColor(0x03 | light_version);
    public final FontColor font_red = new FontColor(0x04);
    public final FontColor font_lightred = new FontColor(0x04 | light_version);
    public final FontColor font_magenta = new FontColor(0x05);
    public final FontColor font_lightmagenta = new FontColor(0x05 | light_version);
    public final FontColor font_brown = new FontColor(0x06);
    public final FontColor font_lightbrown = new FontColor(0x06 | light_version);
    public final FontColor font_grey = new FontColor(0x07);
    public final FontColor font_white = new FontColor(0x07 | light_version);

    private final int offset = 4;
    public final int back_blinking = 0x08<<offset;
    public final BackColor back_black = new BackColor(0x00<<offset);
    public final BackColor back_blackBlinking = new BackColor(0x00<<offset|back_blinking);
    public final BackColor back_blue = new BackColor(0x01<<offset);
    public final BackColor back_blueBlinking = new BackColor(0x01<<offset|back_blinking);
    public final BackColor back_green = new BackColor(0x02<<offset);
    public final BackColor back_greenBlinking = new BackColor(0x02<<offset|back_blinking);
    public final BackColor back_cyan = new BackColor(0x03<<offset);
    public final BackColor back_cyanBlinking = new BackColor(0x03<<offset|back_blinking);
    public final BackColor back_red = new BackColor(0x04<<offset);
    public final BackColor back_redBlinking = new BackColor(0x04<<offset|back_blinking);
    public final BackColor back_magenta = new BackColor(0x05<<offset);
    public final BackColor back_magentaBlinking = new BackColor(0x05<<offset|back_blinking);
    public final BackColor back_brown = new BackColor(0x06<<offset);
    public final BackColor back_brownBlinking = new BackColor(0x06<<offset|back_blinking);
    public final BackColor back_grey = new BackColor(0x07<<offset);
    public final BackColor back_greyBlinking = new BackColor(0x07<<offset|back_blinking);

    public final FontColor defaultFontColor=font_grey;
    public final BackColor defaultBackColor=back_black;
}

