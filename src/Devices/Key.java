package Devices;

/** @noinspection unused*/
public class Key {
	//extended ascii
	public static final int NO_KEY=0;
	public static final int SHARPS=225;
	public static final int SECTION_KEY=245;
	public static final int UE=154;
	public static final int OE=153;
	public static final int AE=142;
	public static final int ue=129;
	public static final int oe=148;
	public static final int ae=132;
	public static final int DEGREE_SIGN=176;
	public static final int AIGU_ACCENT =239;
	//printable ascii characters 0x20 - 0x7F
	public static final int SPACE = ' ';
	public static final int EXCLAMATION_MARK = '!';
	public static final int QUOTATION_MARK = '"';
	public static final int POUND_KEY = '#';
	public static final int DOLLAR_KEY = '$';
	public static final int PERCENT_KEY = '%';
	public static final int AMPERSAND = '&';
	public static final int SINGLE_QUOTE = '\'';
	public static final int LEFT_ROUND_BRACKET = '(';
	public static final int RIGHT_ROUND_BRACKET = ')';
	public static final int STAR = '*';
	public static final int PLUS = '+';
	public static final int COMMA = ',';
	public static final int MINUS = '-';
	public static final int DOT = '.';
	public static final int SLASH = '/';
	public static final int ZERO = '0';
	public static final int ONE = '1';
	public static final int TWO = '2';
	public static final int THREE = '3';
	public static final int FOUR = '4';
	public static final int FIVE = '5';
	public static final int SIX = '6';
	public static final int SEVEN = '7';
	public static final int EIGHT = '8';
	public static final int NINE = '9';
	public static final int COLON = ':';
	public static final int SEMICOLON = ';';
	public static final int LESS_THAN = '<';
	public static final int EQUALS_SIGN = '=';
	public static final int GREATER_THAN = '>';
	public static final int QUESTION_MARK = '?';
	public static final int AT_SIGN = '@';
	public static final int A = 'A';
	public static final int B = 'B';
	public static final int C = 'C';
	public static final int D = 'D';
	public static final int E = 'E';
	public static final int F = 'F';
	public static final int G = 'G';
	public static final int H = 'H';
	public static final int I = 'I';
	public static final int J = 'J';
	public static final int K = 'K';
	public static final int L = 'L';
	public static final int M = 'M';
	public static final int N = 'N';
	public static final int O = 'O';
	public static final int P = 'P';
	public static final int Q = 'Q';
	public static final int R = 'R';
	public static final int S = 'S';
	public static final int T = 'T';
	public static final int U = 'U';
	public static final int V = 'V';
	public static final int W = 'W';
	public static final int X = 'X';
	public static final int Y = 'Y';
	public static final int Z = 'Z';
	public static final int LEFT_SQUARE_BRACKET = '[';
	public static final int BACKSLASH = '\\';
	public static final int RIGHT_SQUARE_BRACKET = ']';
	public static final int CARET = '^';
	public static final int UNDERSCORE = '_';
	public static final int GRAVE_ACCENT = '`';
	public static final int a = 'a';
	public static final int b = 'b';
	public static final int c = 'c';
	public static final int d = 'd';
	public static final int e = 'e';
	public static final int f = 'f';
	public static final int g = 'g';
	public static final int h = 'h';
	public static final int i = 'i';
	public static final int j = 'j';
	public static final int k = 'k';
	public static final int l = 'l';
	public static final int m = 'm';
	public static final int n = 'n';
	public static final int o = 'o';
	public static final int p = 'p';
	public static final int q = 'q';
	public static final int r = 'r';
	public static final int s = 's';
	public static final int t = 't';
	public static final int u = 'u';
	public static final int v = 'v';
	public static final int w = 'w';
	public static final int x = 'x';
	public static final int y = 'y';
	public static final int z = 'z';
	public static final int LEFT_CURLY_BRACKET = '{';
	public static final int VERTICAL_BAR = '|';
	public static final int RIGHT_CURLY_BRACKET = '}';
	public static final int TILDE = '~';
	public static final int DELETE = 0x7F;
	
	//0x80-0xFF reserved for extended ascii
	
	
	//0x100 onwards for rest of the keyboard
	public static final int LEFT_SHIFT = 0x100;
	public static final int RIGHT_SHIFT = 0x101;
	public static final int LEFT_CONTROL = 0x102;
	public static final int RIGHT_CONTROL = 0x103;
	public static final int LEFT_ALT = 0x104;
	public static final int RIGHT_ALT = 0x105;
	public static final int ENTER = 0x106;
	public static final int BACKSPACE = 0x107;
	public static final int TAB = 0x108;
	public static final int CAPSLOCK = 0x109;
	public static final int ESCAPE = 0x10A;
	public static final int SUPER = 0x10B;
	public static final int WINDOWS = SUPER;
	public static final int UP_ARROW = 0x10D;
	public static final int DOWN_ARROW = 0x10E;
	public static final int LEFT_ARROW = 0x10F;
	public static final int RIGHT_ARROW = 0x110;
	public static final int PG_UP = 0x111;
	public static final int PG_DOWN = 0x112;
	public static final int INSERT = 0x113;
	public static final int HOME = 0x114;
	public static final int END = 0x115;
	public static final int PRINT_SCREEN = 0x116;
	public static final int SCROLL_LOCK = 0x117;
	public static final int PAUSE = 0x118;
	public static final int NUMLOCK = 0x119;
	public static final int ALT_GR = 0x11A;
	public static final int MENU = 0x11B;
	
	//function keys 0x140 onwards
	public static final int F1 = 0x140;
	public static final int F2 = 0x141;
	public static final int F3 = 0x142;
	public static final int F4 = 0x143;
	public static final int F5 = 0x144;
	public static final int F6 = 0x145;
	public static final int F7 = 0x146;
	public static final int F8 = 0x147;
	public static final int F9 = 0x148;
	public static final int F10 = 0x149;
	public static final int F11 = 0x14A;
	public static final int F12 = 0x14B;
	public static final int F13 = 0x14C;
	public static final int F14 = 0x14D;
	public static final int F15 = 0x14E;
	public static final int F16 = 0x14F;
	public static final int F17 = 0x150;
	public static final int F18 = 0x151;
	public static final int F19 = 0x152;
	public static final int F20 = 0x153;
	public static final int F21 = 0x154;
	public static final int F22 = 0x155;
	public static final int F23 = 0x156;
	public static final int F24 = 0x157;
}
