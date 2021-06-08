package output.consolecolors;

public class StaticColors {
    private static final int light_version = 0x08;
    public static final int font_black = 0x00;
    public static final int font_darkgrey = 0x00 | light_version;
    public static final int font_blue = 0x01;
    public static final int font_lightblue = 0x01| light_version;
    public static final int font_green = 0x02;
    public static final int font_lightgreen = 0x02| light_version;
    public static final int font_cyan = 0x03;
    public static final int font_lightcyan = 0x03| light_version;
    public static final int font_red = 0x04;
    public static final int font_lightred = 0x04| light_version;
    public static final int font_magenta = 0x05;
    public static final int font_lightmagenta = 0x05| light_version;
    public static final int font_brown = 0x06;
    public static final int font_lightbrown = 0x06| light_version;
    public static final int font_grey = 0x07;
    public static final int font_white = 0x07| light_version;

    private static final int offset = 4;
    public static final int back_blinking = 0x08<<offset;
    public static final int back_black = 0x00<<offset;
    public static final int back_blackBlinking = 0x00<<offset|back_blinking;
    public static final int back_blue = 0x01<<offset;
    public static final int back_blueBlinking = 0x01<<offset|back_blinking;
    public static final int back_green = 0x02<<offset;
    public static final int back_greenBlinking = 0x02<<offset|back_blinking;
    public static final int back_cyan = 0x03<<offset;
    public static final int back_cyanBlinking = 0x03<<offset|back_blinking;
    public static final int back_red = 0x04<<offset;
    public static final int back_redBlinking = 0x04<<offset|back_blinking;
    public static final int back_magenta = 0x05<<offset;
    public static final int back_magentaBlinking = 0x05<<offset|back_blinking;
    public static final int back_brown = 0x06<<offset;
    public static final int back_brownBlinking = 0x06<<offset|back_blinking;
    public static final int back_grey = 0x07<<offset;
    public static final int back_greyBlinking = 0x07<<offset|back_blinking;

    public static final int defaultFont=font_grey;
    public static final int defaultBack=back_black;
}
