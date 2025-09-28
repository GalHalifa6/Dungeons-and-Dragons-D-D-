public class Trap extends Enemy{
    private int visibility_time;
    private int invisibility_time;
    private int tick_count;
    private char original_tile;
    private boolean visible;

    public Trap(char tile, int x, int y, String name, Integer health_pool, Integer attack_points, Integer defense_points, Integer experience_value, int visibility_time, int invisibility_time) {
        super(tile, x, y, name, health_pool, attack_points, defense_points, experience_value);
        this.visible = true;
        this.tick_count = 0;
        this.visibility_time = visibility_time;
        this.invisibility_time = invisibility_time;
        this.original_tile = tile;
    }

    public void setVisible(boolean bool){
        visible = bool;
    }



    @Override
    public void on_GameTick() {
        this.visible = this.tick_count < this.visibility_time;////visible or not
        if (this.visible) {
            this.setChar(this.original_tile);
        }
        else {
            this.setChar('.');
        }

        if (this.tick_count == this.visibility_time + this.invisibility_time) {///tick_count
            this.tick_count = 0;
        }
        else {
            this.tick_count = tick_count + 1;
        }

        if (range(player) < 2) {///attack
            this.interact(player);
        }
    }

    public void interact(Tile tile) {

    }

    public void interact(Unit unit) {
        unit.interact(this);
    }



    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }


}
