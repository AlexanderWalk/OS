package paint.bitmap;

public class BitmapInfoHeader {

    private final byte[] bitmap;
    private boolean valid = true;
    private int infoHeaderSizeInByte;
    private int bitmapWidth;
    private int bitmapHeight;
    private boolean bitmapBottomUp;
    private int colorDepth;
    private boolean compressed = false;
    private int compressedFlag;

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

    public int compressionStatus(){
        return compressedFlag;
    }

    public boolean isValid(){
        return this.valid;
    }

    public boolean IsBottomUp(){
        return this.bitmapBottomUp;
    }

    private void setInfoHeaderSize(){
        int headerSize=0;
        headerSize+=(this.bitmap[17]<<24)&0xFF000000;
        headerSize+=(this.bitmap[16]<<16)&0xFF0000;
        headerSize+=(this.bitmap[15]<<8)&0xFF00;
        headerSize+=(this.bitmap[14])&0xFF;
        this.infoHeaderSizeInByte=headerSize;
    }

    private void setBitmapWidth(){
        int bitmapWidth=0;
        bitmapWidth+=(this.bitmap[21]<<24)&0xFF000000;
        bitmapWidth+=(this.bitmap[20]<<16)&0xFF0000;
        bitmapWidth+=(this.bitmap[19]<<8)&0xFF00;
        bitmapWidth+=this.bitmap[18]&0xFF;
        this.bitmapWidth=bitmapWidth;
        if(this.bitmapWidth<=0)
            this.valid=false;
    }

    private void setBitmapHeight(){
        int bitmapHeight=0;
        bitmapHeight+=(this.bitmap[25]<<24)&0xFF000000;
        bitmapHeight+=(this.bitmap[24]<<16)&0xFF0000;
        bitmapHeight+=(this.bitmap[23]<<8)&0xFF00;
        bitmapHeight+=this.bitmap[22]&0xFF;
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
        colorDepth+=(this.bitmap[29]<<8)&0xFF00;
        colorDepth+=this.bitmap[28]&0xFF;
        this.colorDepth=colorDepth;
        if(this.colorDepth<=0)
            this.valid=false;
    }

    private void setCompressedStatus(){
        compressedFlag=this.bitmap[30];
        this.compressed=compressedFlag!=0;
        if(compressedFlag==3)//custom encoded
            this.valid=false;
    }
}
