public class Empty extends Tile{
    public Empty(int x, int y) {
        super('.', x, y);
    }

    public void interact(Tile tile) {}

    public void interact(Unit unit){
        this.swapTiles(unit);
    }

    public void interact(Empty empty) {}

    public void interact(Wall wall) {}
}
