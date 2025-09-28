import java.util.Random;

public class Monster extends Enemy {

    private Integer vision_range;

    public Monster(char tile, int x, int y, String name, Integer health_pool, Integer attack_points, Integer defense_points, Integer vision_range, Integer experience_value) {
        super(tile, x, y, name, health_pool, attack_points, defense_points, experience_value);
        this.vision_range = vision_range;
    }

    public void on_GameTick() {
        move();
    }

    public void move() {
        if (this.range(player) < (double) this.vision_range) {
            int dx = this.getX() - player.getX();
            int dy = this.getY() - player.getY();
            Tile tile;
            if (Math.abs(dy) > Math.abs(dx)) {
                if (dy > 0) {
                    tile = board.getTile(getX(), getY() - 1); // move left
                } else {
                    tile = board.getTile(getX(), getY() + 1); //move right
                }
                tile.accept(this);
            } else {
                if (dx > 0) {
                    tile = board.getTile(getX() - 1, getY());  //move up
                } else {
                    tile = board.getTile(getX() + 1, getY());  // move down
                }
                tile.accept(this);
            }
        }
        else
        {
            Tile[] arr = {board.getTile(getX(), getY() - 1), board.getTile(getX(), getY() + 1), board.getTile(getX() - 1, getY()), board.getTile(getX() + 1, getY()), board.getTile(getX(), getY())};
            Random r = new Random();
            int random_number = r.nextInt(5);
            Tile tile1 = arr[random_number];
            tile1.accept(this);
        }
    }

    public Integer getVision_range() {
        return vision_range;
    }

    public void interact(Tile tile) {
        tile.interact(this);
    }

    public void interact(Unit unit) {
        unit.interact(this);
    }

    public void interact(Enemy enemy){}

}
