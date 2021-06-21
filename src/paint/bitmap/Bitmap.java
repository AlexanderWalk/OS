package paint.bitmap;

public class Bitmap {

    public final byte[] rawData;

    private boolean valid;
    private BitmapFileHeader fileHeader;
    private BitmapInfoHeader infoHeader;
    private BitmapData data;

    public Bitmap(byte[] bitmap){
        this.rawData = bitmap;
        this.checkMetaData();
        this.setBitmapData();
    }

    public BitmapFileHeader getFileHeader(){
        return this.fileHeader;
    }

    public BitmapInfoHeader getInfoHeader(){
        return this.infoHeader;
    }

    public boolean IsValid(){
        return this.valid;
    }

    public BitmapData getBitmapData(){
        return this.data;
    }

    private void checkMetaData(){
        this.fileHeader = new BitmapFileHeader(this.rawData);
        this.infoHeader = new BitmapInfoHeader(this.rawData);
        this.valid = this.fileHeader.isValid()&&this.infoHeader.isValid();
    }

    private void setBitmapData(){
        this.data = new BitmapData(this);
    }
}
