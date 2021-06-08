package paint.bitmap;

public class BitmapFileHeader {

    public static final int fileHeaderSizeInByte=14;
    private static final int bmFlag=0x424D; //424D = BM in ASCII

    private final byte[] bitmap;
    private boolean valid = true;
    private int dataOffset;

    public BitmapFileHeader(byte[] bitmap){
        this.bitmap=bitmap;
        this.checkBMFlag();
        //Ignore next 8 Byte
        this.setDataOffset();
    }

    public boolean isValid(){
        return this.valid;
    }

    public int getDataOffset(){
        return dataOffset;
    }

    private void checkBMFlag(){
        int i = this.bitmap[0]<<8;
        i+=this.bitmap[1];
        if(i!=bmFlag){
            this.valid=false;
        }
    }

    private void setDataOffset(){
        int offset = 0;
        offset+=this.bitmap[13]<<24;
        offset+=this.bitmap[12]<<16;
        offset+=this.bitmap[11]<<8;
        offset+=this.bitmap[10];
        this.dataOffset=offset;
    }
}
