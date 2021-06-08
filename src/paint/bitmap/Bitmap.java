package paint.bitmap;

public class Bitmap {

    final byte[] bitmap;

    private boolean valid;
    private BitmapFileHeader fileHeader;
    private BitmapInfoHeader infoHeader;

    public Bitmap(byte[] bitmap){
        this.bitmap=bitmap;
        this.checkMetaData();
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

    public int getDataOffset(){
        return this.fileHeader.getDataOffset();
    }

    private void checkMetaData(){
        this.fileHeader = new BitmapFileHeader(this.bitmap);
        this.infoHeader = new BitmapInfoHeader(this.bitmap);
        this.valid = this.fileHeader.isValid()&&this.infoHeader.isValid();
    }
}
