public class Wall extends Tile{
    public Wall (int x, int y){
        super('#', x, y);
    }

    public void interact(Tile tile){
    }

    public void interact(Unit unit) {
    }

    public void interact(Empty empty) {

    }

    public void interact(Wall wall) {

    }
}
