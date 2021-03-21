package kernel;

public class Colors {
    private static final int defaultFontColor = Colors.font_grey;
    private static final int defaultBackColor = Colors.back_black;
    public static final int defaultColor = defaultFontColor | defaultBackColor;

    private static final int light_color = 0x08;
    public static final int font_black = 0x00;
    public static final int font_darkgrey = 0x00 | light_color;
    public static final int font_blue = 0x01;
    public static final int font_lightblue = 0x01 | light_color;
    public static final int font_green = 0x02;
    public static final int font_lightgreen = 0x02 | light_color;
    public static final int font_cyan = 0x03;
    public static final int font_lightcyan = 0x03 | light_color;
    public static final int font_red = 0x04;
    public static final int font_lightred = 0x04 | light_color;
    public static final int font_magenta = 0x05;
    public static final int font_lightmagenta = 0x05 | light_color;
    public static final int font_brown = 0x06;
    public static final int font_lightbrown = 0x06 | light_color;
    public static final int font_grey = 0x07;
    public static final int font_white = 0x07 | light_color;

    private static final int offset = 4;
    public static final int back_black = font_black<<4;
    public static final int back_blue = font_blue<<4;
    public static final int back_green = font_green<<4;
    public static final int back_cyan = font_cyan<<4;
    public static final int back_red = font_red<<4;
    public static final int back_magenta = font_magenta<<4;
    public static final int back_brown = font_brown<<4;
    public static final int back_grey = font_grey<<4;
    public static final int back_blinking = 0x80;
}
