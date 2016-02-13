package cretion.data;

public class TileData {
    private String filename;
    private int x;
    private int y;
    private int sourceX;
    private int sourceY;
    private boolean hasCollision;

    public TileData() {
        filename = "";
        x = 0;
        y = 0;
        sourceX = 0;
        sourceY = 0;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSourceX() {
        return sourceX;
    }

    public void setSourceX(int sourceX) {
        this.sourceX = sourceX;
    }

    public int getSourceY() {
        return sourceY;
    }

    public void setSourceY(int sourceY) {
        this.sourceY = sourceY;
    }

    public void setHasCollision(boolean _hasCollision) {
        this.hasCollision = _hasCollision;
    }

    public boolean getHasCollision() {
        return hasCollision;
    }
}
