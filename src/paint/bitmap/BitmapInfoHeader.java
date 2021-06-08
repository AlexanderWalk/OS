package paint.bitmap;

public class BitmapInfoHeader {

    private final byte[] bitmap;
    private boolean valid = true;
    private int infoHeaderSizeInByte;
    private int bitmapWidth;
    private int bitmapHeight;
    private boolean bitmapBottomUp;
    private int colorDepth;
    private boolean compressed;

    public BitmapInfoHeader(byte[] bitmap){
        this.bitmap=bitmap;
        this.setInfoHeaderSize();
        this.setBitmapWidth();
        this.setBitmapHeight();
        this.setColorDepth();
        this.setCompressedStatus();
    }

    public int getInfoHeaderSizeInByte(){
        return this.infoHeaderSizeInByte;
    }

    public int getBitmapWidth(){
        return this.bitmapWidth;
    }

    public int getBitmapHeight(){
        return this.bitmapHeight;
    }

    public int getColorDepth(){
        return this.colorDepth;
    }

    public boolean isCompressed(){
        return this.compressed;
    }

    public boolean isValid(){
        return this.valid;
    }

    public boolean IsBottomUp(){
        return this.bitmapBottomUp;
    }

    private void setInfoHeaderSize(){
        int headerSize=0;
        headerSize+=this.bitmap[17]<<24;
        headerSize+=this.bitmap[16]<<16;
        headerSize+=this.bitmap[15]<<8;
        headerSize+=this.bitmap[14];
        this.infoHeaderSizeInByte=headerSize;
    }

    private void setBitmapWidth(){
        int bitmapWidth=0;
        bitmapWidth+=this.bitmap[21]<<24;
        bitmapWidth+=this.bitmap[20]<<16;
        bitmapWidth+=this.bitmap[19]<<8;
        bitmapWidth+=this.bitmap[18];
        this.bitmapWidth=bitmapWidth;
        if(this.bitmapWidth<=0)
            this.valid=false;
    }

    private void setBitmapHeight(){
        int bitmapHeight=0;
        bitmapHeight+=this.bitmap[25]<<24;
        bitmapHeight+=this.bitmap[24]<<16;
        bitmapHeight+=this.bitmap[23]<<8;
        bitmapHeight+=this.bitmap[22];
        this.bitmapHeight=bitmapHeight;
        if(this.bitmapHeight<0){
            this.bitmapBottomUp=false;
            this.bitmapHeight*=-1;
        }else{
            this.bitmapBottomUp=true;
        }
        if(this.bitmapHeight<=0)
            this.valid=false;
    }

    private void setColorDepth(){
        int colorDepth=0;
        colorDepth+=this.bitmap[29]<<8;
        colorDepth+=this.bitmap[28];
        this.colorDepth=colorDepth;
        if(this.colorDepth<=0)
            this.valid=false;
    }

    private void setCompressedStatus(){
        int compressedFlag=0;
        compressedFlag+=this.bitmap[30];
        this.compressed=compressedFlag==0;
        if(compressedFlag==3)//custom encoded
            this.valid=false;
    }
}
