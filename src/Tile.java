public abstract class Tile implements Visited, Visitor {

    private char tile;
    private int x;
    private int y;
    protected static Board board;
    protected static Manager manager;

    public Tile(char tile, int x, int y) {
        this.tile = tile;
        this.x = x;
        this.y = y;
        manager.addToTiles(this);
        board.addToBoard(this);
    }

    public void swapTiles(Tile tile){
        board.swapTiles(this, tile);
        int x1 = this.getX();
        int y1 = this.getY();
        int x2 = tile.getX();
        int y2 = tile.getY();
        this.setX(x2);
        this.setY(y2);
        tile.setX(x1);
        tile.setY(y1);

    }

    public double range(Tile tile) {
        int x2 = tile.getX();
        int y2 = tile.getY();
        return Math.sqrt(Math.pow(x2 - x,2) + Math.pow(y2 - y,2));
    }

    public void setChar(char tile) {
        this.tile = tile;
    }
    public char getChar() {
        return this.tile;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x1) {
        this.x = x1;
    }

    public void setY(int y1) {
        this.y = y1;
    }

    public String toString() {
        return "" + tile;
    }

    public void accept(Visitor visitor){
        visitor.interact(this);
    }

    public void setTile(char tile) {
        this.tile = tile;
    }
}
