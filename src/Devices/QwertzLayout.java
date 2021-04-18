package Devices;

public class QwertzLayout extends KeyboardLayout{
    public boolean shift;
    public boolean caps;
    public boolean upperCase;

    public int getKey(int scanCode){
        switch(scanCode){
            case 0x01:
                return Key.ESCAPE;
            case 0x02:
                if(upperCase)
                    return Key.EXCLAMATION_MARK;
                return Key.ONE;
            case 0x03:
                if(upperCase)
                    return Key.QUOTATION_MARK;
                return Key.TWO;
            case 0x04:
                if(upperCase)
                    return Key.SECTION_KEY;
                return Key.THREE;
            case 0x05:
                if(upperCase)
                    return Key.DOLLAR_KEY;
                return Key.FOUR;
            case 0x06:
                if(upperCase)
                    return Key.PERCENT_KEY;
                return Key.FIVE;
            case 0x07:
                if(upperCase)
                    return Key.AMPERSAND;
                return Key.SIX;
            case 0x08:
                if(upperCase)
                    return Key.SLASH;
                return Key.SEVEN;
            case 0x09:
                if(upperCase)
                    return Key.LEFT_ROUND_BRACKET;
                return Key.EIGHT;
            case 0x0A:
                if(upperCase)
                    return Key.RIGHT_ROUND_BRACKET;
                return Key.NINE;
            case 0x0B:
                if(upperCase)
                    return Key.EQUALS_SIGN;
                return Key.ZERO;
            case 0x0C:
                if(upperCase)
                    return Key.QUESTION_MARK;
                return Key.SHARPS;
            case 0x0D:
                if(upperCase)
                    return Key.GRAVE_ACCENT;
                return Key.AIGU_ACCENT;
            case 0x0E:
                return Key.BACKSPACE;
            case 0x0F:
                return Key.TAB;
            case 0x10:
                if(upperCase)
                    return Key.Q;
                return Key.q;
            case 0x11:
                if(upperCase)
                    return Key.W;
                return Key.w;
            case 0x12:
                if(upperCase)
                    return Key.E;
                return Key.e;
            case 0x13:
                if(upperCase)
                    return Key.R;
                return Key.r;
            case 0x14:
                if(upperCase)
                    return Key.T;
                return Key.t;
            case 0x15:
                if(upperCase)
                    return Key.Z;
                return Key.z;
            case 0x16:
                if(upperCase)
                    return Key.U;
                return Key.u;
            case 0x17:
                if(upperCase)
                    return Key.I;
                return Key.i;
            case 0x18:
                if(upperCase)
                    return Key.O;
                return Key.o;
            case 0x19:
                if(upperCase)
                    return Key.P;
                return Key.p;
            case 0x1A:
                if(upperCase)
                    return Key.UE;
                return Key.ue;
            case 0x1B:
                if(upperCase)
                    return Key.STAR;
                return Key.PLUS;
            case 0x1C:
                return Key.ENTER;
            case 0xE01C:
                return Key.ENTER;
            case 0x1D:
                return Key.LEFT_CONTROL;
            case 0xE01D:
                return Key.RIGHT_CONTROL;
            case 0x1E:
                if(upperCase)
                    return Key.A;
                return Key.a;
            case 0x1F:
                if(upperCase)
                    return Key.S;
                return Key.s;
            case 0x20:
                if(upperCase)
                    return Key.D;
                return Key.d;
            case 0x21:
                if(upperCase)
                    return Key.F;
                return Key.f;
            case 0x22:
                if(upperCase)
                    return Key.G;
                return Key.g;
            case 0x23:
                if(upperCase)
                    return Key.H;
                return Key.h;
            case 0x24:
                if(upperCase)
                    return Key.J;
                return Key.j;
            case 0x25:
                if(upperCase)
                    return Key.K;
                return Key.k;
            case 0x26:
                if(upperCase)
                    return Key.L;
                return Key.l;
            case 0x27:
                if(upperCase)
                    return Key.OE;
                return Key.oe;
            case 0x28:
                if(upperCase)
                    return Key.AE;
                return Key.ae;
            case 0x29:
                if(upperCase)
                    return Key.DEGREE_SIGN;
                return Key.CARET;
            case 0x2A:
                return Key.LEFT_SHIFT;
            case 0x2B:
                if(upperCase)
                    return Key.SINGLE_QUOTE;
                return Key.POUND_KEY;
            case 0x2C:
                if(upperCase)
                    return Key.Y;
                return Key.y;
            case 0x2D:
                if(upperCase)
                    return Key.X;
                return Key.x;
            case 0x2E:
                if(upperCase)
                    return Key.C;
                return Key.c;
            case 0x2F:
                if(upperCase)
                    return Key.V;
                return Key.v;
            case 0x30:
                if(upperCase)
                    return Key.B;
                return Key.b;
            case 0x31:
                if(upperCase)
                    return Key.N;
                return Key.n;
            case 0x32:
                if(upperCase)
                    return Key.M;
                return Key.m;
            case 0x33:
                if(upperCase)
                    return Key.SEMICOLON;
                return Key.COMMA;
            case 0x34:
                if(upperCase)
                    return Key.COLON;
                return Key.DOT;
            case 0x35:
                if(upperCase)
                    return Key.UNDERSCORE;
                return Key.MINUS;
            case 0xE035:
                return Key.SLASH;
            case 0x36:
                return Key.RIGHT_SHIFT;
            case 0x37:
                    return Key.STAR;
            case 0x38:
                return Key.LEFT_ALT;
            case 0xE038:
                return Key.ALT_GR;
            case 0x39:
                return Key.SPACE;
            case 0x3A:
                return Key.CAPSLOCK;
            case 0x3B:
                return Key.F1;
            case 0x3C:
                return Key.F2;
            case 0x3D:
                return Key.F3;
            case 0x3E:
                return Key.F4;
            case 0x3F:
                return Key.F5;
            case 0x40:
                return Key.F6;
            case 0x41:
                return Key.F7;
            case 0x42:
                return Key.F8;
            case 0x43:
                return Key.F9;
            case 0x44:
                return Key.F10;
            case 0x45:
                return Key.NUMLOCK;
            case 0x46:
                return Key.SCROLL_LOCK;
            case 0x47:
            case 0xE047:
                return Key.HOME;
            case 0x48:
            case 0xE048:
                return Key.UP_ARROW;
            case 0x49:
            case 0xE049:
                return Key.PG_UP;
            case 0x4A:
                return Key.MINUS;
            case 0x4B:
            case 0xE04B:
                return Key.LEFT_ARROW;
            case 0x4C:
                return Key.NO_KEY;
            case 0x4D:
            case 0xE04D:
                return Key.RIGHT_ARROW;
            case 0x4E:
                return Key.PLUS;
            case 0x4F:
            case 0xE04F:
                return Key.END;
            case 0x50:
            case 0xE050:
                return Key.DOWN_ARROW;
            case 0x51:
            case 0xE051:
                return Key.PG_DOWN;
            case 0x52:
            case 0xE052:
                return Key.INSERT;
            case 0x53:
            case 0xE053:
                return Key.DELETE;
            case 0x56:
                if(upperCase)
                    return Key.GREATER_THAN;
                return Key.LESS_THAN;
            case 0x57:
                return Key.F11;
            case 0x58:
                return Key.F12;
            case 0xE05B:
            case 0xE05C:
                return Key.SUPER;
            case 0xE11D45:
                return Key.PAUSE;
            default:
                return Key.NO_KEY;
        }
    }
}
