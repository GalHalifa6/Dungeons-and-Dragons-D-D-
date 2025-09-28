public interface Visitor {
    public void interact(Tile tile);
    public void interact(Unit unit);
    public void interact(Empty empty);
    public void interact(Wall wall);
}